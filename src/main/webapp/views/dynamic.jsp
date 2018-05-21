<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
    <meta http-equiv="expires" content="0"/>
    <meta charset="utf-8">
    <link rel="shortcut icon" href="./images/favicon.ico" type="image/x-icon" />
    <title>${title}</title>
    <!-- 引入 echarts.js -->
    <script type="text/javascript" src="<%=basePath%>/js/echarts.js"></script>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <!--<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>-->

    <style type="text/css">
        body{
            margin: 0;
            background-color:#FFFFFF;
        }

        #top{
            width: 100%;
            height: 20%;
            text-align: center;
            #background-color: #21202D;
        }

        #title{
            color: black;
            font-size: 1.5em;
        }

        #nowPrice{
            color: black;
            font-size: 1.5em;
        }

        #unit,#changePoint,#changeRate,#time{
            color: black;
            font-size: 0.6em;
        }

        #main{
            height: 80%;
            width:100%;
            #top: 10em;
        }
    </style>
</head>
<body>
<div id="top">
    <br/>
    <div id="title">
        ${title} &nbsp;&nbsp;
        <span id="time" style="font-size:14px;"></span>
    </div>
    <br/>
    <div>
        <span id="nowPrice"></span>&nbsp;
        <span id="unit"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <span id="changePoint"></span>&nbsp;
        <span id="changeRate"></span>
    </div>
</div>
<div id="main"></div>
<script type="text/javascript">
    //alert("path:"+"<%=basePath%>");
    <%--alert("name:"+"${username}");--%>

    //时间
    setInterval(function(){
        document.getElementById("time").innerHTML= formatAllDate();
    },1000);

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    var time = [];
    var value = [];
    var flag = 0;
    var minPrice = 10000000;
    var maxPrice = 0;
    var minPoint;
    var maxPoint;
    function fetchData(cb) {
        if(flag == 0){
            $.ajax({
                type: "GET",  //请求方式
                url: "<%=basePath%>"+"${type}"+"/getLatestData",  //请求路径：页面/方法名字
                data: null,     //参数
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                beforeSend: function (XMLHttpRequest) {
                    //alert("before");
                },
                success: function (result) {  //成功
                    for(var i=0; i<result.length; i++){
                        time.push(formatDate(new Date(result[i]['time'])));
                        value.push(result[i]['nowPrice']);
                        if(i == (result.length-1)){
                            flag = result[i]['id'];
                            document.getElementById("nowPrice").innerHTML= result[i]['nowPrice'];
                            document.getElementById("unit").innerHTML= "${unit}";
                            document.getElementById("changePoint").innerHTML= result[i]['changePoint'];
                            document.getElementById("changeRate").innerHTML= result[i]['changeRate'];
                        }
                        if(minPrice > result[i]['nowPrice']){
                            minPrice = result[i]['nowPrice'];
                            minPoint = result[i]['changeRate'];
                        }
                        if(maxPrice < result[i]['nowPrice']){
                            maxPrice = result[i]['nowPrice'];
                            maxPoint = result[i]['changeRate'];
                        }
                    }
                    // alert("value:"+value);
                    cb({
                        categories: time,
                        data: value
                    });

                },
                error: function (obj, msg, e) {   //异常
                    //alert("OH,NO");
                }
            });
        }else if(flag != 0){
            $.ajax({
                type: "GET",  //请求方式
                url: "<%=basePath%>"+"${type}"+"/getNowData",  //请求路径：页面/方法名字
                data: null,     //参数
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                beforeSend: function (XMLHttpRequest) {
                    //alert("before");
                },
                success: function (result) {  //成功
                    document.getElementById("nowPrice").innerHTML= result.nowPrice;
                    document.getElementById("changePoint").innerHTML= result.changePoint;
                    document.getElementById("changeRate").innerHTML= result.changeRate;

                    time.push(formatDate(new Date(result.time)));
                    value.push(result.nowPrice);
                    if(minPrice > result.nowPrice) {
                        minPrice = result.nowPrice;
                        minPoint = result.changeRate;
                    }
                    if(maxPrice < result.nowPrice) {
                        maxPrice = result.nowPrice;
                        maxPoint = result.changeRate;
                    }
                    cb({
                        categories: time,
                        data: value
                    });

                },
                error: function (obj, msg, e) {   //异常
                    //alert("OH,NO");
                }
            });
        }

    }

    // 初始 option
    option = {
        backgroundColor: '#FFFFFF',
        title: {
            text: "",
            color: '#FFFFFF'
        },
        tooltip: {
            show: true,
            trigger: 'axis',
            //triggerOn: 'none',
            alwaysShowContent: false,
            axisPointer: {
                animation: true
            }
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        legend: {
            // data:['价格']
        },
        calculable : false, //是否启用拖拽重新计算
        xAxis: {
            data: [],
            min: '06:00:00',
            max: '23:59:59',
            splitNumber: 50,
            //minInterval: 3600 * 1000,
            splitLine: {
                show: false
            },
            axisLine: { lineStyle: { color: '#8392A5' } }
        },
        yAxis: [{
            name: '价格',
            boundaryGap: [0, '100%'],
            position: 'right',
            smooth: false,
            min: 0,
            max: 100000,
            splitNumber: 5,
            axisLabel: {
                textStyle: {
                    color: '#5c6076'
                }
            },
            splitLine: {
                show: true,
                lineStyle: { color: '#61202F' }
            },
            axisLine: {
                show: false,
                lineStyle: { color: '#8392A5' }
            },
            axisPointer: {
                show: false,
                type: 'line',
                snap: false,
                value: 1315
            }

        },{
            boundaryGap: [0, '100%'],
            position: 'left',
            splitLine: {
                show: false,
                lineStyle: { color: '#61202F' }
            },
            axisLine: {
                show: false,
                lineStyle: { color: '#8392A5' }
            }
        }],
        series: [{
            name: '',
            showSymbol: false,
            hoverAnimation: false,
            type: 'line',
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            },
            data: []
        }]
    };

    myChart.showLoading();

    setInterval(function () {
        fetchData(function (cb) {
            myChart.hideLoading();
            myChart.setOption({
                xAxis: {
                    data: cb.categories
                },
                yAxis: [{
                    min: minPrice,
                    max: maxPrice,
                    axisPointer: {
                        value: cb.data,
                        z: cb.data
                    }
                },{
                    min: minPoint,
                    max: maxPoint
                }],
                series: [{
                    // 根据名字对应到相应的系列
                    // name: '价格',
                    data: cb.data
                }]
            });
        });
    },1000);


    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }

    function formatDate(now) {
        var hour=now.getHours();
        if(hour.toString().length == 1){
            hour = "0"+hour;
        }
        var minute=now.getMinutes();
        if(minute.toString().length == 1){
            minute = "0"+minute;
        }
        var second=now.getSeconds();
        if(second.toString().length == 1){
            second = "0"+second;
        }
        return hour+":"+minute+":"+second;
    }

    function formatAllDate() {
        var now = new Date();
        var year=now.getFullYear();
        if(year.toString().length == 1){
            year = "0"+year;
        }
        var month=now.getMonth()+1;
        if(month.toString().length == 1){
            month = "0"+month;
        }
        var date=now.getDate();
        if(date.toString().length == 1){
            date = "0"+date;
        }
        var hour=now.getHours();
        if(hour.toString().length == 1){
            hour = "0"+hour;
        }
        var minute=now.getMinutes();
        if(minute.toString().length == 1){
            minute = "0"+minute;
        }
        var second=now.getSeconds();
        if(second.toString().length == 1){
            second = "0"+second;
        }
        return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
    }
</script>
</body>
</html>
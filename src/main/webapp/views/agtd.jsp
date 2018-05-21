<%--
  Created by IntelliJ IDEA.
  User: 28113
  Date: 2018/5/3
  Time: 14:21
  To change this template use File | Settings | File Templates.
--%>
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
    <title>白银T+D</title>
    <!-- 引入 echarts.js -->
    <script type="text/javascript" src="<%=basePath%>/js/echarts.js"></script>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <!--<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>-->
</head>
<body style="height: 100%; margin: 0">
<div id="main" style="height: 100%"></div>
<script type="text/javascript">
    //alert("path:"+"<%=basePath%>");
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
        // alert("flag:"+flag);
        if(flag == 0){
            $.ajax({
                type: "GET",  //请求方式
                url: "<%=basePath%>"+"agtd/getLatestAgTDData",  //请求路径：页面/方法名字
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
                url: "<%=basePath%>"+"agtd/getNowData",  //请求路径：页面/方法名字
                data: null,     //参数
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                beforeSend: function (XMLHttpRequest) {
                    //alert("before");
                },
                success: function (result) {  //成功
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
        backgroundColor: '#21202D',
        title: {
            text: '白银T+D',
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
            show : true,
            orient: 'horizontal',      // 布局方式，默认为水平布局，可选为：
                                       // 'horizontal' ¦ 'vertical'
            x: 'right',                // 水平安放位置，默认为全图右对齐，可选为：
                                       // 'center' ¦ 'left' ¦ 'right'
                                       // ¦ {number}（x坐标，单位px）
            y: 'top',                  // 垂直安放位置，默认为全图顶端，可选为：
                                       // 'top' ¦ 'bottom' ¦ 'center'
                                       // ¦ {number}（y坐标，单位px）
            color : ['#1e90ff','#22bb22','#4b0082','#d2691e'],
            backgroundColor: 'rgba(0,0,0,0)', // 工具箱背景颜色
            borderColor: '#ccc',       // 工具箱边框颜色
            borderWidth: 0,            // 工具箱边框线宽，单位px，默认为0（无边框）
            padding: 5,                // 工具箱内边距，单位px，默认各方向内边距为5，
            showTitle: true,
            feature : {
                mark : {
                    show : true,
                    title : {
                        mark : '辅助线-开关',
                        markUndo : '辅助线-删除',
                        markClear : '辅助线-清空'
                    },
                    lineStyle : {
                        width : 1,
                        color : '#1e90ff',
                        type : 'dashed'
                    }
                },
                dataZoom : {
                    show : true,
                    title : {
                        dataZoom : '区域缩放',
                        dataZoomReset : '区域缩放-后退'
                    }
                },
                dataView : {
                    show : true,
                    title : '数据视图',
                    readOnly: true,
                    lang : ['数据视图', '关闭', '刷新'],
                    optionToContent: function(opt) {
                        var axisData = opt.xAxis[0].data;
                        var series = opt.series;
                        var table = '<table style="width:100%;text-align:center"><tbody><tr>'
                            + '<td>时间</td>'
                            + '<td>' + series[0].name + '</td>'
                            + '<td>' + series[1].name + '</td>'
                            + '</tr>';
                        for (var i = 0, l = axisData.length; i < l; i++) {
                            table += '<tr>'
                                + '<td>' + axisData[i] + '</td>'
                                + '<td>' + series[0].data[i] + '</td>'
                                + '<td>' + series[1].data[i] + '</td>'
                                + '</tr>';
                        }
                        table += '</tbody></table>';
                        return table;
                    }
                },
                magicType: {
                    show : true,
                    title : {
                        line : '动态类型切换-折线图',
                        bar : '动态类型切换-柱形图',
                        stack : '动态类型切换-堆积',
                        tiled : '动态类型切换-平铺'
                    },
                    type : ['line', 'bar', 'stack', 'tiled']
                },
                restore : {
                    show : true,
                    title : '还原',
                    color : 'black'
                },
                saveAsImage : {
                    show : true,
                    title : '保存为图片',
                    type : 'jpeg',
                    lang : ['点击本地保存']
                },
                myTool : {
                    show : true,
                    title : '自定义扩展方法',
                    icon : 'image://../asset/ico/favicon.png',
                    onclick : function (){
                        alert('myToolHandler')
                    }
                }
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
            splitNumber: 8,
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
            name: '实时价格',
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
        var year=now.getFullYear();
        var month=now.getMonth()+1;
        var date=now.getDate();
        var hour=now.getHours();
        var minute=now.getMinutes();
        var second=now.getSeconds();
        return hour+":"+minute+":"+second;
    }
</script>
</body>
</html>
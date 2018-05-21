<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html style="height: 100%">
<head>
    <meta charset="utf-8">
</head>
<body style="height: 100%; margin: 0">
<div id="container" style="height: 100%"></div>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-gl/echarts-gl.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts-stat/ecStat.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/dataTool.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/china.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/map/js/world.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=ZUONbpqGBsYGXNIYHicvbAbM"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/extension/bmap.min.js"></script>
<script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/simplex.js"></script>
<script type="text/javascript">
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    function randomData() {
        now = new Date();
        value = value + Math.random() * 21 - 10;
        return {
            name: now.toString(),
            value: [
                [now.getFullYear(), now.getMonth(), now.getDate()].join('/'),
                Math.round(value)
            ]
        }
    }

    var data = [];
    var now = new Date();
    var oneDay = 24 * 3600 * 1000;
    var value = Math.random() * 1000;
    for (var i = 0; i < 1000; i++) {
        data.push(randomData());
    }

    $.ajax({
        type: "GET",  //请求方式
        url: "http://localhost:8080/AuAg/au/getLatestAuData",  //请求路径：页面/方法名字
        data: null,     //参数
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        beforeSend: function (XMLHttpRequest) {
            //alert("before");
        },
        success: function (result) {  //成功
            for(var i=0; i<result.length; i++){
                data.push(
                    {
                        name:formatDate(new Date(result[i]['time'])),
                        value:
                            [
                                formatDate(new Date(result[i]['time'])),
                                result[i]['nowPrice']
                            ]
                    })
            }
        },
        error: function (obj, msg, e) {   //异常
            //alert("OH,NO");
        }
    });

    option = {
        backgroundColor: '#21202D',
        title: {
            text: '现货黄金预正式002',
            color: '#fff'
        },
        tooltip: {
            trigger: 'axis',
            formatter: function (params) {
                params = params[0];
                var date = new Date(params.name);
                return date.getFullYear()+"-"+date.getMonth()+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+ ' : ' + params.value[1];
            },
            axisPointer: {
                animation: false
            }
        },
        xAxis: {
            type: 'time',
            splitLine: {
                show: false
            },
            axisLine: { lineStyle: { color: '#8392A5' } }
        },
        yAxis: {
            type: 'value',
            boundaryGap: [0, '100%'],
            axisLine: { lineStyle: { color: '#8392A5' } },
            splitLine: {
                show: false
            }
        },
        series: [{
            name: '模拟数据',
            type: 'line',
            showSymbol: false,
            hoverAnimation: true,
            data: data
        }]
    };
    setInterval(function () {

        //for (var i = 0; i < 5; i++) {
            data.shift();
            //data.push(randomData());
            $.ajax({
                type: "GET",  //请求方式
                url: "http://localhost:8080/AuAg/au/getNowData",  //请求路径：页面/方法名字
                data: null,     //参数
                dataType: "text",
                contentType: "application/json; charset=utf-8",
                beforeSend: function (XMLHttpRequest) {
                    //alert("before");
                },
                success: function (result) {  //成功
                    var parsedJson = jQuery.parseJSON(result);
                    //dada = parsedJson.nowPrice;
                    //now = new Date(+now + oneDay);
                    data.push(
                        {
                            name:parsedJson.time,
                            value:[
                                parsedJson.time,
                                parseFloat(parsedJson.nowPrice.toString())
                            ]
                        })
                },
                error: function (obj, msg, e) {   //异常
                    //alert("OH,NO");
                }
            });
       // }

        myChart.setOption({
            series: [{
                data: data
            }]
        });

    }, 1000);;
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
        return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
    }


</script>
</body>
</html>
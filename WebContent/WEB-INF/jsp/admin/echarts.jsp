<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<title>销售榜单</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link href="${ pageContext.request.contextPath }/css/managestyle.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="${ pageContext.request.contextPath }/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="${ pageContext.request.contextPath }/js/echarts.min.js"></script>
<script type="text/javascript">
	$(function(){
		var myChart = echarts.init(document.getElementById('main'));
		$(".zft").click(function(){
			myChart.clear();
			$.get("${ pageContext.request.contextPath }/admin/getdata",function(data){
				myChart.clear();
				var titles = new Array();
				var numbers= new Array();
				for (var i = 0; i < data.length; i++) {
					titles[i]=data[i].name;
					numbers[i]=data[i].soldnum;
					}
				var option = {
						 color: ['#3398DB'],
						    tooltip : {
						        trigger: 'axis',
						        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
						            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
						        }
						    },
						title: {
			                text: '销售图表',
			                subtext:'（只显示销量大于1000的）'
			            },
			            tooltip: {},
			            legend: {
			                data:['销量']
			            },
			            xAxis : [
			                {
			                    type : 'category',
			                    data : titles
			                }
			            ],
			            yAxis: {
			            	 type : 'value'
			            },
			            series: [{
			                name: '销量',
			                type: 'bar',
			                data: numbers
			            }]
			        };

			        myChart.setOption(option);
			});
		});
		$(".round").click(function(){
			myChart.clear();
			$.get("${ pageContext.request.contextPath }/admin/getdata",function(data){
				myChart.clear();
				var titles = new Array();
				var numbers= new Array();
				for (var i = 0; i < data.length; i++) {
					titles[i]=data[i].name;
					numbers[i]=data[i].soldnum;
					}
			var option = {
			        backgroundColor: '#001848',
			        xAxis: {
			            type: 'category',
			            axisTick:{
			                show:false,
			            },
			            boundaryGap: false,
			            axisTick:{
			                show:false,
			            },
			            axisLabel:{
			                color:'#fff'
			            },
			            axisLine:{
			                lineStyle:{
			                    color:'rgba(12,102,173,.5)',
			                    width:2,
			                }
			            },
			        },
			        yAxis: {
			            type: 'value',
			            axisTick:{
			                show:false,//不显示刻度线
			            },
			            axisLabel:{
			                color:'#fff'  //y轴上的字体颜色
			            },
			            axisLine:{
			                lineStyle:{
			                    width:2,
			                    color:'rgba(12,102,173,.5)',//y轴的轴线的宽度和颜色
			                }
			            },
			            splitLine: {
			                show: false       
			            }
			        },
			        series: [
			            {
			                type:'line',
			                symbol: 'none',
			                smooth:true,
			                itemStyle: {
			                    normal: {
			                        color: '#09b0f5',
			                    }
			                },
			                areaStyle: {
			                    normal: {
			                        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
			                            offset: 0,
			                            color: '#09b0f5'
			                        }, {
			                            offset: 1,
			                            color: 'rgba(12,102,173,.5)'
			                        }])
			                    }
			                },
			            }
			        ]
			    };
			option.xAxis.data =titles ;
			option.series[0].data =numbers;
			myChart.setOption(option);
			});
		});
		});
</script>
</head>
<body>
	<div class="top">
		<h1>&nbsp;&nbsp;EasyMall商城管理后台</h1>
	</div>
	<div class="content">
		<div class="left">			
			<%@ include file = "_left.jsp" %>
		</div>
	<div class="right">	
	<div>
		<a href="javascript:void(0)" class="zft">直方图</a>&nbsp;|&nbsp;
		<a href="javascript:void(0)" class="round">图表</a>
	</div>
	<div id="main" style="width: 600px;height:400px;"></div>
	</div>
	</div>
</body>
</html>
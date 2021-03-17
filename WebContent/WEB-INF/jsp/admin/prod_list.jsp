<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>商品管理</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style type="text/css">
	body{
		font-family: "微软雅黑";
		background-color: #EDEDED;
	}
	h2{
		text-align: center;
	}
	table{ 
		margin: 0 auto; 
		/* width: 96%; */
		text-align: center;
		border-collapse:collapse;
	}
	td, th{ padding: 7px;}
	th{
		background-color: #DCDCDC;
	}
	th.ths{
		width: 100px;
	} 
	input.pnum{
		width:80px;
		height:25px;
		font-size: 18px;
		text-align:center;
	}
	a{
		text-decoration: none;
	}
	
</style>
<!--引入jquery的js库-->
<script type="text/javascript" src="${ pageContext.request.contextPath }/js/jquery-1.4.2.js"></script>
<link href="${ pageContext.request.contextPath }/css/managestyle.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	$(function(){
		$(".del").click(function(){
			//1.获取商品的id
			var id = $(this).attr("id");
						
			//2.利用ajax请求删除该商品(后台会删除该商品)
			$.post("${ pageContext.request.contextPath }/admin/delprod",{"id":id});				
					
			//3.删除当前页面中的商品
			$(this).parents("tr").remove();
			
		});	
		$('.pnum').each(function(){
			$(this).bind('input porpertychange',function(){
				$.post("${ pageContext.request.contextPath }/admin/updatepnum",{"id":$(this).attr("id"),"pnum":$(this).val()});
			});
		});
		$(".upd").click(function(){
			var id = $(this).attr("id");
			$.post("${ pageContext.request.contextPath }/admin/showupprod",{"id":id},function(){
				window.location="${pageContext.request.contextPath}/admin/showupprod?id="+id;
			});		
		});
	})
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
	<fieldset>
	<h2>商品管理</h2>
	<table border="1">
		<tr>
			<th>商品图片</th>
			<th width="200px">商品ID</th>
			<th class="ths">商品名称</th>
			<th class="ths">商品种类</th>
			<th class="ths">商品单价</th>
			<th class="ths">库存数量</th>
			<th>描述信息</th>
			<th width="50px">操 作</th>
		</tr>

		<!-- 模版数据 -->
		<c:forEach items="${products}" var="prod">
			<tr>
				<td>
					<img width="120px" height="120px" src="${pageContext.request.contextPath}${prod.imgurl}" alt="" >
				</td>
				<td>${prod.id}</td>
				<td>${prod.name}</td>
				<td>${prod.category}</td>
				<td>${prod.price}</td>
				<td>
					<input type="text" id="${prod.id}" class="pnum" value="${prod.pnum}"/>
				</td>
				<td>${prod.description}</td>
				<td><a class="del" href="javascript:void(0)" id="${prod.id}">删 除</a>
				——
				<a class="upd" href="javascript:void(0)" id="${prod.id}">修 改</a></td>
			</tr>
		</c:forEach>
	</table>
	</fieldset>
	</div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
<title>销售榜单</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link href="${ pageContext.request.contextPath }/css/managestyle.css" rel="stylesheet" type="text/css">
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
</style>
<script type="text/javascript" src="${ pageContext.request.contextPath }/js/jquery-1.4.2.js"></script>
<script type="text/javascript">
$(function() {
	$(".export").click(function(){
		window.location.href="${ pageContext.request.contextPath }/admin/export";
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
	<div><a href="javascript:void(0)" class="export">导出成excel文件</a></div>
	<table border="1">
		<tr>
			<th>商品图片</th>
			<th width="200px">商品ID</th>
			<th class="ths">商品名称</th>
			<th class="ths">商品种类</th>
			<th class="ths">商品单价</th>
			<th class="ths">销售数量</th>
		</tr>
		<c:forEach items="${products}" var="p">
		<tr>
			<td>
				<img width="120px" height="120px" src="${pageContext.request.contextPath }${p.imgurl}" alt="" >
			</td>
			<td>${p.id}</td>
			<td>${p.name }</td>
			<td>${p.category}</td>
			<td>${p.price }</td>
			<td>${p.soldnum}</td>
		</tr>
		</c:forEach>
	</table>
	</div>
	</div>
</body>
</html>
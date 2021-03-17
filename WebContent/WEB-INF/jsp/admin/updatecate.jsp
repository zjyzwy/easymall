<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
	<head>
		<title>添加商品类别</title>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link href="${ pageContext.request.contextPath }/css/managestyle.css" rel="stylesheet" type="text/css">
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
	<div class="addprod">
	
	<form action="${ pageContext.request.contextPath }/admin/upcate" method="POST">			
			<table>
			<tr>
					<td colspan="2" style="color:red;text-align:center;"></span>${ msg }</td>
				</tr>
				<tr>
					<td>商品ID：</td>
					<td><input type="text" name="id" value="${category.id }" readonly/></td>
				</tr>
				<tr>	
					<td>商品类别：</td>
					<td><input type="text" name="name" value="${category.name }"/></td>
				</tr>
				<tr>
					<td>商品描述：</td>
					<td><input type="text" name="description" value="${category.description }"/></td>
				</tr>
				<tr>
					<td colspan="2" style="text-align:center">
						<input type="submit" value="提交"/>
					</td>	
				</tr>
			</table>
		</form>		
	</div>
	</div><!-- right结束 -->
	</div><!-- content结束 -->		
	
	
	</body>
</html>
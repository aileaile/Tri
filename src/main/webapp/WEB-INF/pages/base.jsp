<%--
  Created by IntelliJ IDEA.
  User: LL
  Date: 2017/9/24
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<link rel="stylesheet" href="<%=basePath%>/staticfile/css/bootstrap.css">
<link rel="stylesheet" href="<%=basePath%>/staticfile/css/bootstrapValidator.min.css">
<script src="<%=basePath%>/staticfile/js/jquery-3.1.1.min.js"></script>
<script src="<%=basePath%>/staticfile/js/bootstrap.min.js"></script>
<script src="<%=basePath%>/staticfile/js/bootstrapValidator.min.js"></script>
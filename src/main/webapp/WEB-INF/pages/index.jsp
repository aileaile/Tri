<%--
subbut  Created by IntelliJ IDEA.
  User: LL
  Date: 2017/9/17
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        body{
            font-family:"微软雅黑" !important;
        }
    </style>
    <title>登陆LL的游戏室</title>
    <%@include file="base.jsp"%>
</head>
<body>
    你好，旅行者！
    <br><br><br>
        <form id="buttons" action="main"  method="post" class="" onSubmit="return sub()">
            <ui line-height="100px">
                请输入用户名：<input name="userName" value="" id="userName" size="7%" />
                <span id="errMsg" style="color:indianred;font-size:11px;font-family:微软雅黑">${errMsg}</span>
            </ui>
            <br><br>
            <ui>
                请输入房间号：<input name="roomNum" value="1" id="roomNum" size="7%"/>(1-10)
            </ui>
            <br><br>
            <div>
                <button id="subbut" type="submit" class="btn btn-default">确定！</button>
            </div>
        </form>
    <br/>
    <p style="text-align: left;font-size:13px;font-family: Consolas;color:darkgray">[beta 0.0.2]</p>
</body>

<script type="text/javascript">
    function sub(){
        var loginCheck = document.getElementById('userName');
        if(loginCheck.value.replace(/\s+/g, "").length ==0){
            document.getElementById('errMsg').innerText="用户名不能为空。";
            return false;
        }
        return true;
    }
</script>
</html>

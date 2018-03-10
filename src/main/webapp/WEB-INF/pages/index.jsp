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
    <title>登陆LL的游戏室</title>
    <%@include file="base.jsp"%>
</head>
<body>
    你好，旅行者！
        <form id="buttons" action="chat.action"  method="post" class="">
            <ui>
                请输入用户名：<input name="userName" value="" id="userName" size="5%"/>
            </ui>
            <ui>
                请输入房间号(1-10)：<input name="roomNum" value="1" id="roomNum" size="5%"/>
            </ui>
            <div>
                <button id="subbut" type="submit" class="btn btn-default" onclick="sub()">确定！</button>
            </div>
        </form>
</body>

<script type="text/javascript">
    function sub(){
        submit;
    }
</script>
</html>

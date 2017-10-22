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
    <title>Title</title>
    <%@include file="base.jsp"%>
</head>
<body>
    欢迎！
        <form id="buttons" action="test.do"  method="post" class="">
      <%--  <div class="radio">
            <label>
                <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>小瓶法力药水
            </label>
        </div>
        <div class="radio">
            <label>
                <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">攻击1
            </label>
        </div>--%>
          请输入用户名: <input name="userName" value="" id="222"/>
            <button id="subbut" type="submit" class="btn btn-default" onclick="sub()">确定！</button>
        </form>
</body>

<script type="text/javascript">
    function sub(){
        submit;
    }
</script>
</html>

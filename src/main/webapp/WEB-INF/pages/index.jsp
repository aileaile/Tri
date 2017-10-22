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
    inner index
        <form id="buttons" action="test.do" onsubmit="return false"  class="">
        <div class="radio">
            <label>
                <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>小瓶法力药水
            </label>
        </div>
        <div class="radio">
            <label>
                <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">攻击1
            </label>
        </div>
            <input value="123" id="222">
            <button id="subbut" type="submit" class="btn btn-default" onclick="sub()">确定！</button>
        </form>
</body>

<script type="text/javascript">
    function sub(){
        alert(1)
        $('#222').val(456)
    }
</script>
</html>

<%--
  Created by IntelliJ IDEA.
  User: LL
  Date: 2018/2/23
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

欢迎，${session_user.name}<br/>
<hr/>
</body>
<script>
    var un = '${session_user.name}';
    if (un=='') {
        location.href = "offLine";
    }

</script>
</html>

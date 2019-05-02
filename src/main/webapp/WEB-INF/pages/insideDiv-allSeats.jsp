<%--
  Created by IntelliJ IDEA.
  User: LL
  Date: 2018/3/23
  Time: 11:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<div class="">
    当前房间号：${roomNum}
</div>
<div id="mainDiv" class="hide">
    <div>
        <input type="button"  onclick="sit(this)" class="seatsHide btn btn-default btn-lg" value="位置1"/>
        <input type="button"  onclick="sit(this)" class="seatsHide btn btn-default btn-lg" value="位置2"/>
        <input type="button"  onclick="sit(this)" class="seatsHide btn btn-default btn-lg" value="位置3"/>
        <input type="button"  onclick="sit(this)" class="seatsHide btn btn-default btn-lg" value="位置4"/>
    </div>
    <div>
        <input type="button" class="readyHide" />
        <input type="button" class="readyHide" />
        <input type="button" class="readyHide" />
        <input type="button" class="readyHide" />
    </div>
    <div><br/>
    </div>
    <div>
        <input type="button"  onclick="sit(this)" class="seatsHide btn btn-default btn-lg" value="位置5"/>
        <input type="button"  onclick="sit(this)" class="seatsHide btn btn-default btn-lg" value="位置6"/>
        <input type="button"  onclick="sit(this)" class="seatsHide btn btn-default btn-lg" value="位置7"/>
        <input type="button"  onclick="sit(this)" class="seatsHide btn btn-default btn-lg" value="位置8"/>
    </div>
    <div>
        <input type="button" class="readyHide" />
        <input type="button" class="readyHide" />
        <input type="button" class="readyHide" />
        <input type="button" class="readyHide" />
    </div>
    <div>----------</div>
    <div><button onclick="ready()" class="btn btn-default">准备</button>
        <button onclick="unReady()"class="btn btn-default">取消准备</button>
    </div>
</div>
<div id="errTips" class="hide">
    这个房间并不存在。
</div>
</body>
</html>

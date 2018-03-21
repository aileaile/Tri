<%--
  Created by IntelliJ IDEA.
  User: LL
  Date: 2017/9/24
  Time: 21:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <%@include file="base.jsp"%>
</head>
<body>

<hr/>
<div class="input-append">
    <input  id="text" type="text"/>
    <button type="button" class="btn btn-default" onclick="send()">发送消息</button>
    <button type="button" class="btn btn-default" onclick="lookup()">查看在线人员</button>
</div>

<%--<button onclick="closeWebSocket()">关闭WebSocket连接</button>
<hr/>--%>
<div id="message"></div>
</body>

<script type="text/javascript">
    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        //线上
        //websocket = new WebSocket("ws://120.55.53.110:8080/Triangle/lobbyWS");
        //局域网
       //websocket = new WebSocket("ws://192.168.1.8:8077/Triangle/lobbyWS");
        //本机
       websocket = new WebSocket("ws://localhost:8077/Triangle/lobbyWS");
 }
 else {
     alert('当前浏览器版本太低，无法加载页面，请使用新版本或其他浏览器。')
 }

 //连接发生错误的回调方法
 websocket.onerror = function () {
     setMessageInnerHTML("WebSocket连接发生错误");
 };

 //连接成功建立的回调方法
 websocket.onopen = function () {
     debugger
     setMessageInnerHTML("成功连接至服务器。");
     //每9000ms发送心跳一次，牛逼的写法，防止js进程非空闲状态导致时间间隔不准确
     sendHeartBeat();
 }
 function sendHeartBeat(){
     jQuery.ajax({
         type: "post",
         url: "<%=request.getContextPath()%>/lobby/heartBeat",
         async:true,//默认异步
         data : {},
         success: function(){
         },
         error: function(){
             document.getElementById('message').innerHTML =
                         '断开了与服务器的连接，请重新打开网页。' +'<br/>' +
                         document.getElementById('message').innerHTML ;
         }
     });
     setTimeout("sendHeartBeat()",9000);
 }

 //接收到消息的回调方法
 websocket.onmessage = function (MessageEvent ) {
     var json = $.parseJSON(MessageEvent.data);
     if(json.msgType=="seatStatus"){
         if(json.roomNum==roomNum) {
             var users = json.detail;
             var pre = 't' + roomNum + 's';
             for (var i = 1; i <= 8; i++) {
                 document.getElementById(pre + i).value = "位置" + i;
                 readys[i -1 ].value="";
             }
             debugger;
             for (var i = 0; i < users.length; i++) {
                 document.getElementById(pre + users[i].position).value = users[i].userName;
                 if(users[i].ready){
                     readys[users[i].position-1].value = "已准备"
                 }else{

                 }
             }
         }
     }else if(json.msgType=="broadcast"){
         setMessageInnerHTML(json.msg);
     }
 }

 //连接关闭的回调方法
 websocket.onclose = function () {
     setMessageInnerHTML("断开与服务器的连接。");
 }

 //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
 window.onbeforeunload = function () {
     closeWebSocket();
 }

 //将消息显示在网页上
 function setMessageInnerHTML(innerHTML) {
     document.getElementById('message').innerHTML = innerHTML +'<br/>' +
         document.getElementById('message').innerHTML ;
 }

 //关闭WebSocket连接
 function closeWebSocket() {
     websocket.close();
 }

 //发送消息
 function send() {
     var message = document.getElementById('text').value;
     websocket.send('${userName}'+':'+message);
        //document.getElementById('text').attribute('');
        $('#text').val('');
    }
</script>
</html>

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
   <%-- <button type="button" class="btn btn-default" onclick="lookup()">查看在线人员</button>--%>
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
        websocket = new WebSocket("ws://122.152.201.27:8080/Triangle/lobbyWS");
        //局域网
       //websocket = new WebSocket("ws://192.168.1.8:8077/Triangle/lobbyWS");
        //本机
       //websocket = new WebSocket("ws://localhost:8080/Triangle_war/lobbyWS");
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
     setMessageInnerHTML("成功连接至服务器。");
     //每9000ms发送心跳一次，这种写法防止js进程非空闲状态导致时间间隔不准确
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
             top.location.href = "index.action";
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
     }else if(json.msgType=="gameStart"){
         showGamePanelAndHideSeats();
     }else if(json.msgType=="playerStatus"){
         StartCounting();
         printPlayerInfo(json.playerStatus);
     }else if(json.msgType=="gameStatus"){
         StartCounting();
         updateGameInfo(json.gameStatus);
     }else if(json.msgType=="gameOver"){
         showBackward();
     }else if(json.msgType=="playerMakeDcs"){
         playerMakeDcs(json.playerName);
     }
 }
function playerMakeDcs(playerName) {
    document.getElementById('dcsStatus'+playerName).innerText = "✔";
} 
 
function showBackward() {
    document.getElementById('backwardArea').innerText="游戏结束，单击此处返回房间。";
}

function updateGameInfo(playerStatus) {
    document.getElementById('panel-dcs-value').innerText = "";
    document.getElementsByTagName('tbody')[0].innerHTML = "";
    for(var i = 0;i<playerStatus.length;i++){
        if(playerStatus[i].userName=='${userName}'){
            document.getElementById('panel-hp-value').innerText = playerStatus[i].healthPoint;
            document.getElementById('panel-mp-value').innerText = playerStatus[i].mana;
        }
        document.getElementsByTagName('tbody')[0].innerHTML =
            document.getElementsByTagName('tbody')[0].innerHTML +
            '<tr>\n' +
            '            <tr>\n' +
            '                <td colspan="2" width="30%">'+playerStatus[i].userName+'<span id="dcsStatus'+playerStatus[i].userName+'"></span></td>\n' +
            '                <td rowspan="2" class="decision">'+getShowName(playerStatus[i].decision)+'</td>\n' +
            '            </tr>\n' +
            '            <tr>\n' +
            '                <td width="15%">命：'+playerStatus[i].healthPoint+'</td>\n' +
            '                <td width="15%">气：'+playerStatus[i].mana+'</td>\n' +
            '            </tr>\n' +
            '</tr>'
    }
}

 function printPlayerInfo(playerStatus) {
     document.getElementById('panel-hp-value').innerText = "3";
     document.getElementById('panel-mp-value').innerText = "0";
     document.getElementById('panel-dcs-value').innerText = "";
     document.getElementsByTagName('tbody')[0].innerHTML = "";
     for(var i = 0;i<playerStatus.length;i++){
         document.getElementsByTagName('tbody')[0].innerHTML =
             document.getElementsByTagName('tbody')[0].innerHTML +
             '<tr>\n' +
             '            <tr>\n' +
             '                <td colspan="2" width="30%">'+playerStatus[i].userName+'<span id="dcsStatus'+playerStatus[i].userName+'"></span></td>\n' +
             '                <td rowspan="2" class="decision">'+getShowName(playerStatus[i].decision)+'</td>\n' +
             '            </tr>\n' +
             '            <tr>\n' +
             '                <td width="15%">命：'+playerStatus[i].healthPoint+'</td>\n' +
             '                <td width="15%">气：'+playerStatus[i].mana+'</td>\n' +
             '            </tr>\n' +
             '</tr>'
     }
 }

 function showGamePanelAndHideSeats(){
    $('#allSeats').attr("class","hide");
    $('#game').attr("class","show");
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

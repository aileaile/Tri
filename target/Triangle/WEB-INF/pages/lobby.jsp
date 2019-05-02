<%--
  Created by IntelliJ IDEA.
  User: LL
  Date: 2018/2/23
  Time: 20:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style>
        body{
            font-family:"微软雅黑" !important;
        }
        .hide{
            display: none !important;
        }
        .seatsHide{
            display: none !important;
        }
        .seatsShow{
            width:90px !important;
        }
        .show{

        }
        .readyHide {
            display: none !important;
        }
        .readyShow{
            width:90px !important;
            height:30px ;
        }
        .decision{
            vertical-align:middle !important;
        }
        .decisionBtn{
            width:80px !important;
        }
    </style>
</head>
<body>
<%@include file="title.jsp"%>
    <div id="allSeats">
        <%@include file="insideDiv-allSeats.jsp"%>
    </div>
    <div id="game" class="hide">
        <%@include file="insideDiv-game.jsp"%>
    </div>
<%@include file="lobbyWebSocket.jsp"%>
</body>
<script>

    //防止页面后退(safari无效)
    history.pushState(null, null, document.URL);
    window.addEventListener('popstate', function () {
        history.pushState(null, null, document.URL);
    });

    var roomNum = '${roomNum}';
    var seats = $('.seatsHide');
    var readys =  $('.readyHide');
    $(function (){
        if(!isNaN(roomNum)){
            if (1 <= parseInt(roomNum,10) && parseInt(roomNum,10) <= 10) {
                $('#mainDiv').attr("class", "show");
                var pre = 't' + roomNum + 's';
                for (var i = 1; i <= seats.length; i++) {
                    seats[i - 1].id = pre + i;
                    seats[i - 1].className = "seatsShow btn btn-default btn-lg";
                    readys[i - 1].className = "readyShow btn btn-default "
                }
                setTimeout("firstSit()",50);
            }else {
                $('#errTips').attr("class", "show");
            }
        } else {
            $('#errTips').attr("class", "show");
        }
    });


    //准备
    function ready() {
        jQuery.ajax({
            type: "post",
            url: "<%=request.getContextPath()%>/lobby/ready",
            data : {roomNum:roomNum},
            success: function(obj){
            }
        });
    }

    //准备
    function unReady() {
        jQuery.ajax({
            type: "post",
            url: "<%=request.getContextPath()%>/lobby/unReady",
            data : {roomNum:roomNum},
            success: function(obj){
            }
        });
    }

    //第一次进入房间
    function firstSit(){
        jQuery.ajax({
            type: "post",
            url: "<%=request.getContextPath()%>/lobby/firstSit",
            data : {roomNum:roomNum},
            success: function(obj){
                if(!obj){
                    document.getElementById('message').innerHTML = '当前房间已满。' +'<br/>' +
                        document.getElementById('message').innerHTML ;
                }
            },
            error: function(){
            }
        });
    }

    //[坐下]
    function sit(obj){
        var id = obj.id;
        var room = id.slice(1,2);
        var seat = id.slice(3,4);
        var uName = "${userName}";
        var jsonStr = {"room":room,"seat":seat,"userName":uName};//object类型
        var msg = JSON.stringify(jsonStr);//string类型
        jQuery.ajax({
            type: "post",
            url: "<%=request.getContextPath()%>/lobby/sit",
            /*
                预期[服务器]返回的数据类型 xml html script  json jsonp  text ;
                如果不指定，则服务器根据返回数据类型自行判断
                dataType : 'json',
            */
            async:true,//默认异步
            data : {data:msg},
            //或{'mydata':jsonArrayFinal}
            /*
                要么使用 \ 对json进行手动转译，要么使用JSON.stringify(jsonStr) 将其转换为字符串类型；
                如果不进行转译，使用【2】中的后台接收方法，那么不会报异常，但是获取不到数据。
                data : {mydata:jsonStr},//报空指针异常，传不过去
            */
            contentType:"application/x-www-form-urlencoded;charset=utf-8",//默认值
            success: function(){
            },
            error: function(){
            }
        });
    }

</script>

</html>

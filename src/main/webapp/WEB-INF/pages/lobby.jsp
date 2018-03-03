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
</head>
<body>
<%@include file="title.jsp"%>
    <div>
        <input type="button" id="t1p1" onclick="sit(this)" class="btn btn-default btn-lg" value="位置1"/>
        <input type="button" id="t1p2" onclick="sit(this)" class="btn btn-default btn-lg" value="位置2"/>
        <input type="button" id="t1p3" onclick="sit(this)" class="btn btn-default btn-lg" value="位置3"/>
        <input type="button" id="t1p4" onclick="sit(this)" class="btn btn-default btn-lg" value="位置4"/>
    </div>
    <div>
        <input type="button" id="t1p5" onclick="sit(this)" class="btn btn-default btn-lg" value="位置5"/>
        <input type="button" id="t1p6" onclick="sit(this)" class="btn btn-default btn-lg" value="位置6"/>
        <input type="button" id="t1p7" onclick="sit(this)" class="btn btn-default btn-lg" value="位置7"/>
        <input type="button" id="t1p8" onclick="sit(this)" class="btn btn-default btn-lg" value="位置8"/>
    </div>
    <div>----------</div>
    <div><button onclick="ready()" class="btn btn-default">准备</button>
        <button class="btn btn-default">取消准备</button>
        <a id="gogogo" href="game">开始</a>
    </div>
<%@include file="lobbyChat.jsp"%>
</body>
<script>
    //发送消息
    function ready() {
        var message = 'sys|ready|'+'${userName}';
        websocket.send(message);
    }

    function sit(obj){
        var id = obj.id;
        var table = id.slice(1,2);
        var seat = id.slice(3,4);
        var uName = "${userName}";
        var jsonStr = {"table":table,"seat":seat,"userName":uName};//object类型
        var msg = JSON.stringify(jsonStr);//string类型
        jQuery.ajax({
            type: "post",
            url: "/Triangle/lobby/sit",
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

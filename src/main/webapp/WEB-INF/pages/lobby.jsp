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
        <button id="pos0" onclick="sit(this)" class="btn btn-default">位置1</button>
        <button id="pos1" onclick="sit(this)" class="btn btn-default">位置2</button>
        <button id="pos2" onclick="sit(this)" class="btn btn-default">位置3</button>
        <button id="pos3" onclick="sit(this)" class="btn btn-default">位置4</button>
        <button id="pos4" onclick="sit(this)" class="btn btn-default">位置5</button>
        <button id="pos5" onclick="sit(this)" class="btn btn-default">位置6</button>
        <button id="pos6" onclick="sit(this)" class="btn btn-default">位置7</button>
        <button id="pos7" onclick="sit(this)" class="btn btn-default">位置8</button>
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
        alert(obj.id);
    }

    var url = "lobby/sit.action";
    var jsonStr = {"name":'ji',"age":20};//object类型
    var jsonArrayFinal = JSON.stringify(jsonStr);//string类型

    jQuery.ajax({
        type: "post",
        url: url,
        dataType : 'json',
        /*
        预期服务器返回的数据类型 xml html script  json jsonp  text ;
        如果不指定，则服务器根据返回数据类型自行判断
        */
        async:true,//默认异步
        data : {mydata:jsonArrayFinal},
        //或{'mydata':jsonArrayFinal}
        /*
        要么使用 \ 对json进行手动转译，要么使用JSON.stringify(jsonStr) 将其转换为字符串类型；
        如果不进行转译，使用【2】中的后台接收方法，那么不会报异常，但是获取不到数据。
        data : {mydata:jsonStr},//报空指针异常，传不过去
        */
        contentType:"application/x-www-form-urlencoded",//默认值
        success: function(data,textStatus){
            alert(data);
            alert("操作成功");
        },
        error: function(xhr,status,errMsg){
            alert("操作失败!");
        }
    });
</script>

</html>

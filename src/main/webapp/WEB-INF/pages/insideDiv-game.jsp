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
<div class="table-responsive" >
    <table class="table  table-bordered table-condensed">
        <caption></caption>
        <thead>
        <tr>
            <th colspan="2" width="20%">玩家信息</th>
            <th align="center">本局决策<span id="panel-count" style="font-size:12px"></span></th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <br/>
    <div class="panel panel-default">
        <div id="panel" class="panel-body">
            <span id="panel-hp-text">生命值: </span><span id="panel-hp-value">3 </span>&nbsp&nbsp
            <span id="panel-mp-text">法力值: </span><span id="panel-mp-value">2</span>&nbsp&nbsp
            <span id="panel-dcs-text">当前决策:</span><span id="panel-dcs-value"></span>
        </div>
    </div>
        <div id="dcsBtns" height="20px">
            <div>
                <button id="recoverMana" type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">恢复法力</button>
                <button id="attack1" type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">攻击(1级)</button>
                <button id="attack2" type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">攻击(2级)</button>
                <button id="attack3" type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">攻击(3级)</button>
                <button id="attack4"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">攻击(4级)</button>
            </div>
            <div>
                <button id="defence1"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">防御(1级)</button>
                <button id="defence2"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">防御(2级)</button>
                <button id="defence3"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">防御(3级)</button>
                <button id="manaAttack"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">法力燃烧</button>
                <button id="manaGrab"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">法力夺取</button>
                <button id="beInvincible"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">法力护盾</button>
            </div>
        </div>
    <br/>
    <div>
        <p id="backwardArea" onclick="hideGamePanelAndShowSeats()"></p>
    </div>
</div>
<script>
    var timeCounter;
    var secondLeft = 19;
    function StartCounting(){
        document.getElementById('panel-count').innerText = "";
        secondLeft = 19;
        clearTimeout(timeCounter);
        counting();
    }
    function counting(){
        secondLeft = secondLeft - 1;
        if(0<=secondLeft&&secondLeft<10){
            document.getElementById('panel-count').innerText = "(剩余时间:" + secondLeft + ")";
        }
        timeCounter = setTimeout("counting()",1000);
    }

    function dcs(dcsId){
        var dcs = {"room":"${roomNum}","userName":"${userName}","decision":dcsId};//object类型
        var dcsJson = JSON.stringify(dcs);
        jQuery.ajax({
            type: "post",
            url: "<%=request.getContextPath()%>/triGame/makeDcs",
            data : {data:dcsJson},
            success: function(){
                document.getElementById('panel-dcs-value').innerText = dcsId;
            },
            error: function(){
            }
        });
    }

    function hideGamePanelAndShowSeats(){
        document.getElementById('backwardArea').innerText="";
        $('#allSeats').attr("class","show");
        $('#game').attr("class","hide");
    }
</script>
</body>
</html>

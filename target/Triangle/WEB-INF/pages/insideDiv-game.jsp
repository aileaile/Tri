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
            <span >当前状态 >>></span>
            <span id="panel-hp-text">命: </span><span id="panel-hp-value">3 </span>&nbsp&nbsp
            <span id="panel-mp-text">气: </span><span id="panel-mp-value">2</span>&nbsp&nbsp
            <span id="panel-dcs-text">当前决策:</span><span id="panel-dcs-value"></span>
        </div>
    </div>
        <div id="dcsBtns" height="20px">
            <div>
                <button id="recoverMana" type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">运</button>
                <button id="attack1" type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">打1个</button>
                <button id="attack2" type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">打2个</button>
                <button id="attack3" type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">打3个</button>
                <button id="attack4"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">打4个</button>
            </div>
            <div>
                <button id="defence1"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">防1个</button>
                <button id="defence2"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">防2个</button>
                <button id="defence3"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">防3个</button>
                <button id="manaAttack"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">费</button>
                <button id="manaGrab"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">吸</button>
                <button id="beInvincible"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">无敌</button>
            </div>
        </div>
    <br/>
    <div>
        <p id="backwardArea" style="color:darkgreen;font-family:'微软雅黑'" onclick="hideGamePanelAndShowSeats()"></p>
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
                document.getElementById('panel-dcs-value').innerText = getShowName(dcsId);
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

    function getShowName(sysName){
        var resName = "";
        if(sysName=="recoverMana"){resName = "运";}
        else if(sysName=="attack1"){resName = "打1个";}
        else if(sysName=="attack2"){resName = "打2个";}
        else if(sysName=="attack3"){resName = "打3个";}
        else if(sysName=="attack4"){resName = "打4个";}
        else if(sysName=="defence1"){resName = "防1个";}
        else if(sysName=="defence2"){resName = "防2个";}
        else if(sysName=="defence3"){resName = "防3个";}
        else if(sysName=="manaAttack"){resName = "费";}
        else if(sysName=="manaGrab"){resName = "吸";}
        else if(sysName=="beInvincible"){resName = "无敌";}
        return resName;
    }
</script>
</body>
</html>

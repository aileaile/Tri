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
            <th align="center">本局决策</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
    <button id="recoverMana" type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">恢复法力</button>
    <button id="attack1" type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">攻击(1级)</button>
    <button id="attack2" type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">攻击(2级)</button>
    <button id="attack3" type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">攻击(3级)</button>
    <button id="attack4"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">攻击(4级)</button>
    <button id="defence1"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">防御(1级)</button>
    <button id="defence2"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">防御(2级)</button>
    <button id="defence3"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">防御(3级)</button>
    <button id="manaAttack"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">法力燃烧</button>
    <button id="manaGrab"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">法力夺取</button>
    <button id="beInvincible"  type="button" class="btn btn-default decisionBtn" onclick="dcs(this.id)">法力护盾</button>
</div>
<script>
    function dcs(dcsId){
        var dcs = {"room":"${roomNum}","userName":"${userName}","decision":dcsId};//object类型
        var dcsJson = JSON.stringify(dcs);
        jQuery.ajax({
            type: "post",
            url: "<%=request.getContextPath()%>/triGame/makeDcs",
            data : {data:dcsJson},
            success: function(){
            },
            error: function(){
            }
        });
    }
</script>
</body>
</html>

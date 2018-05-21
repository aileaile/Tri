<%--
subbut  Created by IntelliJ IDEA.
  User: LL
  Date: 2017/9/17
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<meta name="viewport" content="width=device-width,initial-scale=1, minimum-scale=1, maximum-scale=1,user-scalable=no">
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <style>
        body{
            font-family:"微软雅黑" !important;
        }
    </style>
    <title>登陆LLGame</title>
    <%@include file="signin&signup.jsp"%>
</head>
<body>
<form id="buttons" action="main"  method="post" class="" onSubmit="return sub()">
    <div class="col-xs-10" id="welcomeText">
    </div><br/><br/>
  <%--  <div class="col-xs-10" line-height="100px">
        请输入用户名：<input name="userName" value="" id="userName" size="7%" />
        <span id="errMsg" style="color:indianred;font-size:11px;font-family:微软雅黑">${errMsg}</span>
    </div>
    <br><br>--%>
    <div class="col-xs-10" line-height="100px">
        请输入用户名：<input name="roomNum" value="" id="roomNum" size="7%"/>(1-10)
    </div>
    <br><br>
    <div class="col-xs-10">
        <button id="indexSubmitBtn" type="submit" class="btn btn-default">确定！</button>
    </div>
</form>
<br/><br/><br/>
<p style="text-align: left;font-size:13px;font-family: Consolas;color:darkgray">[beta 0.0.3]</p>
</body>


<script type="text/javascript">
    window.onload=welcome();
    function welcome(){
        var randomInt = parseInt(Math.random()*20+1);
        var nowDate = new Date();
        var nowDay = nowDate.getDay();
        var nowHour = nowDate.getHours();
        var welcomeText;
        var welcomeHourText;
        switch (nowHour) {
            case 5:case 6:case 7 :case 8:
                welcomeHourText = '早上好：';
                break;
            case 9:case 10:case 11:
                welcomeHourText = '上午好：';
                break;
            case 12:case 13:
                welcomeHourText = '中午好：';
                break;
            case 14:case 14:case 15:case 16:case 17:case 18:
                welcomeHourText = "下午好：";
                break;
            default:
                welcomeHourText = "晚上好：";
                break;
        }

        if(nowDay==0){
            if(randomInt=20) {
                if(nowHour>17) {
                    welcomeText = "据说还有"+(24-nowHour)+"小时就是周一了。";
                }else {
                    welcomeText = welcomeHourText;
                }
            }else if(randomInt<=10) {
                welcomeText = "周日愉快！";
            }else {
                welcomeText = welcomeHourText;
            }
        }else if(nowDay == 6){
            if(randomInt<=10) {
                welcomeText = "周六愉快！";
            }else {
                welcomeText = welcomeHourText;
            }
        }else if(nowDay == 5){
           if(randomInt<=10) {
               if(17-nowHour>0){
                   welcomeText = "还有"+(17-nowHour)+"小时就是周末了！";
               }else{
                   welcomeText = "周末愉快！";
               }
           }else {
               welcomeText = welcomeHourText;
           }
        }else{
            if(randomInt==20){
                welcomeText = "欢迎回来。";
            }else{
                welcomeText = welcomeHourText;
            }
        }
        document.getElementById('welcomeText').innerText = welcomeText;
    }

    function sub(){
        var loginCheck = document.getElementById('userName');
        if(loginCheck.value.replace(/\s+/g, "").length ==0){
            document.getElementById('errMsg').innerText="用户名不能为空。";
            return false;
        }
        return true;
    }
</script>
</html>

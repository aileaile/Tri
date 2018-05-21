<%@ include file="base.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<head>
    <style type="text/css">
       .topBarRight{
            padding-top: 12px;
            float:right;
        }
       .topBarLeft{
            padding-top: 6px;
            float:left;
        }
        .topBarLeftFont{
            color: #303841;
            font-size: 22px;
        }
       .topBarRightFont{
           color: #303841;
       }
       a:link {text-decoration: none;}
    </style>
</head>

<body>
<nav class="navbar navbar-default" role="navigation">
<div class="container-fluid">
        <div class="topBarLeft">
            <a class="topBarLeftFont" href="#">LL</a>
        </div>
        <div >
            <!-- 如果用户没有登陆, 就提示用户登陆或注册 -->
            <c:if test="${empty session_user}">
                    <div class="topBarRight">
                        <a class="topBarRightFont" data-toggle="modal" data-target="#register" href=""><span class="glyphicon glyphicon-user" ></span> 注册</a>&nbsp&nbsp
                        <a class="topBarRightFont" data-toggle="modal" data-target="#login" href="" ><span class="glyphicon glyphicon-log-in" ></span> 登录</a>
                    </div>
                <!-- 注册窗口 -->
                <div id="register" class="modal fade" tabindex="-1">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-body">
                                <button class="close" data-dismiss="modal">
                                    <span>&times;</span>
                                </button>
                            </div>
                            <div class="modal-title">
                                <h1 class="text-center" style="color:black;font-family:'微软雅黑'">注册</h1>
                            </div>
                            <div class="modal-body">
                                <form id="signUpForm" action="##" method="post" >
                                    <div class="form-group">
                                        <label style="color:black;">用户名</label>
                                        <input name="username" class="form-control" type="text" placeholder="请输入用户名(2-20位)">
                                    </div>
                                    <div class="form-group">
                                        <label style="color:black;">密码</label>
                                        <input name="password" class="form-control" type="password" placeholder="请输入密码(6-20位)"	>
                                    </div>
                                    <div ><p style="color:red" id="signUpErrMsg"></p></div>
                                    <br/>
                                    <div style="float:left">
                                        <a style="line-height:34px" href="" data-toggle="modal" data-dismiss="modal" data-target="#login">已有账号？点我登录</a>
                                    </div>
                                    <div style="float:right">
                                        <button class="btn btn-primary" type="button" onclick="signUp()">提交</button>
                                        <button class="btn btn-danger" data-dismiss="modal">取消</button>
                                    </div>
                                    <br/><br/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 登录窗口 -->
                <div id="login" class="modal fade">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-body">
                                <button class="close" data-dismiss="modal">
                                    <span>&times;</span>
                                </button>
                            </div>
                            <div class="modal-title">
                                <h1 class="text-center" style="color:black;font-family:'微软雅黑'">登陆</h1>
                            </div>
                            <div class="modal-body">
                                <form id="signInForm" onsubmit="return false" action="##" method="post">
                                    <div class="form-group">
                                        <label style="color:black;">用户名</label>
                                        <input name="username" class="form-control" type="text" placeholder="">
                                    </div>
                                    <div class="form-group">
                                        <label style="color:black;">密码</label>
                                        <input name="password" class="form-control" type="password" placeholder="">
                                    </div>
                                    <div ><p style="color:red" id="signInErrMsg"></p></div>
                                    <br/>
                                    <div style="float:left">
                                        <a style="line-height:34px" href="" data-toggle="modal" data-dismiss="modal" data-target="#register">还没有账号？点我注册</a>
                                    </div>
                                    <div style="float:right">
                                        <button class="btn btn-primary" type="button" onclick="signIn()">登录</button>
                                        <button class="btn btn-danger" data-dismiss="modal">取消</button>
                                    </div>
                                    <br/><br/>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
            <!-- 如果用户已经登陆了, 就提示欢迎xxx回来 -->
            <c:if test="${!(empty session_user) }">
                <div class="topBarRight">
                    <a class="topBarRightFont" data-target="" href="#"><span class="glyphicon glyphicon-user"></span>欢迎,  ${session_user.name}</a>&nbsp&nbsp
                    <c:if test="${(session_user.name == 'admin') }">
                        <a href="manage.action">管理网站</a>
                    </c:if>
                    <a class="topBarRightFont" data-toggle="modal" data-target="#logout" href=""><span class="glyphicon glyphicon-log-out"></span> 退出</a>
                </div>
            <!-- 单击退出后,弹出隐藏的form表单和一个单一的按钮"确认退出",单击确认退出后提交隐藏的form表单
                form表单中包含:当前url -->
            <div id="logout" class="modal fade">
                <form class="form-group" action="${prc}/user/signOut.do" method="post">
                    <div class="text-right">
                        <button class="btn btn-danger" type="submit">确认退出</button>
                        <button class="btn btn-primary" type="button" data-dismiss="modal">取消</button>
                    </div>
                </form>
            </div>
            </c:if>
        </div>
</div>
</nav>
</body>
<script>
    $('#login').on('show.bs.modal',function(){
        $('#signInErrMsg').text("");
    });
    $('#register').on('show.bs.modal',function(){
        var signUpFormValidatorTemp = $('#signUpForm').data('bootstrapValidator');
        if(signUpFormValidatorTemp!=undefined){
            signUpFormValidatorTemp.destroy();
        }
        initVali();
        $('#signUpErrMsg').text("");
    });

    function signIn() {
        $.ajax({
            type: "POST",
            dataType: "json",
            url: "${prc}/user/signIn" ,
            data: $('#signInForm').serialize(),
            success: function (result) {
                if (result) {
                    location.href = "index";
                }else{
                    $('#signInErrMsg').text("Oops,用户名或密码错误。");
                };
            },
            error : function() {
                alert("异常！");
            }
        });
    }

    function signUp() {
        var signUpFormValidator = $('#signUpForm').data('bootstrapValidator');
        //手动触发验证
        signUpFormValidator.validate();
        if(signUpFormValidator.isValid()){
            //表单提交的方法
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "${prc}/user/signUp" ,
                data: $('#signUpForm').serialize(),
                success: function (result) {
                    if (result==0) {
                        location.href = "index";
                    }else{
                        $('#signUpErrMsg').text("用户名已经被占用。");
                    }
                },
            });
        }
    }


    function initVali() {
        $('#signUpForm').bootstrapValidator({
            feedbackIcons: {/*输入框不同状态，显示图片的样式*/
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {/*验证*/
                username: {/*键名username和input name值对应*/
                    threshold :2,
                    validators: {
                        notEmpty: {/*非空提示*/
                            message: '用户名不能为空'
                        },
                        regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                            regexp: /^[0-9a-zA-Z_\u4E00-\u9FA5]+$/,
                            message: '不能包含空格或特殊字符'
                        },
                        stringLength: {/*长度提示*/
                            min: 2,
                            max: 20,
                            message: '用户名长度必须在2到20之间'
                        },
                        /* remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                             url: 'usernameVali.action',//验证地址
                             message: '用户已存在',//提示消息
                             dataType: 'json',
                             type: 'POST'//请求方式
                         },
                         delay :  1000//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）*/                        }
                },
                password: {
                    threshold :2,
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                            regexp: /^[^ ]+$/,
                            message: '不能包含空格'
                        },
                        stringLength: {
                            min: 4,
                            max: 20,
                            message: '密码长度必须在4-20之间'
                        },
                        /*different: {//不能和用户名相同
                            field: 'username',
                            message: '不能和用户名相同'
                        },*/
                    },
                },
            }
        });
    };

</script>
</html>

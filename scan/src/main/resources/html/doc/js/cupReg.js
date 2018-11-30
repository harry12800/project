layui.define(["jquery","cupAjax"], function (exports) {
    var $ = layui.jquery,
        cunAjax=layui.cupAjax;
    function setCookie(c_name, value, expiredays) {
        var exdate = new Date();
        　　　　exdate.setDate(exdate.getDate() + expiredays);
        　　　　document.cookie = c_name + "=" + escape(value) + ((expiredays == null) ? "" : ";expires=" + exdate.toGMTString());
    }
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

        if (arr = document.cookie.match(reg))

            return (arr[2]);
        else
            return null;
    }
    function delCookie(name) {
        var exp = new Date();
        exp.setTime(exp.getTime() - 1);
        var cval = getCookie(name);
        if (cval != null)
            document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
    }
    var obj={
        getCookie:function(){
            var name = getCookie('name'),
                pwd = getCookie('pwd');
            if (name === undefined || pwd === undefined) {
                $('.layui-form-checkbox').removeClass('layui-form-checked')
                $('.cupName-js').val('');
                $('.cupPwd-js').val('');
            } else {
                $('.layui-form-checkbox').addClass('layui-form-checked')
                $('.cupPwd-js').val(pwd);
                $('.cupName-js').val(name);
            }
        },
        remeberPwd :function (ID,PSW) {
            if (ID == '') {
                layer.msg('账号不能为空', {icon: 2});
            }else if (PSW == '') {
                layer.msg('密码不能为空', {icon: 2});
            }else if (ID != '' && PSW != '') {
                if ($('.layui-form-checkbox').hasClass('layui-form-checked')) {
//                    ID = ID.toUpperCase();
                    setCookie('name', ID,7);
                    setCookie('pwd', PSW,7);
                } else {
                    delCookie('name');
                    delCookie('pwd');
                };
            }
        },
        remeberUserId:function (ID) {
             setCookie('a', ID);
        }
    }
    exports('cupLogin',obj)
})
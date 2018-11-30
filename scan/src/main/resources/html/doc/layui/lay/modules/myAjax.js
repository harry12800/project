layui.define(['jquery'],function(exports){
    var $=layui.jquery;
    var baseUrl = 'http://172.16.7.125:8060';
    window.myAjax=function(arg){
        arg.url=baseUrl+arg.url;
        $.ajax(arg)
    }
    exports('myAjax'.obj);
})
layui.define(["jquery"], function (exports) {
    var $ = layui.jquery;        
    var baseURL='';
    var obj=function(arg){
        arg.url=baseURL+arg.url;
        $.ajax(arg)
    }  
    exports('cupAjax',obj)
})
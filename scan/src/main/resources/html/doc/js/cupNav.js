
layui.define(["jquery", "cupAjax", "element","layer"], function (exports) {
    var $ = layui.jquery,
        element = layui.element,
        layer=layui.layer;


    function tabAdd(url, id, name) {
        element.tabAdd('cupRightSideNav', {
            title: name,
            content: '<iframe id="' + id + '" scrolling="auto" frameborder="0" src="' + url + '" style="width:100%;height:99%;"></iframe>',
            id: id
        });
        $("iframe").load(function(){
            if($(this)[0].contentWindow.$){
                var width= window.screen.width;
                var height=$(this)[0].contentWindow.$("body").height();
                $(this).height( width < 1370 ? 560 : 700 );
            }else{
                layer.msg("页面不存在")
            }

        });

    }
    function tabChange(id) {
        element.tabChange('cupRightSideNav', id);
    }
    function tabDelete(id) {
        element.tabDelete("cupRightSideNav", id);
    }
    function tabDeleteAll(ids) {
        $.each(ids, function (i, item) {
            element.tabDelete("cupRightSideNav", item);
        })
    }
    var obj = {

        changeNav: function () {

            var layid = location.hash.replace(/^#page\//, '');
            element.tabChange('nav', layid);
            element.on('tab(nav)', function () {
                if ($(this).attr('lay-id')) {
                    location.hash = 'page/' + $(this).attr('lay-id');
                }
            });
        },
        navToRightSideNav:function(data) {
            var url,Id;
            if(data.attr('data-cupurl')){
                url = data.attr('data-cupurl');
                Id = data.attr('data-cupid');
            }else{
                url = data.parent().attr('data-cupurl');
                Id = data.parent().attr('data-cupid');
            }
            var name = data.html();
            var isData = false;
            if (url) {
                location.hash =  url+"?v="+Id;
                $.each($('.cupRightSideNav-js .layui-tab-title li[lay-id]'), function () {
                    if ($(this).attr('lay-id') != Id) {
                        isData = true;
                    } else {
                        isData = false;
                        return false;
                    }
                });
                if (isData) {
                    tabAdd(url, Id, name);
                }
                tabChange(Id);
            }
            $(".layui-nav.layui-nav-tree.layui-nav-itemed ").removeClass("layui-nav-itemed");
        }
    }
    exports('cupNav', obj)
})
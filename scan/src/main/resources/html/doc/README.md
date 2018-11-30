## CommonFrontSystem
通用后台管理系统框架
///////////////////////////////////
JSON（package.json）:
。。。
level：styleOne表示2级导航和3级导航可同时存在；styleTwo表示2级导航；
useName：表示登录用户名称
useImg：表示登录用户头像
cupNav：导航数据
    id：当前数据唯一标识符（每级必须）、
    pid：当前数据父元素的标识符（第二级菜单时必须有此参数）、
    name：当前数据名称（每级必须）、
    isEnd：标识当前是否是最后一级菜单（true表示是最后一级，false表示不是最后一级）（第二级菜单时必须有）
    url：页面路径（isEnd为true时必须有参数url ）
    Menu：子菜单数据（）
。。。
  {
  "level":"styleOne",
  "useName":"wxf",
  "cupNav": [{ "id":"control21","pid":"control","name":"控制台2","isEnd":"true","url":"page/control/control21.html", "Menu":[ ]}
  }
///////////////////////////////////
登录（login.css/Login.html）:
。。。
 图片..
（logo.png）登录系统图标
 class..
cupLoginBg：登录背景图片（bc.png）
cupName-js：登录账号
cupPwd-js：登录密码
cupTip-js：提示信息
 js方法名..
cupLogin.getCookie 判断是否记住密码
cupLogin.remeberPwd 记住密码
。。。
    layui.use('cupLogin',function(){
         var cupLogin=layui.cupLogin;
     })
///////////////////////////////////
导航相关(Main.css/Main.html/cupNav.js)
。。。
 图片..
（case04.fw.png）用户头像图标
  id..
cupCreatNav 导航生成id
  class..
cupLogo:LOGO图片（logo.fw.png）
cupName:系统名称
cupUser-js：登录用户名
cupFirstNav-js：一级导航
cupSecondNav-js：二级和三级导航
cupRightSideNav-js:右侧选项卡导航
cupSideTop：Top值110px
cupPaddingNone：padding设为0px
  js方法名..
cupNav.changeNav()一级导航nav切换
cupNav.navToRightSideNav(data)点击左侧导航，出现快捷按钮和iframe页面切换（data是element.on的$(this)）
。。。
    layui.use('cupNav',function(){
         var cupNav=layui.cupNav;
     })
///////////////////////////////////
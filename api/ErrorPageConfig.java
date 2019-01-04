package cn.harry12800.api;
//package cn.harry12800.scan;
//import org.springframework.boot.web.server.ErrorPage;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//
///**
// * 
// * @ClassName: ErrorPageConfig
// * @Description: 配置错误页面
// * @author cheng
// * @date 2018年4月3日 下午3:45:49
// */
//@Configuration
//public class ErrorPageConfig {
//
//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//        return new EmbeddedServletContainerCustomizer() {
//
//            @Override
//            public void customize(ConfigurableEmbeddedServletContainer container) {
//                ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/400.html");
//                ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
//                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/errorPage/404");
//                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
//
//                container.addErrorPages(error400Page, error401Page, error404Page, error500Page);
//            }
//        };
//    }
//
//}
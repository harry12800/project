package cn.harry12800.scan;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebMvc
public class MyWebConfig implements WebMvcConfigurer {
	// 配置跨域访问(CORS)
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				// .allowedOrigins("http://domain2.com")
				.allowedMethods("GET", "PUT", "POST", "GET", "OPTIONS")
				// .allowedHeaders("header1", "header2", "header3")
				// .exposedHeaders("header1", "header2")
				.allowCredentials(true).maxAge(3600);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// super.configureMessageConverters(converters);

		converters.add(responseBodyConverter());
		converters.add(mappingJackson2HttpMessageConverter());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX + "/html/");
		//		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/api.html").addResourceLocations("classpath:/html/");
		// super.addResourceHandlers(registry);

		// registry.addResourceHandler("/image/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX
		// + "/web/image/");
		// registry.addResourceHandler("/image/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX
		// + "/web/image/");
	}

	// @Override
	// public void configurePathMatch(PathMatchConfigurer configurer) {
	// super.configurePathMatch(configurer);
	// configurer.setUseSuffixPatternMatch(false);
	// }

	@Bean
	// <bean class="org.springframework.http.converter.StringHttpMessageConverter"/>
	public HttpMessageConverter<String> responseBodyConverter() {
		StringHttpMessageConverter converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
		return converter;
	}

	@Bean
	// <bean
	// class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter"/>
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		return new MappingJackson2HttpMessageConverter();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//拦截规则：除了login，其他都拦截判断
		registry.addInterceptor(new MyInterceptor())
		.addPathPatterns("/**")
		.excludePathPatterns(
				"/",
				"/md5",
				"/upload","/v1/monitor/*",
				"/dirmd5",
				"/v1/websocket/**",
				"/remote.html",
				"/doc/**",
				"/images/**",
				"/fonts/**",
				"/js/**",
				"/css/**",
				"/download",
				"/index.html",
				"/405.html", "/v1/doc/viewById/*",
				"/v1/doc/user/login","/v1/doc/user/reg", "/login-callback",
				"/login.html", "/markdown.html", "/v1/doc/app/*");
	}

}

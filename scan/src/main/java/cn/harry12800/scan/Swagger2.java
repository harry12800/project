package cn.harry12800.scan;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class Swagger2 {
	/**
	 * @Description 设置Docket对象中的swagger2需要生成api文档的包目录
	 * @return
	 */
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				//				.groupName("business-api")
				.select()// 选择那些路径和api会生成document
				.apis(RequestHandlerSelectors.basePackage("cn.harry12800.scan"))
				//				.apis(RequestHandlerSelectors.any()) // 对所有api进行监控
				//				.paths(PathSelectors.any()) // 对所有路径进行监控
				.build()
				.securitySchemes(securitySchemes())
				.securityContexts(securityContexts());
	}

	private List<ApiKey> securitySchemes() {
		return newArrayList(
				new ApiKey("clientId", "客户端ID", "header"),
				new ApiKey("clientSecret", "客户端秘钥", "header"),
				new ApiKey("accessToken", "客户端访问标识", "header"));
	}

	private List<SecurityContext> securityContexts() {
		return newArrayList(
				SecurityContext.builder()
						.securityReferences(defaultAuth())
						.forPaths(PathSelectors.regex("/*.*"))
						.build());
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return newArrayList(
				new SecurityReference("clientId", authorizationScopes),
				new SecurityReference("clientSecret", authorizationScopes),
				new SecurityReference("accessToken", authorizationScopes));
	}

	/**
	 * @Description 设置api文档页面的标题描述
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("")
				.description("请关注：http://www.harry12800.xyz/")
				.contact(new Contact("harry12800", "http://www.harry12800.xyz/", "804151219@qq.com") {
				}).version("1.0").build();
	}

}

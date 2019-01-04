package cn.harry12800.scan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.harry12800.api.listener.ApplicationReadyEventListenerImpl;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan({"cn.harry12800"})
@MapperScan("cn.harry12800.db.mapper")
@EnableAutoConfiguration
public class ScanApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ScanApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ScanApplication.class);
		application.addListeners(new ApplicationReadyEventListenerImpl());
		application.run(args);
	}
}

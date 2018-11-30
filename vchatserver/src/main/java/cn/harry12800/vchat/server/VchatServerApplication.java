package cn.harry12800.vchat.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"cn.harry12800"})
@MapperScan("cn.harry12800.db.mapper")
public class VchatServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(VchatServerApplication.class, args);
		Server server = run.getBean(Server.class);
		server.start();
	}
}

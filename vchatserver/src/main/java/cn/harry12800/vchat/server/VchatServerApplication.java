package cn.harry12800.vchat.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class VchatServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(VchatServerApplication.class, args);
		Server server = run.getBean(Server.class);
		server.start();
	}
}

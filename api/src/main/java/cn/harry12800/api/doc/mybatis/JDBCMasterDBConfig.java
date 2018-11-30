package cn.harry12800.api.doc.mybatis;

import org.springframework.beans.factory.annotation.Value;

//@Configuration
public class JDBCMasterDBConfig {

	@Value("${spring.datasource.driverClassName}")
	private String driverClassName;
	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;

	public void setDriverClassName(String driverClassName) {
		System.err.println(driverClassName);
		this.driverClassName = driverClassName;
	}

	public void setUrl(String url) {
		System.err.println(url);
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriverClassName() {
		return this.driverClassName;
	}

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public String getUrl() {
		return this.url;
	}
}

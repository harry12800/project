package cn.harry12800.scan.doc.mybatis;

import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import cn.harry12800.api.doc.mybatis.DBSourceDefinition;
import cn.harry12800.api.doc.mybatis.JDBCMasterDBConfig;

//@Configuration
//@MapperScan("com.hnlens.doc.mapper")
public class SessionFactoryConfig {
	// mybatis配置文件
	private static final String MYBATIS_CONFIG_FILE = "mybatis-config.xml";

	// mybatis mapper resource path
	private static final String MAPPER_PATH = "/mybatis/**.xml";

	// 
	private static final String Type_Alias_Package = "com.hnlens.doc.entity";

	@Autowired
	JDBCMasterDBConfig jdbcMasterDBConfig;

	/**
	 * 创建sqlSessionFactoryBean 实例 并且设置configtion 如驼峰命名.等等 设置mapper 映射路径
	 * 设置datasource数据源
	 * @throws Exception 
	 */
	@Bean
	public SqlSessionFactory createSqlSessionFactoryBean() throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		/** 设置mybatis configuration 扫描路径 */
		bean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG_FILE));

		/** 添加mapper 扫描路径 */
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		String resource_path = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + MAPPER_PATH;
		bean.setMapperLocations(resolver.getResources(resource_path));

		/** 设置datasource */
		DataSource dataSource = rwDataSource();
		bean.setDataSource(dataSource);

		/** 设置typeAlias 包扫描路径 */
		bean.setTypeAliasesPackage(Type_Alias_Package);
		SqlSessionFactory object = bean.getObject();
		return object;
	}

	@Primary
	@Bean(DBSourceDefinition.RW_DataSource)
	public DataSource rwDataSource() {
		//		System.err.println(jdbcMasterDBConfig.getDriverClassName());
		DataSource ds = DataSourceBuilder.create()
				.driverClassName(jdbcMasterDBConfig.getDriverClassName())
				.url(jdbcMasterDBConfig.getUrl())
				.username(jdbcMasterDBConfig.getUsername())
				.password(jdbcMasterDBConfig.getPassword())
				.build();
		if (ds instanceof org.apache.tomcat.jdbc.pool.DataSource) {
			org.apache.tomcat.jdbc.pool.DataSource tomcat_ds = (org.apache.tomcat.jdbc.pool.DataSource) ds;
			tomcat_ds.setTestOnConnect(true);
			tomcat_ds.setTestWhileIdle(true);
			tomcat_ds.setTestOnBorrow(true);
			tomcat_ds.setValidationQuery("SELECT 1");
			tomcat_ds.setValidationInterval(TimeUnit.SECONDS.toMillis(5));
			tomcat_ds.setValidationQueryTimeout((int) TimeUnit.SECONDS.toSeconds(30));
			// tomcat_ds.setRemoveAbandoned(true);
			// tomcat_ds.setRemoveAbandonedTimeout((int) TimeUnit.HOURS.toSeconds(4));
			tomcat_ds.setMaxAge(TimeUnit.HOURS.toMillis(6));
		}

		System.out.println("********RW-DataSource=:" + ds.hashCode());
		return ds;
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(rwDataSource());
	}

}

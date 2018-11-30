/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * 开发组开发的应用Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "application")
public class Application { // extends DataEntity<Application> {
	//	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	//	@DbField(value="主键",type=1,sort=1, title ="主键", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "id")
	private Long id;
	/**
	 * App名称
	 */
	//	@DbField(value="App名称",type=1,sort=1, title ="App名称", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "app_name")
	private String appName;
	/**
	 * 项目Swagger的Json获取路径
	 */
	//	@DbField(value="项目Swagger的Json获取路径",type=1,sort=1, title ="项目Swagger的Json获取路径", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "doc_url")
	private String docUrl;
	/**
	 * 首页地址
	 */
	//	@DbField(value="首页地址",type=1,sort=1, title ="首页地址", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "home_url")
	private String homeUrl;
	/**
	 * logo图片路径
	 */
	//	@DbField(value="logo图片路径",type=1,sort=1, title ="logo图片路径", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "logo_url")
	private String logoUrl;
	/**
	 * App类型
	 */
	//	@DbField(value="App类型",type=1,sort=1, title ="App类型", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "app_type")
	private Integer appType;
	/**
	 * App状态
	 */
	//	@DbField(value="App状态",type=1,sort=1, title ="App状态", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "app_state")
	private Integer appState;
	/**
	 * 创建时间
	 */
	//	@DbField(value="创建时间",type=1,sort=1, title ="创建时间", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "create_time")
	private java.util.Date createTime;
	/**
	 * 修改时间
	 */
	//	@DbField(value="修改时间",type=1,sort=1, title ="修改时间", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "update_time")
	private java.util.Date updateTime;
	/**
	 * 创建用户
	 */
	//	@DbField(value="创建用户",type=1,sort=1, title ="创建用户", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "create_user")
	private String createUser;
	/**
	 * 修改用户
	 */
	//	@DbField(value="修改用户",type=1,sort=1, title ="修改用户", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "update_user")
	private String updateUser;
	/**
	 * 主机地址
	 */
	//	@DbField(value="主机地址",type=1,sort=1, title ="主机地址", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "host")
	private String host;
	/**
	 * 基础路径
	 */
	//	@DbField(value="基础路径",type=1,sort=1, title ="基础路径", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "base_path")
	private String basePath;
	/**
	 * 项目的端口号
	 */
	//	@DbField(value="项目的端口号",type=1,sort=1, title ="项目的端口号", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "port")
	private Integer port;
	/**
	 * 备注
	 */
	//	@DbField(value="备注",type=1,sort=1, title ="备注", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "remark")
	private String remark;
	//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql = "CREATE TABLE application(" +
			"	id		INT  COMMENT '主键'," +
			"	app_name		VARCHAR(255)  COMMENT 'App名称'," +
			"	doc_url		VARCHAR(255)  COMMENT '项目Swagger的Json获取路径'," +
			"	home_url		VARCHAR(255)  COMMENT '首页地址'," +
			"	logo_url		VARCHAR(255)  COMMENT 'logo图片路径'," +
			"	app_type		INT  COMMENT 'App类型'," +
			"	app_state		INT  COMMENT 'App状态'," +
			"	create_time		timestamp  COMMENT '创建时间'," +
			"	update_time		timestamp  COMMENT '修改时间'," +
			"	create_user		VARCHAR(64)  COMMENT '创建用户'," +
			"	update_user		VARCHAR(64)  COMMENT '修改用户'," +
			"	host		VARCHAR(255)  COMMENT '主机地址'," +
			"	base_path		VARCHAR(255)  COMMENT '基础路径'," +
			"	port		INT  COMMENT '项目的端口号'," +
			"	remark		VARCHAR(255)  COMMENT '备注'" +
			")COMMENT='开发组开发的应用';";
	//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql = "CREATE TABLE application(" +
			"	id		NUMBER ," +
			"	app_name		VARCHAR2(255) ," +
			"	doc_url		VARCHAR2(255) ," +
			"	home_url		VARCHAR2(255) ," +
			"	logo_url		VARCHAR2(255) ," +
			"	app_type		NUMBER ," +
			"	app_state		NUMBER ," +
			"	create_time		TIMESTAMP ," +
			"	update_time		TIMESTAMP ," +
			"	create_user		VARCHAR2(64) ," +
			"	update_user		VARCHAR2(64) ," +
			"	host		VARCHAR2(255) ," +
			"	base_path		VARCHAR2(255) ," +
			"	port		NUMBER ," +
			"	remark		VARCHAR2(255) " +
			");";

	/**
	 *获取主键
	 */
	public Long getId() {
		return id;
	}

	/**
	 *获取App名称
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 *获取项目Swagger的Json获取路径
	 */
	public String getDocUrl() {
		return docUrl;
	}

	/**
	 *获取首页地址
	 */
	public String getHomeUrl() {
		return homeUrl;
	}

	/**
	 *获取logo图片路径
	 */
	public String getLogoUrl() {
		return logoUrl;
	}

	/**
	 *获取App类型
	 */
	public Integer getAppType() {
		return appType;
	}

	/**
	 *获取App状态
	 */
	public Integer getAppState() {
		return appState;
	}

	/**
	 *获取创建时间
	 */
	public java.util.Date getCreateTime() {
		return createTime;
	}

	/**
	 *获取修改时间
	 */
	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	/**
	 *获取创建用户
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 *获取修改用户
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 *获取主机地址
	 */
	public String getHost() {
		return host;
	}

	/**
	 *获取基础路径
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 *获取项目的端口号
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 *获取备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设值主键
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 设值App名称
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * 设值项目Swagger的Json获取路径
	 */
	public void setDocUrl(String docUrl) {
		this.docUrl = docUrl;
	}

	/**
	 * 设值首页地址
	 */
	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}

	/**
	 * 设值logo图片路径
	 */
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	/**
	 * 设值App类型
	 */
	public void setAppType(Integer appType) {
		this.appType = appType;
	}

	/**
	 * 设值App状态
	 */
	public void setAppState(Integer appState) {
		this.appState = appState;
	}

	/**
	 * 设值创建时间
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 设值修改时间
	 */
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 设值创建用户
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 设值修改用户
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * 设值主机地址
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 设值基础路径
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * 设值项目的端口号
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * 设值备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}

/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * 项目的接口ApiEntity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "api")
public class Api { // extends DataEntity<Api> {
	//	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	//	@DbField(value="主键",type=1,sort=1, title ="主键", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "id")
	private Long id;
	/**
	 * 所属项目的主键
	 */
	//	@DbField(value="所属项目的主键",type=1,sort=1, title ="所属项目的主键", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "app_id")
	private Long appId;
	/**
	 * 代表是哪份自动生成的markdown文件主键
	 */
	//	@DbField(value="代表是哪份自动生成的markdown文件主键",type=1,sort=1, title ="代表是哪份自动生成的markdown文件主键", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "auto_api_markdown_id")
	private Long autoApiMarkdownId;
	/**
	 * 接口路径(/v1/hello）
	 */
	//	@DbField(value="接口路径(/v1/hello）",type=1,sort=1, title ="接口路径(/v1/hello）", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "path")
	private String path;
	/**
	 * 是哪个类型。 1 代表是单一接口路径。2代表是模块接口路径
	 */
	//	@DbField(value="是哪个类型。 1 代表是单一接口路径。2代表是模块接口路径",type=1,sort=1, title ="是哪个类型。 1 代表是单一接口路径。2代表是模块接口路径", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "type")
	private Integer type;
	/**
	 * 项目host,（和base_path用于能表示一个完整路径）
	 */
	//	@DbField(value="项目host,（和base_path用于能表示一个完整路径）",type=1,sort=1, title ="项目host,（和base_path用于能表示一个完整路径）", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "host")
	private String host;
	/**
	 * 项目的基本路径
	 */
	//	@DbField(value="项目的基本路径",type=1,sort=1, title ="项目的基本路径", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "base_path")
	private String basePath;

	//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql = "CREATE TABLE api(" +
			"	id		INT  COMMENT '主键'," +
			"	app_id		INT  COMMENT '所属项目的主键'," +
			"	auto_api_markdown_id		INT  COMMENT '代表是哪份自动生成的markdown文件主键'," +
			"	path		VARCHAR(255)  COMMENT '接口路径(/v1/hello）'," +
			"	type		INT  COMMENT '是哪个类型。 1 代表是单一接口路径。2代表是模块接口路径'," +
			"	host		VARCHAR(255)  COMMENT '项目host,（和base_path用于能表示一个完整路径）'," +
			"	base_path		VARCHAR(255)  COMMENT '项目的基本路径'" +
			")COMMENT='项目的接口Api';";
	//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql = "CREATE TABLE api(" +
			"	id		NUMBER ," +
			"	app_id		NUMBER ," +
			"	auto_api_markdown_id		NUMBER ," +
			"	path		VARCHAR2(255) ," +
			"	type		NUMBER ," +
			"	host		VARCHAR2(255) ," +
			"	base_path		VARCHAR2(255) " +
			");";

	/**
	 *获取主键
	 */
	public Long getId() {
		return id;
	}

	/**
	 *获取所属项目的主键
	 */
	public Long getAppId() {
		return appId;
	}

	/**
	 *获取代表是哪份自动生成的markdown文件主键
	 */
	public Long getAutoApiMarkdownId() {
		return autoApiMarkdownId;
	}

	/**
	 *获取接口路径(/v1/hello）
	 */
	public String getPath() {
		return path;
	}

	/**
	 *获取是哪个类型。 1 代表是单一接口路径。2代表是模块接口路径
	 */
	public Integer getType() {
		return type;
	}

	/**
	 *获取项目host,（和base_path用于能表示一个完整路径）
	 */
	public String getHost() {
		return host;
	}

	/**
	 *获取项目的基本路径
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * 设值主键
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 设值所属项目的主键
	 */
	public void setAppId(Long appId) {
		this.appId = appId;
	}

	/**
	 * 设值代表是哪份自动生成的markdown文件主键
	 */
	public void setAutoApiMarkdownId(Long autoApiMarkdownId) {
		this.autoApiMarkdownId = autoApiMarkdownId;
	}

	/**
	 * 设值接口路径(/v1/hello）
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 设值是哪个类型。 1 代表是单一接口路径。2代表是模块接口路径
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 设值项目host,（和base_path用于能表示一个完整路径）
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 设值项目的基本路径
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

}

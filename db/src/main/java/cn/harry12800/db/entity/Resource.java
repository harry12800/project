/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * 资源：自动生成文档，markdown文档，上传的文件Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://120.78.177.24:3306/docs?useSSL=false&useUnicode=true&characterEncoding=utf-8
 * <dt>scan
 * <dt>Zhouguozhu@123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "resource")
public class Resource { // extends DataEntity<Resource> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "id")
 	private Long	id;
	/**
	 * 所属目录
	 */
//	@DbField(value="所属目录",type=1,sort=1, title ="所属目录", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "directory_id")
 	private Long	directoryId;
	/**
	 * 所属项目
	 */
//	@DbField(value="所属项目",type=1,sort=1, title ="所属项目", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "app_id")
 	private Long	appId;
	/**
	 * 名称
	 */
//	@DbField(value="名称",type=1,sort=1, title ="名称", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "name")
 	private String	name;
	/**
	 * 创建时间
	 */
//	@DbField(value="创建时间",type=1,sort=1, title ="创建时间", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "create_time")
 	private java.util.Date	createTime;
	/**
	 * 更新时间
	 */
//	@DbField(value="更新时间",type=1,sort=1, title ="更新时间", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "update_time")
 	private java.util.Date	updateTime;
	/**
	 * 创建用户
	 */
//	@DbField(value="创建用户",type=1,sort=1, title ="创建用户", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "create_user")
 	private String	createUser;
	/**
	 * 更新用户
	 */
//	@DbField(value="更新用户",type=1,sort=1, title ="更新用户", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "update_user")
 	private String	updateUser;
	/**
	 * 1= markdown ,2=auto_api_markdown,3.其他
	 */
//	@DbField(value="1= markdown ,2=auto_api_markdown,3.其他",type=1,sort=1, title ="1= markdown ,2=auto_api_markdown,3.其他", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "resource_type")
 	private String	resourceType;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "remark")
 	private String	remark;
	/**
	 * 是否资源公开(所有人都可以查阅）
	 */
//	@DbField(value="是否资源公开(所有人都可以查阅）",type=1,sort=1, title ="是否资源公开(所有人都可以查阅）", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "readable")
 	private Integer	readable;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "owner")
 	private String	owner;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "cipher")
 	private Integer	cipher;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "hint")
 	private Integer	hint;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "sort")
 	private Integer	sort;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE resource("+
		"	id		INT  COMMENT '主键',"+
		"	directory_id		INT  COMMENT '所属目录',"+
		"	app_id		INT  COMMENT '所属项目',"+
		"	name		VARCHAR(255)  COMMENT '名称',"+
		"	create_time		timestamp  COMMENT '创建时间',"+
		"	update_time		timestamp  COMMENT '更新时间',"+
		"	create_user		VARCHAR(64)  COMMENT '创建用户',"+
		"	update_user		VARCHAR(64)  COMMENT '更新用户',"+
		"	resource_type		VARCHAR(255)  COMMENT '1= markdown ,2=auto_api_markdown,3.其他',"+
		"	remark		VARCHAR(255) ,"+
		"	readable		INT  COMMENT '是否资源公开(所有人都可以查阅）',"+
		"	owner		VARCHAR(255) ,"+
		"	cipher		INT ,"+
		"	hint		INT ,"+
		"	sort		INT ,"+
		"	PRIMARY KEY(id)"+
		")COMMENT='资源：自动生成文档，markdown文档，上传的文件';";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE resource("+
		"	id		NUMBER ,"+
		"	directory_id		NUMBER ,"+
		"	app_id		NUMBER ,"+
		"	name		VARCHAR2(255) ,"+
		"	create_time		TIMESTAMP ,"+
		"	update_time		TIMESTAMP ,"+
		"	create_user		VARCHAR2(64) ,"+
		"	update_user		VARCHAR2(64) ,"+
		"	resource_type		VARCHAR2(255) ,"+
		"	remark		VARCHAR2(255) ,"+
		"	readable		NUMBER ,"+
		"	owner		VARCHAR2(255) ,"+
		"	cipher		NUMBER ,"+
		"	hint		NUMBER ,"+
		"	sort		NUMBER ,"+
		"	PRIMARY KEY(id)"+
		");";
	
	
	/**
	 *获取主键
	 */
 	public  Long	getId() {
 		return id;
 	}
	
	/**
	 * 设值主键
	 */
 	public void	setId(Long id) {
 		this.id = id;
 	}
	
	
	
	/**
	 *获取所属目录
	 */
 	public  Long	getDirectoryId() {
 		return directoryId;
 	}
	/**
	 *获取所属项目
	 */
 	public  Long	getAppId() {
 		return appId;
 	}
	/**
	 *获取名称
	 */
 	public  String	getName() {
 		return name;
 	}
	/**
	 *获取创建时间
	 */
 	public  java.util.Date	getCreateTime() {
 		return createTime;
 	}
	/**
	 *获取更新时间
	 */
 	public  java.util.Date	getUpdateTime() {
 		return updateTime;
 	}
	/**
	 *获取创建用户
	 */
 	public  String	getCreateUser() {
 		return createUser;
 	}
	/**
	 *获取更新用户
	 */
 	public  String	getUpdateUser() {
 		return updateUser;
 	}
	/**
	 *获取1= markdown ,2=auto_api_markdown,3.其他
	 */
 	public  String	getResourceType() {
 		return resourceType;
 	}
	/**
	 *获取
	 */
 	public  String	getRemark() {
 		return remark;
 	}
	/**
	 *获取是否资源公开(所有人都可以查阅）
	 */
 	public  Integer	getReadable() {
 		return readable;
 	}
	/**
	 *获取
	 */
 	public  String	getOwner() {
 		return owner;
 	}
	/**
	 *获取
	 */
 	public  Integer	getCipher() {
 		return cipher;
 	}
	/**
	 *获取
	 */
 	public  Integer	getHint() {
 		return hint;
 	}
	/**
	 *获取
	 */
 	public  Integer	getSort() {
 		return sort;
 	}
	
	/**
	 * 设值所属目录
	 */
 	public void	setDirectoryId(Long directoryId) {
 		this.directoryId = directoryId;
 	}
	/**
	 * 设值所属项目
	 */
 	public void	setAppId(Long appId) {
 		this.appId = appId;
 	}
	/**
	 * 设值名称
	 */
 	public void	setName(String name) {
 		this.name = name;
 	}
	/**
	 * 设值创建时间
	 */
 	public void	setCreateTime(java.util.Date createTime) {
 		this.createTime = createTime;
 	}
	/**
	 * 设值更新时间
	 */
 	public void	setUpdateTime(java.util.Date updateTime) {
 		this.updateTime = updateTime;
 	}
	/**
	 * 设值创建用户
	 */
 	public void	setCreateUser(String createUser) {
 		this.createUser = createUser;
 	}
	/**
	 * 设值更新用户
	 */
 	public void	setUpdateUser(String updateUser) {
 		this.updateUser = updateUser;
 	}
	/**
	 * 设值1= markdown ,2=auto_api_markdown,3.其他
	 */
 	public void	setResourceType(String resourceType) {
 		this.resourceType = resourceType;
 	}
	/**
	 * 设值
	 */
 	public void	setRemark(String remark) {
 		this.remark = remark;
 	}
	/**
	 * 设值是否资源公开(所有人都可以查阅）
	 */
 	public void	setReadable(Integer readable) {
 		this.readable = readable;
 	}
	/**
	 * 设值
	 */
 	public void	setOwner(String owner) {
 		this.owner = owner;
 	}
	/**
	 * 设值
	 */
 	public void	setCipher(Integer cipher) {
 		this.cipher = cipher;
 	}
	/**
	 * 设值
	 */
 	public void	setHint(Integer hint) {
 		this.hint = hint;
 	}
	/**
	 * 设值
	 */
 	public void	setSort(Integer sort) {
 		this.sort = sort;
 	}
}
	
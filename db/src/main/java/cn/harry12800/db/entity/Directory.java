/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * 项目的层次目录Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://120.78.177.24:3306/docs?useSSL=false&useUnicode=true&characterEncoding=utf-8
 * <dt>scan
 * <dt>Zhouguozhu@123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "directory")
public class Directory { // extends DataEntity<Directory> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "id")
 	private Long	id;
	/**
	 * 父节点
	 */
//	@DbField(value="父节点",type=1,sort=1, title ="父节点", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "parent_id")
 	private Long	parentId;
	/**
	 * 所属项目
	 */
//	@DbField(value="所属项目",type=1,sort=1, title ="所属项目", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "app_id")
 	private Long	appId;
	/**
	 * 目录名称
	 */
//	@DbField(value="目录名称",type=1,sort=1, title ="目录名称", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "name")
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
	 * 拥有者ID
	 */
//	@DbField(value="拥有者ID",type=1,sort=1, title ="拥有者ID", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "owner")
 	private String	owner;
	/**
	 * 更新用户
	 */
//	@DbField(value="更新用户",type=1,sort=1, title ="更新用户", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "update_user")
 	private String	updateUser;
	/**
	 * 排序号
	 */
//	@DbField(value="排序号",type=1,sort=1, title ="排序号", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "sort")
 	private Integer	sort;
	/**
	 * 备注
	 */
//	@DbField(value="备注",type=1,sort=1, title ="备注", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "remark")
 	private String	remark;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE directory("+
		"	id		INT  COMMENT '主键',"+
		"	parent_id		INT  COMMENT '父节点',"+
		"	app_id		INT  COMMENT '所属项目',"+
		"	name		VARCHAR(255)  COMMENT '目录名称',"+
		"	create_time		timestamp  COMMENT '创建时间',"+
		"	update_time		timestamp  COMMENT '更新时间',"+
		"	create_user		VARCHAR(64)  COMMENT '创建用户',"+
		"	owner		VARCHAR(255)  COMMENT '拥有者ID',"+
		"	update_user		VARCHAR(64)  COMMENT '更新用户',"+
		"	sort		INT  COMMENT '排序号',"+
		"	remark		VARCHAR(255)  COMMENT '备注',"+
		"	PRIMARY KEY(id)"+
		")COMMENT='项目的层次目录';";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE directory("+
		"	id		NUMBER ,"+
		"	parent_id		NUMBER ,"+
		"	app_id		NUMBER ,"+
		"	name		VARCHAR2(255) ,"+
		"	create_time		TIMESTAMP ,"+
		"	update_time		TIMESTAMP ,"+
		"	create_user		VARCHAR2(64) ,"+
		"	owner		VARCHAR2(255) ,"+
		"	update_user		VARCHAR2(64) ,"+
		"	sort		NUMBER ,"+
		"	remark		VARCHAR2(255) ,"+
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
	 *获取父节点
	 */
 	public  Long	getParentId() {
 		return parentId;
 	}
	/**
	 *获取所属项目
	 */
 	public  Long	getAppId() {
 		return appId;
 	}
	/**
	 *获取目录名称
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
	 *获取拥有者ID
	 */
 	public  String	getOwner() {
 		return owner;
 	}
	/**
	 *获取更新用户
	 */
 	public  String	getUpdateUser() {
 		return updateUser;
 	}
	/**
	 *获取排序号
	 */
 	public  Integer	getSort() {
 		return sort;
 	}
	/**
	 *获取备注
	 */
 	public  String	getRemark() {
 		return remark;
 	}
	
	/**
	 * 设值父节点
	 */
 	public void	setParentId(Long parentId) {
 		this.parentId = parentId;
 	}
	/**
	 * 设值所属项目
	 */
 	public void	setAppId(Long appId) {
 		this.appId = appId;
 	}
	/**
	 * 设值目录名称
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
	 * 设值拥有者ID
	 */
 	public void	setOwner(String owner) {
 		this.owner = owner;
 	}
	/**
	 * 设值更新用户
	 */
 	public void	setUpdateUser(String updateUser) {
 		this.updateUser = updateUser;
 	}
	/**
	 * 设值排序号
	 */
 	public void	setSort(Integer sort) {
 		this.sort = sort;
 	}
	/**
	 * 设值备注
	 */
 	public void	setRemark(String remark) {
 		this.remark = remark;
 	}
}
	
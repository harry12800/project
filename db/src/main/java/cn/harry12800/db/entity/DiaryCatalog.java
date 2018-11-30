/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://120.78.177.24:3306/scan?useSSL=false&useUnicode=true&characterEncoding=utf-8
 * <dt>scan
 * <dt>Zhouguozhu@123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "diary_catalog")
public class DiaryCatalog { // extends DataEntity<DiaryCatalog> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "id")
 	private String	id;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "parent_id")
 	private String	parentId;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "name")
 	private String	name;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "create_time")
 	private java.util.Date	createTime;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "update_time")
 	private java.util.Date	updateTime;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "sort")
 	private Integer	sort;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "user_id")
 	private String	userId;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE diary_catalog("+
		"	id		VARCHAR(32) ,"+
		"	parent_id		VARCHAR(32) ,"+
		"	name		VARCHAR(64) ,"+
		"	create_time		timestamp ,"+
		"	update_time		timestamp ,"+
		"	sort		INT ,"+
		"	user_id		VARCHAR(32) ,"+
		"	PRIMARY KEY(id)"+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE diary_catalog("+
		"	id		VARCHAR2(32) ,"+
		"	parent_id		VARCHAR2(32) ,"+
		"	name		VARCHAR2(64) ,"+
		"	create_time		TIMESTAMP ,"+
		"	update_time		TIMESTAMP ,"+
		"	sort		NUMBER ,"+
		"	user_id		VARCHAR2(32) ,"+
		"	PRIMARY KEY(id)"+
		");";
	
	
	/**
	 *获取
	 */
 	public  String	getId() {
 		return id;
 	}
	
	/**
	 * 设值
	 */
 	public void	setId(String id) {
 		this.id = id;
 	}
	
	
	
	/**
	 *获取
	 */
 	public  String	getParentId() {
 		return parentId;
 	}
	/**
	 *获取
	 */
 	public  String	getName() {
 		return name;
 	}
	/**
	 *获取
	 */
 	public  java.util.Date	getCreateTime() {
 		return createTime;
 	}
	/**
	 *获取
	 */
 	public  java.util.Date	getUpdateTime() {
 		return updateTime;
 	}
	/**
	 *获取
	 */
 	public  Integer	getSort() {
 		return sort;
 	}
	/**
	 *获取
	 */
 	public  String	getUserId() {
 		return userId;
 	}
	
	/**
	 * 设值
	 */
 	public void	setParentId(String parentId) {
 		this.parentId = parentId;
 	}
	/**
	 * 设值
	 */
 	public void	setName(String name) {
 		this.name = name;
 	}
	/**
	 * 设值
	 */
 	public void	setCreateTime(java.util.Date createTime) {
 		this.createTime = createTime;
 	}
	/**
	 * 设值
	 */
 	public void	setUpdateTime(java.util.Date updateTime) {
 		this.updateTime = updateTime;
 	}
	/**
	 * 设值
	 */
 	public void	setSort(Integer sort) {
 		this.sort = sort;
 	}
	/**
	 * 设值
	 */
 	public void	setUserId(String userId) {
 		this.userId = userId;
 	}
}
	
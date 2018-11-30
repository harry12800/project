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
//@DbTable(tableName = "share_resource")
public class ShareResource { // extends DataEntity<ShareResource> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "id")
 	private Long	id;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "data")
 	private byte[]	data;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "providerId")
 	private Long	providerid;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "recipientId")
 	private Long	recipientid;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "resourceName")
 	private String	resourcename;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "resourceType")
 	private Integer	resourcetype;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "path")
 	private String	path;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "grantTime")
 	private java.util.Date	granttime;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE share_resource("+
		"	id		INT ,"+
		"	data		BLOB ,"+
		"	providerId		INT ,"+
		"	recipientId		INT ,"+
		"	resourceName		VARCHAR(255) ,"+
		"	resourceType		INT ,"+
		"	path		VARCHAR(255) ,"+
		"	grantTime		timestamp ,"+
		"	PRIMARY KEY(id)"+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE share_resource("+
		"	id		NUMBER ,"+
		"	data		CLOB ,"+
		"	providerId		NUMBER ,"+
		"	recipientId		NUMBER ,"+
		"	resourceName		VARCHAR2(255) ,"+
		"	resourceType		NUMBER ,"+
		"	path		VARCHAR2(255) ,"+
		"	grantTime		TIMESTAMP ,"+
		"	PRIMARY KEY(id)"+
		");";
	
	
	/**
	 *获取
	 */
 	public  Long	getId() {
 		return id;
 	}
	
	/**
	 * 设值
	 */
 	public void	setId(Long id) {
 		this.id = id;
 	}
	
	
	
	/**
	 *获取
	 */
 	public  byte[]	getData() {
 		return data;
 	}
	/**
	 *获取
	 */
 	public  Long	getProviderid() {
 		return providerid;
 	}
	/**
	 *获取
	 */
 	public  Long	getRecipientid() {
 		return recipientid;
 	}
	/**
	 *获取
	 */
 	public  String	getResourcename() {
 		return resourcename;
 	}
	/**
	 *获取
	 */
 	public  Integer	getResourcetype() {
 		return resourcetype;
 	}
	/**
	 *获取
	 */
 	public  String	getPath() {
 		return path;
 	}
	/**
	 *获取
	 */
 	public  java.util.Date	getGranttime() {
 		return granttime;
 	}
	
	/**
	 * 设值
	 */
 	public void	setData(byte[] data) {
 		this.data = data;
 	}
	/**
	 * 设值
	 */
 	public void	setProviderid(Long providerid) {
 		this.providerid = providerid;
 	}
	/**
	 * 设值
	 */
 	public void	setRecipientid(Long recipientid) {
 		this.recipientid = recipientid;
 	}
	/**
	 * 设值
	 */
 	public void	setResourcename(String resourcename) {
 		this.resourcename = resourcename;
 	}
	/**
	 * 设值
	 */
 	public void	setResourcetype(Integer resourcetype) {
 		this.resourcetype = resourcetype;
 	}
	/**
	 * 设值
	 */
 	public void	setPath(String path) {
 		this.path = path;
 	}
	/**
	 * 设值
	 */
 	public void	setGranttime(java.util.Date granttime) {
 		this.granttime = granttime;
 	}
}
	
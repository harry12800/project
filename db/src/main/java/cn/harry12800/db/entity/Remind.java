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
//@DbTable(tableName = "remind")
public class Remind { // extends DataEntity<Remind> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "id")
 	private String	id;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "title")
 	private String	title;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "detail")
 	private String	detail;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "type")
 	private Integer	type;
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
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "lunar")
 	private Integer	lunar;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "time")
 	private java.util.Date	time;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE remind("+
		"	id		VARCHAR(32) ,"+
		"	title		VARCHAR(255) ,"+
		"	detail		VARCHAR(255) ,"+
		"	type		INT ,"+
		"	create_time		timestamp ,"+
		"	update_time		timestamp ,"+
		"	lunar		INT ,"+
		"	time		TIMESTAMP "+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE remind("+
		"	id		VARCHAR2(32) ,"+
		"	title		VARCHAR2(255) ,"+
		"	detail		VARCHAR2(255) ,"+
		"	type		NUMBER ,"+
		"	create_time		TIMESTAMP ,"+
		"	update_time		TIMESTAMP ,"+
		"	lunar		NUMBER ,"+
		"	time		TIMESTAMP "+
		");";
	
	
	
	
	
	
	/**
	 *获取
	 */
 	public  String	getId() {
 		return id;
 	}
	/**
	 *获取
	 */
 	public  String	getTitle() {
 		return title;
 	}
	/**
	 *获取
	 */
 	public  String	getDetail() {
 		return detail;
 	}
	/**
	 *获取
	 */
 	public  Integer	getType() {
 		return type;
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
 	public  Integer	getLunar() {
 		return lunar;
 	}
	/**
	 *获取
	 */
 	public  java.util.Date	getTime() {
 		return time;
 	}
	
	/**
	 * 设值
	 */
 	public void	setId(String id) {
 		this.id = id;
 	}
	/**
	 * 设值
	 */
 	public void	setTitle(String title) {
 		this.title = title;
 	}
	/**
	 * 设值
	 */
 	public void	setDetail(String detail) {
 		this.detail = detail;
 	}
	/**
	 * 设值
	 */
 	public void	setType(Integer type) {
 		this.type = type;
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
 	public void	setLunar(Integer lunar) {
 		this.lunar = lunar;
 	}
	/**
	 * 设值
	 */
 	public void	setTime(java.util.Date time) {
 		this.time = time;
 	}
}
	
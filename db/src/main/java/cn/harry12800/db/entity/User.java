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
//@DbTable(tableName = "user")
public class User { // extends DataEntity<User> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "id")
 	private String	id;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "user_id")
 	private String	userId;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "passward")
 	private String	passward;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "userName")
 	private String	username;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "exp")
 	private Integer	exp;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "level")
 	private Integer	level;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE user("+
		"	id		VARCHAR(32) ,"+
		"	user_id		VARCHAR(60) ,"+
		"	passward		VARCHAR(255) ,"+
		"	userName		VARCHAR(255) ,"+
		"	exp		INT ,"+
		"	level		INT ,"+
		"	PRIMARY KEY(id)"+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE user("+
		"	id		VARCHAR2(32) ,"+
		"	user_id		VARCHAR2(60) ,"+
		"	passward		VARCHAR2(255) ,"+
		"	userName		VARCHAR2(255) ,"+
		"	exp		NUMBER ,"+
		"	level		NUMBER ,"+
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
 	public  String	getUserId() {
 		return userId;
 	}
	/**
	 *获取
	 */
 	public  String	getPassward() {
 		return passward;
 	}
	/**
	 *获取
	 */
 	public  String	getUsername() {
 		return username;
 	}
	/**
	 *获取
	 */
 	public  Integer	getExp() {
 		return exp;
 	}
	/**
	 *获取
	 */
 	public  Integer	getLevel() {
 		return level;
 	}
	
	/**
	 * 设值
	 */
 	public void	setUserId(String userId) {
 		this.userId = userId;
 	}
	/**
	 * 设值
	 */
 	public void	setPassward(String passward) {
 		this.passward = passward;
 	}
	/**
	 * 设值
	 */
 	public void	setUsername(String username) {
 		this.username = username;
 	}
	/**
	 * 设值
	 */
 	public void	setExp(Integer exp) {
 		this.exp = exp;
 	}
	/**
	 * 设值
	 */
 	public void	setLevel(Integer level) {
 		this.level = level;
 	}
}
	
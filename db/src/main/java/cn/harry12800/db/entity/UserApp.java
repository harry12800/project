/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * 用户与App的关系Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://120.78.177.24:3306/docs?useSSL=false&useUnicode=true&characterEncoding=utf-8
 * <dt>scan
 * <dt>Zhouguozhu@123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "user_app")
public class UserApp { // extends DataEntity<UserApp> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "app_id")
 	private Long	appId;
	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "user_id")
 	private String	userId;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE user_app("+
		"	app_id		INT ,"+
		"	user_id		VARCHAR(64) ,"+
		"	PRIMARY KEY(app_id,user_id)"+
		")COMMENT='用户与App的关系';";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE user_app("+
		"	app_id		NUMBER ,"+
		"	user_id		VARCHAR2(64) ,"+
		"	PRIMARY KEY(app_id,user_id)"+
		");";
	
	
	/**
	 *获取
	 */
 	public  Long	getAppId() {
 		return appId;
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
 	public void	setAppId(Long appId) {
 		this.appId = appId;
 	}
	/**
	 * 设值
	 */
 	public void	setUserId(String userId) {
 		this.userId = userId;
 	}
	
	
	
	
}
	
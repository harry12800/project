/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * 用户与资源的关系Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://120.78.177.24:3306/docs?useSSL=false&useUnicode=true&characterEncoding=utf-8
 * <dt>scan
 * <dt>Zhouguozhu@123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "user_resource")
public class UserResource { // extends DataEntity<UserResource> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "user_id")
 	private String	userId;
	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "resource_id")
 	private Long	resourceId;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE user_resource("+
		"	user_id		VARCHAR(11) ,"+
		"	resource_id		INT ,"+
		"	PRIMARY KEY(user_id,resource_id)"+
		")COMMENT='用户与资源的关系';";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE user_resource("+
		"	user_id		VARCHAR2(11) ,"+
		"	resource_id		NUMBER ,"+
		"	PRIMARY KEY(user_id,resource_id)"+
		");";
	
	
	/**
	 *获取
	 */
 	public  String	getUserId() {
 		return userId;
 	}
	/**
	 *获取
	 */
 	public  Long	getResourceId() {
 		return resourceId;
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
 	public void	setResourceId(Long resourceId) {
 		this.resourceId = resourceId;
 	}
	
	
	
	
}
	
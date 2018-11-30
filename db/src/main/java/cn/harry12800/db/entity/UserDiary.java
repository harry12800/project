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
//@DbTable(tableName = "user_diary")
public class UserDiary { // extends DataEntity<UserDiary> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "diary_id")
 	private String	diaryId;
	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "user_id")
 	private String	userId;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE user_diary("+
		"	diary_id		VARCHAR(32) ,"+
		"	user_id		VARCHAR(32) ,"+
		"	PRIMARY KEY(diary_id,user_id)"+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE user_diary("+
		"	diary_id		VARCHAR2(32) ,"+
		"	user_id		VARCHAR2(32) ,"+
		"	PRIMARY KEY(diary_id,user_id)"+
		");";
	
	
	/**
	 *获取
	 */
 	public  String	getDiaryId() {
 		return diaryId;
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
 	public void	setDiaryId(String diaryId) {
 		this.diaryId = diaryId;
 	}
	/**
	 * 设值
	 */
 	public void	setUserId(String userId) {
 		this.userId = userId;
 	}
	
	
	
	
}
	
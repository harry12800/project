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
//@DbTable(tableName = "file_attachment")
public class FileAttachment { // extends DataEntity<FileAttachment> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "id")
 	private String	id;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "title")
 	private String	title;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "link")
 	private String	link;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "description")
 	private String	description;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE file_attachment("+
		"	id		VARCHAR(20) ,"+
		"	title		VARCHAR(1024) ,"+
		"	link		VARCHAR(10240) ,"+
		"	description		VARCHAR(10240) ,"+
		"	PRIMARY KEY(id)"+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE file_attachment("+
		"	id		VARCHAR2(20) ,"+
		"	title		VARCHAR2(1024) ,"+
		"	link		VARCHAR2(10240) ,"+
		"	description		VARCHAR2(10240) ,"+
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
 	public  String	getTitle() {
 		return title;
 	}
	/**
	 *获取
	 */
 	public  String	getLink() {
 		return link;
 	}
	/**
	 *获取
	 */
 	public  String	getDescription() {
 		return description;
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
 	public void	setLink(String link) {
 		this.link = link;
 	}
	/**
	 * 设值
	 */
 	public void	setDescription(String description) {
 		this.description = description;
 	}
}
	
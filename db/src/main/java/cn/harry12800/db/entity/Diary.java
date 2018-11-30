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
//@DbTable(tableName = "diary")
public class Diary { // extends DataEntity<Diary> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "id")
 	private String	id;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "catalog_id")
 	private String	catalogId;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "title")
 	private String	title;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "content")
 	private String	content;
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
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "html")
 	private String	html;
	/**
	 * 默认加密。 1表示b不可见。
	 */
//	@DbField(value="默认加密。 1表示b不可见。",type=1,sort=1, title ="默认加密。 1表示b不可见。", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "cipher")
 	private Integer	cipher;
	/**
	 * 点击次数
	 */
//	@DbField(value="点击次数",type=1,sort=1, title ="点击次数", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "hint")
 	private Long	hint;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE diary("+
		"	id		VARCHAR(32) ,"+
		"	catalog_id		VARCHAR(32) ,"+
		"	title		VARCHAR(255) ,"+
		"	content		text ,"+
		"	create_time		timestamp ,"+
		"	update_time		timestamp ,"+
		"	sort		INT ,"+
		"	html		text ,"+
		"	cipher		INT  COMMENT '默认加密。 1表示b不可见。',"+
		"	hint		INT  COMMENT '点击次数',"+
		"	PRIMARY KEY(id)"+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE diary("+
		"	id		VARCHAR2(32) ,"+
		"	catalog_id		VARCHAR2(32) ,"+
		"	title		VARCHAR2(255) ,"+
		"	content		VARCHAR ,"+
		"	create_time		TIMESTAMP ,"+
		"	update_time		TIMESTAMP ,"+
		"	sort		NUMBER ,"+
		"	html		VARCHAR ,"+
		"	cipher		NUMBER ,"+
		"	hint		NUMBER ,"+
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
 	public  String	getCatalogId() {
 		return catalogId;
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
 	public  String	getContent() {
 		return content;
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
 	public  String	getHtml() {
 		return html;
 	}
	/**
	 *获取默认加密。 1表示b不可见。
	 */
 	public  Integer	getCipher() {
 		return cipher;
 	}
	/**
	 *获取点击次数
	 */
 	public  Long	getHint() {
 		return hint;
 	}
	
	/**
	 * 设值
	 */
 	public void	setCatalogId(String catalogId) {
 		this.catalogId = catalogId;
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
 	public void	setContent(String content) {
 		this.content = content;
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
 	public void	setHtml(String html) {
 		this.html = html;
 	}
	/**
	 * 设值默认加密。 1表示b不可见。
	 */
 	public void	setCipher(Integer cipher) {
 		this.cipher = cipher;
 	}
	/**
	 * 设值点击次数
	 */
 	public void	setHint(Long hint) {
 		this.hint = hint;
 	}
}
	
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
//@DbTable(tableName = "image_attachment")
public class ImageAttachment { // extends DataEntity<ImageAttachment> {
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
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "imageUrl")
 	private String	imageurl;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "description")
 	private String	description;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "width")
 	private Integer	width;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "height")
 	private Integer	height;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "imagesize")
 	private Integer	imagesize;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE image_attachment("+
		"	id		VARCHAR(20) ,"+
		"	title		VARCHAR(1024) ,"+
		"	imageUrl		VARCHAR(10240) ,"+
		"	description		VARCHAR(10240) ,"+
		"	width		INT ,"+
		"	height		INT ,"+
		"	imagesize		INT ,"+
		"	PRIMARY KEY(id)"+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE image_attachment("+
		"	id		VARCHAR2(20) ,"+
		"	title		VARCHAR2(1024) ,"+
		"	imageUrl		VARCHAR2(10240) ,"+
		"	description		VARCHAR2(10240) ,"+
		"	width		NUMBER ,"+
		"	height		NUMBER ,"+
		"	imagesize		NUMBER ,"+
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
 	public  String	getImageurl() {
 		return imageurl;
 	}
	/**
	 *获取
	 */
 	public  String	getDescription() {
 		return description;
 	}
	/**
	 *获取
	 */
 	public  Integer	getWidth() {
 		return width;
 	}
	/**
	 *获取
	 */
 	public  Integer	getHeight() {
 		return height;
 	}
	/**
	 *获取
	 */
 	public  Integer	getImagesize() {
 		return imagesize;
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
 	public void	setImageurl(String imageurl) {
 		this.imageurl = imageurl;
 	}
	/**
	 * 设值
	 */
 	public void	setDescription(String description) {
 		this.description = description;
 	}
	/**
	 * 设值
	 */
 	public void	setWidth(Integer width) {
 		this.width = width;
 	}
	/**
	 * 设值
	 */
 	public void	setHeight(Integer height) {
 		this.height = height;
 	}
	/**
	 * 设值
	 */
 	public void	setImagesize(Integer imagesize) {
 		this.imagesize = imagesize;
 	}
}
	
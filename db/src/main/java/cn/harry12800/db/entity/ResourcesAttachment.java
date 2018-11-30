/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * 上传其他类型文件Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://120.78.177.24:3306/docs?useSSL=false&useUnicode=true&characterEncoding=utf-8
 * <dt>scan
 * <dt>Zhouguozhu@123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "resources_attachment")
public class ResourcesAttachment { // extends DataEntity<ResourcesAttachment> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "id")
 	private Long	id;
	/**
	 * 需要上传文件名
	 */
//	@DbField(value="需要上传文件名",type=1,sort=1, title ="需要上传文件名", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "file_name")
 	private String	fileName;
	/**
	 * 上传后的文件名
	 */
//	@DbField(value="上传后的文件名",type=1,sort=1, title ="上传后的文件名", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "upload_name")
 	private String	uploadName;
	/**
	 * 上传保存路径
	 */
//	@DbField(value="上传保存路径",type=1,sort=1, title ="上传保存路径", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "file_url")
 	private String	fileUrl;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE resources_attachment("+
		"	id		INT ,"+
		"	file_name		VARCHAR(255)  COMMENT '需要上传文件名',"+
		"	upload_name		VARCHAR(255)  COMMENT '上传后的文件名',"+
		"	file_url		VARCHAR(255)  COMMENT '上传保存路径',"+
		"	PRIMARY KEY(id)"+
		")COMMENT='上传其他类型文件';";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE resources_attachment("+
		"	id		NUMBER ,"+
		"	file_name		VARCHAR2(255) ,"+
		"	upload_name		VARCHAR2(255) ,"+
		"	file_url		VARCHAR2(255) ,"+
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
	 *获取需要上传文件名
	 */
 	public  String	getFileName() {
 		return fileName;
 	}
	/**
	 *获取上传后的文件名
	 */
 	public  String	getUploadName() {
 		return uploadName;
 	}
	/**
	 *获取上传保存路径
	 */
 	public  String	getFileUrl() {
 		return fileUrl;
 	}
	
	/**
	 * 设值需要上传文件名
	 */
 	public void	setFileName(String fileName) {
 		this.fileName = fileName;
 	}
	/**
	 * 设值上传后的文件名
	 */
 	public void	setUploadName(String uploadName) {
 		this.uploadName = uploadName;
 	}
	/**
	 * 设值上传保存路径
	 */
 	public void	setFileUrl(String fileUrl) {
 		this.fileUrl = fileUrl;
 	}
}
	
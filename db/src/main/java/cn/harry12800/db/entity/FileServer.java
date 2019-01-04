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
 * <dt>jdbc:mysql://120.78.177.24:3306/scan?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Zhouguozhu@123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "file_server")
public class FileServer { // extends DataEntity<FileServer> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "id")
 	private String	id;
	/**
	 * 访问路径
	 */
//	@DbField(value="访问路径",type=1,sort=1, title ="访问路径", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "visit_path")
 	private String	visitPath;
	/**
	 * 文件存放路径
	 */
//	@DbField(value="文件存放路径",type=1,sort=1, title ="文件存放路径", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "file_path")
 	private String	filePath;
	/**
	 * 文件大小
	 */
//	@DbField(value="文件大小",type=1,sort=1, title ="文件大小", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "length")
 	private Long	length;
	/**
	 * 文件访问次数
	 */
//	@DbField(value="文件访问次数",type=1,sort=1, title ="文件访问次数", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "visit_times")
 	private Long	visitTimes;
	/**
	 * 文件标签
	 */
//	@DbField(value="文件标签",type=1,sort=1, title ="文件标签", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "tag")
 	private String	tag;
	/**
	 * 文件类型
	 */
//	@DbField(value="文件类型",type=1,sort=1, title ="文件类型", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "file_type")
 	private Integer	fileType;
	/**
	 * 后缀
	 */
//	@DbField(value="后缀",type=1,sort=1, title ="后缀", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "suffix")
 	private String	suffix;
	/**
	 * 简单路径
	 */
//	@DbField(value="简单路径",type=1,sort=1, title ="简单路径", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "nil_path")
 	private String	nilPath;
	/**
	 * 文件名
	 */
//	@DbField(value="文件名",type=1,sort=1, title ="文件名", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "file_name")
 	private String	fileName;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE file_server("+
		"	id		VARCHAR(36)  COMMENT '主键',"+
		"	visit_path		VARCHAR(255)  COMMENT '访问路径',"+
		"	file_path		VARCHAR(255)  COMMENT '文件存放路径',"+
		"	length		INT  COMMENT '文件大小',"+
		"	visit_times		INT  COMMENT '文件访问次数',"+
		"	tag		VARCHAR(255)  COMMENT '文件标签',"+
		"	file_type		INT  COMMENT '文件类型',"+
		"	suffix		VARCHAR(255)  COMMENT '后缀',"+
		"	nil_path		VARCHAR(255)  COMMENT '简单路径',"+
		"	file_name		VARCHAR(255)  COMMENT '文件名',"+
		"	PRIMARY KEY(id)"+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE file_server("+
		"	id		VARCHAR2(36) ,"+
		"	visit_path		VARCHAR2(255) ,"+
		"	file_path		VARCHAR2(255) ,"+
		"	length		NUMBER ,"+
		"	visit_times		NUMBER ,"+
		"	tag		VARCHAR2(255) ,"+
		"	file_type		NUMBER ,"+
		"	suffix		VARCHAR2(255) ,"+
		"	nil_path		VARCHAR2(255) ,"+
		"	file_name		VARCHAR2(255) ,"+
		"	PRIMARY KEY(id)"+
		");";
	
	
	/**
	 *获取主键
	 */
 	public  String	getId() {
 		return id;
 	}
	
	/**
	 * 设值主键
	 */
 	public void	setId(String id) {
 		this.id = id;
 	}
	
	
	
	/**
	 *获取访问路径
	 */
 	public  String	getVisitPath() {
 		return visitPath;
 	}
	/**
	 *获取文件存放路径
	 */
 	public  String	getFilePath() {
 		return filePath;
 	}
	/**
	 *获取文件大小
	 */
 	public  Long	getLength() {
 		return length;
 	}
	/**
	 *获取文件访问次数
	 */
 	public  Long	getVisitTimes() {
 		return visitTimes;
 	}
	/**
	 *获取文件标签
	 */
 	public  String	getTag() {
 		return tag;
 	}
	/**
	 *获取文件类型
	 */
 	public  Integer	getFileType() {
 		return fileType;
 	}
	/**
	 *获取后缀
	 */
 	public  String	getSuffix() {
 		return suffix;
 	}
	/**
	 *获取简单路径
	 */
 	public  String	getNilPath() {
 		return nilPath;
 	}
	/**
	 *获取文件名
	 */
 	public  String	getFileName() {
 		return fileName;
 	}
	
	/**
	 * 设值访问路径
	 */
 	public void	setVisitPath(String visitPath) {
 		this.visitPath = visitPath;
 	}
	/**
	 * 设值文件存放路径
	 */
 	public void	setFilePath(String filePath) {
 		this.filePath = filePath;
 	}
	/**
	 * 设值文件大小
	 */
 	public void	setLength(Long length) {
 		this.length = length;
 	}
	/**
	 * 设值文件访问次数
	 */
 	public void	setVisitTimes(Long visitTimes) {
 		this.visitTimes = visitTimes;
 	}
	/**
	 * 设值文件标签
	 */
 	public void	setTag(String tag) {
 		this.tag = tag;
 	}
	/**
	 * 设值文件类型
	 */
 	public void	setFileType(Integer fileType) {
 		this.fileType = fileType;
 	}
	/**
	 * 设值后缀
	 */
 	public void	setSuffix(String suffix) {
 		this.suffix = suffix;
 	}
	/**
	 * 设值简单路径
	 */
 	public void	setNilPath(String nilPath) {
 		this.nilPath = nilPath;
 	}
	/**
	 * 设值文件名
	 */
 	public void	setFileName(String fileName) {
 		this.fileName = fileName;
 	}
	public String toString() {
		return "FileServer ["+"visitPath="+visitPath+","+"filePath="+filePath+","+"length="+length+","+"visitTimes="+visitTimes+","+"tag="+tag+","+"fileType="+fileType+","+"suffix="+suffix+","+"nilPath="+nilPath+","+"fileName="+fileName+","+"]";
	}
}
	
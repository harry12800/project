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
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE file_server("+
		"	id		VARCHAR(36)  COMMENT '主键',"+
		"	file_path		VARCHAR(255)  COMMENT '文件存放路径',"+
		"	length		INT  COMMENT '文件大小',"+
		"	visit_times		INT  COMMENT '文件访问次数',"+
		"	tag		VARCHAR(255)  COMMENT '文件标签',"+
		"	file_type		INT  COMMENT '文件类型',"+
		"	PRIMARY KEY(id)"+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE file_server("+
		"	id		VARCHAR2(36) ,"+
		"	file_path		VARCHAR2(255) ,"+
		"	length		NUMBER ,"+
		"	visit_times		NUMBER ,"+
		"	tag		VARCHAR2(255) ,"+
		"	file_type		NUMBER ,"+
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
	public String toString() {
		return "FileServer ["+"filePath="+filePath+","+"length="+length+","+"visitTimes="+visitTimes+","+"tag="+tag+","+"fileType="+fileType+","+"]";
	}
}
	
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
//@DbTable(tableName = "chat_msg")
public class ChatMsg { // extends DataEntity<ChatMsg> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "id")
 	private Long	id;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "arrive")
 	private Integer	arrive;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "come")
 	private Long	come;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "go")
 	private Long	go;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "data")
 	private byte[]	data;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "dataType")
 	private Integer	datatype;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "sendTime")
 	private java.util.Date	sendtime;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE chat_msg("+
		"	id		INT ,"+
		"	arrive		INT ,"+
		"	come		INT ,"+
		"	go		INT ,"+
		"	data		BLOB ,"+
		"	dataType		INT ,"+
		"	sendTime		timestamp ,"+
		"	PRIMARY KEY(id)"+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE chat_msg("+
		"	id		NUMBER ,"+
		"	arrive		NUMBER ,"+
		"	come		NUMBER ,"+
		"	go		NUMBER ,"+
		"	data		CLOB ,"+
		"	dataType		NUMBER ,"+
		"	sendTime		TIMESTAMP ,"+
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
	 *获取
	 */
 	public  Integer	getArrive() {
 		return arrive;
 	}
	/**
	 *获取
	 */
 	public  Long	getCome() {
 		return come;
 	}
	/**
	 *获取
	 */
 	public  Long	getGo() {
 		return go;
 	}
	/**
	 *获取
	 */
 	public  byte[]	getData() {
 		return data;
 	}
	/**
	 *获取
	 */
 	public  Integer	getDatatype() {
 		return datatype;
 	}
	/**
	 *获取
	 */
 	public  java.util.Date	getSendtime() {
 		return sendtime;
 	}
	
	/**
	 * 设值
	 */
 	public void	setArrive(Integer arrive) {
 		this.arrive = arrive;
 	}
	/**
	 * 设值
	 */
 	public void	setCome(Long come) {
 		this.come = come;
 	}
	/**
	 * 设值
	 */
 	public void	setGo(Long go) {
 		this.go = go;
 	}
	/**
	 * 设值
	 */
 	public void	setData(byte[] data) {
 		this.data = data;
 	}
	/**
	 * 设值
	 */
 	public void	setDatatype(Integer datatype) {
 		this.datatype = datatype;
 	}
	/**
	 * 设值
	 */
 	public void	setSendtime(java.util.Date sendtime) {
 		this.sendtime = sendtime;
 	}
}
	
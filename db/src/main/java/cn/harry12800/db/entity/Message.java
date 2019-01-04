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
//@DbTable(tableName = "message")
public class Message { // extends DataEntity<Message> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "id")
 	private String	id;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "roomId")
 	private String	roomid;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "messageContent")
 	private String	messagecontent;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "groupable")
 	private Integer	groupable;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "timestamp")
 	private Integer	timestamp;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "senderUsername")
 	private String	senderusername;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "senderId")
 	private String	senderid;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "updatedAt")
 	private Integer	updatedat;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "needToResend")
 	private Integer	needtoresend;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "progress")
 	private Integer	progress;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "deleted")
 	private Integer	deleted;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "systemMessage")
 	private Integer	systemmessage;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "fileAttachmentId")
 	private String	fileattachmentid;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "imageAttachmentId")
 	private String	imageattachmentid;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE message("+
		"	id		VARCHAR(20) ,"+
		"	roomId		VARCHAR(20) ,"+
		"	messageContent		text ,"+
		"	groupable		INT ,"+
		"	timestamp		INT ,"+
		"	senderUsername		VARCHAR(64) ,"+
		"	senderId		VARCHAR(20) ,"+
		"	updatedAt		INT ,"+
		"	needToResend		INT ,"+
		"	progress		INT ,"+
		"	deleted		INT ,"+
		"	systemMessage		INT ,"+
		"	fileAttachmentId		VARCHAR(20) ,"+
		"	imageAttachmentId		VARCHAR(20) ,"+
		"	PRIMARY KEY(id)"+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE message("+
		"	id		VARCHAR2(20) ,"+
		"	roomId		VARCHAR2(20) ,"+
		"	messageContent		VARCHAR ,"+
		"	groupable		NUMBER ,"+
		"	timestamp		NUMBER ,"+
		"	senderUsername		VARCHAR2(64) ,"+
		"	senderId		VARCHAR2(20) ,"+
		"	updatedAt		NUMBER ,"+
		"	needToResend		NUMBER ,"+
		"	progress		NUMBER ,"+
		"	deleted		NUMBER ,"+
		"	systemMessage		NUMBER ,"+
		"	fileAttachmentId		VARCHAR2(20) ,"+
		"	imageAttachmentId		VARCHAR2(20) ,"+
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
 	public  String	getRoomid() {
 		return roomid;
 	}
	/**
	 *获取
	 */
 	public  String	getMessagecontent() {
 		return messagecontent;
 	}
	/**
	 *获取
	 */
 	public  Integer	getGroupable() {
 		return groupable;
 	}
	/**
	 *获取
	 */
 	public  Integer	getTimestamp() {
 		return timestamp;
 	}
	/**
	 *获取
	 */
 	public  String	getSenderusername() {
 		return senderusername;
 	}
	/**
	 *获取
	 */
 	public  String	getSenderid() {
 		return senderid;
 	}
	/**
	 *获取
	 */
 	public  Integer	getUpdatedat() {
 		return updatedat;
 	}
	/**
	 *获取
	 */
 	public  Integer	getNeedtoresend() {
 		return needtoresend;
 	}
	/**
	 *获取
	 */
 	public  Integer	getProgress() {
 		return progress;
 	}
	/**
	 *获取
	 */
 	public  Integer	getDeleted() {
 		return deleted;
 	}
	/**
	 *获取
	 */
 	public  Integer	getSystemmessage() {
 		return systemmessage;
 	}
	/**
	 *获取
	 */
 	public  String	getFileattachmentid() {
 		return fileattachmentid;
 	}
	/**
	 *获取
	 */
 	public  String	getImageattachmentid() {
 		return imageattachmentid;
 	}
	
	/**
	 * 设值
	 */
 	public void	setRoomid(String roomid) {
 		this.roomid = roomid;
 	}
	/**
	 * 设值
	 */
 	public void	setMessagecontent(String messagecontent) {
 		this.messagecontent = messagecontent;
 	}
	/**
	 * 设值
	 */
 	public void	setGroupable(Integer groupable) {
 		this.groupable = groupable;
 	}
	/**
	 * 设值
	 */
 	public void	setTimestamp(Integer timestamp) {
 		this.timestamp = timestamp;
 	}
	/**
	 * 设值
	 */
 	public void	setSenderusername(String senderusername) {
 		this.senderusername = senderusername;
 	}
	/**
	 * 设值
	 */
 	public void	setSenderid(String senderid) {
 		this.senderid = senderid;
 	}
	/**
	 * 设值
	 */
 	public void	setUpdatedat(Integer updatedat) {
 		this.updatedat = updatedat;
 	}
	/**
	 * 设值
	 */
 	public void	setNeedtoresend(Integer needtoresend) {
 		this.needtoresend = needtoresend;
 	}
	/**
	 * 设值
	 */
 	public void	setProgress(Integer progress) {
 		this.progress = progress;
 	}
	/**
	 * 设值
	 */
 	public void	setDeleted(Integer deleted) {
 		this.deleted = deleted;
 	}
	/**
	 * 设值
	 */
 	public void	setSystemmessage(Integer systemmessage) {
 		this.systemmessage = systemmessage;
 	}
	/**
	 * 设值
	 */
 	public void	setFileattachmentid(String fileattachmentid) {
 		this.fileattachmentid = fileattachmentid;
 	}
	/**
	 * 设值
	 */
 	public void	setImageattachmentid(String imageattachmentid) {
 		this.imageattachmentid = imageattachmentid;
 	}
}
	
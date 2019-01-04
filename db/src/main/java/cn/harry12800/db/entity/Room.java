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
//@DbTable(tableName = "room")
public class Room { // extends DataEntity<Room> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "roomId")
 	private String	roomid;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "type")
 	private String	type;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "name")
 	private String	name;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "topic")
 	private String	topic;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "muted")
 	private String	muted;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "member")
 	private String	member;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "sysMes")
 	private Integer	sysmes;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "msgSum")
 	private Integer	msgsum;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "lastChatAt")
 	private Integer	lastchatat;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "creatorName")
 	private String	creatorname;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "creatorId")
 	private String	creatorid;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "jitsiTimeout")
 	private String	jitsitimeout;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "readOnly")
 	private Integer	readonly;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "archived")
 	private Integer	archived;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "defaultRoom")
 	private Integer	defaultroom;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "createdAt")
 	private String	createdat;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "updatedAt")
 	private String	updatedat;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "unreadCount")
 	private Integer	unreadcount;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "totalReadCount")
 	private Integer	totalreadcount;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "lastMessage")
 	private String	lastmessage;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE room("+
		"	roomId		VARCHAR(20) ,"+
		"	type		VARCHAR(10) ,"+
		"	name		VARCHAR(64) ,"+
		"	topic		VARCHAR(64) ,"+
		"	muted		VARCHAR(20) ,"+
		"	member		VARCHAR(10240) ,"+
		"	sysMes		INT ,"+
		"	msgSum		INT ,"+
		"	lastChatAt		INT ,"+
		"	creatorName		VARCHAR(64) ,"+
		"	creatorId		VARCHAR(20) ,"+
		"	jitsiTimeout		VARCHAR(20) ,"+
		"	readOnly		INT ,"+
		"	archived		INT ,"+
		"	defaultRoom		INT ,"+
		"	createdAt		VARCHAR(20) ,"+
		"	updatedAt		VARCHAR(20) ,"+
		"	unreadCount		INT ,"+
		"	totalReadCount		INT ,"+
		"	lastMessage		VARCHAR(1024) ,"+
		"	PRIMARY KEY(roomId)"+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE room("+
		"	roomId		VARCHAR2(20) ,"+
		"	type		VARCHAR2(10) ,"+
		"	name		VARCHAR2(64) ,"+
		"	topic		VARCHAR2(64) ,"+
		"	muted		VARCHAR2(20) ,"+
		"	member		VARCHAR2(10240) ,"+
		"	sysMes		NUMBER ,"+
		"	msgSum		NUMBER ,"+
		"	lastChatAt		NUMBER ,"+
		"	creatorName		VARCHAR2(64) ,"+
		"	creatorId		VARCHAR2(20) ,"+
		"	jitsiTimeout		VARCHAR2(20) ,"+
		"	readOnly		NUMBER ,"+
		"	archived		NUMBER ,"+
		"	defaultRoom		NUMBER ,"+
		"	createdAt		VARCHAR2(20) ,"+
		"	updatedAt		VARCHAR2(20) ,"+
		"	unreadCount		NUMBER ,"+
		"	totalReadCount		NUMBER ,"+
		"	lastMessage		VARCHAR2(1024) ,"+
		"	PRIMARY KEY(roomId)"+
		");";
	
	
	/**
	 *获取
	 */
 	public  String	getRoomid() {
 		return roomid;
 	}
	
	/**
	 * 设值
	 */
 	public void	setRoomid(String roomid) {
 		this.roomid = roomid;
 	}
	
	
	
	/**
	 *获取
	 */
 	public  String	getType() {
 		return type;
 	}
	/**
	 *获取
	 */
 	public  String	getName() {
 		return name;
 	}
	/**
	 *获取
	 */
 	public  String	getTopic() {
 		return topic;
 	}
	/**
	 *获取
	 */
 	public  String	getMuted() {
 		return muted;
 	}
	/**
	 *获取
	 */
 	public  String	getMember() {
 		return member;
 	}
	/**
	 *获取
	 */
 	public  Integer	getSysmes() {
 		return sysmes;
 	}
	/**
	 *获取
	 */
 	public  Integer	getMsgsum() {
 		return msgsum;
 	}
	/**
	 *获取
	 */
 	public  Integer	getLastchatat() {
 		return lastchatat;
 	}
	/**
	 *获取
	 */
 	public  String	getCreatorname() {
 		return creatorname;
 	}
	/**
	 *获取
	 */
 	public  String	getCreatorid() {
 		return creatorid;
 	}
	/**
	 *获取
	 */
 	public  String	getJitsitimeout() {
 		return jitsitimeout;
 	}
	/**
	 *获取
	 */
 	public  Integer	getReadonly() {
 		return readonly;
 	}
	/**
	 *获取
	 */
 	public  Integer	getArchived() {
 		return archived;
 	}
	/**
	 *获取
	 */
 	public  Integer	getDefaultroom() {
 		return defaultroom;
 	}
	/**
	 *获取
	 */
 	public  String	getCreatedat() {
 		return createdat;
 	}
	/**
	 *获取
	 */
 	public  String	getUpdatedat() {
 		return updatedat;
 	}
	/**
	 *获取
	 */
 	public  Integer	getUnreadcount() {
 		return unreadcount;
 	}
	/**
	 *获取
	 */
 	public  Integer	getTotalreadcount() {
 		return totalreadcount;
 	}
	/**
	 *获取
	 */
 	public  String	getLastmessage() {
 		return lastmessage;
 	}
	
	/**
	 * 设值
	 */
 	public void	setType(String type) {
 		this.type = type;
 	}
	/**
	 * 设值
	 */
 	public void	setName(String name) {
 		this.name = name;
 	}
	/**
	 * 设值
	 */
 	public void	setTopic(String topic) {
 		this.topic = topic;
 	}
	/**
	 * 设值
	 */
 	public void	setMuted(String muted) {
 		this.muted = muted;
 	}
	/**
	 * 设值
	 */
 	public void	setMember(String member) {
 		this.member = member;
 	}
	/**
	 * 设值
	 */
 	public void	setSysmes(Integer sysmes) {
 		this.sysmes = sysmes;
 	}
	/**
	 * 设值
	 */
 	public void	setMsgsum(Integer msgsum) {
 		this.msgsum = msgsum;
 	}
	/**
	 * 设值
	 */
 	public void	setLastchatat(Integer lastchatat) {
 		this.lastchatat = lastchatat;
 	}
	/**
	 * 设值
	 */
 	public void	setCreatorname(String creatorname) {
 		this.creatorname = creatorname;
 	}
	/**
	 * 设值
	 */
 	public void	setCreatorid(String creatorid) {
 		this.creatorid = creatorid;
 	}
	/**
	 * 设值
	 */
 	public void	setJitsitimeout(String jitsitimeout) {
 		this.jitsitimeout = jitsitimeout;
 	}
	/**
	 * 设值
	 */
 	public void	setReadonly(Integer readonly) {
 		this.readonly = readonly;
 	}
	/**
	 * 设值
	 */
 	public void	setArchived(Integer archived) {
 		this.archived = archived;
 	}
	/**
	 * 设值
	 */
 	public void	setDefaultroom(Integer defaultroom) {
 		this.defaultroom = defaultroom;
 	}
	/**
	 * 设值
	 */
 	public void	setCreatedat(String createdat) {
 		this.createdat = createdat;
 	}
	/**
	 * 设值
	 */
 	public void	setUpdatedat(String updatedat) {
 		this.updatedat = updatedat;
 	}
	/**
	 * 设值
	 */
 	public void	setUnreadcount(Integer unreadcount) {
 		this.unreadcount = unreadcount;
 	}
	/**
	 * 设值
	 */
 	public void	setTotalreadcount(Integer totalreadcount) {
 		this.totalreadcount = totalreadcount;
 	}
	/**
	 * 设值
	 */
 	public void	setLastmessage(String lastmessage) {
 		this.lastmessage = lastmessage;
 	}
}
	
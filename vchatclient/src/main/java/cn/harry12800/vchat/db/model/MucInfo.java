/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.vchat.db.model;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://120.78.177.24:3306/im?useSSL=false&useUnicode=true&characterEncoding=utf-8
 * <dt>scan
 * <dt>Zhouguozhu@123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "muc_info")
public class MucInfo extends BasicModel {
//	private static final long serialVersionUID = 1L;

	/**
	 * MUC群ID
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "muc_id")
 	private Long	mucId;
	/**
	 * 群名称
	 */
//	@DbField(value="群名称",type=1,sort=1, title ="群名称", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "muc_name")
 	private String	mucName;
	/**
	 * 群组主题
	 */
//	@DbField(value="群组主题",type=1,sort=1, title ="群组主题", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "muc_topic")
 	private String	mucTopic;
	/**
	 * 群状态
	 */
//	@DbField(value="群状态",type=1,sort=1, title ="群状态", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "muc_status")
 	private Integer	mucStatus;
	/**
	 * 入群方式
	 */
//	@DbField(value="入群方式",type=1,sort=1, title ="入群方式", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "enter_flag")
 	private Integer	enterFlag;
	/**
	 * 最大成员数
	 */
//	@DbField(value="最大成员数",type=1,sort=1, title ="最大成员数", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "max_members")
 	private Integer	maxMembers;
	/**
	 * 
	 */
//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "mtime")
 	private java.util.Date	mtime;
	/**
	 * 创建时间
	 */
//	@DbField(value="创建时间",type=1,sort=1, title ="创建时间", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "ctime")
 	private java.util.Date	ctime;
	/**
	 * 创建者
	 */
//	@DbField(value="创建者",type=1,sort=1, title ="创建者", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "creator")
 	private String	creator;
	/**
	 * normal, temp, ...
	 */
//	@DbField(value="normal, temp, ...",type=1,sort=1, title ="normal, temp, ...", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "muc_type")
 	private Integer	mucType;
	/**
	 * url(muc image)
	 */
//	@DbField(value="url(muc image)",type=1,sort=1, title ="url(muc image)", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "muc_img")
 	private String	mucImg;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE muc_info("+
		"	muc_id		INT  COMMENT 'MUC群ID',"+
		"	muc_name		VARCHAR(100)  COMMENT '群名称',"+
		"	muc_topic		VARCHAR(100)  COMMENT '群组主题',"+
		"	muc_status		INT  COMMENT '群状态',"+
		"	enter_flag		INT  COMMENT '入群方式',"+
		"	max_members		INT  COMMENT '最大成员数',"+
		"	mtime		timestamp ,"+
		"	ctime		timestamp  COMMENT '创建时间',"+
		"	creator		VARCHAR(50)  COMMENT '创建者',"+
		"	muc_type		INT  COMMENT 'normal, temp, ...',"+
		"	muc_img		VARCHAR(1024)  COMMENT 'url(muc image)',"+
		"	PRIMARY KEY(muc_id)"+
		");";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE muc_info("+
		"	muc_id		NUMBER ,"+
		"	muc_name		VARCHAR2(100) ,"+
		"	muc_topic		VARCHAR2(100) ,"+
		"	muc_status		NUMBER ,"+
		"	enter_flag		NUMBER ,"+
		"	max_members		NUMBER ,"+
		"	mtime		TIMESTAMP ,"+
		"	ctime		TIMESTAMP ,"+
		"	creator		VARCHAR2(50) ,"+
		"	muc_type		NUMBER ,"+
		"	muc_img		VARCHAR2(1024) ,"+
		"	PRIMARY KEY(muc_id)"+
		");";
	
	
	/**
	 *获取MUC群ID
	 */
 	public  Long	getMucId() {
 		return mucId;
 	}
	
	/**
	 * 设值MUC群ID
	 */
 	public void	setMucId(Long mucId) {
 		this.mucId = mucId;
 	}
	
	
	
	/**
	 *获取群名称
	 */
 	public  String	getMucName() {
 		return mucName;
 	}
	/**
	 *获取群组主题
	 */
 	public  String	getMucTopic() {
 		return mucTopic;
 	}
	/**
	 *获取群状态
	 */
 	public  Integer	getMucStatus() {
 		return mucStatus;
 	}
	/**
	 *获取入群方式
	 */
 	public  Integer	getEnterFlag() {
 		return enterFlag;
 	}
	/**
	 *获取最大成员数
	 */
 	public  Integer	getMaxMembers() {
 		return maxMembers;
 	}
	/**
	 *获取
	 */
 	public  java.util.Date	getMtime() {
 		return mtime;
 	}
	/**
	 *获取创建时间
	 */
 	public  java.util.Date	getCtime() {
 		return ctime;
 	}
	/**
	 *获取创建者
	 */
 	public  String	getCreator() {
 		return creator;
 	}
	/**
	 *获取normal, temp, ...
	 */
 	public  Integer	getMucType() {
 		return mucType;
 	}
	/**
	 *获取url(muc image)
	 */
 	public  String	getMucImg() {
 		return mucImg;
 	}
	
	/**
	 * 设值群名称
	 */
 	public void	setMucName(String mucName) {
 		this.mucName = mucName;
 	}
	/**
	 * 设值群组主题
	 */
 	public void	setMucTopic(String mucTopic) {
 		this.mucTopic = mucTopic;
 	}
	/**
	 * 设值群状态
	 */
 	public void	setMucStatus(Integer mucStatus) {
 		this.mucStatus = mucStatus;
 	}
	/**
	 * 设值入群方式
	 */
 	public void	setEnterFlag(Integer enterFlag) {
 		this.enterFlag = enterFlag;
 	}
	/**
	 * 设值最大成员数
	 */
 	public void	setMaxMembers(Integer maxMembers) {
 		this.maxMembers = maxMembers;
 	}
	/**
	 * 设值
	 */
 	public void	setMtime(java.util.Date mtime) {
 		this.mtime = mtime;
 	}
	/**
	 * 设值创建时间
	 */
 	public void	setCtime(java.util.Date ctime) {
 		this.ctime = ctime;
 	}
	/**
	 * 设值创建者
	 */
 	public void	setCreator(String creator) {
 		this.creator = creator;
 	}
	/**
	 * 设值normal, temp, ...
	 */
 	public void	setMucType(Integer mucType) {
 		this.mucType = mucType;
 	}
	/**
	 * 设值url(muc image)
	 */
 	public void	setMucImg(String mucImg) {
 		this.mucImg = mucImg;
 	}
}
	
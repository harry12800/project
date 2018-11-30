/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * 用户表Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://120.78.177.24:3306/docs?useSSL=false&useUnicode=true&characterEncoding=utf-8
 * <dt>scan
 * <dt>Zhouguozhu@123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "fingerchat_user")
public class FingerchatUser { // extends DataEntity<FingerchatUser> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "id")
 	private Long	id;
	/**
	 * 用户id  唯一键
	 */
//	@DbField(value="用户id  唯一键",type=1,sort=1, title ="用户id  唯一键", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "user_id")
 	private String	userId;
	/**
	 * 昵称
	 */
//	@DbField(value="昵称",type=1,sort=1, title ="昵称", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "nick_name")
 	private String	nickName;
	/**
	 * 真实姓名
	 */
//	@DbField(value="真实姓名",type=1,sort=1, title ="真实姓名", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "real_name")
 	private String	realName;
	/**
	 * 员工号
	 */
//	@DbField(value="员工号",type=1,sort=1, title ="员工号", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "employee_no")
 	private String	employeeNo;
	/**
	 * 头像路径
	 */
//	@DbField(value="头像路径",type=1,sort=1, title ="头像路径", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "avatar_url")
 	private String	avatarUrl;
	/**
	 * 飞哥令牌。（第三方登录）
	 */
//	@DbField(value="飞哥令牌。（第三方登录）",type=1,sort=1, title ="飞哥令牌。（第三方登录）", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "finger_token")
 	private String	fingerToken;
	/**
	 * 创建时间。
	 */
//	@DbField(value="创建时间。",type=1,sort=1, title ="创建时间。", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "create_time")
 	private java.util.Date	createTime;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE fingerchat_user("+
		"	id		INT  COMMENT '主键',"+
		"	user_id		VARCHAR(255)  COMMENT '用户id  唯一键',"+
		"	nick_name		VARCHAR(255)  COMMENT '昵称',"+
		"	real_name		VARCHAR(255)  COMMENT '真实姓名',"+
		"	employee_no		VARCHAR(255)  COMMENT '员工号',"+
		"	avatar_url		VARCHAR(255)  COMMENT '头像路径',"+
		"	finger_token		VARCHAR(255)  COMMENT '飞哥令牌。（第三方登录）',"+
		"	create_time		timestamp  COMMENT '创建时间。',"+
		"	PRIMARY KEY(id)"+
		")COMMENT='用户表';";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE fingerchat_user("+
		"	id		NUMBER ,"+
		"	user_id		VARCHAR2(255) ,"+
		"	nick_name		VARCHAR2(255) ,"+
		"	real_name		VARCHAR2(255) ,"+
		"	employee_no		VARCHAR2(255) ,"+
		"	avatar_url		VARCHAR2(255) ,"+
		"	finger_token		VARCHAR2(255) ,"+
		"	create_time		TIMESTAMP ,"+
		"	PRIMARY KEY(id)"+
		");";
	
	
	/**
	 *获取主键
	 */
 	public  Long	getId() {
 		return id;
 	}
	
	/**
	 * 设值主键
	 */
 	public void	setId(Long id) {
 		this.id = id;
 	}
	
	
	
	/**
	 *获取用户id  唯一键
	 */
 	public  String	getUserId() {
 		return userId;
 	}
	/**
	 *获取昵称
	 */
 	public  String	getNickName() {
 		return nickName;
 	}
	/**
	 *获取真实姓名
	 */
 	public  String	getRealName() {
 		return realName;
 	}
	/**
	 *获取员工号
	 */
 	public  String	getEmployeeNo() {
 		return employeeNo;
 	}
	/**
	 *获取头像路径
	 */
 	public  String	getAvatarUrl() {
 		return avatarUrl;
 	}
	/**
	 *获取飞哥令牌。（第三方登录）
	 */
 	public  String	getFingerToken() {
 		return fingerToken;
 	}
	/**
	 *获取创建时间。
	 */
 	public  java.util.Date	getCreateTime() {
 		return createTime;
 	}
	
	/**
	 * 设值用户id  唯一键
	 */
 	public void	setUserId(String userId) {
 		this.userId = userId;
 	}
	/**
	 * 设值昵称
	 */
 	public void	setNickName(String nickName) {
 		this.nickName = nickName;
 	}
	/**
	 * 设值真实姓名
	 */
 	public void	setRealName(String realName) {
 		this.realName = realName;
 	}
	/**
	 * 设值员工号
	 */
 	public void	setEmployeeNo(String employeeNo) {
 		this.employeeNo = employeeNo;
 	}
	/**
	 * 设值头像路径
	 */
 	public void	setAvatarUrl(String avatarUrl) {
 		this.avatarUrl = avatarUrl;
 	}
	/**
	 * 设值飞哥令牌。（第三方登录）
	 */
 	public void	setFingerToken(String fingerToken) {
 		this.fingerToken = fingerToken;
 	}
	/**
	 * 设值创建时间。
	 */
 	public void	setCreateTime(java.util.Date createTime) {
 		this.createTime = createTime;
 	}
}
	
/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * 资源转让记录表Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://120.78.177.24:3306/docs?useSSL=false&useUnicode=true&characterEncoding=utf-8
 * <dt>scan
 * <dt>Zhouguozhu@123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "resource_transfer")
public class ResourceTransfer { // extends DataEntity<ResourceTransfer> {
//	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
//	@DbField(value="主键",isKey=true,type=0, title = "主键",show=false, canAdd = false, canEdit = false, dbFieldName = "id")
 	private Long	id;
	/**
	 * 资源id
	 */
//	@DbField(value="资源id",type=1,sort=1, title ="资源id", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "resource_id")
 	private Long	resourceId;
	/**
	 * 从哪个用户到哪个用户
	 */
//	@DbField(value="从哪个用户到哪个用户",type=1,sort=1, title ="从哪个用户到哪个用户", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "from_user")
 	private String	fromUser;
	/**
	 * 从哪个用户到哪个用户
	 */
//	@DbField(value="从哪个用户到哪个用户",type=1,sort=1, title ="从哪个用户到哪个用户", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "to_user")
 	private String	toUser;
	/**
	 * 消息产生时间
	 */
//	@DbField(value="消息产生时间",type=1,sort=1, title ="消息产生时间", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "create_time")
 	private java.util.Date	createTime;
	/**
	 * 备注
	 */
//	@DbField(value="备注",type=1,sort=1, title ="备注", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "remark")
 	private String	remark;
//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql="CREATE TABLE resource_transfer("+
		"	id		INT  COMMENT '主键',"+
		"	resource_id		INT  COMMENT '资源id',"+
		"	from_user		VARCHAR(64)  COMMENT '从哪个用户到哪个用户',"+
		"	to_user		VARCHAR(64)  COMMENT '从哪个用户到哪个用户',"+
		"	create_time		timestamp  COMMENT '消息产生时间',"+
		"	remark		VARCHAR(255)  COMMENT '备注',"+
		"	PRIMARY KEY(id)"+
		")COMMENT='资源转让记录表';";
//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql="CREATE TABLE resource_transfer("+
		"	id		NUMBER ,"+
		"	resource_id		NUMBER ,"+
		"	from_user		VARCHAR2(64) ,"+
		"	to_user		VARCHAR2(64) ,"+
		"	create_time		TIMESTAMP ,"+
		"	remark		VARCHAR2(255) ,"+
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
	 *获取资源id
	 */
 	public  Long	getResourceId() {
 		return resourceId;
 	}
	/**
	 *获取从哪个用户到哪个用户
	 */
 	public  String	getFromUser() {
 		return fromUser;
 	}
	/**
	 *获取从哪个用户到哪个用户
	 */
 	public  String	getToUser() {
 		return toUser;
 	}
	/**
	 *获取消息产生时间
	 */
 	public  java.util.Date	getCreateTime() {
 		return createTime;
 	}
	/**
	 *获取备注
	 */
 	public  String	getRemark() {
 		return remark;
 	}
	
	/**
	 * 设值资源id
	 */
 	public void	setResourceId(Long resourceId) {
 		this.resourceId = resourceId;
 	}
	/**
	 * 设值从哪个用户到哪个用户
	 */
 	public void	setFromUser(String fromUser) {
 		this.fromUser = fromUser;
 	}
	/**
	 * 设值从哪个用户到哪个用户
	 */
 	public void	setToUser(String toUser) {
 		this.toUser = toUser;
 	}
	/**
	 * 设值消息产生时间
	 */
 	public void	setCreateTime(java.util.Date createTime) {
 		this.createTime = createTime;
 	}
	/**
	 * 设值备注
	 */
 	public void	setRemark(String remark) {
 		this.remark = remark;
 	}
}
	
/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * 项目的层次目录Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "directory")
public class Directory { // extends DataEntity<Directory> {
	//	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	//	@DbField(value="主键",type=1,sort=1, title ="主键", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "id")
	private Long id;
	/**
	 * 父节点
	 */
	//	@DbField(value="父节点",type=1,sort=1, title ="父节点", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "parent_id")
	private Long parentId;
	/**
	 * 所属项目
	 */
	//	@DbField(value="所属项目",type=1,sort=1, title ="所属项目", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "app_id")
	private Long appId;
	/**
	 * 目录名称
	 */
	//	@DbField(value="目录名称",type=1,sort=1, title ="目录名称", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "name")
	private String name;
	/**
	 * 创建时间
	 */
	//	@DbField(value="创建时间",type=1,sort=1, title ="创建时间", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "create_time")
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	//	@DbField(value="更新时间",type=1,sort=1, title ="更新时间", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "update_time")
	private java.util.Date updateTime;
	/**
	 * 创建用户
	 */
	//	@DbField(value="创建用户",type=1,sort=1, title ="创建用户", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "create_user")
	private String createUser;
	/**
	 * 更新用户
	 */
	//	@DbField(value="更新用户",type=1,sort=1, title ="更新用户", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "update_user")
	private String updateUser;
	/**
	 * 备注
	 */
	//	@DbField(value="备注",type=1,sort=1, title ="备注", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "remark")
	private String remark;
	
	private String owner;
	private Integer sort;
	

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	/**
	 *获取主键
	 */
	public Long getId() {
		return id;
	}

	/**
	 *获取父节点
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 *获取所属项目
	 */
	public Long getAppId() {
		return appId;
	}

	/**
	 *获取目录名称
	 */
	public String getName() {
		return name;
	}

	/**
	 *获取创建时间
	 */
	public java.util.Date getCreateTime() {
		return createTime;
	}

	/**
	 *获取更新时间
	 */
	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	/**
	 *获取创建用户
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 *获取更新用户
	 */
	public String getUpdateUser() {
		return updateUser;
	}

	/**
	 *获取备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设值主键
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 设值父节点
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * 设值所属项目
	 */
	public void setAppId(Long appId) {
		this.appId = appId;
	}

	/**
	 * 设值目录名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设值创建时间
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 设值更新时间
	 */
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 设值创建用户
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 设值更新用户
	 */
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	/**
	 * 设值备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
}

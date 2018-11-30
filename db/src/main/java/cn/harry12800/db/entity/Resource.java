/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * 资源：自动生成文档，markdown文档，上传的文件Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "resource")
public class Resource { // extends DataEntity<Resource> {
	//	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	//	@DbField(value="主键",type=1,sort=1, title ="主键", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "id")
	private Long id;
	/**
	 * 所属目录
	 */
	//	@DbField(value="所属目录",type=1,sort=1, title ="所属目录", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "directory_id")
	private Long directoryId;
	/**
	 * 所属项目
	 */
	//	@DbField(value="所属项目",type=1,sort=1, title ="所属项目", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "app_id")
	private Long appId;
	/**
	 * 名称
	 */
	//	@DbField(value="名称",type=1,sort=1, title ="名称", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "name")
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
	 * 1= markdown ,2=other
	 */
	//	@DbField(value="1= markdown ,2=other",type=1,sort=1, title ="1= markdown ,2=other", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "resource_type")
	private Integer resourceType;
	/**
	 * 
	 */
	//	@DbField(value="null",type=1,sort=1, title ="null", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "remark")
	private String remark;
	/**
	 * 是否资源公开(所有人都可以查阅）
	 */
	//	@DbField(value="是否资源公开(所有人都可以查阅）",type=1,sort=1, title ="是否资源公开(所有人都可以查阅）", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "readable")
	private Integer readable;

	private String owner;
	
	private Integer cipher;
	private Integer hint;
	private Integer sort;
	 
	/**
	 *获取主键
	 */
	public Long getId() {
		return id;
	}

	/**
	 *获取所属目录
	 */
	public Long getDirectoryId() {
		return directoryId;
	}

	/**
	 *获取所属项目
	 */
	public Long getAppId() {
		return appId;
	}

	/**
	 *获取名称
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
	 *获取1= markdown ,2=other
	 */
	public Integer getResourceType() {
		return resourceType;
	}

	/**
	 *获取
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 *获取是否资源公开(所有人都可以查阅）
	 */
	public Integer getReadable() {
		return readable;
	}

	/**
	 * 设值主键
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 设值所属目录
	 */
	public void setDirectoryId(Long directoryId) {
		this.directoryId = directoryId;
	}

	/**
	 * 设值所属项目
	 */
	public void setAppId(Long appId) {
		this.appId = appId;
	}

	/**
	 * 设值名称
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
	 * 设值1= markdown ,2=other
	 */
	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * 设值
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 设值是否资源公开(所有人都可以查阅）
	 */
	public void setReadable(Integer readable) {
		this.readable = readable;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", directoryId=" + directoryId + ", appId=" + appId + ", name=" + name + ", createTime=" + createTime + ", updateTime=" + updateTime + ", createUser="
				+ createUser + ", updateUser=" + updateUser + ", resourceType=" + resourceType + ", remark=" + remark + ", readable=" + readable + ", owner=" + owner + "]";
	}

	public Integer getCipher() {
		return cipher;
	}

	public void setCipher(Integer cipher) {
		this.cipher = cipher;
	}

	public Integer getHint() {
		return hint;
	}

	public void setHint(Integer hint) {
		this.hint = hint;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}

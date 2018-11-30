package cn.harry12800.vchat.entity;

public class DiaryCatalog {

	private String id;
	private String parentId;
	private String name;
	private Integer sort;
	private String userId;
	private java.util.Date createTime;
	private java.util.Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "DiaryCatalog [id=" + id + ", parentId=" + parentId + ", name=" + name + ", sort=" + sort + ", userId=" + userId + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

}

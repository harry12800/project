package cn.harry12800.common.module.packet.entity;

import io.protostuff.Tag;

/**
 * 用户信息
 * 
 * @author harry12800
 *
 */
public class UserEnity {

	/**
	 * id
	 */
	@Tag(1)
	public long id;
 
	/**
	 * 用户名
	 */
	@Tag(2)
	public String userId;

	/**
	 * 昵称
	 */
	@Tag(3)
	public String nickName;
	/**
	 * 真实姓名
	 */
	@Tag(4)
	public String realName;
	/**
	 * 头像路径
	 */
	@Tag(5)
	public String avatarUrl;
	/**
	 * 创建时间。
	 */
	@Tag(6)
	public Long createTime;
	@Tag(7)
	public String phone;
	@Tag(8)
	public String sex;
	@Tag(9)
	public String mail;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	@Override
	public String toString() {
		return "UserEnity [id=" + id + ", userId=" + userId + ", nickName=" + nickName + ", realName=" + realName
				+ ", avatarUrl=" + avatarUrl + ", createTime=" + createTime + ", phone=" + phone + ", sex=" + sex
				+ ", mail=" + mail + "]";
	}

}

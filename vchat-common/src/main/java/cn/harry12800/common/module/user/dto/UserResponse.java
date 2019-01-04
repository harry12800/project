package cn.harry12800.common.module.user.dto;

import cn.harry12800.common.core.serial.Serializer;

/**
 * 用户信息
 * 
 * @author harry12800
 *
 */
public class UserResponse extends Serializer {

	/**
	 * id
	 */
	private long id;
 
	/**
	 * 用户名
	 */
	private String userId;

	/**
	 * 昵称
	 */
	private String nickName;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 头像路径
	 */
	private String avatarUrl;
	/**
	 * 创建时间。
	 */
	private Long createTime;
	private String phone;
	private String sex;
	private String mail;

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

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
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
	protected void read() {
		this.id = readLong();
		this.userId = readString();
		this.avatarUrl =readString();
		this.createTime =readLong();
		this.mail =readString();
		this.nickName =readString();
		this.phone =readString();
		this.realName =readString();
		this.sex = readString();
	}

	@Override
	protected void write() {
		writeLong(id);
		writeString(userId);
		writeString(avatarUrl);
		writeLong(createTime);
		writeString(mail);
		writeString(nickName);
		writeString(phone);
		writeString(realName);
		writeString(sex);
	}
}

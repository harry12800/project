/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
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
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "user")
public class UserInfo { // extends DataEntity<User> {
	//	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	//	@DbField(value="主键",type=1,sort=1, title ="主键", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "id")
	private Long id;
	/**
	 * 用户id  唯一键
	 */
	//	@DbField(value="用户id  唯一键",type=1,sort=1, title ="用户id  唯一键", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "user_id")
	private String userId;
	/**
	 * 昵称
	 */
	//	@DbField(value="昵称",type=1,sort=1, title ="昵称", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "nick_name")
	private String nickName;
	/**
	 * 真实姓名
	 */
	//	@DbField(value="真实姓名",type=1,sort=1, title ="真实姓名", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "real_name")
	private String realName;
	/**
	 * 员工号
	 */
	//	@DbField(value="员工号",type=1,sort=1, title ="员工号", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "employee_no")
	private String employeeNo;
	/**
	 * 头像路径
	 */
	//	@DbField(value="头像路径",type=1,sort=1, title ="头像路径", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "avatar_url")
	private String avatarUrl;
	/**
	 * 飞哥令牌。（第三方登录）
	 */
	private String password;
	/**
	 * 飞哥令牌。（第三方登录）
	 */
	private String salt;
	/**
	 * 创建时间。
	 */
	//	@DbField(value="创建时间。",type=1,sort=1, title ="创建时间。", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "create_time")
	private java.util.Date createTime;

	private String phone;
	private String sex;
	private String mail;
	
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

	/**
	 *获取主键
	 */
	public Long getId() {
		return id;
	}

	/**
	 *获取用户id  唯一键
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 *获取昵称
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 *获取真实姓名
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 *获取员工号
	 */
	public String getEmployeeNo() {
		return employeeNo;
	}

	/**
	 *获取头像路径
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}

	/**
	 *获取创建时间。
	 */
	public java.util.Date getCreateTime() {
		return createTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * 设值主键
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 设值用户id  唯一键
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 设值昵称
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * 设值真实姓名
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 设值员工号
	 */
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	/**
	 * 设值头像路径
	 */
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	/**
	 * 设值创建时间。
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}

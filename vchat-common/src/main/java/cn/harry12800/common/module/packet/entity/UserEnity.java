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

}

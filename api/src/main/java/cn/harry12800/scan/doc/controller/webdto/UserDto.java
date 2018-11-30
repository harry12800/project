/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.scan.doc.controller.webdto;

import java.util.Date;

import cn.harry12800.db.entity.FingerChatUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户表Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
@ApiModel
public class UserDto {

	@ApiModelProperty("主键")
	public Long id;
	@ApiModelProperty("用户id  唯一键")
	public String userId = "";
	@ApiModelProperty("昵称")
	public String nickName = "";
	@ApiModelProperty("真实姓名")
	public String realName = "";
	@ApiModelProperty("员工号")
	public String employeeNo = "";
	@ApiModelProperty("头像路径")
	public String avatarUrl = "";
	@ApiModelProperty("飞哥令牌。（第三方登录）")
	public String fingerToken = "";
	//	@ApiModelProperty("创建时间。")
	//	public Date createTime;

	public FingerChatUser toUserEntity() {
		FingerChatUser user = new FingerChatUser();
		user.setId(id);
		user.setUserId(userId);
		user.setNickName(nickName);
		user.setRealName(realName);
		user.setEmployeeNo(employeeNo);
		user.setAvatarUrl(avatarUrl);
		user.setFingerToken(fingerToken);
		user.setCreateTime(new Date());
		return user;
	}
}

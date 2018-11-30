package cn.harry12800.db.service;

import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.module.user.dto.ShowAllUserResponse;
import cn.harry12800.common.module.user.dto.UserResponse;
import cn.harry12800.db.entity.UserInfo;

/**
 * 用户基本接口
 * @author harry12800
 *
 */
public interface UserService {

	/**
	 * 登录
	 * @param userName
	 * @param passward
	 * @return
	 */
	public UserResponse login(Session session, String userName, String passward);
	
	/**
	 * 所有用户
	 * @return
	 */
	public ShowAllUserResponse showAllUser(Session session);

	public UserInfo findByUserId(String userId);

}

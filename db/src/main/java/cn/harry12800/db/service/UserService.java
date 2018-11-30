package cn.harry12800.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.harry12800.common.core.exception.ErrorCodeException;
import cn.harry12800.common.core.model.ResultCode;
import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.core.session.SessionManager;
import cn.harry12800.common.module.user.dto.ShowAllUserResponse;
import cn.harry12800.common.module.user.dto.UserResponse;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.db.mapper.UserInfoMapper;

/**
 * 用户服务
 * 
 * @author harry12800
 * 
 */
@Component
public class UserService {

	@Autowired
	private UserInfoMapper userMapper;


	public UserResponse login(Session session, String userName, String passward) {

		// 判断当前会话是否已登录
		if (session.getAttachment() != null) {
			throw new ErrorCodeException(ResultCode.HAS_LOGIN);
		}
		// 用户不存在
		UserInfo user = userMapper.findByUserId(userName);
		if (user == null) {
			throw new ErrorCodeException(ResultCode.USER_NO_EXIST);
		}

		// 密码错误
		if (!user.getPassword().equals(passward)) {
			throw new ErrorCodeException(ResultCode.PASSWARD_ERROR);
		}

		// 判断是否在其他地方登录过
		boolean onlineUser = SessionManager.isOnlineUser(user.getId());
		if (onlineUser) {
			Session oldSession = SessionManager.removeSession(user.getId());
			oldSession.removeAttachment();
			// 踢下线
			oldSession.close();
		}

		// 加入在线用户会话
		if (SessionManager.putSession(user.getId(), session)) {
			session.setAttachment(user);
		} else {
			throw new ErrorCodeException(ResultCode.LOGIN_FAIL);
		}

		// 创建Response传输对象返回
		UserResponse userResponse = new UserResponse();
		userResponse.setId(user.getId());
		userResponse.setUserId(user.getUserId());
		userResponse.setAvatarUrl(user.getAvatarUrl());
		userResponse.setCreateTime(user.getCreateTime().getTime());
		userResponse.setMail(user.getMail());
		userResponse.setNickName(user.getNickName());
		userResponse.setPhone(user.getPhone());
		userResponse.setRealName(user.getRealName());
		userResponse.setSex(user.getSex());
		return userResponse;
	}

	public ShowAllUserResponse showAllUser(Session session) {
		Object attachment = session.getAttachment();
		UserInfo user = (UserInfo) attachment;
		List<UserInfo> lists = userMapper.getAllUserExcept(user.getId());
		ShowAllUserResponse showAllUserResponse = new ShowAllUserResponse();
		for (UserInfo item : lists) {
			if (item.getId() == user.getId())
				continue;
			UserResponse userResponse = new UserResponse();
			userResponse.setId(item.getId());
			userResponse.setUserId(item.getUserId());
			userResponse.setAvatarUrl(item.getAvatarUrl());
			userResponse.setCreateTime(item.getCreateTime().getTime());
			userResponse.setMail(item.getMail());
			userResponse.setNickName(item.getNickName());
			userResponse.setPhone(item.getPhone());
			userResponse.setRealName(item.getRealName());
			userResponse.setSex(item.getSex());
			showAllUserResponse.getUsers().add(userResponse);
			System.out.println(item);
		}
		return showAllUserResponse;
	}

	public UserInfo findByUserId(String userId) {
		// 用户不存在
		UserInfo user = userMapper.findByUserId(userId);
		if (user == null) {
			throw new ErrorCodeException(ResultCode.USER_NO_EXIST);
		}
		return user;
	}

	public void saveOrUpdate(String user_id, String access_token, Double expires_in) {
		
	}
}

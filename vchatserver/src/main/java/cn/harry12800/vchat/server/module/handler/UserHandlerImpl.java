package cn.harry12800.vchat.server.module.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.harry12800.common.core.exception.ErrorCodeException;
import cn.harry12800.common.core.model.Result;
import cn.harry12800.common.core.model.ResultCode;
import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.module.user.dto.LoginRequest;
import cn.harry12800.common.module.user.dto.PullMsgRequest;
import cn.harry12800.common.module.user.dto.PullMsgResponse;
import cn.harry12800.common.module.user.dto.ShowAllUserRequest;
import cn.harry12800.common.module.user.dto.ShowAllUserResponse;
import cn.harry12800.common.module.user.dto.UserResponse;
import cn.harry12800.vchat.server.module.service.ChatService;
import cn.harry12800.vchat.server.module.service.UserService;

/**
 * 用户模块
 * @author harry12800
 *
 */
@Component
public class UserHandlerImpl implements UserHandler {

	@Autowired
	private UserService userService;
	@Autowired
	private ChatService chatService ;

	@Override
	public Result<UserResponse> login(Session session, byte[] data) {
		UserResponse result = null;
		try {
			//反序列化
			LoginRequest loginRequest = new LoginRequest();
			loginRequest.readFromBytes(data);

			//参数判空
			if (StringUtils.isEmpty(loginRequest.getUserName()) || StringUtils.isEmpty(loginRequest.getPassward())) {
				return Result.ERROR(ResultCode.USERNAME_NULL);
			}

			//执行业务
			result = userService.login(session, loginRequest.getUserName(), loginRequest.getPassward());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS(result);
	}

	@Override
	public Result<ShowAllUserResponse> showAllUser(Session session, byte[] data) {
		ShowAllUserResponse result = null;
		try {
			//反序列化
			ShowAllUserRequest loginRequest = new ShowAllUserRequest();
			loginRequest.readFromBytes(data);

			//执行业务
			result = userService.showAllUser(session);
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS(result);
	}

	@Override
	public Result<PullMsgResponse> pullMsg(Session session, byte[] data) {
		PullMsgResponse result = null;
		try {
			//反序列化
			PullMsgRequest request = new PullMsgRequest();
			request.readFromBytes(data);

			//执行业务
			result = chatService.pullMsg(request.getUserid());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS(result);
	}

}

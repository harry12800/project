package cn.harry12800.vchat.server.module.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.harry12800.common.core.exception.ErrorCodeException;
import cn.harry12800.common.core.model.Result;
import cn.harry12800.common.core.model.ResultCode;
import cn.harry12800.common.core.session.SessionManager;
import cn.harry12800.common.module.ChatCmd;
import cn.harry12800.common.module.ModuleId;
import cn.harry12800.common.module.chat.dto.FileChatRequest;
import cn.harry12800.common.module.chat.dto.FileChatResponse;
import cn.harry12800.common.module.chat.dto.MsgResponse;
import cn.harry12800.common.module.chat.dto.PrivateChatRequest;
import cn.harry12800.common.module.chat.dto.PublicChatRequest;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.db.service.ChatService;
import cn.harry12800.db.service.UserInfoService;

@Component
public class ChatHandlerImpl implements ChatHandler {

	@Autowired
	private ChatService chatService;
	@Autowired
	private UserInfoService userService;

	@Override
	public Result<?> publicChat(long userId, byte[] data) {
		try {
			//反序列化
			PublicChatRequest request = new PublicChatRequest();
			request.readFromBytes(data);

			//参数校验
			if (StringUtils.isEmpty(request.getContext())) {
				return Result.ERROR(ResultCode.AGRUMENT_ERROR);
			}

			//执行业务
			chatService.publicChat(userId, request.getContext());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS();
	}

	@Override
	public Result<MsgResponse> privateChat(long userId, byte[] data) {
		MsgResponse privateChat;
		try {
			//反序列化
			PrivateChatRequest request = new PrivateChatRequest();
			request.readFromBytes(data);
			//参数校验
			if (StringUtils.isEmpty(request.getContext()) || request.getTargetUserId() <= 0) {
				return Result.ERROR(ResultCode.AGRUMENT_ERROR);
			}
			//执行业务
			privateChat = chatService.privateChat(userId, request.getTargetUserId(), request.getContext());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS(privateChat);
	}


	@Override
	public Result<?> FileChat(long userId, byte[] data) {
		try {
			//反序列化
			FileChatRequest request = new FileChatRequest();
			request.readFromBytes(data);
			//参数校验
			UserInfo user = userService.findById(request.getTargetUserId());
			if (user == null) {
				return Result.ERROR(ResultCode.AGRUMENT_ERROR);
			}
			FileChatResponse e = new FileChatResponse();
			e.ok = "ok";
//			SessionManager.sendMessage(user.getId(), ModuleId.CHAT, ChatCmd.FILE_CHAT, request);
//			SessionManager.sendMessage(userId, ModuleId.CHAT, ChatCmd.FILE_CHAT_RESULT, e);
			//执行业务
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS();
	}
}

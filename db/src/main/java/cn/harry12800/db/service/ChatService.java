package cn.harry12800.db.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.harry12800.common.core.exception.ErrorCodeException;
import cn.harry12800.common.core.model.ResultCode;
import cn.harry12800.common.core.session.SessionManager;
import cn.harry12800.common.module.ChatCmd;
import cn.harry12800.common.module.ModuleId;
import cn.harry12800.common.module.chat.dto.MsgResponse;
import cn.harry12800.common.module.user.dto.PullMsgResponse;
import cn.harry12800.db.entity.ChatMsg;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.db.mapper.ChatMsgMapper;
import cn.harry12800.db.mapper.UserInfoMapper;

/**
 * 聊天服务
 * @author harry12800
 *
 */
@Component
public class ChatService   {

	@Autowired
	private UserInfoMapper userMapper;
	@Autowired
	private ChatMsgMapper chatMsgMapper;

	public void publicChat(long userId, String content) {

//		User user = userMapper.getUserById(userId);

		//获取所有在线用户
//		Set<Long> onlineUsers = SessionManager.getOnlineUsers();

		//创建消息对象

		//发送消息
//		for (long targetUserId : onlineUsers) {
//			SessionManager.sendMessage(targetUserId, ModuleId.CHAT, ChatCmd.PUSHCHAT, chatResponse);
//		}

	}
	public MsgResponse privateChat(long userId, long targetUserId, String content) {
		//不能和自己私聊
		if (userId == targetUserId) {
			throw new ErrorCodeException(ResultCode.CAN_CHAT_YOUSELF);
		}

		//判断目标是否存在
		UserInfo targetUser = userMapper.findById(targetUserId);
		UserInfo user = userMapper.findById(userId);
		if (targetUser == null) {
			throw new ErrorCodeException(ResultCode.USER_NO_EXIST);
		}

		//判断对方是否在线
		if (!SessionManager.isOnlineUser(targetUserId)) {
			ChatMsg msg = new ChatMsg();
			msg.setDataType(1);
			msg.setArrive(2);
			msg.setCome(user.getId());
			msg.setData(content.getBytes());
			msg.setSendTime(new Date());
			msg.setGo(targetUser.getId());
			chatMsgMapper.createChatMsg(msg);
			 
			MsgResponse e = new MsgResponse();
			e.setId(msg.getId())
					.setSendTime(msg.getSendTime())
					.setDataType(msg.getDataType())
					.setOnline(2)
					.setFromId(user.getId())
					.setToId(targetUser.getId())
					.setData(msg.getData());
			//给自己也回一个(偷懒做法)
//			SessionManager.sendMessage(userId, ModuleId.CHAT, ChatCmd.PUSHCHAT, e);
			return e;
//			throw new ErrorCodeException(ResultCode.USER_NO_ONLINE);
		}

		ChatMsg msg = new ChatMsg();
		msg.setDataType(1);
		msg.setArrive(1);
		msg.setCome(user.getId());
		msg.setData(content.getBytes());
		msg.setSendTime(new Date());
		msg.setGo(targetUser.getId());
		chatMsgMapper.createChatMsg(msg);
		//创建消息对象
		MsgResponse e = new MsgResponse();
		e.setId(msg.getId())
				.setSendTime(msg.getSendTime())
				.setDataType(msg.getDataType())
				.setFromId(user.getId())
				.setToId(targetUser.getId())
				.setData(msg.getData());
		//给目标对象发送消息
		SessionManager.sendMessage(targetUserId, ModuleId.CHAT, ChatCmd.PUSHCHAT, e);
//		//给自己也回一个
//		SessionManager.sendMessage(userId, ModuleId.CHAT, ChatCmd.PUSHCHAT, e);
		
		return e;
	}

	public PullMsgResponse pullMsg(long userid) {
		List<ChatMsg> userById = chatMsgMapper.getUnReadInfo(userid);
		chatMsgMapper.setReadInfo(userid);
		PullMsgResponse resp = new PullMsgResponse();
		for (ChatMsg a : userById) {
			MsgResponse e = new MsgResponse();
			e.setId(a.getId())
					.setSendTime(a.getSendTime())
					.setDataType(a.getDataType())
					.setFromId(a.getCome())
					.setToId(a.getGo())
					.setData(a.getData())	
					.setOnline(2);
			resp.getMsgs().add(e);
		}
		return resp;
	}
}

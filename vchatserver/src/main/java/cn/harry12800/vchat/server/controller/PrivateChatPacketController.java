package cn.harry12800.vchat.server.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.harry12800.common.core.exception.ErrorCodeException;
import cn.harry12800.common.core.model.ResultCode;
import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.core.session.SessionManager;
import cn.harry12800.common.module.packet.PrivateChatPacket;
import cn.harry12800.db.entity.ChatMsg;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.db.mapper.ChatMsgMapper;
import cn.harry12800.db.mapper.UserInfoMapper;
import cn.harry12800.vchat.server.server.Session;
import cn.harry12800.vchat.server.server.bussess.ServerIP;
import cn.harry12800.vchat.server.server.bussess.ServerServlet;

@Component
@ServerIP(desc = "用户私聊", ip = "0.0.3.1", reqType = PrivateChatPacket.Request.class)
public class PrivateChatPacketController extends ServerServlet<PrivateChatPacket.Request, PrivateChatPacket.Response> {

	@Autowired
	UserInfoMapper userMapper;
	@Autowired
	private ChatMsgMapper chatMsgMapper;

	@Override
	public Packet<PrivateChatPacket.Response> todo(Session session, Packet<PrivateChatPacket.Request> t) {
		PrivateChatPacket.Response res = new PrivateChatPacket.Response();
		Packet<PrivateChatPacket.Response> packet = new Packet<>();
		Object attachment = session.getAttachment();
		UserInfo user = (UserInfo) attachment;
		System.err.println(SessionManager.isOnlineUser(user.getId()));
		// 不能和自己私聊
		System.err.println(t.body);
		if (t.body.targetUserId == user.getId()) {
			throw new ErrorCodeException(ResultCode.CAN_CHAT_YOUSELF);
		}
		// 判断目标是否存在
		UserInfo targetUser = userMapper.findById(t.body.targetUserId);
		user = userMapper.findById(user.getId());
		if (targetUser == null) {
			throw new ErrorCodeException(ResultCode.USER_NO_EXIST);
		}
		// 判断对方是否在线
		ChatMsg msg = new ChatMsg();
		
		boolean online = SessionManager.isOnlineUser(t.body.targetUserId);
		boolean online1 = SessionManager.isOnlineUser(user.getId());
		System.out.println(online+ "Rpc:"+online1+""+t.body.targetUserId);
		System.out.println(user);
		System.out.println(t.body.targetUserId);
		if (!online) {
			System.out.println("对方不在线");
			msg.setDataType(1);
			msg.setArrive(2);
			msg.setCome(user.getId());
			msg.setData(t.body.content.getBytes());
			msg.setSendTime(new Date());
			msg.setGo(targetUser.getId());
			chatMsgMapper.createChatMsg(msg);
		}else {
			System.out.println("对方在线");
			msg.setDataType(1);
			msg.setArrive(1);
			msg.setCome(user.getId());
			msg.setData(t.body.content.getBytes());
			msg.setSendTime(new Date());
			msg.setGo(targetUser.getId());
			chatMsgMapper.createChatMsg(msg);
			t.body.targetUserId = user.getId();
			SessionManager.sendMessage(targetUser.getId(), t);
		}
		// 创建消息对象

		packet.header = t.header;
		packet.header.commandId++;
		packet.body = res;
		return (Packet<PrivateChatPacket.Response>) packet;
	}

}

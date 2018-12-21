package cn.harry12800.vchat.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.core.session.SessionManager;
import cn.harry12800.common.module.packet.PrivateChatPacket;
import cn.harry12800.common.module.packet.PullMsgPacket;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.db.mapper.ChatMsgMapper;
import cn.harry12800.db.mapper.UserInfoMapper;
import cn.harry12800.vchat.server.server.Session;
import cn.harry12800.vchat.server.server.bussess.ServerIP;
import cn.harry12800.vchat.server.server.bussess.ServerServlet;

@Component
@ServerIP(desc = "拉取离线消息包", ip = "0.0.3.7", reqType = PullMsgPacket.Request.class)
public class PullOffLineMsgPacketController extends ServerServlet<PullMsgPacket.Request, PullMsgPacket.Response> {

	@Autowired
	UserInfoMapper userMapper;
	@Autowired
	private ChatMsgMapper chatMsgMapper;

	@Override
	public Packet<PullMsgPacket.Response> todo(Session session, Packet<PullMsgPacket.Request> t) {
		PrivateChatPacket.Response res = new PrivateChatPacket.Response();
		Packet<PrivateChatPacket.Response> packet = new Packet<>();
		Object attachment = session.getAttachment();
		UserInfo user = (UserInfo) attachment;
		System.err.println(SessionManager.isOnlineUser(user.getId()));
		packet.header = t.header;
		packet.header.commandId++;
		packet.body = res;
		return null;
	}

}

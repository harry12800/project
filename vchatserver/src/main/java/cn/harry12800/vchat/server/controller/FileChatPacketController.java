package cn.harry12800.vchat.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.core.session.SessionManager;
import cn.harry12800.common.module.packet.FileChatPacket;
import cn.harry12800.db.mapper.UserInfoMapper;
import cn.harry12800.vchat.server.server.bussess.ServerIP;
import cn.harry12800.vchat.server.server.bussess.ServerServlet;

@Component
@ServerIP(desc = "文件私聊", ip = "0.0.6.1", reqType = FileChatPacket.Request.class)
public class FileChatPacketController extends ServerServlet<FileChatPacket.Request, FileChatPacket.Response> {

	@Autowired
	UserInfoMapper userMapper;

	@Override
	public Packet<FileChatPacket.Response> todo(Session session, Packet<FileChatPacket.Request> t) {
		FileChatPacket.Response res = new FileChatPacket.Response();
		Packet<FileChatPacket.Response> packet = new Packet<>();
		long targetUserId = t.body.targetUserId;
		boolean onlineUser = SessionManager.isOnlineUser(targetUserId);
		System.out.println(t.body);
		if(onlineUser) {
			SessionManager.sendMessage(t.body.targetUserId, t);
		}
		// 创建消息对象
		packet.header =FileChatPacket.copyHeader();
		packet.header.commandId++;
		packet.body = res;
		return (Packet<FileChatPacket.Response>) packet;
	}

}

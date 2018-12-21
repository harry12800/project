package cn.harry12800.vchat.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.module.packet.PullMsgPacket;
import cn.harry12800.common.module.packet.entity.MsgEntity;
import cn.harry12800.db.entity.ChatMsg;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.db.mapper.ChatMsgMapper;
import cn.harry12800.db.mapper.UserInfoMapper;
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
		PullMsgPacket.Response res = new PullMsgPacket.Response();
		Packet<PullMsgPacket.Response> packet = new Packet<>();
		Object attachment = session.getAttachment();
		UserInfo user = (UserInfo) attachment;
		List<ChatMsg> userById = chatMsgMapper.getUnReadInfo(user.getId());
		chatMsgMapper.setReadInfo(user.getId());
		for (ChatMsg a : userById) {
			MsgEntity e = new MsgEntity();
			e.data =a.getData();
			e.dataType = a.getDataType();
			e.fromId = a.getCome();
			e.id = a.getId();
			e.online = a.getArrive();
			e.sendTime = a.getSendTime();
			e.toId = a.getGo();
			res.msgs.add(e);
		}
		packet.header = t.header;
		packet.header.commandId++;
		packet.body = res;
		return null;
	}

}

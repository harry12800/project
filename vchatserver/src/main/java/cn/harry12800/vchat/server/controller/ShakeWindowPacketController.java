package cn.harry12800.vchat.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.harry12800.common.core.exception.ErrorCodeException;
import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.core.session.SessionManager;
import cn.harry12800.common.module.packet.ShakeWindowPacket;
import cn.harry12800.db.mapper.UserInfoMapper;
import cn.harry12800.vchat.server.server.bussess.ServerIP;
import cn.harry12800.vchat.server.server.bussess.ServerServlet;

@Component
@ServerIP(desc = "抖动窗口", ip = "0.0.3.11", reqType = ShakeWindowPacket.Request.class)
public class ShakeWindowPacketController extends ServerServlet<ShakeWindowPacket.Request, ShakeWindowPacket.Response> {

	@Autowired
	UserInfoMapper userMapper;

	@Override
	public Packet<ShakeWindowPacket.Response> todo(Session session, Packet<ShakeWindowPacket.Request> t) {
		ShakeWindowPacket.Response res = new ShakeWindowPacket.Response();
		Packet<ShakeWindowPacket.Response> packet = new Packet<>();
		packet.header = ShakeWindowPacket.copyHeader();
		try {
			if (SessionManager.isOnlineUser(t.body.targetUserId)) {
				SessionManager.sendMessage(t.body.targetUserId, t);
			}else{
				res.ok = 15;
			}
		} catch (Exception e) {
			
			if(e instanceof ErrorCodeException){
				packet.header.dataType = (short) ((ErrorCodeException) e).getErrorCode();
			}else{
				packet.header.dataType = 3;
			}
			
		}
		packet.header.commandId++;
		packet.body = res;
		return (Packet<ShakeWindowPacket.Response>) packet;
	}

}

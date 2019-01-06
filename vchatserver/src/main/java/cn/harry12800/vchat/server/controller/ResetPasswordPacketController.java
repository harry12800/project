<<<<<<< .mine
package cn.harry12800.vchat.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.module.packet.ResetPasswordPacket;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.db.mapper.UserInfoMapper;
import cn.harry12800.vchat.server.server.bussess.ServerIP;
import cn.harry12800.vchat.server.server.bussess.ServerServlet;

@Component
@ServerIP(desc = "修改密码请求", ip = "0.0.8.1", reqType = ResetPasswordPacket.Request.class)
public class ResetPasswordPacketController
		extends ServerServlet<ResetPasswordPacket.Request, ResetPasswordPacket.Response> {

	private static final Logger LOG = LoggerFactory.getLogger(ResetPasswordPacketController.class);
	@Autowired
	UserInfoMapper userMapper;

	@Override
	public Packet<ResetPasswordPacket.Response> todo(Session session, Packet<ResetPasswordPacket.Request> t) {
		ResetPasswordPacket.Response res = new ResetPasswordPacket.Response();
		Packet<ResetPasswordPacket.Response> packet = new Packet<>();
		Object attachment = session.getAttachment();
		UserInfo user = (UserInfo) attachment;
		packet.header = ResetPasswordPacket.copyHeader();
		packet.header.commandId++;
		packet.body = res;
		if (t.body.userId != user.getId()) {
			res.ok = 14;
			return (Packet<ResetPasswordPacket.Response>) packet;
		}
		UserInfo dbUser = userMapper.findById(t.body.userId);
		// 创建消息对象
		LOG.info("{}:{}",dbUser.getPassword(),t.body.oldPwd);
		if (!dbUser.getPassword().equals(t.body.oldPwd) ) {
			res.ok = 13;
			return (Packet<ResetPasswordPacket.Response>) packet;
		}
		dbUser.setPassword(t.body.newPwd);
		userMapper.update(dbUser);
		res.ok = 0;
		return (Packet<ResetPasswordPacket.Response>) packet;
	}

}
=======
package cn.harry12800.vchat.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.module.packet.ResetPasswordPacket;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.db.mapper.UserInfoMapper;
import cn.harry12800.vchat.server.server.bussess.ServerIP;
import cn.harry12800.vchat.server.server.bussess.ServerServlet;

@Component
@ServerIP(desc = "修改密码请求", ip = "0.0.8.1", reqType = ResetPasswordPacket.Request.class)
public class ResetPasswordPacketController
		extends ServerServlet<ResetPasswordPacket.Request, ResetPasswordPacket.Response> {

	private static final Logger LOG = LoggerFactory.getLogger(ResetPasswordPacketController.class);
	@Autowired
	UserInfoMapper userMapper;

	@Override
	public Packet<ResetPasswordPacket.Response> todo(Session session, Packet<ResetPasswordPacket.Request> t) {
		ResetPasswordPacket.Response res = new ResetPasswordPacket.Response();
		Packet<ResetPasswordPacket.Response> packet = new Packet<>();
		Object attachment = session.getAttachment();
		UserInfo user = (UserInfo) attachment;
		packet.header = ResetPasswordPacket.copyHeader();
		packet.header.commandId++;
		packet.body = res;
		if (t.body.userId != user.getId()) {
			res.ok = 1;
			return (Packet<ResetPasswordPacket.Response>) packet;
		}
		UserInfo dbUser = userMapper.findById(t.body.userId);
		// 创建消息对象
		LOG.info("{}:{}",dbUser.getPassword(),t.body.oldPwd);
		if (!dbUser.getPassword().equals(t.body.oldPwd) ) {
			res.ok = 2;
			return (Packet<ResetPasswordPacket.Response>) packet;
		}
		dbUser.setPassword(t.body.newPwd);
		userMapper.update(dbUser);
		res.ok = 0;
		return (Packet<ResetPasswordPacket.Response>) packet;
	}

}
>>>>>>> .theirs

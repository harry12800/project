package cn.harry12800.vchat.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.module.packet.PullAllUserPacket;
import cn.harry12800.common.module.packet.entity.UserEnity;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.db.mapper.UserInfoMapper;
import cn.harry12800.vchat.server.server.Session;
import cn.harry12800.vchat.server.server.SessionManager;
import cn.harry12800.vchat.server.server.bussess.ServerIP;
import cn.harry12800.vchat.server.server.bussess.ServerServlet;

@Component
@ServerIP(desc = "拉取所有用户", ip = "0.0.2.14", reqType = PullAllUserPacket.Request.class)
public class PullAllUserPacketController extends ServerServlet<PullAllUserPacket.Request, PullAllUserPacket.Response> {

	@Autowired
	UserInfoMapper userMapper;

	@Override
	public Packet<PullAllUserPacket.Response> todo(Session session, Packet<PullAllUserPacket.Request> t) {
		PullAllUserPacket.Response res = new PullAllUserPacket.Response();
		Packet<PullAllUserPacket.Response> packet = new Packet<>();
		Object attachment = session.getAttachment();
		UserInfo user = (UserInfo) attachment;
		System.err.println(SessionManager.isOnlineUser(user.getId()));
		List<UserInfo> lists = userMapper.getAllUserExcept(user.getId());
		for (UserInfo item : lists) {
			if (item.getId() == user.getId())
				continue;
			UserEnity userEntity = new UserEnity();
			userEntity.setId(item.getId());
			userEntity.setUserId(item.getUserId());
			userEntity.setAvatarUrl(item.getAvatarUrl());
			userEntity.setCreateTime(item.getCreateTime().getTime());
			userEntity.setMail(item.getMail());
			userEntity.setNickName(item.getNickName());
			userEntity.setPhone(item.getPhone());
			userEntity.setRealName(item.getRealName());
			userEntity.setSex(item.getSex());
			res.users.add(userEntity);
			System.out.println(item);
		}
		packet.header = t.header;
		packet.header.commandId++;
		packet.body = res;
		return (Packet<PullAllUserPacket.Response>) packet;
	}

}

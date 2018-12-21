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
import cn.harry12800.vchat.server.server.bussess.ServerIP;
import cn.harry12800.vchat.server.server.bussess.ServerServlet;

@Component
@ServerIP(desc = "用户登录", ip = "0.0.2.14", reqType = PullAllUserPacket.Request.class)
public class PullAllUserPacketController extends ServerServlet<PullAllUserPacket.Request, PullAllUserPacket.Response> {

	@Autowired
	UserInfoMapper userMapper;

	@Override
	public Packet<PullAllUserPacket.Response> todo(Session session, Packet<PullAllUserPacket.Request> t) {
		PullAllUserPacket.Response res = new PullAllUserPacket.Response();
		Packet<PullAllUserPacket.Response> packet = new Packet<>();
		Object attachment = session.getAttachment();
		UserInfo user = (UserInfo) attachment;
		List<UserInfo> lists = userMapper.getAllUserExcept(user.getId());
		for (UserInfo item : lists) {
			if (item.getId() == user.getId())
				continue;
			UserEnity userResponse = new UserEnity();
			userResponse.setId(item.getId());
			userResponse.setUserId(item.getUserId());
			userResponse.setAvatarUrl(item.getAvatarUrl());
			userResponse.setCreateTime(item.getCreateTime().getTime());
			userResponse.setMail(item.getMail());
			userResponse.setNickName(item.getNickName());
			userResponse.setPhone(item.getPhone());
			userResponse.setRealName(item.getRealName());
			userResponse.setSex(item.getSex());
			res.users.add(userResponse);
			System.out.println(item);
		}
		packet.header = t.header;
		packet.header.commandId++;
		packet.body = res;
		return (Packet<PullAllUserPacket.Response>) packet;
	}

}

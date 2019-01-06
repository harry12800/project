package cn.harry12800.vchat.server.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.harry12800.common.core.exception.ErrorCodeException;
import cn.harry12800.common.core.model.ResultCode;
import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.core.session.SessionManager;
import cn.harry12800.common.module.packet.LoginPacket;
import cn.harry12800.common.module.packet.UserOnlineOROffLinePacket;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.db.mapper.UserInfoMapper;
import cn.harry12800.vchat.server.server.bussess.ServerIP;
import cn.harry12800.vchat.server.server.bussess.ServerServlet;

@Component
@ServerIP(desc = "用户登录", ip = "0.0.1.3", reqType = LoginPacket.Request.class)
public class LoginPacketController extends ServerServlet<LoginPacket.Request, LoginPacket.Response> {
	private static Logger LOG = LoggerFactory.getLogger(LoginPacketController.class);
	@Autowired
	UserInfoMapper userMapper;

	@Override
	public Packet<LoginPacket.Response> todo(Session session, Packet<LoginPacket.Request> t) {
		LoginPacket.Response res = new LoginPacket.Response();
		Packet<LoginPacket.Response> packet = new Packet<>();
		try {
			// 参数判空
			if (StringUtils.isEmpty(t.body.userName) || StringUtils.isEmpty(t.body.passward)) {

			}
			// 判断当前会话是否已登录
			if (session.getAttachment() != null) {
				throw new ErrorCodeException(ResultCode.HAS_LOGIN);
			}
			// 用户不存在
			UserInfo user = userMapper.findByUserId(t.body.userName);
			if (user == null) {
				throw new ErrorCodeException(ResultCode.USER_NO_EXIST);
			}

			// 密码错误
			if (!user.getPassword().equals(t.body.passward)) {
				throw new ErrorCodeException(ResultCode.PASSWARD_ERROR);
			}

			// 判断是否在其他地方登录过
			boolean onlineUser = SessionManager.isOnlineUser(user.getId());
			if (onlineUser) {
				Session oldSession = SessionManager.removeSession(user.getId());
				oldSession.removeAttachment();
				// 踢下线
				oldSession.close();
			}
			// 获取所有在线用户
			Set<Long> onlineUsers = SessionManager.getOnlineUsers();
			// 创建消息对象
			// 发送消息
			UserOnlineOROffLinePacket userOnlineOROffLinePacket = new UserOnlineOROffLinePacket(user.getId(),
					user.getNickName(), true);
			for (long targetUserId : onlineUsers) {
				SessionManager.sendMessage(targetUserId, userOnlineOROffLinePacket.requestPacket);
			}
			// 加入在线用户会话
			if (SessionManager.putSession(user.getId(), session)) {
				session.setAttachment(user);
			} else {
				throw new ErrorCodeException(ResultCode.LOGIN_FAIL);
			}
			LOG.info("{}", SessionManager.isOnlineUser(user.getId()));
			// 创建Response传输对象返回
			res.setId(user.getId());
			res.setUserId(user.getUserId());
			res.setAvatarUrl(user.getAvatarUrl());
			res.setCreateTime(user.getCreateTime().getTime());
			res.setMail(user.getMail());
			res.setNickName(user.getNickName());
			res.setPhone(user.getPhone());
			res.setRealName(user.getRealName());
			res.setSex(user.getSex());
			packet.header = t.header;
			packet.header.commandId++;
			packet.body = res;
		} catch (ErrorCodeException e) {
			packet.header = LoginPacket.copyHeader();
			packet.header.dataType = (short) e.getErrorCode();
			packet.header.commandId++;
			packet.body = res;
		}
		return (Packet<LoginPacket.Response>) packet;
	}

}

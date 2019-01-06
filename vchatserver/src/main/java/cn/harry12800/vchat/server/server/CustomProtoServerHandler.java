package cn.harry12800.vchat.server.server;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.harry12800.common.core.exception.ErrorCodeException;
import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.core.session.SessionImpl;
import cn.harry12800.common.core.session.SessionManager;
import cn.harry12800.common.module.packet.UserOnlineOROffLinePacket;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.vchat.server.server.bussess.Invoker;
import cn.harry12800.vchat.server.server.bussess.InvokerHoler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 消息接受处理类
 * 
 * @author harry12800
 *
 */
public class CustomProtoServerHandler extends SimpleChannelInboundHandler<Packet<?>> {
	private static Logger LOG = LoggerFactory.getLogger(CustomProtoServerHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.err.println(" 连接激活");
		super.channelActive(ctx);
	}

	/** 空闲次数 */
	private int idle_count = 1;
	/** 发送次数 */
	// private int count = 1;

	/**
	 * 超时处理 如果5秒没有接受客户端的心跳，就触发; 如果超过两次，则直接关闭;
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
		if (obj instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) obj;
			if (IdleState.READER_IDLE.equals(event.state())) { // 如果读通道处于空闲状态，说明没有接收到心跳命令
				LOG.info("已经5秒没有接收到客户端的信息了");
				if (idle_count > 2) {
					LOG.info("关闭这个不活跃的channel");
					ctx.channel().close();
				}
				idle_count++;
			}
		} else {
			super.userEventTriggered(ctx, obj);
		}
	}

	/**
	 * 接收消息
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, Packet<?> request) throws Exception {
		// LOG.info("localAddress:" + ctx.channel().localAddress());
		// LOG.info("remoteAddress:" + ctx.channel().remoteAddress());
		// System.err.println(request.getClass());
		try {
			handlerMessage(new SessionImpl(ctx.channel()), request);
		} catch (ErrorCodeException e) {
			// e.printStackTrace();
			Packet<?> result = new Packet<>();
			result.header = request.header;
			result.header.commandId++;
			result.header.dataType = (short) e.getErrorCode();
			ctx.channel().writeAndFlush(result);

		}

	}

	/**
	 * 消息处理
	 * 
	 * @param channelId
	 * @param request
	 */
	private void handlerMessage(Session session, Packet<?> request) throws ErrorCodeException {

		// System.err.println(request.body);
		// 获取命令执行器
		Invoker invoker = InvokerHoler.getIpInvoker(request.header);
		if (invoker != null) {
			LOG.info("功能请求：" + invoker.getDesc());
			try {
				Packet<?> result = (Packet<?>) invoker.invoke(session, request);
				// LOG.info("返回数据："+result);
				if (result != null)
					session.write(result);
			} catch (Exception e) {
				e.printStackTrace();
				// 系统未知异常
			}
		} else {
			// 未找到执行者
			LOG.info("未找到执行者！");
			return;
		}
	}

	/**
	 * 断线移除会话
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//		Session session = new SessionImpl(ctx.channel());
//		Object object = session.getAttachment();
//		if (object != null) {
//			UserInfo user = (UserInfo) object;
//			LOG.info("掉线：" + user);
//			SessionManager.removeSession(user.getId());
//			// 获取所有在线用户
//			Set<Long> onlineUsers = SessionManager.getOnlineUsers();
//			// 创建消息对象
//			// 发送消息
//			UserOnlineOROffLinePacket userOnlineOROffLinePacket = new UserOnlineOROffLinePacket(user.getId(),user.getNickName(),false);
//			for (long targetUserId : onlineUsers) {
//				LOG.info("告诉" + targetUserId + "用户" + user.getId() + "已经掉线");
//				SessionManager.sendMessage(targetUserId,userOnlineOROffLinePacket.requestPacket);
//			}
//		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		Session session = new SessionImpl(ctx.channel());
		Object object = session.getAttachment();
		if (object != null) {
			UserInfo user = (UserInfo) object;
			LOG.info("掉线：" + user);
			SessionManager.removeSession(user.getId());
			// 获取所有在线用户
			Set<Long> onlineUsers = SessionManager.getOnlineUsers();
			// 创建消息对象
			// 发送消息
			UserOnlineOROffLinePacket userOnlineOROffLinePacket = new UserOnlineOROffLinePacket(user.getId(),user.getNickName(),false);
			for (long targetUserId : onlineUsers) {
				LOG.info("告诉" + targetUserId + "用户" + user.getId() + "已经掉线");
				SessionManager.sendMessage(targetUserId,userOnlineOROffLinePacket.requestPacket);
			}
		}
		try {
			super.exceptionCaught(ctx, cause);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

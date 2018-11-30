package cn.harry12800.vchat.server;

import java.util.Set;

import com.google.protobuf.GeneratedMessage;

import cn.harry12800.common.core.model.Request;
import cn.harry12800.common.core.model.Response;
import cn.harry12800.common.core.model.Result;
import cn.harry12800.common.core.model.ResultCode;
import cn.harry12800.common.core.serial.Serializer;
import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.core.session.SessionImpl;
import cn.harry12800.common.core.session.SessionManager;
import cn.harry12800.common.module.ChatCmd;
import cn.harry12800.common.module.ModuleId;
import cn.harry12800.common.module.chat.dto.OfflineResponse;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.vchat.server.scanner.Invoker;
import cn.harry12800.vchat.server.scanner.InvokerHoler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 消息接受处理类
 * @author harry12800
 *
 */
public class ServerHandler extends SimpleChannelInboundHandler<Request> {
	/** 空闲次数 */
	private int idle_count = 1;
	/** 发送次数 */
//	private int count = 1;

	/**
	 * 超时处理
	 * 如果5秒没有接受客户端的心跳，就触发;
	 * 如果超过两次，则直接关闭;
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
		if (obj instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) obj;
			if (IdleState.READER_IDLE.equals(event.state())) { //如果读通道处于空闲状态，说明没有接收到心跳命令
				System.out.println("已经5秒没有接收到客户端的信息了");
				if (idle_count > 2) {
					System.out.println("关闭这个不活跃的channel");
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
	public void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
		System.out.println("localAddress:" + ctx.channel().localAddress());
		System.out.println("remoteAddress:" + ctx.channel().remoteAddress());
		handlerMessage(new SessionImpl(ctx.channel()), request);
	}

	/**
	 * 消息处理
	 * @param channelId
	 * @param request
	 */
	private void handlerMessage(Session session, Request request) {

		Response response = new Response(request);
		System.out.println("module:" + request.getModule() + "   " + "cmd：" + request.getCmd());

		//获取命令执行器
		Invoker invoker = InvokerHoler.getInvoker(request.getModule(), request.getCmd());
		if (invoker != null) {
			System.out.println("功能请求：" + invoker.getDesc());
			try {
				Result<?> result = null;
				//假如是用户模块传入channel参数，否则传入userId参数
				if (request.getModule() == ModuleId.USER) {
					result = (Result<?>) invoker.invoke(session, request.getData());
				} else if (request.getModule() == ModuleId.RESOURCE) {
					result = (Result<?>) invoker.invoke(session, request.getData());
				} else {
					Object attachment = session.getAttachment();
					if (attachment != null) {
						UserInfo user = (UserInfo) attachment;
						result = (Result<?>) invoker.invoke(user.getId(), request.getData());
					} else {
						//会话未登录拒绝请求
						response.setStateCode(ResultCode.LOGIN_PLEASE);
						session.write(response);
						return;
					}
				}

				//判断请求是否成功
				if (result.getResultCode() == ResultCode.SUCCESS) {
					//回写数据
					Object object = result.getContent();
					if (object != null) {
						if (object instanceof Serializer) {
							Serializer content = (Serializer) object;
							response.setData(content.getBytes());
						} else if (object instanceof GeneratedMessage) {
							GeneratedMessage content = (GeneratedMessage) object;
							response.setData(content.toByteArray());
						} else {
							System.out.println(String.format("不可识别传输对象:%s", object));
						}
					}
					session.write(response);
				} else {
					//返回错误码
					response.setStateCode(result.getResultCode());
					session.write(response);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				//系统未知异常
				response.setStateCode(ResultCode.UNKOWN_EXCEPTION);
				session.write(response);
			}
		} else {
			//未找到执行者
			response.setStateCode(ResultCode.NO_INVOKER);
			session.write(response);
			return;
		}
	}

	/**
	 * 断线移除会话
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Session session = new SessionImpl(ctx.channel());
		Object object = session.getAttachment();
		if (object != null) {
			UserInfo user = (UserInfo) object;
			SessionManager.removeSession(user.getId());
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		Session session = new SessionImpl(ctx.channel());
		Object object = session.getAttachment();
		if (object != null) {
			UserInfo user = (UserInfo) object;
			System.out.println("掉线：" + user);
			SessionManager.removeSession(user.getId());
			//获取所有在线用户
			Set<Long> onlineUsers = SessionManager.getOnlineUsers();
			//创建消息对象
			//发送消息
			for (long targetUserId : onlineUsers) {
				OfflineResponse response = new OfflineResponse();
				response.userId = user.getId();
				System.out.println("告诉" + targetUserId + "用户" + user.getId() + "已经掉线");
				SessionManager.sendMessage(targetUserId, ModuleId.SYSTEM, ChatCmd.OFFLINE, response);
			}
		}
		//		try {
		//			super.exceptionCaught(ctx, cause);
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
	}
}

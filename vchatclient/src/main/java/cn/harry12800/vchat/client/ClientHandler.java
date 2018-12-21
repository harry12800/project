package cn.harry12800.vchat.client;

import java.util.Date;

import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.vchat.scaner.Invoker;
import cn.harry12800.vchat.scaner.InvokerHoler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;

/**
 * 消息接受处理类
 * @author harry12800
 *
 */
public class ClientHandler extends SimpleChannelInboundHandler<Packet<?>> {
	/** 客户端请求的心跳命令  */
	private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hb_request",
			CharsetUtil.UTF_8));

	/** 空闲次数 */
	//	private int idle_count = 1;

	/** 发送次数 */
	private int count = 1;

	/**循环次数 */
	@SuppressWarnings("unused")
	private int fcount = 1;

	private Client client;

	public ClientHandler(Client client) {
		this.client = client;
	}

	/**
	 * 接收消息
	 */
	public void channelRead0(ChannelHandlerContext ctx , Packet<?> msg) throws Exception {
		System.out.println("第" + count + "次" + ",客户端接受的消息:");
		count++;
		System.out.println(msg);
		handlerResponse(msg);
	}

	/**
	 * 心跳请求处理
	 * 每4秒发送一次心跳请求;
	 * 
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object obj) throws Exception {
//		System.out.println("循环发送心跳请求的时间：" + new Date() + "，次数" + fcount);
		if (obj instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) obj;
			if (IdleState.WRITER_IDLE.equals(event.state())) { //如果写通道处于空闲状态,就发送心跳命令
				//				if (idle_count <= 3) { //设置发送次数
				//				idle_count++;
				ctx.channel().writeAndFlush(HEARTBEAT_SEQUENCE.duplicate());
				//				} else {
//				System.out.println("发送心跳请求！");
				//				}
				fcount++;
			}
		}
	}

	/**
	 * 消息处理
	 * @param channelId
	 * @param request
	 */
	private void handlerResponse(Packet<?> response) {
		//获取命令执行器
		Invoker invoker = InvokerHoler.getIpInvoker(response.header);
		if(invoker==null){
			System.out.println("未找到命令执行器！"+response.header);
		}else{
			invoker.invoke(response);
		}
	}

	/**
	* 建立连接时
	*/
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("建立连接时：" + new Date());
		ctx.fireChannelActive();
	}

	/**
	 * 断开链接
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		ctx.fireChannelInactive();
		client.reconnect();
		System.out.println("与服务器断开连接~~~");
	}

}

package cn.harry12800.vchat.client;

import java.util.Date;

import javax.swing.SwingUtilities;

import cn.harry12800.common.core.model.Response;
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
public class ClientHandlerOld extends SimpleChannelInboundHandler<Response> {
	/** 客户端请求的心跳命令  */
	private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("hb_request",
			CharsetUtil.UTF_8));

	/** 空闲次数 */
	//	private int idle_count = 1;

	/** 发送次数 */
	private int count = 1;

	/**循环次数 */
	private int fcount = 1;

	private ClientOld client;

	public ClientHandlerOld(ClientOld client) {
		this.client = client;
	}

	/**
	 * 接收消息
	 */
	@Override
	public void channelRead0(ChannelHandlerContext ctx, Response response) throws Exception {
//		System.out.println("第" + count + "次" + ",客户端接受的消息:");
		count++;
		handlerResponse(response);
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
	private void handlerResponse(Response response) {
		//获取命令执行器
		Invoker invoker = InvokerHoler.getInvoker(response.getModule(), response.getCmd());
		if (invoker != null) {
			System.out.println("接收消息--module：" + response.getModule() + "  cmd：" + response.getCmd() + " 功能：" + invoker.getDesc());
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						invoker.invoke(response.getStateCode(), response.getData());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			//找不到执行器
			System.out.println(String.format("module:%s  cmd:%s 找不到命令执行器", response.getModule(), response.getCmd()));
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

	//	@Override
	//	public void run(Timeout timeout) throws Exception {
	//		 ChannelFuture future;
	//	        //bootstrap已经初始化好了，只需要将handler填入就可以了
	//	        synchronized (bootstrap) {
	//	            bootstrap.handler(new ChannelInitializer<Channel>(){
	//	                @Override
	//	                protected void initChannel(Channel ch) throws Exception {
	//	                    ch.pipeline().addLast(handlers());
	//	                }
	//	            });
	//	            future = bootstrap.connect(host,port);
	//	        }
	//	         //future对象
	//	         future.addListener(new ChannelFutureListener() {
	//	             public void operationComplete(ChannelFuture f) throws Exception {
	//	                 boolean succeed = f.isSuccess();
	//	         //如果重连失败，则调用ChannelInactive方法，再次出发重连事件，一直尝试12次，如果失败则不再重连
	//	                 if (!succeed) {
	//	                     System.out.println("重连失败");
	//	                     f.channel().pipeline().fireChannelInactive();
	//	                 }else{
	//	                     System.out.println("重连成功");
	//	                 }
	//	             }
	//	         });

}

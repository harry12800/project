package cn.harry12800.vchat.client;

import java.net.InetSocketAddress;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import cn.harry12800.common.core.codc.CustomProtobufDecoder;
import cn.harry12800.common.core.codc.CustomProtobufEncoder;
import cn.harry12800.common.core.packet.MessagePacket;
import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.module.packet.LoginPacket;
import cn.harry12800.j2se.utils.Config;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * netty客户端
 * 
 * @author harry12800
 * 
 */
public class Client {

 	/**
	 * 服务类
	 */
	Bootstrap bootstrap = new Bootstrap();

	Properties p = new Properties();
	String serverip = "127.0.0.1";
	String webHost = "http://127.0.0.1";
	int serverport = 10000;
	/**
	 * 会话
	 */
	private Channel channel;

	/**
	 * 线程池
	 */
	private EventLoopGroup workerGroup = new NioEventLoopGroup();

	private ChannelFutureListener channelFutureListener;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		config();
		// 设置循环线程组事例
		bootstrap.group(workerGroup);
		// 设置channel工厂
		bootstrap.channel(NioSocketChannel.class);
		// 设置管道
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				// ch.pipeline().addLast(new IdleStateHandler(0, 6, 0,
				// TimeUnit.SECONDS));
				ch.pipeline().addLast(new CustomProtobufDecoder());
				ch.pipeline().addLast(new CustomProtobufEncoder());
				ClientHandler clientHandler = new ClientHandler(Client.this);
				ch.pipeline().addLast(clientHandler);
			}
		});
		this.channelFutureListener = new ChannelFutureListener() {
			public void operationComplete(ChannelFuture f) throws Exception {
				// Log.d(Config.TAG, "isDone:" + f.isDone() + " isSuccess:" +
				// f.isSuccess() + " cause" + f.cause() + " isCancelled" +
				// f.isCancelled());
				if (f.isSuccess()) {
//					listener.exe();
					System.out.println("重新连接服务器成功");
				} else {
					System.out.println("重新连接服务器失败");
					// 3秒后重新连接
					f.channel().eventLoop().schedule(new Runnable() {
						@Override
						public void run() {
							reconnect();
						}
					}, 3, TimeUnit.SECONDS);
				}
			}
		};

	}

	private void config() {
		serverip = Config.getProp("server.ip");
		webHost = Config.getProp("webHost");
		serverport = Config.getIntProp("server.port");
		System.out.println(serverip);
		System.out.println(webHost);
		System.out.println(serverport);
	}

	/**
	 * 连接
	 * 
	 * @param ip
	 * @param port
	 * @throws InterruptedException
	 */
	public void connect() throws InterruptedException {
		// 连接服务端
		ChannelFuture connect = bootstrap.connect(new InetSocketAddress(serverip, serverport));
		connect.sync();
		channel = connect.channel();
		connect.isSuccess();
	}

	/**
	 * 连接
	 * 
	 * @param ip
	 * @param port
	 * @throws InterruptedException
	 */

	public void reconnect() {
		try {
			ChannelFuture connect = bootstrap.connect(new InetSocketAddress(serverip, serverport));
			// 连接服务端
			connect.addListener(channelFutureListener);
			connect.sync();
			channel = connect.channel();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭
	 */
	public void shutdown() {
		workerGroup.shutdownGracefully();
	}

	/**
	 * 获取会话
	 * 
	 * @return
	 */
	public Channel getChannel() {
		return channel;
	}

	/**
	 * 发送消息
	 * 
	 * @param request
	 * @throws InterruptedException
	 */
	public void sendRequest(Packet<?> request) throws InterruptedException {
		if (channel == null || !channel.isActive()) {
			connect();
		}
		channel.writeAndFlush(request);
	}

	public String getWebHost() {
		return webHost;
	}

	public void setWebHost(String webHost) {
		this.webHost = webHost;
	}
	/**
	 * 发送消息
	 * 
	 * @param request
	 * @throws InterruptedException
	 */
	public void sendLoginRequest(Packet<LoginPacket.Request> request) throws InterruptedException {
		if (channel == null || !channel.isActive()) {
			connect();
		}
		channel.writeAndFlush(request);
	}
	/**
	 * 发送消息
	 * 
	 * @param request
	 * @throws InterruptedException
	 */
	public void sendChatRequest(Packet<MessagePacket.PacketRequest> request) throws InterruptedException {
		if (channel == null || !channel.isActive()) {
			connect();
		}
		channel.writeAndFlush(request);
	}
}

package cn.harry12800.vchat.server.old;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import cn.harry12800.common.core.codc.RequestDecoder;
import cn.harry12800.common.core.codc.ResponseEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * netty服务端
 * 
 * @author harry12800
 * 
 */
@Component
public class Server {

	/**
	 * 启动
	 */
	public void start() {

		// 服务类
		ServerBootstrap b = new ServerBootstrap();

		// 创建boss和worker
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			// 设置循环线程组事例
			b.group(bossGroup, workerGroup);

			// 设置channel工厂
			b.channel(NioServerSocketChannel.class);

			// 设置管道
			b.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new IdleStateHandler(8, 0, 0, TimeUnit.SECONDS));
					ch.pipeline().addLast(new RequestDecoder());
					ch.pipeline().addLast(new ResponseEncoder());
					ch.pipeline().addLast(new ServerHandler());
				}
			});

			b.option(ChannelOption.SO_BACKLOG, 2048);// 链接缓冲池队列大小

			// 绑定端口
			b.bind(10000).sync();

			System.out.println("start!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

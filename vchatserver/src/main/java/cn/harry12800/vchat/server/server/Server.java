package cn.harry12800.vchat.server.server;

import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

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
			b.childHandler(new ServerChannelInitializer());
			b.option(ChannelOption.SO_BACKLOG, 2048);// 链接缓冲池队列大小

			// 绑定端口
			b.bind(10000).sync();

			System.out.println("start!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

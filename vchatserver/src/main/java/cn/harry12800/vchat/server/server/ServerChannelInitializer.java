package cn.harry12800.vchat.server.server;


import cn.harry12800.common.core.codc.CustomProtobufDecoder;
import cn.harry12800.common.core.codc.CustomProtobufEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
	EventLoopGroup jobGroup = new NioEventLoopGroup();

	public ServerChannelInitializer() {
	}

	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
//		pipeline.addLast(new IdleStateHandler(8, 0, 0, TimeUnit.SECONDS));
		pipeline.addLast("protobufDncoder", new CustomProtobufDecoder());
		pipeline.addLast("protobufEncoder", new CustomProtobufEncoder());
		pipeline.addLast( new CustomProtoServerHandler());
	}
//	发出：
//	10 10 104 97 114 114 121 49 50 56 48 48 18 9 -27 -111 -88 -27 -101 -67 -26 -108 -65 
}

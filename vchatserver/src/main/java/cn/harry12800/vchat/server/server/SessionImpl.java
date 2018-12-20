package cn.harry12800.vchat.server.server;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * 会话封装类
 * @author harry12800
 *
 */
public class SessionImpl implements Session {

	/**
	 * 绑定对象key
	 */
	public static AttributeKey<Object> ATTACHMENT_KEY = AttributeKey.valueOf("ATTACHMENT_KEY");

	/**
	 * 实际会话对象
	 */
	private Channel channel;

	public SessionImpl(Channel channel) {
		this.channel = channel;
	}

	@Override
	public Object getAttachment() {
		return channel.attr(ATTACHMENT_KEY).get();
	}

	@Override
	public void setAttachment(Object attachment) {
		channel.attr(ATTACHMENT_KEY).set(attachment);
	}

	@Override
	public void removeAttachment() {
		//		remove 过过期
		channel.attr(ATTACHMENT_KEY).set(null);
	}

	@Override
	public void write(Object message) {
		System.out.println("写出消息："+message);
		channel.writeAndFlush(message);
	}

	@Override
	public boolean isConnected() {
		return channel.isActive();
	}

	@Override
	public void close() {
		channel.close();
	}

}

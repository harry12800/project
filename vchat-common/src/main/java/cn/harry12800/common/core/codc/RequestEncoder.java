package cn.harry12800.common.core.codc;

import cn.harry12800.common.core.model.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <pre>
 * 数据包格式
 * +——----——+——-----——+——----——+——----——+——-----——+
 * |  包头	|  模块号         |  命令号      |   长度       |   数据          |
 * +——----——+——-----——+——----——+——----——+——-----——+
 * </pre>
 * @author harry12800
 *
 */
public class RequestEncoder extends MessageToByteEncoder<Request> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Request message, ByteBuf buffer) throws Exception {

		//包头
		buffer.writeInt(ConstantValue.HEADER_FLAG);
		//module
		buffer.writeShort(message.getModule());
		//cmd
		buffer.writeShort(message.getCmd());
		//长度
		int lenth = message.getData() == null ? 0 : message.getData().length;
		if (lenth <= 0) {
			buffer.writeInt(lenth);
		} else {
			buffer.writeInt(lenth);
			buffer.writeBytes(message.getData());
		}
	}
}

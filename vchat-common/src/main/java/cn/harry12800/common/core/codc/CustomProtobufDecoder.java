package cn.harry12800.common.core.codc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.harry12800.common.core.packet.base.BaseBody;
import cn.harry12800.common.core.packet.base.Header;
import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.core.util.PbUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

/**
 * 参考 ProtobufVarint32FrameDecoder 和 ProtobufDecoder
 */

public class CustomProtobufDecoder extends ByteToMessageDecoder {

	private static Logger Log  =LoggerFactory.getLogger(CustomProtobufDecoder.class);
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		Log.info("开始解析数据");
		in.markReaderIndex();
		int preIndex = in.readerIndex();
		int length = readRawVarint32(in);
		Log.info("总长度:" + length+"  "+preIndex);
		if (preIndex == in.readerIndex()) {
			return;
		}
		if (length < 0) {
			throw new CorruptedFrameException("negative length: " + length);
		}
        int y = in.readableBytes();
        Log.info("可以读的长度："+y);
		if (y < length) {
			in.resetReaderIndex();
			return;
		}
		preIndex = in.readerIndex();
		int headlength = readRawVarint32(in);
		Log.info("头长度:" + headlength);
		// 读取body
		int lastIndex = in.readerIndex();
		Log.info(preIndex + "  " + lastIndex);
		ByteBuf bodyByteBuf = in.readBytes(length - (lastIndex - preIndex));
		byte[] array;
		int offset;

		int readableLen = bodyByteBuf.readableBytes();
		if (bodyByteBuf.hasArray()) {
			array = bodyByteBuf.array();
			offset = bodyByteBuf.arrayOffset() + bodyByteBuf.readerIndex();
		} else {
			array = new byte[readableLen];
			bodyByteBuf.getBytes(bodyByteBuf.readerIndex(), array, 0, readableLen);
			offset = 0;
		}
		Packet<?> result = decodePacket(array, offset, readableLen, headlength);
		out.add(result);
	}

	public Packet<?> decodePacket(byte[] array, int offset, int length, int headlength) throws Exception {
		System.out.print("header:" + array.length + "  ");
		for (byte b : array) {
			System.out.print(b + " ");
		}
		System.out.println();
		Header header = PbUtil.decode(array, offset, headlength, Header.class);
		int serviceId = header.serviceId;
		int commandId = header.commandId;
		int ip = serviceId << 8 | commandId;
		Log.info("{}",header);
		Class<? extends BaseBody> class1 = HeaderBodyMap.get(ip);
		BaseBody body = PbUtil.decode(array, offset + headlength, length - headlength, class1);
		Packet<BaseBody> p = new Packet<BaseBody>();
		p.header = header;
		p.body = body;
		return p; // or throw exception
	}

	/**
	 * Reads variable length 32bit int from buffer
	 *
	 * @return decoded int if buffers readerIndex has been forwarded else
	 *         nonsense value
	 */
	private static int readRawVarint32(ByteBuf buffer) {
		if (!buffer.isReadable()) {
			return 0;
		}
		buffer.markReaderIndex();
		byte tmp = buffer.readByte();
		if (tmp >= 0) {
			return tmp;
		} else {
			int result = tmp & 127;
			if (!buffer.isReadable()) {
				buffer.resetReaderIndex();
				return 0;
			}
			if ((tmp = buffer.readByte()) >= 0) {
				result |= tmp << 7;
			} else {
				result |= (tmp & 127) << 7;
				if (!buffer.isReadable()) {
					buffer.resetReaderIndex();
					return 0;
				}
				if ((tmp = buffer.readByte()) >= 0) {
					result |= tmp << 14;
				} else {
					result |= (tmp & 127) << 14;
					if (!buffer.isReadable()) {
						buffer.resetReaderIndex();
						return 0;
					}
					if ((tmp = buffer.readByte()) >= 0) {
						result |= tmp << 21;
					} else {
						result |= (tmp & 127) << 21;
						if (!buffer.isReadable()) {
							buffer.resetReaderIndex();
							return 0;
						}
						result |= (tmp = buffer.readByte()) << 28;
						if (tmp < 0) {
							throw new CorruptedFrameException("malformed varint.");
						}
					}
				}
			}
			return result;
		}
	}
}
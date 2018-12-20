package cn.harry12800.common.core.packet;

import java.util.ArrayList;
import java.util.List;

import cn.harry12800.common.core.config.ProtocolConstant;
import cn.harry12800.common.core.config.SysConstant;
import cn.harry12800.common.core.packet.base.DefaultHeader;
import cn.harry12800.common.core.packet.base.GoBackPacket;
import cn.harry12800.common.core.packet.base.ReqBody;
import cn.harry12800.common.core.packet.base.RequestPacket;
import cn.harry12800.common.core.packet.base.RespBody;

/**
 * MsgServerPacket:请求(返回)登陆消息服务器 yugui 2014-05-04
 */

public class UnreadMsgPacket extends GoBackPacket {

//    private Logger logger = Logger.getLogger(UnreadMsgPacket.class);

    public UnreadMsgPacket(String contactId) {
        requestPacket = new RequestPacket();
        requestPacket.body = new PacketRequest(contactId);
        requestPacket.body.setNeedMonitor(true);
        requestPacket.header = new DefaultHeader(ProtocolConstant.SID_MSG,
                ProtocolConstant.CID_MSG_UNREAD_MSG_REUQEST);

        requestPacket.header.setLength(SysConstant.PROTOCOL_HEADER_LENGTH + (4 + 0));

    }

    public UnreadMsgPacket() {
        requestPacket = new RequestPacket();
        requestPacket.body = new PacketRequest("");
        requestPacket.body.setNeedMonitor(true);
    }

    public static class PacketRequest extends ReqBody {
        public String contactId;

        public PacketRequest(String contactId) {
            this.contactId = contactId;

        }
    }

    public static class PacketResponse extends RespBody {

        public List<MessageEntity> entityList = new ArrayList<MessageEntity>();
    }
}

package cn.harry12800.common.core.packet;

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

public class GroupChangeMemberNotify extends GoBackPacket {

//    private Logger logger = Logger.getLogger(GroupChangeMemberNotify.class);

    public GroupChangeMemberNotify() {
        // todo eric remove this
        requestPacket = new RequestPacket();
        requestPacket.body.mNeedMonitor = true;
    }

    public GroupChangeMemberNotify(PacketRequest.Entity entity) {
        requestPacket = new RequestPacket();
        requestPacket.body = new PacketRequest(entity);
        requestPacket.header = new DefaultHeader(ProtocolConstant.SID_GROUP,
                ProtocolConstant.CID_GROUP_CHANGE_MEMBER_REQUEST);
        requestPacket.body.mNeedMonitor = true;
        int contentLength = 9;
        requestPacket.header.setLength(SysConstant.PROTOCOL_HEADER_LENGTH + contentLength);
    }

    public static class PacketRequest extends ReqBody {
        public static class Entity {
            public String groupId;
            public int changeType;
            public List<String> memberList;
        }

        public PacketRequest.Entity entity;

        public PacketRequest(PacketRequest.Entity entity) {
            this.entity = entity;

        }
    }

    public static class PacketResponse extends RespBody {
        public static class Entity {
            String groupId;
            List<String> memberList;
        }

        public PacketResponse.Entity entity = new PacketResponse.Entity();
    }
}

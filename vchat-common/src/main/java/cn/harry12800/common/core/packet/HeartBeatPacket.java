
package cn.harry12800.common.core.packet;

import cn.harry12800.common.core.codc.HeaderBodyMap;
import cn.harry12800.common.core.config.ProtocolConstant;
import cn.harry12800.common.core.config.SequenceNumberMaker;
import cn.harry12800.common.core.config.SysConstant;
import cn.harry12800.common.core.packet.base.GoBackPacket;
import cn.harry12800.common.core.packet.base.Header;
import cn.harry12800.common.core.packet.base.ReqBody;
import cn.harry12800.common.core.packet.base.RequestPacket;
import cn.harry12800.common.core.packet.base.RespBody;

public class HeartBeatPacket extends GoBackPacket{

//    private Logger logger = Logger.getLogger(HeartBeatPacket.class);

    static{
        HeaderBodyMap.register(ProtocolConstant.SID_DEFAULT,ProtocolConstant.CID_HEART_BEAT,HeartBeatResponse.class);
    }
    public HeartBeatPacket() {
        requestPacket = new RequestPacket();
        requestPacket.body = new HeartBeatRequest();
        requestPacket.body.setNeedMonitor(false);
        Header recentcontactHeader = new Header();
        recentcontactHeader.version =((short) SysConstant.PROTOCOL_VERSION);
        recentcontactHeader.serviceId = ProtocolConstant.SID_DEFAULT;
        recentcontactHeader.commandId =ProtocolConstant.CID_HEART_BEAT;
        short seqNo = SequenceNumberMaker.getInstance().make();
        recentcontactHeader.reserved = seqNo;
        requestPacket.header = recentcontactHeader;
    }



    public static class HeartBeatRequest extends ReqBody {

        public HeartBeatRequest() {

        }
    }

    // 服务器目前不会给心跳进行回复，但是会主动发心跳过来
    public static class HeartBeatResponse extends RespBody {

    }
}


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
import io.protostuff.Tag;

/**
 * MsgServerPacket:请求(返回)分配一个消息服务器的IP和端口 yugui 2014-05-04
 */

public class MsgServerPacket extends GoBackPacket {

//    private Logger logger = Logger.getLogger(MsgServerPacket.class);
    static Header msrHeader;
    static {
        HeaderBodyMap.register(ProtocolConstant.SID_LOGIN,ProtocolConstant.CID_LOGIN_REQ_MSGSERVER,MsgServerResponse.class);
        HeaderBodyMap.register(ProtocolConstant.SID_LOGIN,ProtocolConstant.CID_LOGIN_REQ_MSGSERVER+1,MsgServerResponse.class);
            msrHeader = new Header();
            msrHeader.version = ((short) SysConstant.PROTOCOL_VERSION);
            msrHeader.serviceId =(ProtocolConstant.SID_LOGIN);
            msrHeader.commandId =(ProtocolConstant.CID_LOGIN_REQ_MSGSERVER);
    }
    public MsgServerPacket() {
        requestPacket= new RequestPacket();
        requestPacket.header = msrHeader;
        requestPacket.body = new MsgServerRequest();
        short seqNo = SequenceNumberMaker.getInstance().make();
        requestPacket.header.reserved = (seqNo);
        requestPacket.body.setNeedMonitor(true);
    }




    public static class MsgServerRequest  extends ReqBody {
        public MsgServerRequest() {

        }
    }

    public static class MsgServerResponse extends RespBody {
        @Tag(2)
        private int result;
        @Tag(3)
        private String strIp1;
        @Tag(4)
        private String strIp2;
        @Tag(5)
        private short port;
        public MsgServerResponse() {

        }

        @Override
        public String toString() {
            return "MsgServerResponse{" +
                    "result=" + result +
                    ", strIp1='" + strIp1 + '\'' +
                    ", strIp2='" + strIp2 + '\'' +
                    ", port=" + port +
                    '}';
        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public String getStrIp1() {
            return strIp1;
        }

        public void setStrIp1(String strIp1) {
            this.strIp1 = strIp1;
        }

        public String getStrIp2() {
            return strIp2;
        }

        public void setStrIp2(String strIp2) {
            this.strIp2 = strIp2;
        }

        public short getPort() {
            return port;
        }

        public void setPort(short port) {
            this.port = port;
        }

    }
}

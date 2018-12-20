package cn.harry12800.common.core.packet.base;

public abstract class GoBackPacket  {

    public RequestPacket requestPacket;
    public ResponsePacket responsePacket;
    public void setRequestHeader(Header h){
        requestPacket.header = h;
    }
    public void setResponseHeader(Header h){
        responsePacket.header = h;
    }

}

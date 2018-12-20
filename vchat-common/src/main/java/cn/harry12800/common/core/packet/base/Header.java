package cn.harry12800.common.core.packet.base;

import io.protostuff.Tag;

public class Header {
    @Tag(1)
    private int length; // 数据包长度，包括包头
    @Tag(2)
    private int serviceId; // SID
    @Tag(3)
    private int commandId; // CID
    @Tag(4)
    private short version; // 版本号
    @Tag(5)
    private short reserved; // 保留，可用于如序列号等

    public Header() {
        length = 0;
        version = 1;
        serviceId = 0;
        commandId = 0;
        reserved = 0;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getCommandId() {
        return commandId;
    }

    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }

    public short getVersion() {
        return version;
    }

    public void setVersion(short version) {
        this.version = version;
    }

    public short getReserved() {
        return reserved;
    }

    public void setReserved(short reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "Header [length=" + length + ", serviceId=" + serviceId + ", commandId=" + commandId + ", version="
                + version + ", reserved=" + reserved + "]";
    }
}

package cn.harry12800.common.core.packet.base;

public class Packet<T extends BaseBody> {
    public Header header;
    public T body;
    @Override
    public String toString() {
        return "Packet [header=" + header + ", body=" + body + "]";
    }
}

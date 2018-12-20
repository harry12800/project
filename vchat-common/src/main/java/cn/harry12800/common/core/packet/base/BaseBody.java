package cn.harry12800.common.core.packet.base;

import io.protostuff.Tag;

public class BaseBody {
    @Tag(1)
    public boolean mNeedMonitor;
    public void   setNeedMonitor(boolean b){
        mNeedMonitor  = b;
    }
}

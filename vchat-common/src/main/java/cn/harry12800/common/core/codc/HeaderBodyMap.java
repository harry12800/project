package cn.harry12800.common.core.codc;

import java.util.HashMap;
import java.util.Map;

import cn.harry12800.common.core.packet.base.BaseBody;


public class HeaderBodyMap {
    static Map<Integer,Class<? extends BaseBody>> map = new HashMap<Integer,Class<? extends BaseBody>>();

    public static void register(int ip,Class<?extends BaseBody> clazz){
        map.put(ip, clazz);
    }
    public static void register(String ip,Class<?extends BaseBody> clazz){
        String[] split = ip.split("[.]");
        Short b0 = Short.valueOf(split[0]);
        Short b1 = Short.valueOf(split[1]);
        Short b2 = Short.valueOf(split[2]);
        Short b3 = Short.valueOf(split[3]);
        Integer x = 0 | (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
        map.put(x, clazz);
    }

	public static void register(int serviceId ,int commandId,Class<?extends BaseBody> clazz){
        Integer x = 0 | (serviceId << 8) | commandId ;
        map.put(x, clazz);
    }
    public static Class<? extends BaseBody>  get(int ip){
        return  map.get(ip);
    }
}

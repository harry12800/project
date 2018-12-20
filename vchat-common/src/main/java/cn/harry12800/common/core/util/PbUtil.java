package cn.harry12800.common.core.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
/**
 * protobuf 工具类
 *
 * @author ww
 */
public class PbUtil {
    public static <T> byte[] encode(T obj) {
        Class<?> cls = obj.getClass();
        Schema<?> schema = getSchema(cls);
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] data = ProtobufIOUtil.toByteArray(obj, (Schema<T>) schema, buffer);
        return data;
    }

    public static <T> T decode(byte[] data, Class<T> t) {
        try {
            T object = t.newInstance();
            Schema<?> schema = getSchema(t);
//			System.err.println(object.getClass());
            ProtobufIOUtil.mergeFrom(data, object, (Schema<T>) schema);
            return object;
        } catch (Exception e) {e.printStackTrace();
            return null;
        }
    }
    public static <T> T decode(byte[] data,int offset, int length, Class<T> t) {
        try {
            T object = t.newInstance();
            Schema<?> schema = getSchema(t);
//			System.err.println(object.getClass());
            ProtobufIOUtil.mergeFrom(data,offset,length, object, (Schema<T>) schema);
            return object;
        } catch (Exception e) {e.printStackTrace();
            return null;
        }
    }
    private static Schema<?> getSchema(Class<?> cls) {
        Schema<?> schema = map.get(cls);
        if (schema == null) {
            synchronized (PbUtil.class) {
                if (schema == null) {
                    System.err.println(cls);
                    schema = RuntimeSchema.getSchema(cls);
                    System.err.println(schema);
                    map.put(cls, schema);
                }
            }
        }
        return schema;
    }

    static Map<Class<?>, Schema<?>> map = new ConcurrentHashMap<Class<?>, Schema<?>>();
}

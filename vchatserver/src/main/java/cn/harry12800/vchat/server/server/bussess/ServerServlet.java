package cn.harry12800.vchat.server.server.bussess;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import cn.harry12800.common.core.packet.base.BaseBody;
import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.core.session.Session;

public abstract class ServerServlet<A extends BaseBody, B extends BaseBody> {

	public Type aType;
	public Type bType;
	public Type getAClass() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		aType = actualTypeArguments[0];
		for (Type actualTypeArgument : actualTypeArguments) {
			System.out.println(actualTypeArgument);
		}
		return aType;

	}

	ServerServlet<A, B> head;
	ServerServlet<A, B> tail;
	protected Map<String, String> map;

	public abstract Packet<B> todo(Session session, Packet<A> t);
}

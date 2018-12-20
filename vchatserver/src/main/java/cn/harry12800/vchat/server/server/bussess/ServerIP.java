package cn.harry12800.vchat.server.server.bussess;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.harry12800.common.core.packet.base.BaseBody;

/**
 * 请求命令
 * @author harry12800
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServerIP {

	/**
	 * 请求的命令号
	 * @return
	 */
	String ip();
	Class<? extends BaseBody> reqType();
	String desc();
}

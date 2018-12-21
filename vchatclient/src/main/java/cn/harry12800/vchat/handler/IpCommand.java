package cn.harry12800.vchat.handler;

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
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IpCommand {

	String ip();
	Class<? extends BaseBody> bodyType();
	String desc();
}

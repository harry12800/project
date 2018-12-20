/**
 * 
 */
package cn.harry12800.common.core.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求命令
 * @author harry12800
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SocketCommand {

	/**
	 * 请求的命令号
	 * @return
	 */
	short cmd();

	String desc();
}

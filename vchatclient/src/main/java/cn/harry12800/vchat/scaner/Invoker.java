package cn.harry12800.vchat.scaner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 命令执行器
 * @author harry12800
 *
 */
public class Invoker {

	/**
	 * 方法
	 */
	private Method method;

	/**
	 * 目标对象
	 */
	private Object target;

	private String desc;

	public static Invoker valueOf(String desc, Method method, Object target) {
		Invoker invoker = new Invoker();
		invoker.setMethod(method);
		invoker.setTarget(target);
		invoker.setDesc(desc);
		return invoker;
	}

	/**
	 * 执行
	 * @param paramValues
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public Object invoke(Object... paramValues) {
		try {
			return method.invoke(target, paramValues);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}

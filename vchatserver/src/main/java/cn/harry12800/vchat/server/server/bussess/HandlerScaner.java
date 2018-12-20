package cn.harry12800.vchat.server.server.bussess;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import cn.harry12800.common.core.codc.HeaderBodyMap;
import cn.harry12800.common.core.packet.base.BaseBody;


/**
 * handler扫描器
 * 
 * @author harry12800
 *
 */
@Component
public class HandlerScaner implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		Class<? extends Object> clazz = bean.getClass();

		// 扫描类的所有接口父类
		// 判断是否为handler接口类
		ServerIP serverip = clazz.getAnnotation(ServerIP.class);
		if (serverip == null) {
			return bean;
		}
		System.err.println(clazz);
		String sip = serverip.ip();
		String desc = serverip.desc();
		Class<? extends BaseBody> reqType = serverip.reqType();
		// 找出命令方法
		Method[] methods = clazz.getMethods();
		if (methods != null && methods.length > 0) {
			for (Method method : methods) {
				if (method.getName().equals("todo")) {
					System.out.println("添加命令：" + desc);
					int ip = InvokerHoler.addIpInvoker(sip, Invoker.valueOf(desc, method, bean));
					HeaderBodyMap.register(ip, reqType);
				}
			}
		}
		return bean;
	}
}

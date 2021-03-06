package cn.harry12800.vchat.scaner;

// package cn.harry12800.lnk.core.scanner;
//
// import java.lang.reflect.Method;
//
// import org.springframework.beans.BeansException;
// import org.springframework.beans.factory.config.BeanPostProcessor;
// import org.springframework.stereotype.Component;
//
// import cn.harry12800.common.core.annotion.SocketCommand;
// import cn.harry12800.common.core.annotion.SocketModule;
//
/// **
// * handler扫描器
// * @author harry12800
// *
// */
// @Component
// public class HandlerScaner implements BeanPostProcessor {
//
// @Override
// public Object postProcessBeforeInitialization(Object bean, String beanName)
// throws BeansException {
// return bean;
// }
//
// @Override
// public Object postProcessAfterInitialization(Object bean, String beanName)
// throws BeansException {
//
// Class<? extends Object> clazz = bean.getClass();
//
// Class<?>[] interfaces = clazz.getInterfaces();
//
// if (interfaces != null && interfaces.length > 0) {
// //扫描类的所有接口父类
// for (Class<?> interFace : interfaces) {
// //判断是否为handler接口类
// SocketModule socketModule = interFace.getAnnotation(SocketModule.class);
// if (socketModule == null) {
// continue;
// }
//
// //找出命令方法
// Method[] methods = interFace.getMethods();
// if (methods != null && methods.length > 0) {
// for (Method method : methods) {
// SocketCommand socketCommand = method.getAnnotation(SocketCommand.class);
// if (socketCommand == null) {
// continue;
// }
//
// final short module = socketModule.module();
// final short cmd = socketCommand.cmd();
// final String desc = socketCommand.desc();
//
// if (InvokerHoler.getInvoker(module, cmd) == null) {
// InvokerHoler.addInvoker(module, cmd, Invoker.valueOf(desc, method, bean));
// } else {
// LOG.info("重复命令:" + "module:" + module + " " + "cmd：" + cmd);
// }
// }
// }
//
// }
// }
// return bean;
// }
//
// }

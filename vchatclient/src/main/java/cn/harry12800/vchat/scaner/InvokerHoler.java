package cn.harry12800.vchat.scaner;

import java.util.HashMap;
import java.util.Map;

import cn.harry12800.common.core.packet.base.Header;



/**
 * 命令执行器管理者
 * 
 * @author harry12800
 *
 */
public class InvokerHoler {

	/** 命令调用器 */
	private static Map<Short, Map<Short, Invoker>> invokers = new HashMap<>();
	private static Map<Integer, Invoker> ipinvokers = new HashMap<>();

	/**
	 * 添加命令调用
	 * 
	 * @param module
	 * @param cmd
	 * @param invoker
	 */
	public static void addInvoker(short module, short cmd, Invoker invoker) {
		Map<Short, Invoker> map = invokers.get(module);
		if (map == null) {
			map = new HashMap<>();
			invokers.put(module, map);
		}
		map.put(cmd, invoker);
	}

	/**
	 * 添加命令调用
	 * 
	 * @param module
	 * @param cmd
	 * @param invoker
	 */
	public static void addIpInvoker(int ip, Invoker invoker) {
		ipinvokers.put(ip, invoker);
	}

	/**
	 * 添加命令调用
	 * 
	 * @param module
	 * @param cmd
	 * @param invoker
	 */
	public static int addIpInvoker(String ip, Invoker invoker) {
		String[] split = ip.split("[.]");
		Short b0 = Short.valueOf(split[0]);
		Short b1 = Short.valueOf(split[1]);
		Short b2 = Short.valueOf(split[2]);
		Short b3 = Short.valueOf(split[3]);
		Integer x = 0 | (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
		System.out.println(Integer.MAX_VALUE + "  " + x);
		ipinvokers.put(x, invoker);
		return x;
	}

	public static void main(String[] args) {
		addIpInvoker("0.0.0.255", null);
		addIpInvoker("0.0.255.255", null);
		addIpInvoker("0.255.255.255", null);
		addIpInvoker("255.255.255.255", null);
	}

	/**
	 * 获取命令调用
	 * 
	 * @param module
	 * @param cmd
	 * @param invoker
	 */
	public static Invoker getInvoker(short module, short cmd) {
		Map<Short, Invoker> map = invokers.get(module);
		if (map != null) {
			return map.get(cmd);
		}
		return null;
	}

	/**
	 * 获取命令调用
	 * 
	 * @param module
	 * @param cmd
	 * @param invoker
	 */
	public static Invoker getIpInvoker(Header header) {
		int serviceId = header.serviceId;
		int commandId = header.commandId;
		int ip = serviceId << 8 | commandId;
		Invoker invoker = ipinvokers.get(ip);
		return invoker;
	}
	
	/**
	 * 获取命令调用
	 * 
	 * @param module
	 * @param cmd
	 * @param invoker
	 */
	public static Invoker getIpInvoker(String ip) {
		String[] split = ip.split("[.]");
		Short b0 = Short.valueOf(split[0]);
		Short b1 = Short.valueOf(split[1]);
		Short b2 = Short.valueOf(split[2]);
		Short b3 = Short.valueOf(split[3]);
		Integer x = 0 | (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
		Invoker invoker = ipinvokers.get(x);
		return invoker;
	}
}

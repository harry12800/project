package cn.harry12800.vchat.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.harry12800.common.core.packet.base.Packet;
import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.module.packet.ExecuteShellPacket;
import cn.harry12800.db.mapper.UserInfoMapper;
import cn.harry12800.tools.MachineUtils;
import cn.harry12800.vchat.server.server.bussess.ServerIP;
import cn.harry12800.vchat.server.server.bussess.ServerServlet;

@Component
@ServerIP(desc = "脚本执行", ip = "0.0.9.1", reqType = ExecuteShellPacket.Request.class)
public class ExecuteShellPacketController extends ServerServlet<ExecuteShellPacket.Request, ExecuteShellPacket.Response> {

	@Autowired
	UserInfoMapper userMapper;

	@Override
	public Packet<ExecuteShellPacket.Response> todo(Session session, Packet<ExecuteShellPacket.Request> t) {
		ExecuteShellPacket.Response res = new ExecuteShellPacket.Response();
		Packet<ExecuteShellPacket.Response> packet = new Packet<>();
		System.out.println(t.body);
		String sentence = t.body.shell;
		try {
			String runtimeOut = MachineUtils.runtimeOut(sentence);
			res.result = runtimeOut;
		} catch (Exception e) {
			e.printStackTrace();
			res.ok = 17;
			res.result = e.getMessage();
		}
		// 创建消息对象
		packet.header =ExecuteShellPacket.copyHeader();
		packet.header.commandId++;
		packet.body = res;
		return (Packet<ExecuteShellPacket.Response>) packet;
	}

}

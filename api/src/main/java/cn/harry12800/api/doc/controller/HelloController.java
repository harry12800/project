package cn.harry12800.api.doc.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.harry12800.api.doc.http.MyResponse;
import cn.harry12800.tools.MachineUtils;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/monitor")
public class HelloController {
	private static final Logger LOG = LoggerFactory.getLogger(HelloController.class);
	private static final String API_Tags = "服务存活检测接口 /HelloController";

	public HelloController() {
		LOG.info(HelloController.class.getName());
	}

	@ApiOperation(value = "服务存活检测", tags = { API_Tags }, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(path = { "/hello" }, method = RequestMethod.GET)
	public MyResponse hello(HttpServletRequest request) {
		MyResponse r = MyResponse.newOk();
		r.setContent("hello, 你好！");
		return r;
	}

	@ApiOperation(value = "服务存活检测", tags = { API_Tags }, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(path = { "/version" }, method = RequestMethod.GET)
	public MyResponse version(HttpServletRequest request) {
		MyResponse r = MyResponse.newOk();
		r.setContent("hello, 你好！");
		return r;
	}
	@ApiOperation(value = "重新启动", tags = { API_Tags }, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(path = { "/restartScan" }, method = RequestMethod.GET)
	public MyResponse restartScan(HttpServletRequest request) {
		MyResponse r = MyResponse.newOk();
		try {
			Runtime.getRuntime().exec("java -jar /root/scan/scan.jar");
		} catch (IOException e) {
			MyResponse setMsg = MyResponse.newServerError().setMsg(e.getMessage());
			e.printStackTrace();
			return setMsg;
		}
		return r;
	}
	@ApiOperation(value = "重新启动", tags = { API_Tags }, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(path = { "/restartVchatServer" }, method = RequestMethod.GET)
	public MyResponse restartVchatServer(HttpServletRequest request) {
		MyResponse r = MyResponse.newOk();
		try {
			Runtime.getRuntime().exec("java -jar /root/vchat/server/vchat-server-1.0.jar");
		} catch (IOException e) {
			MyResponse setMsg = MyResponse.newServerError().setMsg(e.getMessage());
			e.printStackTrace();
			return setMsg;
		}
		return r;
	}
	@ApiOperation(value = "脚本启动", tags = { API_Tags }, produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping(path = { "/exeshell" }, method = RequestMethod.GET)
	public MyResponse exeshell(HttpServletRequest request, @RequestParam String sentence) {
		MyResponse r = MyResponse.newOk();
		if(StringUtils.isAllBlank(sentence))
			return MyResponse.newBad();
		try {
			String runtimeOut = MachineUtils.runtimeOut(sentence);
			r.setContent(runtimeOut);
		} catch (IOException e) {
			MyResponse setMsg = MyResponse.newServerError().setMsg(e.getMessage());
			e.printStackTrace();
			return setMsg;
		}
		return r;
	}
}

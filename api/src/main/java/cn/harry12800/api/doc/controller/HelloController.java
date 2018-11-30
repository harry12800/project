package cn.harry12800.api.doc.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.harry12800.api.doc.http.MyResponse;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1")
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
}

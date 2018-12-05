package cn.harry12800.scan.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.harry12800.api.doc.http.MyResponse;
import cn.harry12800.api.doc.http.MyResponse.EResponseCode;
import cn.harry12800.api.redis.JedisUtil;
import cn.harry12800.db.service.AppService;
import cn.harry12800.db.service.DiaryCatalogService;
import cn.harry12800.db.service.DiaryService;
import cn.harry12800.scan.WebSocketServer;
import cn.harry12800.scan.WebSocketServer.Letter;
import cn.harry12800.tools.StringUtils;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping(value = "/v1/websocket")
public class WebSocketController {
	@Autowired
	DiaryCatalogService diaryCatalogService;
	@Autowired
	DiaryService diaryService;
	@Autowired
	AppService appService;

	@RequestMapping(value = "/getByUserName", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse get(String userName, HttpServletResponse res) {
		MyResponse r = MyResponse.newOk();
		try (Jedis jedis = JedisUtil.getJedisPool().getResource()){
			List<String> lrange = jedis.lrange("letter:" + userName, 0, -1);
			for (String string : lrange) {
				jedis.lpush("letter:" + userName + ":history", string);
			}
			jedis.lpop("letter:" + userName);
			r.setContent(lrange);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		} 
		return r;
	}

	@RequestMapping(value = "/pullByUserName", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse pull(String userName, HttpServletResponse res) {
		MyResponse r = MyResponse.newOk();
		try (Jedis jedis = JedisUtil.getJedisPool().getResource()){
			List<String> lrange = jedis.lrange("letter:" + userName + ":history", 0, -1);
			r.setContent(lrange);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/sendText", method = RequestMethod.POST)
	public MyResponse saveOrUpdate(Model model, @RequestBody Letter letter) {
		MyResponse r = MyResponse.newOk();
		System.out.println(letter);
		try {
			if (!StringUtils.isEmpty(letter.to)) {
				WebSocketServer webSocketServer = WebSocketServer.smaps.get(letter.to);
				if(webSocketServer!=null) {
					webSocketServer.sendMessageBody(letter);
				}
			}
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

}

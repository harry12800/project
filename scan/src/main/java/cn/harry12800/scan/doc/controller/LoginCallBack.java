package cn.harry12800.scan.doc.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.collect.Maps;
import com.google.gson.internal.LinkedTreeMap;

import cn.harry12800.api.doc.cache.UserCacheUtil;
import cn.harry12800.api.util.HttpUtil;
import cn.harry12800.api.util.JsonUtil;
import cn.harry12800.api.util.PropertiesUtil;
import cn.harry12800.db.entity.FingerChatUser;
import cn.harry12800.db.mapper.FingerChatUserMapper;
import cn.harry12800.db.service.UserService;

@Controller
public class LoginCallBack {
	@Autowired
	UserService userService;
	@Autowired
	FingerChatUserMapper userMapper;

	@RequestMapping("/login-callback")
	public String view(ModelMap model, HttpServletRequest req,
			@RequestParam String code,
			@RequestParam String state) {
		System.out.println(code);
		Map<String, String> params = Maps.newHashMap();
		Map<String, String> headers = Maps.newHashMap();
		String path = PropertiesUtil.getValue("app.imsso.url");
		String ssoCallback = PropertiesUtil.getValue("app.ssoCallback");
		String url = path+ "/v2/oauth2/access_token";
		params.put("appId", "725218e83cb24e18");
		params.put("appToken", "b586495264094342b8f82602acd7266d");
		params.put("redirect_uri", ssoCallback);
		params.put("grant_type", "code");
		params.put("code", code);
		try {
			String string = HttpUtil.get(url, headers, params);
			System.err.println(string);
			HashMap<?, ?> json = JsonUtil.string2Json(string, HashMap.class);
			Double rcode = (Double) json.get("code");
			if (rcode - 10 < 0.0001) {
				LinkedTreeMap<?, ?> content = (LinkedTreeMap<?, ?>) json.get("content");
				String user_id = (String) content.get("user_id");
				String access_token = (String) content.get("access_token");
				Double expires_in = (Double) content.get("expires_in");
				System.out.println(rcode);
				System.err.println(user_id);
				System.out.println(access_token);
				System.out.println(expires_in);
				userService.saveOrUpdate(user_id, access_token, expires_in);
				req.getSession().setAttribute("userId", user_id);
				FingerChatUser user = userMapper.findByUserId(user_id);
				UserCacheUtil.addUser(user);
				System.out.println(req.getSession().getAttribute("userId"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println("url:" + req.getSession().getAttribute("url"));
		String redirect = (String) req.getSession().getAttribute("url");
		if (redirect != null)
			return "redirect:" + (String) req.getSession().getAttribute("url");
		else {
			return "index";
		}
	}
}

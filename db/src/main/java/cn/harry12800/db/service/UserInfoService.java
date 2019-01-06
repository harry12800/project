/**
 * Copyright &copy; 2015-2020 <a href="http://www.harry12800.xyz/">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

import com.google.gson.internal.LinkedTreeMap;

import cn.harry12800.db.entity.FingerChatUser;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.db.mapper.FingerChatUserMapper;
import cn.harry12800.db.mapper.UserInfoMapper;
import cn.harry12800.j2se.utils.HttpUtil;
import cn.harry12800.j2se.utils.JsonUtils;
import cn.harry12800.tools.Maps;

/**
 * 用户表Service
 * @author 周国柱
 * @version 1.0
 */
@Component
//@Transactional(readOnly = true)
public class UserInfoService {// extends CrudService<UserInfoMapper, UserInfo> {

	@Autowired
	private UserInfoMapper userMapper;


	public UserInfo findByUserId(String userId) {
		 
		return  userMapper.findByUserId(userId);
	}
	public UserInfo findById(long userId) {
		// 用户不存在
		UserInfo user = userMapper.findById(userId);
		return user;
	}
	@Autowired
	FingerChatUserMapper mapper;
	@Autowired
	UserInfoMapper userInfoMapper;

	public FingerChatUser findById(String id) {
		return mapper.findById(id);
	}

	public List<FingerChatUser> findAll() {
		return mapper.findAll();
	}

	public int save(FingerChatUser t) {
		return mapper.save(t);
	}

	public int update(FingerChatUser t) {
		return mapper.update(t);
	}

	public int deleteByIds(Set<?> ids) {
		return mapper.deleteByIds(ids);
	}

	public int deleteById(String id) {
		return mapper.deleteById(id);
	}

	public void saveOrUpdate(String userId, String access_token, Double expires_in,String path) {
		FingerChatUser user = mapper.findByUserId(userId);
		if (user == null) {
			user = getUserByFingerToken(access_token,path);
			user.setFingerToken(access_token);
			user.setCreateTime(new Date());
			user.setUserId(userId);
			mapper.save(user);
			UserInfo t = new UserInfo();
			t.setAvatarUrl(user.getAvatarUrl());
			t.setCreateTime(new Date());
			t.setEmployeeNo(user.getEmployeeNo());
			t.setNickName(user.getNickName());
			t.setPassword("123456");
			t.setRealName(user.getRealName());
			t.setUserId(user.getUserId());
			userInfoMapper.save(t);
		} else {
			FingerChatUser newUser = getUserByFingerToken(access_token,path);
			newUser.setFingerToken(access_token);
			newUser.setCreateTime(user.getCreateTime());
			newUser.setUserId(userId);
			newUser.setId(user.getId());
			mapper.update(newUser);
		}
	}

	public FingerChatUser getUserByFingerToken(String fingerToken,String path) {
		FingerChatUser user = new FingerChatUser();
		Map<String, String> params = Maps.newHashMap();
		Map<String, String> headers = Maps.newHashMap();

		String url = path + "/v2/oauth2/resource/get";
		params.put("access_token", fingerToken);
		try {
			String string = HttpUtil.get(url, headers, params);
			System.err.println(string);
			HashMap<?, ?> json = JsonUtils.string2Json(string, HashMap.class);
			Double rcode = (Double) json.get("code");
			if (rcode - 10 < 0.0001) {
				LinkedTreeMap<?, ?> content = (LinkedTreeMap<?, ?>) json.get("content");
				String employeeNo = (String) content.get("employeeNo");
				String nickname = (String) content.get("nickname");
				String userImage = (String) content.get("userImage");
				user.setAvatarUrl(userImage);
				user.setNickName(nickname);
				user.setEmployeeNo(employeeNo);
				user.setRealName(nickname);
				//userId":"zr0014","nickname":"周国柱","employeeNo":"zr0014","userPassword":"","userMobile":"13126452456","userImage":"","isEnabled":1,"isValid":1,"userPrivileges":15,"userFlags":0,"registerTime":1531205555000
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}
}
	
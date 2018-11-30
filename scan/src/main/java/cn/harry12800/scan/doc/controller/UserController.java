/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package cn.harry12800.scan.doc.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.harry12800.db.entity.FingerChatUser;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.db.mapper.FingerChatUserMapper;
import cn.harry12800.db.mapper.UserInfoMapper;
import cn.harry12800.scan.doc.cache.RSACode;
import cn.harry12800.scan.doc.cache.UserCacheUtil;
import cn.harry12800.scan.doc.controller.webdto.UserDto;
import cn.harry12800.scan.doc.http.EResponseCode;
import cn.harry12800.scan.doc.http.ResponseEntity;
import cn.harry12800.tools.StringUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 用户表Controller
 * 
 * @author 周国柱
 * @version 1.0
 */
// @Transactional(readOnly = true)
@RestController
@RequestMapping("/v1/doc/user")
public class UserController {// extends CrudService<UserMapper, User> {

	private static final String API_TAGS = "用户表/UserController";

	@Autowired
	FingerChatUserMapper mapper;
	@Autowired
	UserInfoMapper userInfoMapper;

	public static class RegUser {
		public String username;
		public String realname;
		public String pwd;
		public String sex;
		public String phone;
		public String mail;
	}

	@RequestMapping(value = "/reg", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "添加用户", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity reg(@ApiParam @RequestBody RegUser login, HttpServletRequest req) {
		ResponseEntity r = ResponseEntity.newOk();
		try {
			System.out.println(login.username);
			System.out.println(login.pwd);
			String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALCMObUdcPDxAflm0YxAiXaH8jwT5yE5ADxpDzEJH/5oedE5o39lTlWZ3ZnPuDpwBpxH0FKbrT6JIJi28QYkqXRaq9s8YmRzy152M0XVCqBaqSS4TpR2DDY6QQokLEODo+sCeJHsJzSKj0bxtbg/wkMmNJttp+8w8MMVtVVRYnHHAgMBAAECgYAOLuW/8CKPqL0A3Uq+WrzwYdGLFApAeATV1Zbb2KDSXnBS56+D346gf+D2p2Jkh3VwfrB0wn7zhC6zNhc86BsY1K6Q7xU8b7asDBqki48H3ExuxjEosEqUdLf9p9pPBCPI3I4CN/EZPEoFjNRNi5ZX/CY4iaOgsXPHEkcxuW3GQQJBAOBo9UpXesZYCsmuuGN5SOy6tXI613NUBvXKXkxBil3ZOqozlaSjv5NSQb2zLeh2DcYfecumfgn04rNM/pLeDmECQQDJZnXWg4VrXdjc9hqu/8rkmxdhsr3ERkX1uKJrDUB+AOdFs6mS9gEHnJ70zqQ2ucbhQ4htulbLc9pBVL5gy+EnAkEArdhhfa/7SsBVyxvxeA4zMkEJ4242Df/gTHTzTDvRxxZL3iKMILlB5gzpJN40CEu8K+miXuOh7HCrVp+k733awQJBAMDkERhS/wXF7F40l3nkIz6wC8TWnEnPxFGDdItzNcF4vAhV+qN2WaYgq11sTHrdk01MkO4G+foCC5dmwq+SlSECQGm58ReqUTRDAKl32VX0vEdVvOj2getVxW6jQjJFiGj8iNDfK+DpiLfns3YjwSlWHGxHz1S6/lQ+58+M+fEyvUs=";
			byte[] decryptByPrivateKey = RSACode.decryptByPrivateKey(Base64.decodeBase64(login.pwd), privateKey);
			String outputStr = new String(decryptByPrivateKey);
			System.out.println(outputStr);
			login.pwd = outputStr;
			if (StringUtils.isEmpty(login.username)) {
				r.setCode(EResponseCode.UNAUTHORIZED.value());
				r.setMessage("用户名不能为空！");
				return r;
			}
			if (StringUtils.isEmpty(login.realname)) {
				r.setCode(EResponseCode.UNAUTHORIZED.value());
				r.setMessage("真实名称不能为空！");
				return r;
			}
			if (StringUtils.isEmpty(login.pwd)) {
				r.setCode(EResponseCode.UNAUTHORIZED.value());
				r.setMessage("密码不能为空！");
				return r;
			}
			UserInfo userinfo = userInfoMapper.findByUserId(login.username);
			if (userinfo != null) {
				r.setCode(EResponseCode.UNAUTHORIZED.value());
				r.setMessage("用户已存在！");
				return r;
			}
			
			if (login.pwd.length() < 6) {
				r.setCode(EResponseCode.UNAUTHORIZED.value());
				r.setMessage("密码过于简单！");
				return r;
			}
			if (!isMobileNO(login.phone)) {
				r.setCode(EResponseCode.UNAUTHORIZED.value());
				r.setMessage("手机号码不正确！");
				return r;
			}
			if (!isMail(login.mail)) {
				r.setCode(EResponseCode.UNAUTHORIZED.value());
				r.setMessage("邮箱地址不正确！");
				return r;
			}
			UserInfo t = new UserInfo();
			t.setCreateTime(new Date());
			t.setUserId(login.username);
			t.setNickName(login.username);
			t.setMail(login.mail);
			t.setEmployeeNo("");
			t.setAvatarUrl("");
			t.setSex(login.sex);
			t.setPassword(login.pwd);
			t.setPhone(login.phone);
			t.setRealName(login.realname);
			userInfoMapper.save(t);
			req.getSession().setAttribute("userId", login.username);
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	public static boolean isMobileNO(String mobileNums) {
		/**
		 * 判断字符串是否符合手机号码格式 移动号段:
		 * 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,
		 * 184,187,188 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
		 * 电信号段: 133,149,153,170,173,177,180,181,189
		 * 
		 * @param str
		 * @return 待检测的字符串
		 */
		String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (StringUtils.isEmpty(mobileNums))
			return false;
		else
			return mobileNums.matches(telRegex);
	}

	/** Regex for single EmailValidator */
	public static final String SINGLE_EMAIL_REGEX = "(?:(?:[A-Za-z0-9\\-_@!#$%&'*+/=?^`{|}~]|(?:\\\\[\\x00-\\xFF]?)|(?:\"[\\x00-\\xFF]*\"))+(?:\\.(?:(?:[A-Za-z0-9\\-_@!#$%&'*+/=?^`{|}~])|(?:\\\\[\\x00-\\xFF]?)|(?:\"[\\x00-\\xFF]*\"))+)*)@(?:(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+(?:(?:[A-Za-z0-9]*[A-Za-z][A-Za-z0-9]*)(?:[A-Za-z0-9-]*[A-Za-z0-9])?))";

	public static boolean isMail(String mail) {
		if (StringUtils.isEmpty(mail))
			return false;
		else
			return mail.matches(SINGLE_EMAIL_REGEX);
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "删除", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delete(@ApiParam @RequestParam int id) {
		if (Objects.isNull(id)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			int save = mapper.deleteById(String.valueOf(id));
			r.setContent(new Object() {
				public int result = save;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "修改", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity update(@ApiParam @RequestBody UserDto user) {
		if (Objects.isNull(user)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			int save = mapper.update(user.toUserEntity());
			r.setContent(new Object() {
				public int result = save;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/findById", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "通过主键查找", tags = {
			API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findById(@ApiParam @RequestParam int id) {
		if (Objects.isNull(id)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			FingerChatUser findById = mapper.findById(String.valueOf(id));
			r.setContent(new Object() {
				public FingerChatUser user = findById;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/findAll", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "通过主键查找", tags = {
			API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findAll() {
		ResponseEntity r = ResponseEntity.newOk();
		try {
			List<FingerChatUser> findAll = mapper.findAll();
			r.setContent(new Object() {
				public List<FingerChatUser> lists = findAll;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@RequestMapping(value = "/alphabetUser", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "获取字母序的用户列表！包含系统所有人！", tags = {
			API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity sort() {
		ResponseEntity r = ResponseEntity.newOk();
		try {
			r.setContent(UserCacheUtil.getSpellUsers());
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@RequestMapping(value = "/getCurrentUser", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "获取当前客户端连接登录的用户！", tags = {
			API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getCurrentUser(HttpServletRequest req) {
		ResponseEntity r = ResponseEntity.newOk();
		try {
			String userId = (String) req.getSession().getAttribute("userId");
			FingerChatUser findByUserId = mapper.findByUserId(userId);
			r.setContent(findByUserId);
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "用户登出", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity logOut(HttpServletRequest req) {
		ResponseEntity r = ResponseEntity.newOk();
		try {
			req.getSession().setAttribute("userId", null);
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	public static class Login {
		public String username;
		public String pwd;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "用户登录", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity login(@ApiParam @RequestBody Login login, HttpServletRequest req) {
		ResponseEntity r = ResponseEntity.newOk();
		try {
			System.out.println(login.username);
			System.out.println(login.pwd);
			String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALCMObUdcPDxAflm0YxAiXaH8jwT5yE5ADxpDzEJH/5oedE5o39lTlWZ3ZnPuDpwBpxH0FKbrT6JIJi28QYkqXRaq9s8YmRzy152M0XVCqBaqSS4TpR2DDY6QQokLEODo+sCeJHsJzSKj0bxtbg/wkMmNJttp+8w8MMVtVVRYnHHAgMBAAECgYAOLuW/8CKPqL0A3Uq+WrzwYdGLFApAeATV1Zbb2KDSXnBS56+D346gf+D2p2Jkh3VwfrB0wn7zhC6zNhc86BsY1K6Q7xU8b7asDBqki48H3ExuxjEosEqUdLf9p9pPBCPI3I4CN/EZPEoFjNRNi5ZX/CY4iaOgsXPHEkcxuW3GQQJBAOBo9UpXesZYCsmuuGN5SOy6tXI613NUBvXKXkxBil3ZOqozlaSjv5NSQb2zLeh2DcYfecumfgn04rNM/pLeDmECQQDJZnXWg4VrXdjc9hqu/8rkmxdhsr3ERkX1uKJrDUB+AOdFs6mS9gEHnJ70zqQ2ucbhQ4htulbLc9pBVL5gy+EnAkEArdhhfa/7SsBVyxvxeA4zMkEJ4242Df/gTHTzTDvRxxZL3iKMILlB5gzpJN40CEu8K+miXuOh7HCrVp+k733awQJBAMDkERhS/wXF7F40l3nkIz6wC8TWnEnPxFGDdItzNcF4vAhV+qN2WaYgq11sTHrdk01MkO4G+foCC5dmwq+SlSECQGm58ReqUTRDAKl32VX0vEdVvOj2getVxW6jQjJFiGj8iNDfK+DpiLfns3YjwSlWHGxHz1S6/lQ+58+M+fEyvUs=";
			byte[] decryptByPrivateKey = RSACode.decryptByPrivateKey(Base64.decodeBase64(login.pwd), privateKey);
			String outputStr = new String(decryptByPrivateKey);
			System.out.println(outputStr);
			login.pwd = outputStr;
			if (login.username == null) {
				r.setCode(EResponseCode.UNAUTHORIZED.value());
				r.setMessage("用户名不能为空！");
				return r;
			}
			if (login.pwd == null) {
				r.setCode(EResponseCode.UNAUTHORIZED.value());
				r.setMessage("密码不能为空！");
				return r;
			}
			UserInfo userinfo = userInfoMapper.findByUserId(login.username);
			if (userinfo == null) {
				r.setCode(EResponseCode.UNAUTHORIZED.value());
				r.setMessage("用户名或者密码错误！");
				return r;
			}
			if (userinfo.getPassword().equals(login.pwd)) {
				req.getSession().setAttribute("userId", userinfo.getUserId());
				return r;
			} else {
				r.setCode(EResponseCode.UNAUTHORIZED.value());
				r.setMessage("用户名或者密码错误！");
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}
}

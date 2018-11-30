package cn.harry12800.scan.controller.rest;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.harry12800.db.entity.Diary;
import cn.harry12800.db.entity.User;
import cn.harry12800.scan.http.MyResponse;
import cn.harry12800.scan.http.MyResponse.EResponseCode;
import cn.harry12800.scan.service.DiaryService;
import cn.harry12800.tools.StringUtils;

@RestController
@RequestMapping(value = "v1/diary")
public class DiaryRestController {
	@Autowired
	DiaryService service;

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse getAll(HttpServletResponse res) {
		MyResponse r = MyResponse.newOk();
		try {
			res.setContentType("text/plain");
			List<Diary> diarys = service.findAll();
			r.setContent(diarys);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/getAllByUserId", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse getAllByUserId(HttpServletResponse res, @RequestParam(required = true) String userId) {
		MyResponse r = MyResponse.newOk();
		try {
			res.setContentType("text/plain");
			List<Diary> diarys = service.findAllByUserId(userId);
			r.setContent(diarys);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/getAllByCatalogId", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse getAllByCatalogId(String catalogId, HttpServletResponse res) {
		MyResponse r = MyResponse.newOk();
		try {
			res.setContentType("text/plain");
			List<Diary> diarys = service.findAllByCatalogId(catalogId);
			r.setContent(diarys);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse getById(String id, HttpServletResponse res) {
		MyResponse r = MyResponse.newOk();
		try {
			res.setContentType("text/plain");
			res.setHeader("Cache-Control", "no-cache");
			res.setHeader("Pragma", "no-cache");
			res.setDateHeader("Expires", -1);
			Diary diary = service.findById(id);
			r.setContent(diary);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public MyResponse add(Model model, @ModelAttribute() Diary diary, String userId) {
		MyResponse r = MyResponse.newOk();
		try {
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			diary.setId(id);
			diary.setCreateTime(new Date());
			diary.setUpdateTime(new Date());
			service.save(diary, userId);
			r.setContent(diary);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public MyResponse save(Model model, @RequestBody Diary diary, String userId) {
		MyResponse r = MyResponse.newOk();
		try {
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			diary.setId(id);
			diary.setCreateTime(new Date());
			diary.setUpdateTime(new Date());
			service.save(diary, userId);
			r.setContent(diary);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public MyResponse saveOrUpdate(Model model, @RequestBody Diary diary, @RequestParam(required = true) String userId) {
		if (StringUtils.isEmpty(userId)) {
			userId = "2";
		}
		MyResponse r = MyResponse.newOk();
		try {
			if (StringUtils.isEmpty(diary.getId())) {
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				diary.setId(id);
				diary.setCreateTime(new Date());
				diary.setUpdateTime(new Date());
				service.save(diary, userId);
				r.setContent(diary);
			} else {
				Diary data = service.findById(diary.getId());
				if (data == null) {
					service.save(diary, userId);
					r.setContent(diary);
				} else {

					data.setTitle(diary.getTitle());
					data.setContent(diary.getContent());
					data.setHtml(diary.getHtml());
					data.setUpdateTime(new Date());
					if (!StringUtils.isEmpty(diary.getCatalogId()))
						data.setCatalogId(diary.getCatalogId());
					service.update(data);
					r.setContent(data);
				}
			}
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public MyResponse update(Model model, @RequestBody Diary diary) {
		MyResponse r = MyResponse.newOk();
		try {
			if (StringUtils.isEmpty(diary.getId())) {
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				diary.setId(id);
			}
			Diary data = service.findById(diary.getId());
			data.setTitle(diary.getTitle());
			data.setContent(diary.getContent());
			data.setHtml(diary.getHtml());
			data.setUpdateTime(new Date());
			data.setCipher(diary.getCipher());
			if (!StringUtils.isEmpty(diary.getCatalogId()))
				data.setCatalogId(diary.getCatalogId());
			service.update(data);
			r.setContent(data);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/incHint", method = RequestMethod.GET)
	public MyResponse incHint(Model model, String id) {
		MyResponse r = MyResponse.newOk();
		try {
			if (StringUtils.isEmpty(id)) {
				return MyResponse.newBad();
			} else {
				service.incHint(id);
			}
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/cipherOrDecode", method = RequestMethod.GET)
	public MyResponse encipher(@RequestParam String userName, @RequestParam String pwd, @RequestParam Integer type, @RequestParam(required = false) String id, HttpServletResponse res) {
		MyResponse r = MyResponse.newOk();
		System.out.println(userName);
		System.out.println(pwd);
		try {
			User user = service.findUserByDiaryId(id);
			if (user == null) {
				r.setContent(new Object() {
					public String result = "error";
				});
			} else if (user.getUserName().equals(userName) && user.getPassward().equals(pwd)) {
				if (1 == type)
					r.setContent(service.encipher(id));
				else {
					r.setContent(service.decipher(id));
				}
			} else {
				r.setContent(new Object() {
					public String result = "error";
				});
			}
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/decipher", method = RequestMethod.GET)
	public MyResponse decipher(@RequestParam String userName, @RequestParam String pwd, @RequestParam(required = false) String id, HttpServletResponse res) {
		MyResponse r = MyResponse.newOk();
		try {
			if (StringUtils.isEmpty(id)) {
				return MyResponse.newBad();
			} else {
				service.decipher(id);
			}
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public MyResponse del(String id) {
		MyResponse r = MyResponse.newOk();
		try {
			service.deleteById(id);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}
}

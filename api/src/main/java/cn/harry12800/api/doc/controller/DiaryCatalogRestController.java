package cn.harry12800.api.doc.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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
import cn.harry12800.db.entity.DiaryCatalog;
import cn.harry12800.db.service.DiaryCatalogService;
import cn.harry12800.tools.StringUtils;

@RestController
@RequestMapping(value = "v1/diaryCatalog")
public class DiaryCatalogRestController {
	@Autowired
	DiaryCatalogService service;

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse get(HttpServletResponse res) {
		MyResponse r = MyResponse.newOk();
		try {
			res.setContentType("text/plain");
			List<DiaryCatalog> findAll = service.findAll();
			r.setContent(findAll);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/getAllByUserId", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse get(HttpServletResponse res, String userId) {
		MyResponse r = MyResponse.newOk();
		try {
			res.setContentType("text/plain");
			List<DiaryCatalog> findAll = service.findAllByUserId(userId);
			r.setContent(findAll);
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
			DiaryCatalog findById = service.findById(id);
			r.setContent(findById);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public MyResponse add(Model model, @RequestBody DiaryCatalog diary) {
		MyResponse r = MyResponse.newOk();
		try {
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			diary.setId(id);
			diary.setCreateTime(new Date());
			diary.setUpdateTime(new Date());
			service.save(diary);
			System.out.println(diary);
			r.setContent(diary);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public MyResponse update(Model model, @RequestBody DiaryCatalog diary) {
		MyResponse r = MyResponse.newOk();
		try {
			if (StringUtils.isEmpty(diary.getId())) {
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				diary.setId(id);
			}
			DiaryCatalog data = service.findById(diary.getId());
			data.setName(diary.getName());
			data.setUpdateTime(new Date());
			service.update(data);
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

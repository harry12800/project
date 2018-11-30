/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package cn.harry12800.scan.doc.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.harry12800.db.entity.Api;
import cn.harry12800.db.entity.Application;
import cn.harry12800.db.entity.AutoApiMarkdown;
import cn.harry12800.db.mapper.ApiMapper;
import cn.harry12800.db.mapper.ApplicationMapper;
import cn.harry12800.db.mapper.AutoApiMarkdownMapper;
import cn.harry12800.db.mapper.Int64SequenceEntityMapper;
import cn.harry12800.db.mapper.ResourceMapper;
import cn.harry12800.scan.doc.controller.webdto.ApiDto;
import cn.harry12800.scan.doc.http.EResponseCode;
import cn.harry12800.scan.doc.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 项目的接口ApiController
 * @author 周国柱
 * @version 1.0
 */
//@Transactional(readOnly = true)
@RestController
@RequestMapping("/v1/doc/api")
public class ApiController {// extends CrudService<ApiMapper, Api> {

	private static final String API_TAGS = "项目的接口Api/ApiController";

	@Autowired
	ApiMapper mapper;
	@Autowired
	Int64SequenceEntityMapper sequenceMapper;

	@Autowired
	ApplicationMapper appMapper;

	@Autowired
	AutoApiMarkdownMapper autoApiMarkdownMapper;
	@Autowired
	ResourceMapper resourceMapper;

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
	@RequestMapping(value = "/updatePath", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "单独修改路径", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updatePath(@ApiParam("api路径") @RequestParam String path, @RequestParam long id, HttpServletRequest req) {
		if (StringUtils.isEmpty(path)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		String userId = (String) req.getSession().getAttribute("userId");
		try {
			int save = mapper.updatePath(path, id);
			if (save < 1) {
				r.setContent(new Object() {
					public int result = save;
				});
				return r;
			}
			Api findById = mapper.findById(id);
			//修改资源修改时间和修改人
			int save2 = resourceMapper.updateAlone(findById.getAutoApiMarkdownId(), new Date(), userId);
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
	public ResponseEntity update(@ApiParam @RequestBody ApiDto api, HttpServletRequest req) {
		if (Objects.isNull(api.appId) || Objects.isNull(api.autoApiMarkdownId)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		Application application = appMapper.findById(api.appId);
		AutoApiMarkdown autoApiMarkdown = autoApiMarkdownMapper.findById(api.autoApiMarkdownId);
		if (Objects.isNull(autoApiMarkdown) || Objects.isNull(application)) {
			r.setCode(EResponseCode.BAD_REQUEST.value());
			r.setMessage("该自动生成markdown或app应用不存在！！！");
			return r;
		}
		String userId = (String) req.getSession().getAttribute("userId");
		try {
			int save = mapper.update(api.toApiEntity());
			if (save < 1) {
				r.setContent(new Object() {
					public int result = save;
				});
				return r;
			}
			//修改资源修改时间和修改人
			int save2 = resourceMapper.updateAlone(api.autoApiMarkdownId, new Date(), userId);
			r.setContent(new Object() {
				public int result = save2;
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
	@ApiOperation(httpMethod = "POST", value = "通过主键查找", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findById(@ApiParam @RequestParam Long id) {
		if (Objects.isNull(id)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			Api findById = mapper.findById(id);
			r.setContent(new Object() {
				public Api api = findById;
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
	@ApiOperation(httpMethod = "POST", value = "通过主键查找", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findAll() {
		ResponseEntity r = ResponseEntity.newOk();
		try {
			List<Api> findAll = mapper.findAll();
			r.setContent(new Object() {
				public List<Api> lists = findAll;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

}

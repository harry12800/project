/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package cn.harry12800.scan.doc.controller;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.harry12800.db.entity.Application;
import cn.harry12800.db.mapper.ApplicationMapper;
import cn.harry12800.db.mapper.Int64SequenceEntityMapper;
import cn.harry12800.db.service.ApplicationService;
import cn.harry12800.scan.doc.cache.MarkDownCacheUtil;
import cn.harry12800.scan.doc.controller.webdto.ApplicationDto;
import cn.harry12800.scan.doc.http.EResponseCode;
import cn.harry12800.scan.doc.http.ResponseEntity;
import cn.harry12800.scan.doc.swagger2markdown.ApiBean;
import cn.harry12800.scan.doc.swagger2markdown.Module;
import cn.harry12800.scan.doc.swagger2markdown.SwaggerUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 开发组开发的应用Controller
 * @author 周国柱
 * @version 1.0
 */
//@Transactional(readOnly = true)
@RestController
@RequestMapping("/v1/doc/application")
public class ApplicationController {// extends CrudService<ApplicationMapper, Application> {

	private static final String API_TAGS = "开发组开发的应用/ApplicationController";
	@Autowired
	ApplicationMapper mapper;
	@Autowired
	ApplicationService applicationService;
	@Autowired
	Int64SequenceEntityMapper sequenceMapper;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "添加", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity add(@ApiParam @RequestBody ApplicationDto application, HttpServletRequest req) {
		if (Objects.isNull(application.port)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			Long id = sequenceMapper.getNextSequence("fingerchat_dev_docs.application.id");
			Application entity = application.toApplicationEntity();
			entity.setId(id);
			String userId = (String) req.getSession().getAttribute("userId");
			entity.setCreateUser(userId);
			entity.setUpdateUser(userId);
			if (entity.getAppType() == 1) {
				String docUrl = entity.getDocUrl();
				boolean swaggerUrl = SwaggerUtils.isSwaggerUrl(docUrl);
				if (!swaggerUrl) {
					r.setCode(20);
					r.setMessage("不是swagger项目的doc-api地址");
					return r;
				}
				MarkDownCacheUtil.addApp(entity);
			}
			int save = mapper.save(entity);
			r.setContent(new Object() {
				public Application app = entity;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "删除", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delete(@ApiParam @RequestParam Long id, HttpServletRequest req) {
		if (Objects.isNull(id)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			String userId = (String) req.getSession().getAttribute("userId");
			Application app = mapper.findById(id);
			if (Objects.isNull(app)) {
				r.setCode(20);
				r.setMessage("不存在应用！");
				return r;
			}
			if (!app.getCreateUser().equals(userId)) {
				r.setCode(EResponseCode.NOT_ALLOWED.value());
				r.setMessage("无权限操作");

				return r;
			}
			System.out.println("删除应用：" + id);
			int save = applicationService.deleteById(id);
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
	public ResponseEntity update(@ApiParam @RequestBody ApplicationDto application) {
		if (Objects.isNull(application.port) || Objects.isNull(application.id)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			int save = mapper.update(application.toApplicationEntity());
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
	@ApiOperation(httpMethod = "POST", value = "通过主键查找", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findById(@ApiParam @RequestParam Long id) {
		if (Objects.isNull(id)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			Application findById = mapper.findById(id);
			r.setContent(new Object() {
				public Application application = findById;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@RequestMapping(value = "/getModuleByAppid", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "通过Appid拿项目下的所有接口模块", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getModuleByAppid(@ApiParam @RequestParam Long id) {
		if (Objects.isNull(id)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			Collection<Module> moduleByAppId = MarkDownCacheUtil.getModuleByAppId(id);
			r.setContent(moduleByAppId);
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@RequestMapping(value = "/getApiByAppIdModuleId", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "通过ModuleId拿项目模块下的所有接口", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getApiByAppIdModuleId(@ApiParam @RequestParam Long appId, String mId) {
		if (Objects.isNull(appId)) {
			return ResponseEntity.newBadRequest();
		}
		if (Objects.isNull(mId)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			List<ApiBean> apiListByAppIdAndMid = MarkDownCacheUtil.getApiListByAppIdAndMid(appId, mId);
			r.setContent(apiListByAppIdAndMid);
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@RequestMapping(value = "/getAllMarkdownApp", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "获取所有可以配置Api接口文档的项目", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getAllMarkdownApp() {
		ResponseEntity r = ResponseEntity.newOk();
		try {
			r.setContent(MarkDownCacheUtil.getAllApplication());
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}
}

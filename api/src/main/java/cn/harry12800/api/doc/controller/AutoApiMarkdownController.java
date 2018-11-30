/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package cn.harry12800.api.doc.controller;

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

import com.google.common.collect.Lists;

import cn.harry12800.api.doc.cache.MarkDownCacheUtil;
import cn.harry12800.api.doc.http.EResponseCode;
import cn.harry12800.api.doc.http.ResponseEntity;
import cn.harry12800.api.doc.swagger2markdown.ApiBean;
import cn.harry12800.db.entity.Api;
import cn.harry12800.db.entity.Application;
import cn.harry12800.db.entity.AutoApiMarkdown;
import cn.harry12800.db.entity.Directory;
import cn.harry12800.db.entity.Resource;
import cn.harry12800.db.mapper.ApiMapper;
import cn.harry12800.db.mapper.ApplicationMapper;
import cn.harry12800.db.mapper.AutoApiMarkdownMapper;
import cn.harry12800.db.mapper.DirectoryMapper;
import cn.harry12800.db.mapper.Int64SequenceEntityMapper;
import cn.harry12800.db.mapper.ResourceMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 自动拼接接口文档Controller
 * @author 周国柱
 * @version 1.0
 */
//@Transactional(readOnly = true)
@RestController
@RequestMapping("/v1/doc/autoApiMarkdown")
public class AutoApiMarkdownController {// extends CrudService<AutoApiMarkdownMapper, AutoApiMarkdown> {

	private static final String API_TAGS = "自动拼接接口文档/AutoApiMarkdownController";
	@Autowired
	ApiMapper apiMapper;
	@Autowired
	AutoApiMarkdownMapper mapper;
	@Autowired
	Int64SequenceEntityMapper sequenceMapper;
	@Autowired
	ResourceMapper resourceMapper;
	@Autowired
	ApplicationMapper appMapper;
	@Autowired
	DirectoryMapper directoryMapper;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "添加(同时增加资源数据信息)", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity add(@ApiParam @RequestBody Api_Add_Body body, HttpServletRequest req) {
		System.out.println("进啦");
		if (Objects.isNull(body.directoryId)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		Directory directory = directoryMapper.findById(body.directoryId);
		Long _appId = directory.getAppId();
		if (Objects.isNull(directory)) {
			r.setCode(EResponseCode.BAD_REQUEST.value());
			r.setMessage("所属目录不存在！！！");
			return r;
		}
		String userId = (String) req.getSession().getAttribute("userId");
		try {
			Long id = sequenceMapper.getNextSequence("fingerchat_dev_docs.resource.id");
			AutoApiMarkdown apiMarkdown = new AutoApiMarkdown();
			apiMarkdown.setId(id);
			apiMarkdown.setName(body.name);
			apiMarkdown.setPrefixContent(body.prefix);
			apiMarkdown.setSuffixContent(body.suffix);
			int save = mapper.save(apiMarkdown);
			if (save < 1) {
				r.setCode(EResponseCode.NOT_ACCEPTABLE.value());
				r.setMessage("自动markdown添加失败");
				r.setContent(new Object() {
					public int result = save;
				});
				return r;
			}
			Date date = new Date();
			//同时增加资源数据
			Resource resource = new Resource();
			resource.setId(id);
			resource.setDirectoryId(body.directoryId);
			resource.setAppId(_appId);
			resource.setName(body.name);
			resource.setCreateTime(date);
			resource.setUpdateTime(date);
			resource.setCreateUser(userId);
			resource.setUpdateUser(userId);
			resource.setResourceType(2);
			resource.setRemark("");
			resource.setReadable(0);
			resource.setOwner(userId);
			resource.setCipher(1);
			resource.setHint(0);
			resource.setSort(0);
			int save2 = resourceMapper.save(resource);
			if (save2 < 1) {
				r.setCode(EResponseCode.NOT_ACCEPTABLE.value());
				r.setMessage("资源添加失败");
				r.setContent(new Object() {
					public int result = save2;
				});
				return r;
			}
			//添加对应用户权限
			int save3 = resourceMapper.insertUserResource(userId, id);
			r.setContent(new Object() {
				public int result = save3;
				public long directoryId = body.directoryId;
				public long appId = _appId;
				public long auto_api_markdown_id = id;
			});
			int x = 0;
			for (Request_Body api : body.apis) {
				Application findById = appMapper.findById(api.appId);
				if (Objects.isNull(findById)) {
					r.setCode(EResponseCode.BAD_REQUEST.value());
					r.setMessage("app不存在！！！");
					return r;
				}
				Long idx = sequenceMapper.getNextSequence("fingerchat_dev_docs.api.id");
				Api apiEntity = new Api();
				apiEntity.setId(idx);
				apiEntity.setAppId(api.appId);
				apiEntity.setAutoApiMarkdownId(id);
				apiEntity.setPath(api.path);
				if (StringUtils.isEmpty(api.moduleId)) {
					apiEntity.setType(1);
				} else {
					List<ApiBean> apiListByAppIdAndMid = MarkDownCacheUtil.getApiListByAppIdAndMid(api.appId, api.moduleId);
					apiEntity.setPath(apiListByAppIdAndMid.get(0).path);
					apiEntity.setType(2);
				}
				x += apiMapper.save(apiEntity);
			}
			int xy = x;
			r.setContent(new Object() {
				public AutoApiMarkdown _apiMarkdown = apiMarkdown;
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
	public ResponseEntity update(@ApiParam @RequestBody Api_Update_Body autoApiMarkdown, HttpServletRequest req) {
		if (Objects.isNull(autoApiMarkdown.id)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		String userId = (String) req.getSession().getAttribute("userId");
		try {
			Resource dbResource = resourceMapper.findById(autoApiMarkdown.id);
			Long directoryId = dbResource.getDirectoryId();
			Directory dir = directoryMapper.findById(directoryId);
			AutoApiMarkdown apiMarkdown = new AutoApiMarkdown();
			apiMarkdown.setId(autoApiMarkdown.id);
			apiMarkdown.setName(autoApiMarkdown.name);
			apiMarkdown.setPrefixContent(autoApiMarkdown.prefix);
			apiMarkdown.setSuffixContent(autoApiMarkdown.suffix);
			int save = mapper.update(apiMarkdown);
			if (save < 1) {
				r.setContent(new Object() {
					public int result = save;
				});
				return r;
			}
			Date date = new Date();
			//同时增加资源数据
			dbResource.setName(autoApiMarkdown.name);
			dbResource.setUpdateTime(date);
			dbResource.setUpdateUser(userId);
			dbResource.setResourceType(2);
			dbResource.setRemark("");
			int save2 = resourceMapper.update(dbResource);
			if (save2 < 1) {
				r.setContent(new Object() {
					public int result = save2;
				});
				return r;
			}
			//删除添加
			int deleteByResourceId = apiMapper.deleteByResourceId(autoApiMarkdown.id);
			int x = 0;
			for (Request_Body api : autoApiMarkdown.apis) {
				Application findById = appMapper.findById(api.appId);
				if (Objects.isNull(findById)) {
					r.setCode(EResponseCode.BAD_REQUEST.value());
					r.setMessage("app不存在！！！");
					return r;
				}
				Long idx = sequenceMapper.getNextSequence("fingerchat_dev_docs.api.id");
				Api apiEntity = new Api();
				apiEntity.setId(idx);
				apiEntity.setAppId(api.appId);
				apiEntity.setAutoApiMarkdownId(autoApiMarkdown.id);
				apiEntity.setPath(api.path);
				if (StringUtils.isEmpty(api.moduleId)) {
					apiEntity.setType(1);
				} else {
					List<ApiBean> apiListByAppIdAndMid = MarkDownCacheUtil.getApiListByAppIdAndMid(api.appId, api.moduleId);
					apiEntity.setPath(apiListByAppIdAndMid.get(0).path);
					apiEntity.setType(2);
				}
				x += apiMapper.save(apiEntity);
			}
			int xy = x;
			r.setContent(new Object() {
				public int result = xy;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/updatePrefix", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "单独修改前缀", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updatePrefix(@RequestParam long id, @ApiParam @RequestParam String prefix, HttpServletRequest req) {
		if (StringUtils.isEmpty(prefix)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		String userId = (String) req.getSession().getAttribute("userId");
		try {
			int save = mapper.updatePrefix(prefix, id);
			if (save < 1) {
				r.setContent(new Object() {
					public int result = save;
				});
				return r;
			}
			//同时修改资源数据
			int save2 = resourceMapper.updateAlone(id, new Date(), userId);
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
	@RequestMapping(value = "/updateSuffix", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "单独修改后缀", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateSuffix(@RequestParam long id, @ApiParam @RequestParam String suffix, HttpServletRequest req) {
		if (StringUtils.isEmpty(suffix)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		String userId = (String) req.getSession().getAttribute("userId");
		try {
			int save = mapper.updatePrefix(suffix, id);
			if (save < 1) {
				r.setContent(new Object() {
					public int result = save;
				});
				return r;
			}
			//同时修改资源数据
			int save2 = resourceMapper.updateAlone(id, new Date(), userId);
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

	@RequestMapping(value = "/findById", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "通过主键查找", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findById(@ApiParam @RequestParam Long id) {
		if (Objects.isNull(id)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			AutoApiMarkdown findById = mapper.findById(id);
			if (findById == null) {
				r.setCode(20);
				r.setMessage("不存在此文档！");
				return r;
			}
			r.setContent(findById);
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "autoApiMarkdown检索", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity search(@ApiParam("检索数据") @RequestParam String content) {
		if (StringUtils.isEmpty(content)) {
			content = "";
		}
		ResponseEntity r = ResponseEntity.newOk();
		StringBuilder sb = new StringBuilder();
		sb.append("'%" + content + "%'");
		try {
			List<AutoApiMarkdown> search = mapper.search(sb.toString());
			r.setContent(new Object() {
				public List<AutoApiMarkdown> lists = search;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	//	public static class Add_ReqBody {
	//		@ApiModelProperty("主键")
	//		public Long id;
	//		@ApiModelProperty("名称")
	//		public String name = "";
	//		@ApiModelProperty("文档前缀")
	//		public String prefix_content = "";
	//		@ApiModelProperty("文档后缀")
	//		public String suffix_content = "";
	//		@ApiModelProperty("目录id")
	//		public long directoryId;
	//		@ApiModelProperty("项目id")
	//		public long appId;
	//		@ApiModelProperty("备注")
	//		public String remark = "";
	//		@ApiModelProperty("是否资源公开(所有人都可以查阅）0否，1是")
	//		public Integer readable = 0;
	//
	//	}

	@ApiModel
	public static class Api_Add_Body {
		@ApiModelProperty("目录id")
		public Long directoryId;
		@ApiModelProperty("名称")
		public String name;
		@ApiModelProperty("前缀")
		public String prefix;
		@ApiModelProperty("后缀")
		public String suffix;
		@ApiModelProperty("中间部分选择的某些Api")
		public List<Request_Body> apis = Lists.newArrayList();
	}

	@ApiModel
	public static class Request_Body {
		@ApiModelProperty("此API所属项目Id")
		public Long appId;
		@ApiModelProperty("有此数据就是全模块API")
		public String moduleId;
		@ApiModelProperty("单路径的API")
		public String path;
	}

	@ApiModel
	public static class Api_Update_Body {
		@ApiModelProperty("资源id")
		public Long id;
		@ApiModelProperty("名称")
		public String name;
		@ApiModelProperty("前缀")
		public String prefix;
		@ApiModelProperty("后缀")
		public String suffix;
		@ApiModelProperty("中间部分选择的某些Api")
		public List<Request_Body> apis = Lists.newArrayList();
	}

}

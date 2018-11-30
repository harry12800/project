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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.harry12800.db.entity.Application;
import cn.harry12800.db.entity.Directory;
import cn.harry12800.db.entity.Markdown;
import cn.harry12800.db.entity.Resource;
import cn.harry12800.db.mapper.ApplicationMapper;
import cn.harry12800.db.mapper.DirectoryMapper;
import cn.harry12800.db.mapper.Int64SequenceEntityMapper;
import cn.harry12800.db.mapper.MarkdownMapper;
import cn.harry12800.db.mapper.ResourceMapper;
import cn.harry12800.scan.doc.http.EResponseCode;
import cn.harry12800.scan.doc.http.ResponseEntity;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 手写的markdownController
 * @author 周国柱
 * @version 1.0
 */
//@Transactional(readOnly = true)
@RestController
@RequestMapping("/v1/doc/markdown")
public class MarkdownController {// extends CrudService<MarkdownMapper, Markdown> {

	private static final String API_TAGS = "手写的markdown/MarkdownController";

	@Autowired
	MarkdownMapper mapper;
	@Autowired
	Int64SequenceEntityMapper sequenceMapper;
	@Autowired
	ResourceMapper resourceMapper;
	@Autowired
	ApplicationMapper appMapper;
	@Autowired
	DirectoryMapper directoryMapper;
	//	@Autowired
	//	DataSourceTransactionManager dataSourceTransactionManager;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "添加(同时增加资源数据信息)", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional(rollbackFor = { Exception.class })
	public ResponseEntity add(@ApiParam @RequestBody Add_reqBody markdown, HttpServletRequest req) {
		if (Objects.isNull(markdown.appId) || Objects.isNull(markdown.directoryId)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		Application application = appMapper.findById(markdown.appId);
		Directory directory = directoryMapper.findById(markdown.directoryId);
		if (Objects.isNull(application) || Objects.isNull(directory)) {
			r.setCode(EResponseCode.BAD_REQUEST.value());
			r.setMessage("所属目录或app应用不存在！！！");
			return r;
		}
		String userId = (String) req.getSession().getAttribute("userId");
		try {
			Long id = sequenceMapper.getNextSequence("fingerchat_dev_docs.resource.id");
			Markdown markdownEntity = new Markdown();
			markdownEntity.setId(id);
			markdownEntity.setName(markdown.name);
			markdownEntity.setContent(markdown.content);
			//			TransactionStatus status = dataSourceTransactionManager.getTransaction(new DefaultTransactionDefinition());
			int save = mapper.save(markdownEntity);
			//			Integer.valueOf("dsf");

			if (save < 1) {
				r.setCode(EResponseCode.NOT_ACCEPTABLE.value());
				r.setMessage("markdown添加失败");
				r.setContent(new Object() {
					public int result = save;
				});
				return r;
			}
			//同时增加资源数据
			Resource resource = new Resource();
			resource.setId(id);
			resource.setDirectoryId(markdown.directoryId);
			resource.setAppId(markdown.appId);
			resource.setName(markdown.name);
			Date date = new Date();
			resource.setCreateTime(date);
			resource.setUpdateTime(date);
			resource.setCreateUser(userId);
			resource.setUpdateUser(userId);
			resource.setResourceType(1);
			resource.setRemark(markdown.remark);
			resource.setReadable(markdown.readable);
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
			//			dataSourceTransactionManager.commit(status);
			r.setContent(markdownEntity);
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
			//			throw e;
		}
		return r;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "修改", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity update(@ApiParam @RequestBody Add_reqBody markdown, HttpServletRequest req) {
		if (Objects.isNull(markdown.appId) || Objects.isNull(markdown.directoryId)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		Application application = appMapper.findById(markdown.appId);
		Directory directory = directoryMapper.findById(markdown.directoryId);
		if (Objects.isNull(application) || Objects.isNull(directory)) {
			r.setCode(EResponseCode.BAD_REQUEST.value());
			r.setMessage("所属目录或app应用不存在！！！");
			return r;
		}
		String userId = (String) req.getSession().getAttribute("userId");
		try {
			Markdown markdownEntity = new Markdown();
			markdownEntity.setId(markdown.id);
			markdownEntity.setName(markdown.name);
			markdownEntity.setContent(markdown.content);
			int save = mapper.update(markdownEntity);
			if (save < 1) {
				r.setContent(new Object() {
					public int result = save;
				});
				return r;
			}
			//同时修改资源数据
			Resource resource = new Resource();
			resource.setId(markdown.id);
			resource.setDirectoryId(markdown.directoryId);
			resource.setAppId(markdown.appId);
			resource.setName(markdown.name);
			resource.setUpdateTime(new Date());
			resource.setUpdateUser(userId);
			resource.setResourceType(1);
			resource.setRemark(markdown.remark);
			resource.setReadable(markdown.readable);
			int save2 = resourceMapper.update(resource);
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

	public static class Update_reqBody {
		@ApiModelProperty("主键")
		public Long id;
		@ApiModelProperty("名称")
		public String name = "";
		@ApiModelProperty("内容")
		public String content = "";

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/updateContent", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "修改内容", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity update(@ApiParam @RequestBody Update_reqBody markdown, HttpServletRequest req) {
		if (Objects.isNull(markdown.id)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		String userId = (String) req.getSession().getAttribute("userId");
		try {
			Resource resource = resourceMapper.findById(markdown.id);
			if (resource == null) {
				r.setContent("资源不存在！");
				r.setMessage("资源不存在！");
				r.setCode(EResponseCode.NOT_FOUND.value());
				return r;
			}
			if (!resource.getOwner().equals(userId)) {
				r.setContent("没有权限操作！");
				r.setMessage("没有权限操作！");
				r.setCode(EResponseCode.NOT_ALLOWED.value());
				return r;
			}
			Markdown markdownEntity = new Markdown();
			markdownEntity.setId(markdown.id);
			markdownEntity.setName(markdown.name);
			markdownEntity.setContent(markdown.content);
			int save = mapper.update(markdownEntity);
			if (save < 1) {
				r.setContent(new Object() {
					public int result = save;
				});
				return r;
			}
			//同时修改资源数据
			resource.setName(markdown.name);
			resource.setUpdateTime(new Date());
			resource.setUpdateUser(userId);
			resource.setResourceType(1);
			int save2 = resourceMapper.update(resource);
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
			Markdown findById = mapper.findById(id);
			r.setContent(new Object() {
				public Markdown markdown = findById;
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
	@ApiOperation(httpMethod = "POST", value = "查找所有", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findAll() {
		ResponseEntity r = ResponseEntity.newOk();
		try {
			List<Markdown> findAll = mapper.findAll();
			r.setContent(new Object() {
				public List<Markdown> lists = findAll;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "markdown检索", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity search(@ApiParam("检索数据") @RequestParam String content) {
		if (StringUtils.isEmpty(content)) {
			content = "";
		}
		ResponseEntity r = ResponseEntity.newOk();
		StringBuilder sb = new StringBuilder();
		sb.append("'%" + content + "%'");
		try {
			List<Markdown> search = mapper.search(sb.toString());
			r.setContent(new Object() {
				public List<Markdown> lists = search;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	public static class Add_reqBody {
		@ApiModelProperty("主键")
		public Long id;
		@ApiModelProperty("名称")
		public String name = "";
		@ApiModelProperty("内容")
		public String content = "";
		@ApiModelProperty("目录id")
		public long directoryId;
		@ApiModelProperty("项目id")
		public long appId;
		@ApiModelProperty("备注")
		public String remark = "";
		@ApiModelProperty("是否资源公开(所有人都可以查阅）0否，1是")
		public Integer readable = 0;

	}
}

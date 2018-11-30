/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package cn.harry12800.api.doc.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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
import com.google.common.collect.Maps;

import cn.harry12800.api.doc.controller.webdto.ResourceDto;
import cn.harry12800.api.doc.http.EResponseCode;
import cn.harry12800.api.doc.http.ResponseEntity;
import cn.harry12800.api.util.DateUtils;
import cn.harry12800.api.util.SQLUtils;
import cn.harry12800.db.entity.Application;
import cn.harry12800.db.entity.AutoApiMarkdown;
import cn.harry12800.db.entity.Directory;
import cn.harry12800.db.entity.Markdown;
import cn.harry12800.db.entity.Resource;
import cn.harry12800.db.entity.ResourcesUpload;
import cn.harry12800.db.mapper.ApiMapper;
import cn.harry12800.db.mapper.ApplicationMapper;
import cn.harry12800.db.mapper.AutoApiMarkdownMapper;
import cn.harry12800.db.mapper.DirectoryMapper;
import cn.harry12800.db.mapper.Int64SequenceEntityMapper;
import cn.harry12800.db.mapper.MarkdownMapper;
import cn.harry12800.db.mapper.ResourceMapper;
import cn.harry12800.db.mapper.ResourceUploadMapper;
import cn.harry12800.db.service.ResourceService;
import cn.harry12800.tools.RegularExpression;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 资源：自动生成文档，markdown文档，上传的文件Controller
 * 
 * @author 周国柱
 * @version 1.0
 */
// @Transactional(readOnly = true)
@RestController
@RequestMapping("/v1/doc/resource")
public class ResourceController {// extends CrudService<ResourceMapper,
									// Resource> {

	private static final String API_TAGS = "资源：自动生成文档，markdown文档，上传的文件/ResourceController";

	@Autowired
	ResourceMapper mapper;
	@Autowired
	ResourceService resourceService;
	@Autowired
	ApplicationMapper appMapper;
	@Autowired
	DirectoryMapper directoryMapper;
	@Autowired
	Int64SequenceEntityMapper sequenceMapper;
	@Autowired
	MarkdownMapper markdownMapper;
	@Autowired
	AutoApiMarkdownMapper apiMarkdownMapper;
	@Autowired
	ApiMapper apiMapper;
	@Autowired
	ResourceUploadMapper resourceUploadMapper;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "添加", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity add(@ApiParam @RequestBody ResourceDto resource, HttpServletRequest req) {
		if (Objects.isNull(resource.appId) || Objects.isNull(resource.directoryId)) {
			return ResponseEntity.newBadRequest();
		}
		String userId = (String) req.getSession().getAttribute("userId");
		ResponseEntity r = ResponseEntity.newOk();
		Application application = appMapper.findById(resource.appId);
		Directory directory = directoryMapper.findById(resource.directoryId);
		if (Objects.isNull(application) || Objects.isNull(directory)) {
			r.setCode(EResponseCode.BAD_REQUEST.value());
			r.setMessage("所属目录或app应用不存在！！！");
			return r;
		}
		try {
			Resource entity = resource.toResourceEntity();
			entity.setCreateUser(userId);
			Long nextSequence = sequenceMapper.getNextSequence("fingerchat_dev_docs.resource.id");
			entity.setId(nextSequence);
			entity.setOwner(userId);
			int save = mapper.save(entity);
			// 添加对应用户权限
			mapper.insertUserResource(userId, nextSequence);
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
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "删除", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delete(@ApiParam @RequestParam Long id, HttpServletRequest req) {
		if (Objects.isNull(id)) {
			return ResponseEntity.newBadRequest();
		}
		String userId = (String) req.getSession().getAttribute("userId");
		ResponseEntity r = ResponseEntity.newOk();
		try {
			Resource resource = mapper.findById(id);
			if (resource == null) {
				r.setCode(20);
				r.setMessage("资源不存在！");
				return r;
			}
			if (!resource.getOwner().equals(userId)) {
				r.setCode(EResponseCode.NOT_ACCEPTABLE.value());
				r.setMessage("无权限操作！");
				return r;
			}
			if (resource.getResourceType() == 1)
				markdownMapper.deleteById(id);
			else if (resource.getResourceType() == 2) {
				apiMarkdownMapper.deleteById(id);
				apiMapper.deleteByResourceId(id);
			} else if (resource.getResourceType() == 3) {
				resourceUploadMapper.deleteById(id);
			}

			int save = resourceService.deleteById(id);
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
	public ResponseEntity update(@ApiParam @RequestBody ResourceDto resource) {
		if (Objects.isNull(resource.appId) || Objects.isNull(resource.directoryId)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		Application application = appMapper.findById(resource.appId);
		Directory directory = directoryMapper.findById(resource.directoryId);
		if (Objects.isNull(application) || Objects.isNull(directory)) {
			r.setCode(EResponseCode.BAD_REQUEST.value());
			r.setMessage("所属目录或app应用不存在！！！");
			return r;
		}
		try {
			int save = mapper.update(resource.toResourceEntity());
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
	public ResponseEntity findById(@ApiParam @RequestParam Long id) {
		if (Objects.isNull(id)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			Resource findById = mapper.findById(id);
			r.setContent(new Object() {
				public Resource resource = findById;
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
			List<Resource> findAll = mapper.findAll();
			r.setContent(new Object() {
				public List<Resource> lists = findAll;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/public", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "资源公开", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity resourcePublic(HttpServletRequest req, Long resourceId) {

		ResponseEntity r = ResponseEntity.newOk();
		try {

			String userId = (String) req.getSession().getAttribute("userId");
			Resource re = mapper.findById(resourceId);
			if (re == null | !re.getOwner().equalsIgnoreCase(userId)) {
				r.setCode(EResponseCode.NOT_ACCEPTABLE.value());
				r.setMessage("非资源拥有者，无权限转让到其他用户！！");
			} else {
				int x = mapper.publicResource(resourceId);
				r.setContent(new Object() {
					public int result = x;
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@ApiModel
	public static class PageView {
		@ApiModelProperty("查询页数")
		public int pageIndex = 1;
		@ApiModelProperty("每页条数")
		public int pageSize = 10;
		@ApiModelProperty("目录id")
		public long directoryId;
		@ApiModelProperty("检索目录下资源状态(0所有，1自己，2其他)")
		public int state = 0;
		@ApiModelProperty("检索内容")
		public String keyWord = "";
	}

	static class WebResource {
		public Long id;
		public String name;
		public String lastTime;
		public String content;
		public Date updateTime;
		public Date createTime;
		public String updateTimeStr;
		public String createTimeStr;
		public String createUser;
		public String updateUser;
		public String url;
		public String copyUrl;
		public boolean isOwner;
		public Integer resourceType;
	}

	static class PageData<T> {
		// 总条数
		public int total;
		public int pageSize;
		public int pageIndex;
		// 总页数
		public int pageCount;
		public List<T> data = Lists.newArrayList();
	}

	@RequestMapping(value = "/getOtherByDirectoryId", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "通过目录id拿仅仅可以读的资源信息", tags = {
			API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getOtherByDirectoryId(@ApiParam("资源分页数据") @RequestBody PageView pageView,
			HttpServletRequest req) {
		if (Objects.isNull(pageView.directoryId)) {
			return ResponseEntity.newBadRequest();
		}
		List<WebResource> webResources = Lists.newArrayList();
		ResponseEntity r = ResponseEntity.newOk();
		String userId = (String) req.getSession().getAttribute("userId");
		try {
			List<Resource> lists = mapper.findByDirectoryId(pageView.directoryId);
			// System.out.println(lists.size());
			Set<Long> ids = new HashSet<Long>();
			Set<Long> autoids = new HashSet<Long>();
			Set<Long> attachmentIds = new HashSet<Long>();
			Map<Long, Resource> maps = Maps.newHashMap();
			for (Resource resource : lists) {
				if (resource.getOwner().equals(userId))
					continue;
				maps.put(resource.getId(), resource);
				switch (resource.getResourceType()) {
				case 1: // markdown
					ids.add(resource.getId());
					break;
				case 2:
					autoids.add(resource.getId());
					break;
				case 3:
					attachmentIds.add(resource.getId());
					break;
				}
			}
			// System.out.println("11:" + ids);
			// System.out.println("22:" + autoids);
			String markdownIds = SQLUtils.getSQLInSentenceLong(ids, "id");
			String autoApiMarkdownIds = SQLUtils.getSQLInSentenceLong(autoids, "id");
			String attachmentFileIds = SQLUtils.getSQLInSentenceLong(attachmentIds, "id");
			// System.out.println(markdownIds);
			// System.out.println(autoApiMarkdownIds);
			List<Markdown> markdowns = markdownMapper.findByIds(markdownIds);
			List<AutoApiMarkdown> autoMarkdowns = apiMarkdownMapper.findByIds(autoApiMarkdownIds);
			List<ResourcesUpload> resourcesUploads = resourceUploadMapper.findByIds(attachmentFileIds);

			collectMarkdown(userId, webResources, markdowns, maps);
			collectMarkdownApi(userId, webResources, autoMarkdowns, maps);
			collectResourcesUpload(userId, webResources, resourcesUploads, maps);
			// System.out.println(webResources.size());

			PageData<WebResource> pageData = getPageData(pageView, webResources);
			// System.out.println(webResources.size());
			r.setContent(pageData);
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	private void collectResourcesUpload(String userId, List<WebResource> webResources,
			List<ResourcesUpload> resourcesUploads, Map<Long, Resource> maps) {
		for (ResourcesUpload resourcesUpload : resourcesUploads) {
			WebResource wr = new WebResource();
			System.err.println(resourcesUpload);
			wr.id = resourcesUpload.getId();
			wr.name = resourcesUpload.getFileName();
			wr.content = resourcesUpload.getFileName();
			wr.updateTimeStr = DateUtils.getTimeByFormat(maps.get(wr.id).getUpdateTime(), "MM月dd日");
			wr.createTimeStr = DateUtils.getTimeByFormat(maps.get(wr.id).getCreateTime(), "MM月dd日");
			wr.updateTime = maps.get(wr.id).getUpdateTime();
			wr.createTime = maps.get(wr.id).getCreateTime();
			wr.createUser = maps.get(wr.id).getCreateUser();
			wr.updateUser = maps.get(wr.id).getUpdateUser();
			wr.resourceType = maps.get(wr.id).getResourceType();
			wr.url = "/v1/doc/viewById/" + wr.id;
			if (wr.name.endsWith(".png") || wr.name.endsWith(".jpg"))
				wr.copyUrl = "![" + wr.name + "](" + wr.url + ")";
			else {
				wr.copyUrl = "[" + wr.name + "](" + wr.url + ")";
			}
			wr.isOwner = maps.get(wr.id).getOwner().equals(userId);
			webResources.add(wr);
		}

	}

	private void collectMarkdownApi(String userId, List<WebResource> webResources, List<AutoApiMarkdown> autoMarkdowns,
			Map<Long, Resource> maps) {
		for (AutoApiMarkdown autoApiMarkdown : autoMarkdowns) {
			WebResource wr = new WebResource();
			wr.id = autoApiMarkdown.getId();
			wr.name = autoApiMarkdown.getName();
			wr.updateTimeStr = DateUtils.getTimeByFormat(maps.get(wr.id).getUpdateTime(), "MM月dd日");
			wr.createTimeStr = DateUtils.getTimeByFormat(maps.get(wr.id).getCreateTime(), "MM月dd日");
			wr.updateTime = maps.get(wr.id).getUpdateTime();
			wr.createTime = maps.get(wr.id).getCreateTime();
			wr.createUser = maps.get(wr.id).getCreateUser();
			wr.updateUser = maps.get(wr.id).getUpdateUser();
			wr.resourceType = maps.get(wr.id).getResourceType();
			wr.url = "/v1/doc/viewById/" + wr.id;
			wr.isOwner = maps.get(wr.id).getOwner().equals(userId);
			wr.copyUrl = "[" + wr.name + "](" + wr.url + ")";
			if (wr.isOwner) {
				wr.content = StringUtils.isEmpty(autoApiMarkdown.getPrefixContent())
						? autoApiMarkdown.getSuffixContent() : autoApiMarkdown.getPrefixContent();
				wr.content = RegularExpression.realContent("", wr.content);
			} else {
				wr.content = "";
			}
			webResources.add(wr);
		}
	}

	private void collectMarkdown(String userId, List<WebResource> webResources, List<Markdown> markdowns,
			Map<Long, Resource> maps) {
		// System.out.println(markdowns.size());
		for (Markdown markdown : markdowns) {
			WebResource wr = new WebResource();
			wr.id = markdown.getId();
			wr.name = markdown.getName();
			wr.updateTimeStr = DateUtils.getTimeByFormat(maps.get(wr.id).getUpdateTime(), "MM月dd日");
			wr.createTimeStr = DateUtils.getTimeByFormat(maps.get(wr.id).getCreateTime(), "MM月dd日");
			wr.updateTime = maps.get(wr.id).getUpdateTime();
			wr.createTime = maps.get(wr.id).getCreateTime();
			wr.createUser = maps.get(wr.id).getCreateUser();
			wr.updateUser = maps.get(wr.id).getUpdateUser();
			wr.resourceType = maps.get(wr.id).getResourceType();
			wr.url = "/v1/doc/viewById/" + wr.id;
			wr.isOwner = maps.get(wr.id).getOwner().equals(userId);
			wr.copyUrl = "[" + wr.name + "](" + wr.url + ")";
			if (wr.isOwner) {
				wr.content = RegularExpression.realContent("", markdown.getContent());
			} else {
				wr.content = "";
			}
			webResources.add(wr);
		}
	}

	private PageData<WebResource> getSearchPageData(PageView pageView, List<WebResource> webResources) {
		List<WebResource> lists = Lists.newArrayList();
		for (WebResource webResource : webResources) {
			if (webResource.name.contains(pageView.keyWord)) {
				lists.add(webResource);
			} else if (webResource.content.contains(pageView.keyWord)) {
				lists.add(webResource);
			}
		}
		webResources = lists;
		PageData<WebResource> PageData = new PageData<WebResource>();
		Collections.sort(webResources, new Comparator<WebResource>() {
			@Override
			public int compare(WebResource o1, WebResource o2) {
				return o2.createTime.compareTo(o1.createTime);
			}
		});
		int pageSize = (pageView.pageSize > 0) ? pageView.pageSize : 10;
		int pageIndex = (pageView.pageIndex > 0) ? pageView.pageIndex : 1;
		PageData.pageCount = webResources.size() / pageSize;
		if (webResources.size() % pageSize != 0) {
			PageData.pageCount++;
		}
		PageData.pageIndex = pageIndex;
		PageData.pageSize = pageSize;
		PageData.total = webResources.size();

		System.err.println(pageIndex + "   " + webResources.size());
		int fromIndex = (pageIndex - 1) * pageSize;
		if (fromIndex >= webResources.size()) {
			return PageData;
		}

		int toIndex = pageIndex * pageSize;
		if (toIndex >= webResources.size()) {
			toIndex = webResources.size();
		}
		System.err.println(fromIndex + "   " + toIndex);
		PageData.data = webResources.subList(fromIndex, toIndex);

		return PageData;
	}

	private PageData<WebResource> getPageData(PageView pageView, List<WebResource> webResources) {
		PageData<WebResource> PageData = new PageData<WebResource>();
		Collections.sort(webResources, new Comparator<WebResource>() {
			@Override
			public int compare(WebResource o1, WebResource o2) {
				return o2.createTime.compareTo(o1.createTime);
			}
		});
		int pageSize = (pageView.pageSize > 0) ? pageView.pageSize : 10;
		int pageIndex = (pageView.pageIndex > 0) ? pageView.pageIndex : 1;
		PageData.pageCount = webResources.size() / pageSize;
		if (webResources.size() % pageSize != 0) {
			PageData.pageCount++;
		}
		PageData.pageIndex = pageIndex;
		PageData.pageSize = pageSize;
		PageData.total = webResources.size();

		System.err.println(pageIndex + "   " + webResources.size());
		int fromIndex = (pageIndex - 1) * pageSize;
		if (fromIndex >= webResources.size()) {
			return PageData;
		}

		int toIndex = pageIndex * pageSize;
		if (toIndex >= webResources.size()) {
			toIndex = webResources.size();
		}
		System.err.println(fromIndex + "   " + toIndex);
		PageData.data = webResources.subList(fromIndex, toIndex);

		return PageData;
	}

	@RequestMapping(value = "/findSelfResource", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "通过目录id拿自己的资源信息", tags = {
			API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findSelfResource(@ApiParam("资源分页数据") @RequestBody PageView pageView, HttpServletRequest req) {
		if (Objects.isNull(pageView.directoryId)) {
			return ResponseEntity.newBadRequest();
		}
		List<WebResource> webResources = Lists.newArrayList();
		ResponseEntity r = ResponseEntity.newOk();
		try {
			String userId = (String) req.getSession().getAttribute("userId");
			List<Resource> lists = mapper.findByDirectoryId(pageView.directoryId);
			// System.out.println(lists.size());
			Set<Long> ids = new HashSet<Long>();
			Set<Long> autoids = new HashSet<Long>();
			Set<Long> attachmentIds = new HashSet<Long>();
			Map<Long, Resource> maps = Maps.newHashMap();
			for (Resource resource : lists) {
				if (!resource.getOwner().equals(userId))
					continue;
				maps.put(resource.getId(), resource);
				switch (resource.getResourceType()) {
				case 1: // markdown
					ids.add(resource.getId());
					break;
				case 2:
					autoids.add(resource.getId());
					break;
				case 3:
					attachmentIds.add(resource.getId());
					break;
				}
			}
			// System.out.println("11:" + ids);
			// System.out.println("22:" + autoids);
			String markdownIds = SQLUtils.getSQLInSentenceLong(ids, "id");
			String autoApiMarkdownIds = SQLUtils.getSQLInSentenceLong(autoids, "id");
			String attachmentFileIds = SQLUtils.getSQLInSentenceLong(attachmentIds, "id");
			// System.out.println(markdownIds);
			// System.out.println(autoApiMarkdownIds);
			List<Markdown> markdowns = markdownMapper.findByIds(markdownIds);
			List<ResourcesUpload> resourcesUploads = resourceUploadMapper.findByIds(attachmentFileIds);
			List<AutoApiMarkdown> autoMarkdowns = apiMarkdownMapper.findByIds(autoApiMarkdownIds);

			collectMarkdown(userId, webResources, markdowns, maps);
			collectMarkdownApi(userId, webResources, autoMarkdowns, maps);
			collectResourcesUpload(userId, webResources, resourcesUploads, maps);
			// System.out.println(webResources.size());

			PageData<WebResource> pageData = getPageData(pageView, webResources);
			// System.out.println(webResources.size());
			r.setContent(pageData);
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@RequestMapping(value = "/findReadableResource", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "通过目录id拿自己全部的资源信息", tags = {
			API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findReadableResource(@ApiParam("资源分页数据") @RequestBody PageView pageView,
			HttpServletRequest req) {
		if (Objects.isNull(pageView.directoryId)) {
			return ResponseEntity.newBadRequest();
		}
		List<WebResource> webResources = Lists.newArrayList();
		ResponseEntity r = ResponseEntity.newOk();
		try {
			String userId = (String) req.getSession().getAttribute("userId");
			System.err.println("aaaa:" + userId);
			List<Resource> lists = mapper.findByDirectoryId(pageView.directoryId);
			// System.out.println(lists.size());
			Set<Long> ids = new HashSet<Long>();
			Set<Long> autoids = new HashSet<Long>();
			Set<Long> attachmentIds = new HashSet<Long>();
			Map<Long, Resource> maps = Maps.newHashMap();
			for (Resource resource : lists) {
				maps.put(resource.getId(), resource);
				switch (resource.getResourceType()) {
				case 1: // markdown
					ids.add(resource.getId());
					break;
				case 2:
					autoids.add(resource.getId());
					break;
				case 3:
					attachmentIds.add(resource.getId());
					break;
				}
			}
			// System.out.println("11:" + ids);
			// System.out.println("22:" + autoids);
			String markdownIds = SQLUtils.getSQLInSentenceLong(ids, "id");
			String autoApiMarkdownIds = SQLUtils.getSQLInSentenceLong(autoids, "id");
			String attachmentFileIds = SQLUtils.getSQLInSentenceLong(attachmentIds, "id");
			// System.out.println(markdownIds);
			// System.out.println(autoApiMarkdownIds);
			List<Markdown> markdowns = markdownMapper.findByIds(markdownIds);
			List<ResourcesUpload> resourcesUploads = resourceUploadMapper.findByIds(attachmentFileIds);
			List<AutoApiMarkdown> autoMarkdowns = apiMarkdownMapper.findByIds(autoApiMarkdownIds);

			collectMarkdown(userId, webResources, markdowns, maps);
			collectMarkdownApi(userId, webResources, autoMarkdowns, maps);
			collectResourcesUpload(userId, webResources, resourcesUploads, maps);
			// System.out.println(webResources.size());

			PageData<WebResource> pageData = getPageData(pageView, webResources);
			// System.out.println(webResources.size());
			r.setContent(pageData);
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@RequestMapping(value = "/findDifferentResource", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "通过目录id根据状态检索资源信息", tags = {
			API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findDifferentResource(@ApiParam("资源分页数据") @RequestBody PageView pageView,
			HttpServletRequest req) {
		if (Objects.isNull(pageView.directoryId)) {
			return ResponseEntity.newBadRequest();
		}
		List<WebResource> otherwebResources = Lists.newArrayList();
		List<WebResource> myselwebResources = Lists.newArrayList();
		List<WebResource> allwebResources = Lists.newArrayList();
		ResponseEntity r = ResponseEntity.newOk();
		Map<Long, Resource> otherMaps = Maps.newHashMap();
		Map<Long, Resource> myselfMaps = Maps.newHashMap();
		try {
			String userId = (String) req.getSession().getAttribute("userId");
			List<Resource> lists = mapper.findByDirectoryId(pageView.directoryId);
			for (Resource resource : lists) {
				if (resource.getOwner().equalsIgnoreCase(userId)) {
					myselfMaps.put(resource.getId(), resource);
					WebResource wr = null;
					switch (resource.getResourceType()) {
					case 1: // markdown
						wr = new WebResource();
						Markdown markdown = markdownMapper.findById(resource.getId());
						wr.id = markdown.getId();
						wr.name = markdown.getName();
						wr.content = RegularExpression.realContent(pageView.keyWord, markdown.getContent());
						wr.updateTimeStr = DateUtils.getTimeByFormat(myselfMaps.get(wr.id).getUpdateTime(), "MM月dd日");
						wr.createTimeStr = DateUtils.getTimeByFormat(myselfMaps.get(wr.id).getCreateTime(), "MM月dd日");
						wr.updateTime = myselfMaps.get(wr.id).getUpdateTime();
						wr.createTime = myselfMaps.get(wr.id).getCreateTime();
						wr.createUser = myselfMaps.get(wr.id).getCreateUser();
						wr.updateUser = myselfMaps.get(wr.id).getUpdateUser();
						wr.resourceType = myselfMaps.get(wr.id).getResourceType();
						wr.url = "/v1/doc/viewById/" + wr.id;
						wr.isOwner = true;
						myselwebResources.add(wr);
						break;
					case 2:
						wr = new WebResource();
						AutoApiMarkdown autoApiMarkdown = apiMarkdownMapper.findById(resource.getId());
						wr.id = autoApiMarkdown.getId();
						wr.name = autoApiMarkdown.getName();
						wr.content = StringUtils.isEmpty(autoApiMarkdown.getPrefixContent())
								? autoApiMarkdown.getSuffixContent() : autoApiMarkdown.getPrefixContent();
						wr.content = RegularExpression.realContent(pageView.keyWord, wr.content);
						wr.updateTimeStr = DateUtils.getTimeByFormat(myselfMaps.get(wr.id).getUpdateTime(), "MM月dd日");
						wr.createTimeStr = DateUtils.getTimeByFormat(myselfMaps.get(wr.id).getCreateTime(), "MM月dd日");
						wr.updateTime = myselfMaps.get(wr.id).getUpdateTime();
						wr.createTime = myselfMaps.get(wr.id).getCreateTime();
						wr.createUser = myselfMaps.get(wr.id).getCreateUser();
						wr.updateUser = myselfMaps.get(wr.id).getUpdateUser();
						wr.resourceType = myselfMaps.get(wr.id).getResourceType();
						wr.url = "/v1/doc/viewById/" + wr.id;
						wr.isOwner = true;
						myselwebResources.add(wr);
						break;
					case 3:
						break;
					}
				} else {
					otherMaps.put(resource.getId(), resource);
					WebResource wr = null;
					switch (resource.getResourceType()) {
					case 1: // markdown
						wr = new WebResource();
						Markdown markdown = markdownMapper.findById(resource.getId());
						wr.id = markdown.getId();
						wr.name = markdown.getName();
						wr.content = RegularExpression.realContent(pageView.keyWord, markdown.getContent());
						wr.updateTimeStr = DateUtils.getTimeByFormat(otherMaps.get(wr.id).getUpdateTime(), "MM月dd日");
						wr.createTimeStr = DateUtils.getTimeByFormat(otherMaps.get(wr.id).getCreateTime(), "MM月dd日");
						wr.updateTime = otherMaps.get(wr.id).getUpdateTime();
						wr.createTime = otherMaps.get(wr.id).getCreateTime();
						wr.createUser = otherMaps.get(wr.id).getCreateUser();
						wr.updateUser = otherMaps.get(wr.id).getUpdateUser();
						wr.resourceType = otherMaps.get(wr.id).getResourceType();
						wr.url = "/v1/doc/viewById/" + wr.id;
						wr.isOwner = false;
						otherwebResources.add(wr);
						break;
					case 2:
						wr = new WebResource();
						AutoApiMarkdown autoApiMarkdown = apiMarkdownMapper.findById(resource.getId());
						wr.id = autoApiMarkdown.getId();
						wr.name = autoApiMarkdown.getName();
						wr.content = StringUtils.isEmpty(autoApiMarkdown.getPrefixContent())
								? autoApiMarkdown.getSuffixContent() : autoApiMarkdown.getPrefixContent();
						wr.content = RegularExpression.realContent(pageView.keyWord, wr.content);
						wr.updateTimeStr = DateUtils.getTimeByFormat(otherMaps.get(wr.id).getUpdateTime(), "MM月dd日");
						wr.createTimeStr = DateUtils.getTimeByFormat(otherMaps.get(wr.id).getCreateTime(), "MM月dd日");
						wr.updateTime = otherMaps.get(wr.id).getUpdateTime();
						wr.createTime = otherMaps.get(wr.id).getCreateTime();
						wr.createUser = otherMaps.get(wr.id).getCreateUser();
						wr.updateUser = otherMaps.get(wr.id).getUpdateUser();
						wr.resourceType = otherMaps.get(wr.id).getResourceType();
						wr.url = "/v1/doc/viewById/" + wr.id;
						wr.isOwner = false;
						otherwebResources.add(wr);
						break;
					case 3:
						break;
					}
				}
			}
			allwebResources.addAll(myselwebResources);
			allwebResources.addAll(otherwebResources);
			PageData<WebResource> pageData = null;
			switch (pageView.state) { // search
			case 0: //
				pageData = getSearchPageData(pageView, allwebResources);
				break;
			case 1:
				pageData = getSearchPageData(pageView, myselwebResources);
				break;
			case 2:
				pageData = getSearchPageData(pageView, otherwebResources);
				break;
			}
			r.setContent(pageData);
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

}

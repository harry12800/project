/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package cn.harry12800.scan.doc.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.harry12800.db.entity.Application;
import cn.harry12800.db.entity.Directory;
import cn.harry12800.db.mapper.ApiMapper;
import cn.harry12800.db.mapper.ApplicationMapper;
import cn.harry12800.db.mapper.AutoApiMarkdownMapper;
import cn.harry12800.db.mapper.DirectoryMapper;
import cn.harry12800.db.mapper.Int64SequenceEntityMapper;
import cn.harry12800.db.mapper.MarkdownMapper;
import cn.harry12800.db.mapper.ResourceMapper;
import cn.harry12800.db.mapper.ResourceUploadMapper;
import cn.harry12800.db.service.DirectoryService;
import cn.harry12800.scan.doc.controller.webdto.DirectoryDto;
import cn.harry12800.scan.doc.http.EResponseCode;
import cn.harry12800.scan.doc.http.ResponseEntity;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 项目的层次目录Controller
 * 
 * @author 周国柱
 * @version 1.0
 */
// @Transactional(readOnly = true)
@RestController
@RequestMapping("/v1/doc/directory")
public class DirectoryController {// extends CrudService<DirectoryMapper,
									// Directory> {

	private static final String API_TAGS = "项目的层次目录/DirectoryController";

	@Autowired
	DirectoryMapper directoryMapper;
	@Autowired
	DirectoryService directoryService;

	@Autowired
	ApplicationMapper applicationMapper;
	@Autowired
	ResourceMapper resourceMapper;
	@Autowired
	ApiMapper apiMapper;
	@Autowired
	MarkdownMapper markdownMapper;
	@Autowired
	AutoApiMarkdownMapper autoApiMarkdownMapper;
	@Autowired
	ResourceUploadMapper resourceUploadMapper;
	@Autowired
	Int64SequenceEntityMapper sequenceMapper;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "添加", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity add(@ApiParam @RequestBody DirectoryDto directory, HttpServletRequest req) {
		if (!ObjectUtils.anyNotNull(directory.appId, directory.parentId, directory.name)) {
			return ResponseEntity.newBadRequest();
		}
		String userId = (String) req.getSession().getAttribute("userId");
		ResponseEntity r = ResponseEntity.newOk();
		if (directory.parentId != 0) {
			Directory findById = directoryMapper.findById(directory.parentId);
			if (Objects.isNull(findById)) {
				r.setCode(EResponseCode.BAD_REQUEST.value());
				r.setMessage("该父目录不存在！！！");
				return r;
			}
		}
		Application application = applicationMapper.findById(directory.appId);
		if (Objects.isNull(application)) {
			r.setCode(EResponseCode.BAD_REQUEST.value());
			r.setMessage("该app应用不存在！！！");
			return r;
		}
		try {
			Long nextId = sequenceMapper.getNextSequence("fingerchat.directory.id");
			Directory directoryEntity = directory.toDirectoryEntity();
			directoryEntity.setCreateTime(new Date());
			directoryEntity.setUpdateTime(new Date());
			directoryEntity.setCreateUser(userId);
			directoryEntity.setUpdateUser(userId);
			directoryEntity.setId(nextId);
			directoryEntity.setOwner(userId);
			directoryEntity.setSort(0);
			int save = directoryMapper.save(directoryEntity);
			r.setContent(new Object() {
				public Directory directory = directoryEntity;
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
	@ApiOperation(httpMethod = "POST", value = "删除目录，以及目录下的资源！", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delete(@ApiParam @RequestParam Long id, HttpServletRequest req) {
		ResponseEntity r = ResponseEntity.newOk();
		try {
			String userId = (String) req.getSession().getAttribute("userId");
			Directory directory = directoryMapper.findById(id);
			if (Objects.isNull(directory)) {
				r.setCode(20);
				r.setMessage("无此目录");
				return r;
			}
			if (!directory.getCreateUser().equals(userId)) {
				r.setCode(20);
				r.setMessage("无权限操作");
				return r;
			}
			int deleteById = directoryService.deleteById(id);
			r.setContent(new Object() {
				public int result = deleteById;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "修改整个目录参数，除了id", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity update(@ApiParam @RequestBody DirectoryDto directory, HttpServletRequest req) {
		if (!ObjectUtils.anyNotNull(directory.appId, directory.parentId, directory.name)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			String userId = (String) req.getSession().getAttribute("userId");
			Directory dir = directoryMapper.findById(directory.id);
			if (Objects.isNull(dir)) {
				r.setCode(20);
				r.setMessage("无此目录");
				return r;
			}
			if (!dir.getCreateUser().equals(userId)) {
				r.setCode(20);
				r.setMessage("无权限操作");
				return r;
			}
			if (directory.parentId != 0) {
				Directory findById = directoryMapper.findById(directory.parentId);
				if (Objects.isNull(findById)) {
					r.setCode(EResponseCode.BAD_REQUEST.value());
					r.setMessage("该父目录不存在！！！");
					return r;
				}
			}
			Application application = applicationMapper.findById(directory.appId);
			if (Objects.isNull(application)) {
				r.setCode(EResponseCode.BAD_REQUEST.value());
				r.setMessage("该app应用不存在！！！");
				return r;
			}

			int save = directoryMapper.update(directory.toDirectoryEntity());
			r.setContent(new Object() {
				@SuppressWarnings("unused")
				public int result = save;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@RequestMapping(value = "/update-name", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "修改名字", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity updateName(@ApiParam @RequestBody DirectoryDto directory, HttpServletRequest req) {
		if (!ObjectUtils.anyNotNull(directory.id, directory.name)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			String userId = (String) req.getSession().getAttribute("userId");
			Directory findById = directoryMapper.findById(directory.id);
			if (Objects.isNull(findById)) {
				r.setCode(20);
				r.setMessage("无此目录");
				return r;
			}
			if (!findById.getCreateUser().equals(userId)) {
				r.setCode(20);
				r.setMessage("无权限操作");
				return r;
			}
			int save = directoryMapper.updateName(directory.id, directory.name);
			r.setContent(new Object() {
				@SuppressWarnings("unused")
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
			Directory findById = directoryMapper.findById(id);
			r.setContent(new Object() {
				public Directory directory = findById;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	static class DirectoryLevel {
		public Long id;
		public Long pid;
		public String name;
		public Boolean isEnd = true;
		public String url;
		public List<DirectoryLevel> menu = Lists.newArrayList();

		public List<DirectoryLevel> getMenu() {
			return menu;
		}

		public void setMenu(List<DirectoryLevel> menu) {
			this.menu = menu;
		}

		public static DirectoryLevel directory2Level(Directory directory) {
			DirectoryLevel dl = new DirectoryLevel();
			dl.id = directory.getId();
			dl.pid = directory.getParentId();
			dl.name = directory.getName();
			dl.url = "page/page.html";
			return dl;
		}
	}

	static class Applications {

		public Long id;
		public Long pid;
		public String name;
		public Boolean isEnd = true;
		public String url;
		public List<DirectoryLevel> menu = Lists.newArrayList();

		public List<DirectoryLevel> getMenu() {
			return menu;
		}

		public void setMenu(List<DirectoryLevel> menu) {
			this.menu = menu;
		}
	}

	@RequestMapping(value = "/findCompleteDirectory", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "获取完整的目录", tags = {
			API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findCompleteDirectory(HttpServletRequest req) {
		ResponseEntity r = ResponseEntity.newOk();
		try {
			String userId = (String) req.getSession().getAttribute("userId");
			List<Application> findAll = applicationMapper.findAll();
			List<Applications> _apps = Lists.newArrayList();
			for (Application application : findAll) {
				Applications app = new Applications();
				app.id = application.getId();
				app.name = application.getAppName();
				app.pid = 0L;
				app.url = "";
				List<Directory> lists = directoryMapper.findByAppIdAndUserId(application.getId(),userId);
				Map<Long, DirectoryLevel> maps = Maps.newHashMap();
				for (Directory directory : lists) {
					DirectoryLevel dl = DirectoryLevel.directory2Level(directory);
					maps.put(directory.getId(), dl);
				}
				for (Directory directory : lists) {
					DirectoryLevel directoryLevel = maps.get(directory.getParentId());
					if (directoryLevel == null) {
						app.menu.add(maps.get(directory.getId()));
					} else {
						directoryLevel.isEnd = false;
						directoryLevel.menu.add(maps.get(directory.getId()));
					}
				}
				if (app.menu.size() > 0) {
					app.isEnd = false;
				} else {
					app.isEnd = true;
				}
				_apps.add(app);

			}
			r.setContent(_apps);
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
			List<Directory> findAll = directoryMapper.findAll();
			r.setContent(new Object() {
				public List<Directory> lists = findAll;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}
}

/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package cn.harry12800.api.doc.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.harry12800.api.doc.controller.webdto.ResourceTransferDto;
import cn.harry12800.api.doc.http.EResponseCode;
import cn.harry12800.api.doc.http.ResponseEntity;
import cn.harry12800.db.entity.FingerChatUser;
import cn.harry12800.db.entity.Resource;
import cn.harry12800.db.entity.ResourceTransfer;
import cn.harry12800.db.mapper.FingerChatUserMapper;
import cn.harry12800.db.mapper.ResourceMapper;
import cn.harry12800.db.mapper.ResourceTransferMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 资源转让记录表Controller
 * @author 周国柱
 * @version 1.0
 */
//@Transactional(readOnly = true)
@RestController
@RequestMapping("/v1/doc/resourceTransfer")
public class ResourceTransferController {// extends CrudService<ResourceTransferMapper, ResourceTransfer> {

	private static final String API_TAGS = "资源转让记录表/ResourceTransferController";

	@Autowired
	ResourceTransferMapper resourceTransferMapper;

	@Autowired
	ResourceMapper resourceMapper;
	@Autowired
	FingerChatUserMapper userMapper;

	@SuppressWarnings("unused")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "添加", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity add(@ApiParam @RequestBody ResourceTransferDto resourceTransfer) {
		if (Objects.isNull(resourceTransfer.resourceId)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		Resource resource = resourceMapper.findById(resourceTransfer.resourceId);
		if (Objects.isNull(resource)) {
			r.setCode(EResponseCode.BAD_REQUEST.value());
			r.setMessage("该资源目录不存在！！！");
			return r;
		}
		try {
			int save = resourceTransferMapper.save(resourceTransfer.toResourceTransferEntity());
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
	public ResponseEntity delete(@ApiParam @RequestParam int id) {
		if (Objects.isNull(id)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			int save = resourceTransferMapper.deleteById(String.valueOf(id));
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
	public ResponseEntity update(@ApiParam @RequestBody ResourceTransferDto resourceTransfer) {
		if (Objects.isNull(resourceTransfer.resourceId)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		Resource resource = resourceMapper.findById(resourceTransfer.resourceId);
		if (Objects.isNull(resource)) {
			r.setCode(EResponseCode.BAD_REQUEST.value());
			r.setMessage("该资源目录不存在！！！");
			return r;
		}
		try {
			int save = resourceTransferMapper.update(resourceTransfer.toResourceTransferEntity());
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
	public ResponseEntity findById(@ApiParam @RequestParam int id) {
		if (Objects.isNull(id)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			ResourceTransfer findById = resourceTransferMapper.findById(String.valueOf(id));
			r.setContent(new Object() {
				public ResourceTransfer resourceTransfer = findById;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/findByResourceId", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "通过资源主键查询转让记录", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity findByResourceId(@ApiParam @RequestParam Long resourceId) {
		ResponseEntity r = ResponseEntity.newOk();
		try {
			List<ResourceTransfer> _records = resourceTransferMapper.findByResourceId(resourceId);
			r.setContent(new Object() {
				public List<ResourceTransfer> records = _records;
			});
		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/transfer", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "将资源文件的拥有权转让给其他人，他将拥有修改文档的权限，如果不是拥有者调用不会成功！", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity transferResource(@ApiParam @RequestParam String userId, @ApiParam @RequestParam Long resourceId, HttpServletRequest req) {
		ResponseEntity r = ResponseEntity.newOk();
		try {
			String nowUserId = (String) req.getSession().getAttribute("userId");
			Resource resource = resourceMapper.findById(resourceId);
			if (Objects.isNull(resource)) {
				r.setCode(EResponseCode.NOT_FOUND.value());
				r.setMessage("资源未找到！！");
				return r;
			}
			if (!resource.getOwner().equalsIgnoreCase(nowUserId)) {
				r.setCode(EResponseCode.NOT_ACCEPTABLE.value());
				r.setMessage("非资源拥有者，无权限转让到其他用户！！");
				return r;
			}
			FingerChatUser user = userMapper.findByUserId(userId);
			if (Objects.isNull(resource)) {
				r.setCode(EResponseCode.NOT_FOUND.value());
				r.setMessage("用户不存在！！");
				return r;
			}
			if (resource.getOwner().equalsIgnoreCase(userId)) {
				if (Objects.isNull(resource)) {
					r.setCode(EResponseCode.NOT_ACCEPTABLE.value());
					r.setMessage("资源文件不能转让自己！！");
					return r;
				}
			} else {
				int count = resourceMapper.updateOwner(resourceId, userId);
				ResourceTransfer t = new ResourceTransfer();
				t.setFromUser(nowUserId);
				t.setCreateTime(new Date());
				t.setToUser(userId);
				t.setResourceId(resourceId);
				resourceTransferMapper.save(t);
				r.setContent(new Object() {
					public ResourceTransfer record = t;
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/getReadableUser", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "拿到拥有查看资源人员", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity getReadableUser(@ApiParam @RequestParam Long resourceId, HttpServletRequest req) {
		ResponseEntity r = ResponseEntity.newOk();
		try {
			String nowUserId = (String) req.getSession().getAttribute("userId");
			Resource resource = resourceMapper.findById(resourceId);
			if (Objects.isNull(resource)) {
				r.setCode(EResponseCode.NOT_FOUND.value());
				r.setMessage("资源未找到！！");
				return r;
			} else if (!resource.getOwner().equalsIgnoreCase(nowUserId)) {
				r.setCode(EResponseCode.NOT_ACCEPTABLE.value());
				r.setMessage("非资源拥有者，无权限进行此操作！！");
				return r;
			} else {
				List<FingerChatUser> _users = userMapper.getResourceUserByResourceId(resourceId);
				r.setContent(new Object() {
					public List<FingerChatUser> users = _users;
					public Integer readable = resource.getReadable();
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}

	public static class UrRequest_Body {
		public Long resourceId;
		public List<String> userIds;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/setReadableUser", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "添加查看资源权限人员！", tags = { API_TAGS }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity setReadableUser(@ApiParam @RequestBody UrRequest_Body body, HttpServletRequest req) {
		if (Objects.isNull(body.resourceId) || Objects.isNull(body.userIds)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		try {
			String nowUserId = (String) req.getSession().getAttribute("userId");
			Resource resource = resourceMapper.findById(body.resourceId);
			if (Objects.isNull(resource)) {
				r.setCode(EResponseCode.NOT_FOUND.value());
				r.setMessage("资源未找到！！");
				return r;
			}
			if (!resource.getOwner().equalsIgnoreCase(nowUserId)) {
				r.setCode(EResponseCode.NOT_ALLOWED.value());
				r.setMessage("非资源拥有者，无权限进行此操作！！");
				return r;
			} else {
				resourceMapper.selfResource(body.resourceId);
				resourceMapper.deleteUserResourceByResouceId(body.resourceId);
				int x = 0;
				Set<String> set = new HashSet<String>();
				for (String userId : body.userIds) {
					set.add(userId);
				}
				set.add(resource.getOwner());
				set.add(resource.getCreateUser());
				for (String userId : set) {
					x += resourceMapper.insertUserResource(userId, body.resourceId);
				}
				int t = x;
				r.setContent(new Object() {
					public int record = t;
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
			r.setCode(30);
			r.setMessage(e.getMessage());
		}
		return r;
	}
}

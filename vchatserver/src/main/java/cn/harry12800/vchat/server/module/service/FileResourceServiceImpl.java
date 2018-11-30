package cn.harry12800.vchat.server.module.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.harry12800.common.core.session.SessionManager;
import cn.harry12800.common.module.ModuleId;
import cn.harry12800.common.module.ResourceShareCmd;
import cn.harry12800.common.module.chat.dto.ResourceDto;
import cn.harry12800.common.module.chat.dto.SourceShareRequest;
import cn.harry12800.common.module.user.dto.PullResouceResponse;
import cn.harry12800.vchat.server.module.entity.FileResource;
import cn.harry12800.vchat.server.module.mapper.FileResourceMapper;

/**
 * 文件接口
 * 
 * @author harry12800
 * 
 */
@Component
public class FileResourceServiceImpl implements FileResourceService {
	@Autowired
	FileResourceMapper dao;

	@Override
	public void upLoadResouce(SourceShareRequest request) {
		FileResource resource = new FileResource();
		resource.setData(request.getData());
		resource.setPath(request.getPath());
		resource.setProviderId(request.getProviderId());
		resource.setRecipientId(request.getRecipientId());
		resource.setResourceName(request.getResourceName());
		resource.setResourceType(request.getResourceType());
		resource.setGrantTime(new Date());
		dao.createResourceShare(resource);
		ResourceDto e  = new ResourceDto();
		e.setId(resource.getId());
		e.setGrantTime(resource.getGrantTime().getTime());
		e.setResourceName(resource.getResourceName());
		e.setResourceType(resource.getResourceType());
		SessionManager.sendMessage(request.getRecipientId(), ModuleId.RESOURCE, ResourceShareCmd.pushResource, e);
		SessionManager.sendMessage(request.getProviderId(), ModuleId.RESOURCE, ResourceShareCmd.pushResource, e);
	}

	@Override
	public PullResouceResponse pullAllResouces(long userid) {
		System.err.println(userid);
		List<FileResource> resources = dao.getReadResource(userid);
		if(resources!=null)
		System.err.println("资源条数："+resources.size());
		else
			System.err.println("资源条数：0");
		PullResouceResponse resp = new PullResouceResponse();
		for (FileResource a : resources) {
			ResourceDto e = new ResourceDto();
			e.setId(a.getId());
			e.setGrantTime(a.getGrantTime().getTime());
			e.setResourceName(a.getResourceName());
			e.setResourceType(a.getResourceType());
			System.err.println(e);
			resp.getResources().add(e);
		}
		return resp;
	}

	@Override
	public ResourceDto downLoadResource(long resourceId) {
		ResourceDto dto  = new ResourceDto();
		System.out.println("资源id："+resourceId);
		FileResource res = dao.getResourceShareById(resourceId);
		dto.setId(res.getId());
		dto.setProviderId(res.getProviderId());
		dto.setRecipientId(res.getRecipientId());
		dto.setResourceName(res.getResourceName());
		dto.setResourceType(res.getResourceType());
		dto.setData(res.getData());
		return dto;
	}
}

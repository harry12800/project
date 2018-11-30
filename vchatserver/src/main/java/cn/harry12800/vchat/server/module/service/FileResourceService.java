package cn.harry12800.vchat.server.module.service;

import cn.harry12800.common.module.chat.dto.ResourceDto;
import cn.harry12800.common.module.chat.dto.SourceShareRequest;
import cn.harry12800.common.module.user.dto.PullResouceResponse;

/**
 * 
 * @author harry12800
 *
 */
public interface FileResourceService {

	 /**
	  * 
	  * @param request
	  */
	public void upLoadResouce(SourceShareRequest request);

	public PullResouceResponse pullAllResouces(long userid);

	public ResourceDto downLoadResource(long resourceId);

}

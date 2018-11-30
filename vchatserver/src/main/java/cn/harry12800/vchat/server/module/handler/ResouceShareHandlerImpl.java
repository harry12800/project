package cn.harry12800.vchat.server.module.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.harry12800.common.core.exception.ErrorCodeException;
import cn.harry12800.common.core.model.Result;
import cn.harry12800.common.core.model.ResultCode;
import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.module.chat.dto.ResourceDto;
import cn.harry12800.common.module.chat.dto.SourceShareRequest;
import cn.harry12800.common.module.user.dto.DownLoadResourceRequest;
import cn.harry12800.common.module.user.dto.PullResouceResponse;
import cn.harry12800.common.module.user.dto.PullResourceRequest;
import cn.harry12800.vchat.server.module.service.FileResourceService;

@Component
public class ResouceShareHandlerImpl implements ResouceShareHandler {
	@Autowired
	private FileResourceService resourceShareService;

	@Override
	public Result<?> uploadSource(Session session, byte[] data) {
		SourceShareRequest request = new SourceShareRequest();
		request.readFromBytes(data);
		resourceShareService.upLoadResouce( request);
		return Result.SUCCESS();
	}

	@Override
	public Result<PullResouceResponse> pullResouce(Session session, byte[] data) {
		PullResouceResponse result = null;
		try {
			//反序列化
			PullResourceRequest  request = new PullResourceRequest();
			request.readFromBytes(data);

			System.err.println("id:"+request.getUserid());
			//执行业务
			result = resourceShareService.pullAllResouces(request.getUserid());
		} catch (ErrorCodeException e) {
			return Result.ERROR(e.getErrorCode());
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(ResultCode.UNKOWN_EXCEPTION);
		}
		return Result.SUCCESS(result);
	}

	@Override
	public Result<ResourceDto> downloadSource(Session session, byte[] data) {
		DownLoadResourceRequest req = new DownLoadResourceRequest();
		req.readFromBytes(data);
		ResourceDto dto = resourceShareService.downLoadResource(req.getResourceId());
		return Result.SUCCESS(dto);
	}

}

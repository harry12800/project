package cn.harry12800.vchat.server.module.handler;

import cn.harry12800.common.core.annotion.SocketCommand;
import cn.harry12800.common.core.annotion.SocketModule;
import cn.harry12800.common.core.model.Result;
import cn.harry12800.common.core.session.Session;
import cn.harry12800.common.module.ModuleId;
import cn.harry12800.common.module.ResourceShareCmd;
import cn.harry12800.common.module.user.dto.LoginRequest;
import cn.harry12800.common.module.user.dto.PullResouceResponse;

@SocketModule(module = ModuleId.RESOURCE)
public interface ResouceShareHandler {

	@SocketCommand(cmd = ResourceShareCmd.upload_source,desc="上传资源")
	public Result<?> uploadSource(Session session, byte[] data);
	
	
	
	@SocketCommand(cmd = ResourceShareCmd.pullResouces,desc="下载资源")
	public Result<?> downloadSource(Session session, byte[] data);
	/**
	 * @param channel
	 * @param data {@link LoginRequest}
	 * @return
	 */
	@SocketCommand(cmd = ResourceShareCmd.pullAllResouces,desc="拉取资源")
	public Result<PullResouceResponse> pullResouce(Session session, byte[] data);

}

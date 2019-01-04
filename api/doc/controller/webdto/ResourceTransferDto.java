/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.api.doc.controller.webdto;

import java.util.Date;

import cn.harry12800.db.entity.ResourceTransfer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资源转让记录表Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
@ApiModel
public class ResourceTransferDto {

	@ApiModelProperty("主键")
	public Long id;
	@ApiModelProperty("资源id")
	public Long resourceId;
	@ApiModelProperty("从哪个用户到哪个用户")
	public String fromUser = "";
	@ApiModelProperty("从哪个用户到哪个用户")
	public String toUser = "";
	//	@ApiModelProperty("消息产生时间")
	//	public Date createTime;
	@ApiModelProperty("备注")
	public String remark = "";

	public ResourceTransfer toResourceTransferEntity() {
		ResourceTransfer resourceTransfer = new ResourceTransfer();
		resourceTransfer.setId(id);
		resourceTransfer.setResourceId(resourceId);
		resourceTransfer.setFromUser(fromUser);
		resourceTransfer.setToUser(toUser);
		resourceTransfer.setCreateTime(new Date());
		resourceTransfer.setRemark(remark);
		return resourceTransfer;
	}
}

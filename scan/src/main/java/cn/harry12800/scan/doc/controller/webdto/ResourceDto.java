/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.scan.doc.controller.webdto;

import java.util.Date;

import cn.harry12800.db.entity.Resource;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资源：自动生成文档，markdown文档，上传的文件Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
@ApiModel
public class ResourceDto {

	@ApiModelProperty("主键")
	public Long id;
	@ApiModelProperty("所属目录")
	public Long directoryId;
	@ApiModelProperty("所属项目")
	public Long appId;
	@ApiModelProperty("名称")
	public String name = "";
	//	@ApiModelProperty("创建时间")
	//	public Date createTime;
	//	@ApiModelProperty("更新时间")
	//	public Date updateTime;
	//	@ApiModelProperty("创建用户")
	//	public String createUser="";
	@ApiModelProperty("更新用户")
	public String updateUser = "";
	@ApiModelProperty("1= markdown ,2=other")
	public int resourceType = 1;
	@ApiModelProperty("")
	public String remark = "";
	@ApiModelProperty("是否资源公开(所有人都可以查阅）")
	public Integer readable = 0;

	public Resource toResourceEntity() {
		Resource resource = new Resource();
		resource.setId(id);
		resource.setDirectoryId(directoryId);
		resource.setAppId(appId);
		resource.setName(name);
		resource.setCreateTime(new Date());
		resource.setUpdateTime(new Date());
		//		resource.setCreateUser(createUser);
		resource.setUpdateUser(updateUser);
		resource.setResourceType(resourceType);
		resource.setRemark(remark);
		resource.setReadable(readable);
		return resource;
	}
}

/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.scan.doc.controller.webdto;

import java.util.Date;

import cn.harry12800.db.entity.Application;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 开发组开发的应用Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
@ApiModel
public class ApplicationDto {

	@ApiModelProperty("主键")
	public Long id;
	@ApiModelProperty("App名称")
	public String appName = "";
	@ApiModelProperty("项目Swagger的Json获取路径")
	public String docUrl = "";
	@ApiModelProperty("首页地址")
	public String homeUrl = "";
	@ApiModelProperty("logo图片路径")
	public String logoUrl = "";
	@ApiModelProperty("App类型")
	public Integer appType = 0;
	@ApiModelProperty("App状态")
	public Integer appState = 0;
	//	@ApiModelProperty("创建时间")
	//	public Date createTime;
	//	@ApiModelProperty("修改时间")
	//	public Date updateTime;
	@ApiModelProperty("创建用户")
	public String createUser = "";
	@ApiModelProperty("修改用户")
	public String updateUser = "";
	@ApiModelProperty("主机地址")
	public String host = "";
	@ApiModelProperty("基础路径")
	public String basePath = "";
	@ApiModelProperty("项目的端口号")
	public Integer port;
	@ApiModelProperty("备注")
	public String remark = "";

	public Application toApplicationEntity() {
		Application application = new Application();
		application.setId(id);
		application.setAppName(appName);
		application.setDocUrl(docUrl);
		application.setHomeUrl(homeUrl);
		application.setLogoUrl(logoUrl);
		application.setAppType(appType);
		application.setAppState(appState);
		application.setCreateTime(new Date());
		application.setUpdateTime(new Date());
		application.setCreateUser(createUser);
		application.setUpdateUser(updateUser);
		application.setHost(host);
		application.setBasePath(basePath);
		application.setPort(port);
		application.setRemark(remark);
		return application;
	}
}

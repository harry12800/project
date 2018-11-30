/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.scan.doc.controller.webdto;

import cn.harry12800.db.entity.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目的接口ApiEntity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
@ApiModel
public class ApiDto {

	@ApiModelProperty("主键")
	public Long id;
	@ApiModelProperty("所属项目的主键")
	public Long appId;
	@ApiModelProperty("代表是哪份自动生成的markdown文件主键")
	public Long autoApiMarkdownId;
	@ApiModelProperty("接口路径(/v1/hello）")
	public String path = "";
	@ApiModelProperty("是哪个类型。 1 代表是单一接口路径。2代表是模块接口路径")
	public Integer type = 1;
	@ApiModelProperty("项目host,（和base_path用于能表示一个完整路径）")
	public String host;
	@ApiModelProperty("项目的基本路径")
	public String basePath;

	public Api toApiEntity() {
		Api api = new Api();
		api.setId(id);
		api.setAppId(appId);
		api.setAutoApiMarkdownId(autoApiMarkdownId);
		api.setPath(path);
		api.setType(type);
		api.setHost(host);
		api.setBasePath(basePath);
		return api;
	}
}

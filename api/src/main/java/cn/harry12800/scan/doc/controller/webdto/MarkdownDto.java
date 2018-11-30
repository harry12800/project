/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.scan.doc.controller.webdto;

import cn.harry12800.db.entity.Markdown;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 手写的markdownEntity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
@ApiModel
public class MarkdownDto {

	@ApiModelProperty("主键")
	public Long id;
	@ApiModelProperty("名称")
	public String name = "";
	@ApiModelProperty("内容")
	public String content = "";
	//	@ApiModelProperty("创建时间")
	//	public Date createTime;
	//	@ApiModelProperty("更新时间")
	//	public Date updateTime;
	//	@ApiModelProperty("创建用户")
	//	public String createUser="";
	//	@ApiModelProperty("更新用户")
	//	public String updateUser="";
	//	@ApiModelProperty("拥有者")
	//	public String owner="";

	public Markdown toMarkdownEntity() {
		Markdown markdown = new Markdown();
		markdown.setId(id);
		markdown.setName(name);
		markdown.setContent(content);
		//		markdown.setCreateTime(new Date());
		//		markdown.setUpdateTime(new Date());
		//		markdown.setCreateUser(createUser);
		//		markdown.setUpdateUser(updateUser);
		//		markdown.setOwner(owner);
		return markdown;
	}
}

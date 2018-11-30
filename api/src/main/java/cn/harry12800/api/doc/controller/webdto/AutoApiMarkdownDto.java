/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.api.doc.controller.webdto;

import cn.harry12800.db.entity.AutoApiMarkdown;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 自动拼接接口文档Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
@ApiModel
public class AutoApiMarkdownDto {

	@ApiModelProperty("主键")
	public Long id;
	@ApiModelProperty("名字")
	public String name = "";
	@ApiModelProperty("markdown文档的前缀。用于自己来写markdown文档的头部分")
	public String prefixContent;
	@ApiModelProperty("markdown文档的后缀。用于自己来写markdown文档的尾部分")
	public String suffixContent;
	//	@ApiModelProperty("创建时间")
	//	public Date createTime;
	//	@ApiModelProperty("更新时间")
	//	public Date updateTime;
	//	@ApiModelProperty("创建用户")
	//	public String createUser = "";
	//	@ApiModelProperty("更新用户")
	//	public String updateUser = "";
	//	@ApiModelProperty("最终拥有者")
	//	public String owner = "";

	public AutoApiMarkdown toAutoApiMarkdownEntity() {
		AutoApiMarkdown autoApiMarkdown = new AutoApiMarkdown();
		autoApiMarkdown.setId(id);
		autoApiMarkdown.setName(name);
		autoApiMarkdown.setPrefixContent(prefixContent);
		autoApiMarkdown.setSuffixContent(suffixContent);
		//		autoApiMarkdown.setCreateTime(new Date());
		//		autoApiMarkdown.setUpdateTime(new Date());
		//		autoApiMarkdown.setCreateUser(createUser);
		//		autoApiMarkdown.setUpdateUser(updateUser);
		//		autoApiMarkdown.setOwner(owner);
		return autoApiMarkdown;
	}
}

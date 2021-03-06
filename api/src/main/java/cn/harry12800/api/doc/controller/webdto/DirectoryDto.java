/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.api.doc.controller.webdto;

import java.util.Date;

import cn.harry12800.db.entity.Directory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 项目的层次目录Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
@ApiModel
public class DirectoryDto {

	@ApiModelProperty("主键")
	public Long id;
	@ApiModelProperty("父节点,默认0没有父节点")
	public Long parentId = 0L;
	@ApiModelProperty("所属项目")
	public Long appId;
	@ApiModelProperty("目录名称")
	public String name;
	//	@ApiModelProperty("创建时间")
	//	public Date createTime;
	//	@ApiModelProperty("更新时间")
	//	public Date updateTime;
	//	@ApiModelProperty("创建用户")
	//	public String createUser="";
	//	@ApiModelProperty("更新用户")
	//	public String updateUser="";
	@ApiModelProperty("备注")
	public String remark;

	public Directory toDirectoryEntity() {
		Directory directory = new Directory();
		directory.setId(id);
		directory.setParentId(parentId);
		directory.setAppId(appId);
		directory.setName(name);
		directory.setCreateTime(new Date());
		directory.setUpdateTime(new Date());
		//		directory.setCreateUser(createUser);
		//		directory.setUpdateUser(updateUser);
		directory.setRemark(remark);
		return directory;
	}
}

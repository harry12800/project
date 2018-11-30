/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * 手写的markdownEntity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "markdown")
public class Markdown { // extends DataEntity<Markdown> {
	//	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	//	@DbField(value="主键",type=1,sort=1, title ="主键", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "id")
	private Long id;
	/**
	 * 名称
	 */
	//	@DbField(value="名称",type=1,sort=1, title ="名称", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "name")
	private String name;
	/**
	 * 内容
	 */
	//	@DbField(value="内容",type=1,sort=1, title ="内容", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "content")
	private String content;

	//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql = "CREATE TABLE markdown(" +
			"	id		INT  COMMENT '主键'," +
			"	name		VARCHAR(255)  COMMENT '名称'," +
			"	content		longtext  COMMENT '内容'," +
			"	create_time		timestamp  COMMENT '创建时间'," +
			"	update_time		timestamp  COMMENT '更新时间'," +
			"	create_user		VARCHAR(64)  COMMENT '创建用户'," +
			"	update_user		VARCHAR(64)  COMMENT '更新用户'," +
			"	owner		VARCHAR(64)  COMMENT '拥有者'" +
			")COMMENT='手写的markdown';";
	//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql = "CREATE TABLE markdown(" +
			"	id		NUMBER ," +
			"	name		VARCHAR2(255) ," +
			"	content		VARCHAR ," +
			"	create_time		TIMESTAMP ," +
			"	update_time		TIMESTAMP ," +
			"	create_user		VARCHAR2(64) ," +
			"	update_user		VARCHAR2(64) ," +
			"	owner		VARCHAR2(64) " +
			");";

	/**
	 *获取主键
	 */
	public Long getId() {
		return id;
	}

	/**
	 *获取名称
	 */
	public String getName() {
		return name;
	}

	/**
	 *获取内容
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设值主键
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 设值名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设值内容
	 */
	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Markdown [id=" + id + ", name=" + name + ", content=" + content + "]";
	}

}

/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.db.entity;

//import cn.harry12800.tools.DbField;
//import cn.harry12800.tools.DbInitSentence;
//import cn.harry12800.tools.DbInitType;
//import cn.harry12800.tools.DbTable;
/**
 * 自动拼接接口文档Entity
 * @author 周国柱
 * @version 1.0
 * <dt>jdbc:mysql://10.3.9.142:3306/fingerchat_dev_docs?useSSL=false&allowMultiQueries=true
 * <dt>root
 * <dt>Lenovo,,123
 * <dt>代码自动生成!数据库的资源文件.
 */
//@DbTable(tableName = "auto_api_markdown")
public class AutoApiMarkdown { // extends DataEntity<AutoApiMarkdown> {
	//	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	//	@DbField(value="主键",type=1,sort=1, title ="主键", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "id")
	private Long id;
	/**
	 * 名字
	 */
	//	@DbField(value="名字",type=1,sort=1, title ="名字", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "name")
	private String name;
	/**
	 * markdown文档的前缀。用于自己来写markdown文档的头部分
	 */
	//	@DbField(value="markdown文档的前缀。用于自己来写markdown文档的头部分",type=1,sort=1, title ="markdown文档的前缀。用于自己来写markdown文档的头部分", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "prefix_content")
	private String prefixContent;
	/**
	 * markdown文档的后缀。用于自己来写markdown文档的尾部分
	 */
	//	@DbField(value="markdown文档的后缀。用于自己来写markdown文档的尾部分",type=1,sort=1, title ="markdown文档的后缀。用于自己来写markdown文档的尾部分", exp=true,  canAdd = true, canEdit = false, canSearch = false, dbFieldName = "suffix_content")
	private String suffixContent;

	//	@DbInitSentence(type = DbInitType.Create)
	public static String initSql = "CREATE TABLE auto_api_markdown(" +
			"	id		INT  COMMENT '主键'," +
			"	name		VARCHAR(255)  COMMENT '名字'," +
			"	prefix_content		longtext  COMMENT 'markdown文档的前缀。用于自己来写markdown文档的头部分'," +
			"	suffix_content		longtext  COMMENT 'markdown文档的后缀。用于自己来写markdown文档的尾部分'," +
			"	create_time		timestamp  COMMENT '创建时间'," +
			"	update_time		timestamp  COMMENT '更新时间'," +
			"	create_user		VARCHAR(64)  COMMENT '创建用户'," +
			"	update_user		VARCHAR(64)  COMMENT '更新用户'" +
			")COMMENT='自动拼接接口文档';";
	//	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql = "CREATE TABLE auto_api_markdown(" +
			"	id		NUMBER ," +
			"	name		VARCHAR2(255) ," +
			"	prefix_content		VARCHAR ," +
			"	suffix_content		VARCHAR ," +
			"	create_time		TIMESTAMP ," +
			"	update_time		TIMESTAMP ," +
			"	create_user		VARCHAR2(64) ," +
			"	update_user		VARCHAR2(64) " +
			");";

	/**
	 *获取主键
	 */
	public Long getId() {
		return id;
	}

	/**
	 *获取名字
	 */
	public String getName() {
		return name;
	}

	/**
	 *获取markdown文档的前缀。用于自己来写markdown文档的头部分
	 */
	public String getPrefixContent() {
		return prefixContent;
	}

	/**
	 *获取markdown文档的后缀。用于自己来写markdown文档的尾部分
	 */
	public String getSuffixContent() {
		return suffixContent;
	}

	/**
	 * 设值主键
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 设值名字
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设值markdown文档的前缀。用于自己来写markdown文档的头部分
	 */
	public void setPrefixContent(String prefixContent) {
		this.prefixContent = prefixContent;
	}

	/**
	 * 设值markdown文档的后缀。用于自己来写markdown文档的尾部分
	 */
	public void setSuffixContent(String suffixContent) {
		this.suffixContent = suffixContent;
	}
}

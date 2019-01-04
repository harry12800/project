/**
 * Copyright &copy; 2015-2020 <a href=" ">harry12800</a> All rights reserved.
 */
package cn.harry12800.vchat.entity;

import cn.harry12800.tools.DbField;
import cn.harry12800.tools.DbInitSentence;
import cn.harry12800.tools.DbInitType;
import cn.harry12800.tools.DbTable;

/**
 * Entity
 * 
 * @author 周国柱
 * @version 1.0
 *          <dt>jdbc:mysql://119.23.9.164:3306/scan
 *          <dt>root
 *          <dt>zhouguozhu
 *          <dt>代码自动生成!数据库的资源文件.
 */
@DbTable(tableName = "diary")
public class Diary { // extends DataEntity<Diary> {
	/**
	 * 
	 */
	@DbField(value = "null", type = 1, sort = 1, title = "null", exp = true, canAdd = true, canEdit = false, canSearch = false, dbFieldName = "id")
	private String id;
	/**
	 * 
	 */
	@DbField(value = "null", type = 1, sort = 1, title = "null", exp = true, canAdd = true, canEdit = false, canSearch = false, dbFieldName = "catalog_id")
	private String catalogId;
	/**
	 * 
	 */
	@DbField(value = "null", type = 1, sort = 1, title = "null", exp = true, canAdd = true, canEdit = false, canSearch = false, dbFieldName = "title")
	private String title;
	/**
	 * 
	 */
	@DbField(value = "null", type = 1, sort = 1, title = "null", exp = true, canAdd = true, canEdit = false, canSearch = false, dbFieldName = "content")
	private String content;
	/**
	 * 1表示 密文
	 */
	@DbField(value = "null", type = 1, sort = 1, title = "null", exp = true, canAdd = true, canEdit = false, canSearch = false, dbFieldName = "cipher")
	private Integer cipher = 1;
	/**
	 * 1表示 密文
	 */
	@DbField(value = "null", type = 1, sort = 1, title = "null", exp = true, canAdd = true, canEdit = false, canSearch = false, dbFieldName = "hint")
	private Integer hint = 1;

	/**
	 * 
	 */
	@DbField(value = "null", type = 1, sort = 1, title = "null", exp = true, canAdd = true, canEdit = false, canSearch = false, dbFieldName = "html")
	private String html;
	/**
	 * 
	 */
	@DbField(value = "null", type = 1, sort = 1, title = "null", exp = true, canAdd = true, canEdit = false, canSearch = false, dbFieldName = "create_time")
	private java.util.Date createTime;
	/**
	 * 
	 */
	@DbField(value = "null", type = 1, sort = 1, title = "null", exp = true, canAdd = true, canEdit = false, canSearch = false, dbFieldName = "update_time")
	private java.util.Date updateTime;
	/**
	 * 
	 */
	@DbField(value = "null", type = 1, sort = 1, title = "null", exp = true, canAdd = true, canEdit = false, canSearch = false, dbFieldName = "sort")
	private Integer sort;
	@DbInitSentence(type = DbInitType.Create)
	public static String initSql = "CREATE TABLE diary(" + "	id		VARCHAR(255) ,"
			+ "	parent_id		VARCHAR(255) ," + "	title		VARCHAR(255) ," + "	content		VARCHAR(65535) ,"
			+ "	create_time		timestamp ," + "	update_time		timestamp ," + "	is_folder		INT " + ");";
	@DbInitSentence(type = DbInitType.Create)
	public static String initOracleSql = "CREATE TABLE diary(" + "	id		VARCHAR2(255) ,"
			+ "	parent_id		VARCHAR2(255) ," + "	title		VARCHAR2(255) ," + "	content		VARCHAR ,"
			+ "	create_time		TIMESTAMP ," + "	update_time		TIMESTAMP ," + "	is_folder		NUMBER " + ");";

	/**
	 * 获取
	 */
	public String getId() {
		return id;
	}

	/**
	 * 获取
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 获取
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 获取
	 */
	public java.util.Date getCreateTime() {
		return createTime;
	}

	/**
	 * 获取
	 */
	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 设值
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 设值
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	/**
	 * 设值
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 设值
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 设值
	 */
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getCipher() {
		return cipher;
	}

	public void setCipher(Integer cipher) {
		this.cipher = cipher;
	}

	public Integer getHint() {
		return hint;
	}

	public void setHint(Integer hint) {
		this.hint = hint;
	}

	@Override
	public String toString() {
		return "Diary [id=" + id + ", catalogId=" + catalogId + ", title=" + title + ", content=" + content
				+ ", cipher=" + cipher + ", hint=" + hint + ", html=" + html + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", sort=" + sort + "]";
	}

}

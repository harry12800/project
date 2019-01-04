package cn.harry12800.vchat.model.file;

import java.util.List;

import cn.harry12800.tools.Lists;

public class FileJsonConfig {

	private List<OpenFileType> list = Lists.newArrayList();

	/**
	 * 获取list
	 * 
	 * @return the list
	 */
	public List<OpenFileType> getList() {
		return list;
	}

	/**
	 * 设置list
	 * 
	 * @param list
	 *            the list to set
	 */
	public void setList(List<OpenFileType> list) {
		this.list = list;
	}

}

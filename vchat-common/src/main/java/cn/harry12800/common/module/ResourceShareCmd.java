package cn.harry12800.common.module;

/**
 * ziyuan模块
 * @author harry12800
 *
 */
public interface ResourceShareCmd {

	short upload_source = 1;
	short download_source = 2;
	short pullAllResouces = 3;
	short pullResouces = 4;
	short pushResource = 5;

}

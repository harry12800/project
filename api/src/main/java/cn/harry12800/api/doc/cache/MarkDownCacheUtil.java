package cn.harry12800.api.doc.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import cn.harry12800.api.doc.swagger2markdown.ApiBean;
import cn.harry12800.api.doc.swagger2markdown.Module;
import cn.harry12800.api.doc.swagger2markdown.ProjectApiFactory;
import cn.harry12800.db.entity.Application;

public class MarkDownCacheUtil {
	private static final Logger LOG = LoggerFactory.getLogger(MarkDownCacheUtil.class);

	static Map<Application, ProjectApiFactory> app2ProjectApi = Maps.newConcurrentMap();
	static Map<Long, Application> appid2App = Maps.newConcurrentMap();

	/**
	 * 更新整个项目缓存
	 * @param application
	 * @param client
	 */
	public static void cacheProjectApi(Application application, ProjectApiFactory client) {
		app2ProjectApi.put(application, client);
		appid2App.put(application.getId(), application);
	}

	/**
	 * 根据项目应用的ID和类型（模块类型，单一接口类型） api路径获取markdown内容
	 * @param appId
	 * @param type  1,2 1代表单一的，2是模块的
	 * @param apiPath
	 * @return
	 */
	public static String getMarkdownByApiPath(Long appId, int type, String apiPath) {

		if (type == 1) {
			ProjectApiFactory projectApiFactory = app2ProjectApi.get(appid2App.get(appId));
			return projectApiFactory.apiPath2Markdown.get(apiPath);
		} else if (type == 2) {
			ProjectApiFactory projectApiFactory = app2ProjectApi.get(appid2App.get(appId));
			return projectApiFactory.apiPath2Module.get(apiPath).markdown;
		}
		return "null";
	}

	/**
	 * 根据项目应用ID获取项目的模块信息。
	 * @param appId
	 * @return
	 */
	public static Collection<Module> getModuleByAppId(Long appId) {
		ProjectApiFactory projectApiFactory = app2ProjectApi.get(appid2App.get(appId));
		return projectApiFactory.getModules();
	}

	/**
	 * 根据项目应用ID 和模块id List<ApiBean>
	 * @param appId
	 * @param mId
	 * @return
	 */
	public static List<ApiBean> getApiListByAppIdAndMid(Long appId, String mId) {
		ProjectApiFactory projectApiFactory = app2ProjectApi.get(appid2App.get(appId));
		return projectApiFactory.moduleId2ApiList.get(mId);
	}

	/**
	 * 根据项目应用ID 和apiPath 获取 Module。
	 * @param appId
	 * @param apiPath
	 * @return
	 */
	public static Module getModuleByAppIdAndApiPath(Long appId, String apiPath) {
		ProjectApiFactory projectApiFactory = app2ProjectApi.get(appid2App.get(appId));
		return projectApiFactory.apiPath2Module.get(apiPath);
	}

	/**
	 * 获取所有可以配置Api接口文档的项目
	 * @return
	 */
	public static Collection<Application> getAllApplication() {
		return appid2App.values();
	}

	public static void addApp(Application entity) {
		ProjectApiFactory client = new ProjectApiFactory();
		try {
			client.setApp(entity).go();
			MarkDownCacheUtil.cacheProjectApi(entity, client);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
	}
}

package cn.harry12800.scan.doc.swagger2markdown;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import com.google.gson.internal.LinkedTreeMap;

import cn.harry12800.db.entity.Application;
import cn.harry12800.scan.doc.util.HttpUtil;
import cn.harry12800.scan.doc.util.JsonUtil;

public class ProjectApiFactory {
	static int times = 0;
	public String host;
	public String basePath;

	/**
	 * 获取所有模块分类。 模块名，与模块描述的映射关系
	 */
	public Map<String, String> tags = new HashMap<>();
	/**
	 * api路径与 具体那一个Api的映射关系。
	 */
	public Map<String, ApiBean> apis = new HashMap<>();
	/**
	 * 模块名与模块下的Api集合映射关系
	 */
	public Map<String, List<ApiBean>> tagApis = new HashMap<>();
	/**
	 * swagger中对象引用的详情
	 */
	public Map<String, RefObject> refObjects = new HashMap<>();
	/**
	 * 路径与模块的映射关系
	 */
	public Map<String, Module> apiPath2Module = new HashMap<>();

	/**
	 * 模块id与ApiBeanList
	 */
	public Map<String, List<ApiBean>> moduleId2ApiList = new HashMap<>();
	/**
	 * 模块ID与模块的映射关系
	 */
	public Map<String, Module> moduleMaps = new HashMap<>();
	/**
	 * 路径与markdown的映射关系。
	 */
	public Map<String, String> apiPath2Markdown = new HashMap<>();
	public Application app;
	public String md;

	public Collection<Module> getModules() {
		return moduleMaps.values();
	}

	/**
	 * 分析所有的API 路径， API 路径下会包括 请求信息。返回信息。
	 * 
	 * @param paths
	 */
	public ProjectApiFactory analysisPaths(LinkedTreeMap<?, ?> paths) {
		if (Objects.isNull(paths))
			return this;
		Set<?> keySet = paths.keySet();
		Object[] array = keySet.toArray();
		for (Object key : array) {
			ApiBean bean = new ApiBean();
			apis.put(key.toString(), bean);
			bean.path = key.toString();
			LinkedTreeMap<?, ?> value = (LinkedTreeMap<?, ?>) paths.get(key);
			analysisApiMethod(value, bean);
		}
		return this;
	}

	/**
	 * 一个路径可以有多个方法。get,post方法等。
	 * 
	 * @param bean
	 * @param object2
	 */
	ProjectApiFactory analysisApiMethod(LinkedTreeMap<?, ?> api, ApiBean bean) {
		if (Objects.isNull(api))
			return this;
		Set<?> keySet = api.keySet();
		Object[] array = keySet.toArray();
		boolean flag = false;
		for (Object key : array) {
			bean.methods = bean.methods + key.toString().toUpperCase() + "  ";
			if (flag)
				continue;
			LinkedTreeMap<?, ?> value = (LinkedTreeMap<?, ?>) api.get(key);
			analysisApi(value, bean);
			flag = true;
		}
		return this;
	}

	ProjectApiFactory analysisApi(LinkedTreeMap<?, ?> apiParam, ApiBean bean) {
		if (Objects.isNull(apiParam))
			return this;
		Set<?> keySet = apiParam.keySet();
		Object[] array = keySet.toArray();
		for (Object key : array) {
			switch (key.toString()) {
			case "tags":
				analysisOneApiTags((ArrayList<?>) apiParam.get(key), bean);
				break;
			case "summary":
				analysisOneApiSummary((String) apiParam.get(key), bean);
				break;
			case "operationId":
				analysisOneApiOperationId((String) apiParam.get(key), bean);
				break;
			case "consumes":
				analysisOneApiConsumes((ArrayList<?>) apiParam.get(key), bean);
				break;
			case "parameters":
				analysisOneApiParameters((ArrayList<?>) apiParam.get(key), bean);
				break;
			case "produces":
				analysisOneApiProduces((ArrayList<?>) apiParam.get(key), bean);
				break;
			case "responses":
				analysisOneApiResponses((LinkedTreeMap<?, ?>) apiParam.get(key), bean);
				break;
			}
		}
		return this;
	}

	private void analysisOneApiParameters(ArrayList<?> arrayList, ApiBean bean) {
		if (Objects.isNull(arrayList))
			return;
		for (Object object : arrayList) {
			Param p = new Param();
			LinkedTreeMap<?, ?> parameters = (LinkedTreeMap<?, ?>) object;
			p.name = (String) parameters.get("name");
			p.description = (String) parameters.get("description");
			p.in = (String) parameters.get("in");
			p.required = (Boolean) parameters.get("required");
			p.format = (String) parameters.get("format");
			Object schema = parameters.get("schema");
			if (schema != null) {
				String type = (String) ((LinkedTreeMap<?, ?>) schema).get("type");
				if (type == null)
					p.ref = ((LinkedTreeMap<?, ?>) schema).get("$ref").toString();
				else if ("array".equals(type)) {
					p.refType = type;
					p.isRef = true;
					LinkedTreeMap<?, ?> items = (LinkedTreeMap<?, ?>) ((LinkedTreeMap<?, ?>) schema).get("items");
					if (items != null) {
						if (items.get("$ref") != null)
							p.ref = items.get("$ref").toString();
						if (items.get("type") != null)
							p.ref = items.get("type").toString();
					}
				}
			}
			p.type = (String) parameters.get("type");
			bean.params.add(p);
		}
	}

	// class com.google.gson.internal.LinkedTreeMap
	// {200={description=OK, schema={$ref=#/definitions/MyResponse}},
	// 401={description=Unauthorized}, 403={description=Forbidden},
	// 404={description=Not Found}}
	private void analysisOneApiResponses(LinkedTreeMap<?, ?> responses, ApiBean bean) {
		// TODO Auto-generated method stub

	}

	// class java.util.ArrayList
	// [application/json, */*]
	private void analysisOneApiProduces(ArrayList<?> produces, ApiBean bean) {
		bean.produces = produces.toString();
	}

	// class java.util.ArrayList
	// [application/json]
	private void analysisOneApiConsumes(ArrayList<?> consumes, ApiBean bean) {
		bean.consumes = consumes.toString();
	}

	// class java.lang.String
	// versionUsingGET
	private void analysisOneApiOperationId(String operationId, ApiBean bean) {
		bean.operationId = operationId;
	}

	// class java.lang.String
	// 服务存活检测
	private void analysisOneApiSummary(String summary, ApiBean bean) {
		bean.summary = summary;
	}

	// tags
	// class java.util.ArrayList
	// [服务存活检测接口 /HelloController]
	private void analysisOneApiTags(ArrayList<?> tags, ApiBean bean) {
		if (Objects.isNull(tags))
			return;
		for (Object tag : tags) {
			if (this.tagApis.get(tag.toString()) == null) {
				List<ApiBean> list = new ArrayList<>();
				this.tagApis.put(tag.toString(), list);
			}
			List<ApiBean> list = this.tagApis.get(tag.toString());
			list.add(bean);
		}
	}

	/**
	 * 获取所有模块分类。
	 * 
	 * @param lists
	 */
	public ProjectApiFactory analysisAllTags(ArrayList<?> tags) {
		if (Objects.isNull(tags))
			return this;
		for (Object object : tags) {
			LinkedTreeMap<?, ?> obj = (LinkedTreeMap<?, ?>) object;
			this.tags.put(obj.get("name").toString(), obj.get("description").toString());
		}
		return this;
	}

	public ProjectApiFactory setBasePath(String basePath) {
		this.basePath = basePath;
		return this;
	}

	public ProjectApiFactory setHost(String host) {
		this.host = host;
		return this;
	}

	@SuppressWarnings("unchecked")
	public ProjectApiFactory analysisDefinitions(LinkedTreeMap<?, ?> definitions) {
		if (Objects.isNull(definitions))
			return this;

		Set<?> keySet = definitions.keySet();
		for (Object object : keySet) {
			LinkedTreeMap<?, ?> obj = (LinkedTreeMap<?, ?>) definitions.get(object);
			RefObject ref = new RefObject();
			LinkedTreeMap<?, ?> properties = (LinkedTreeMap<?, ?>) obj.get("properties");
			for (Object propertiesName : properties.keySet()) {
				LinkedTreeMap<?, ?> property = (LinkedTreeMap<?, ?>) properties.get(propertiesName);
				Param p = new Param();
				p.name = (String) propertiesName;
				p.type = (String) property.get("type");
				p.format = (String) property.get("format");
				p.description = (String) property.get("description");
				p.ref = (String) property.get("$ref");
				ref.properties.add(p);
			}
			ref.type = (String) obj.get("type");
			ref.required = (ArrayList<String>) obj.get("required");
			refObjects.put("#/definitions/" + object, ref);
		}
		return this;
	}

	int index = 1;

	public String build() {
		StringBuilder sb = new StringBuilder();
		this.host = getHost();
		System.out.println(this.host);
		sb.append("[TOC]\r\n\r\n");
		sb.append(++times + "  times\r\n");
		for (Entry<String, List<ApiBean>> kinds : this.tagApis.entrySet()) {
			String kindName = kinds.getKey();
			List<ApiBean> apis = kinds.getValue();
			Module module = new Module();
			module.name = kindName;
			module.desc = this.tags.get(kindName);
			moduleMaps.put(module.id, module);
			moduleId2ApiList.put(module.id, apis);
			module.markdown = generateMd4Module(kindName, apis, module);
			sb.append(module.markdown);
		}
		return sb.toString();
	}

	private String generateMd4Module(String kindName, List<ApiBean> apis, Module m) {
		StringBuilder sb = new StringBuilder();
		sb.append("####" + kindName + "\r\n");
		if (apis.size() > 0) {
			String path = "(javascript:window.modelCustom&#40;'" + host + "','" + apis.get(0).path + "'&#41;)";
			sb.append("[模块测试]" + path + "\r\n");
		}
		if (this.tags.get(kindName) != null)
			sb.append("######" + this.tags.get(kindName) + "\r\n");

		for (ApiBean apiBean : apis) {
			String md = generateMd4OneApi(apiBean);
			sb.append(md);
			apiPath2Markdown.put(apiBean.path, md);
			apiPath2Module.put(apiBean.path, m);
		}
		return sb.toString();
	}

	private String generateMd4OneApi(ApiBean apiBean) {
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n");
		sb.append("描述：" + apiBean.summary + "\r\n");
		String path = "(javascript:window.apiCustom&#40;'" + host + "','" + apiBean.path + "'&#41;)";
		sb.append("[测试]" + path + "\r\n");
		sb.append("\r\n");
		sb.append("|接口编号|" + index++ + "|\r\n");
		sb.append("|:-----  |:-------|\r\n");
		sb.append("|接口地址|" + apiBean.path + "|\r\n");
		sb.append("|请求格式|" + apiBean.consumes + "|\r\n");
		sb.append("|返回格式|" + apiBean.produces + "|\r\n");
		sb.append("|HTTP请求方式|" + apiBean.methods + "|\r\n");

		sb.append("\r\n");
		if (apiBean.params.size() > 0) {
			sb.append("1、 请求参数\r\n\r\n");
			sb.append("|参数|必填|类型|说明|\r\n");
			sb.append("|:-----  |:-------|:-----|-----|\r\n");
			for (Param p : apiBean.params) {
				if (Objects.isNull(p.type)) {
					//					String ref = p.ref;
					RefObject refObject = refObjects.get(p.ref);
					if (refObject != null) {
						sb.append("|" + p.in + "|" + p.required + "|" + refObject.type + "|" + refObject.description + " |\r\n");
						List<Param> properties = refObject.properties;
						for (Param param : properties) {
							if (Objects.nonNull(param.ref)) {
								RefObject paramRefObject = refObjects.get(param.ref);
								if (Objects.nonNull(paramRefObject)) {
									sb.append("|" + param.name + "|" + p.required + "|" + paramRefObject.type + "|" + "父类：" + p.in + " |\r\n");
									List<Param> paramProperties = paramRefObject.properties;
									for (Param paramSub : paramProperties) {
										sb.append("|" + paramSub.name + "|" + p.required + "|" + paramSub.type + "|" + "父类：" + param.name + " |\r\n");
									}
								}
							}
							if (Objects.nonNull(param.type)) {
								sb.append("|" + param.name + "|" + p.required + "|" + param.type + "|" + "父类：" + p.in + " |\r\n");
							}
						}
					}
				} else {
					sb.append("|" + p.name + "|" + p.required + "|" + p.type + "|" + p.description + " |\r\n");
				}
			}
		} else {
			sb.append("无请求参数\r\n");
		}
		sb.append("\r\n");
		sb.append("----------\r\n");
		return sb.toString();
	}

	private String getHost() {
		System.out.println(this.basePath);
		if (this.basePath != null & this.basePath.length() > 1) {
			this.basePath = this.basePath.substring(1, this.basePath.length());
			String[] split = this.basePath.split("/");
			this.basePath = "/" + split[0];
		}
		String host = this.host + this.basePath + "/" + "api.html";
		if (!host.startsWith("http")) {
			host = "http://" + host;
		}
		return host;
	}

	public ProjectApiFactory setApp(Application application) {
		this.app = application;
		return this;
	}

	public boolean go() {
		String swaggerJsn = null;
		try {
			swaggerJsn = HttpUtil.get(app.getDocUrl());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println(swaggerJsn);
		HashMap<?, ?> json = JsonUtil.string2Json(swaggerJsn, HashMap.class);
		java.util.ArrayList<?> lists = (ArrayList<?>) json.get("tags");
		LinkedTreeMap<?, ?> paths = (LinkedTreeMap<?, ?>) json.get("paths");
		LinkedTreeMap<?, ?> definitions = (LinkedTreeMap<?, ?>) json.get("definitions");
		this.md = analysisAllTags(lists)
				.analysisPaths(paths)
				.analysisDefinitions(definitions)
				.setHost((String) json.get("host"))
				.setBasePath((String) json.get("basePath")).build();
		return true;
	}
}
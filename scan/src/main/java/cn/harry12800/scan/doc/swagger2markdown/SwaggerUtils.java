package cn.harry12800.scan.doc.swagger2markdown;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.internal.LinkedTreeMap;

import cn.harry12800.scan.doc.util.HttpUtil;
import cn.harry12800.scan.doc.util.JsonUtil;

public class SwaggerUtils {

	public static boolean isSwaggerUrl(String url) {
		String swaggerJsn = null;
		try {
			swaggerJsn = HttpUtil.get(url);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		HashMap<?, ?> json = JsonUtil.string2Json(swaggerJsn, HashMap.class);
		java.util.ArrayList<?> lists = (ArrayList<?>) json.get("tags");
		if (lists == null || lists.size() == 0)
			return false;
		LinkedTreeMap<?, ?> paths = (LinkedTreeMap<?, ?>) json.get("paths");
		if (paths == null || paths.size() == 0)
			return false;
		return true;
	}
}

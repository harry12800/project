package cn.harry12800.vchat.app.config;

import cn.harry12800.j2se.utils.Config;
import cn.harry12800.tools.StringUtils;

public class Contants {
	public final static String userDiaryCatalogUrl = "/v1/diaryCatalog/getAllByUserId";
	public final static String userDiaryUrl = "/v1/diary/getAllByUserId";
	public final static String userDiarySaveUrl = "/v1/diary/saveOrUpdate";
	public final static String diaryCatalogAddUrl = "/v1/diaryCatalog/add";
	public final static String diaryDelUrl = "/v1/diary/del";
	public final static String diaryCatalogDelUrl = "/v1/diaryCatalog/del";
	public final static String uploadAvatarPath = "/v1/user/uploadAvatar";
	public static final String downloadPath = "/download";
	public static final String websocketPath = "/v1/websocket/getByUserName";
	public static final String pullAllWebsocketPath = "/v1/websocket/pullByUserName";
	public static final String sendTextWebsocketPath = "/v1/websocket/sendText";

	public static String getPath(String path) {
		return config() + path;
	}

	private static String config() {
		String prop = Config.getPropForce("webHost");
		if (!StringUtils.isEmpty(prop))
			return prop;
		return "";
	}

}

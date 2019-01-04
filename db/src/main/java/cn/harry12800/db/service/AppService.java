package cn.harry12800.db.service;

import org.springframework.stereotype.Service;

@Service
public class AppService {

	public String getVersion() {
//		return PropertiesUtil.getValue("app.version");
		return "";
	}

	public String getAvatarPath() {
//		return PropertiesUtil.getValue("app.avatar.path");
		return "";
	}

	public String getUploadFilePath() {
//		return PropertiesUtil.getValue("app.file.upload.path");
		return "";
	}
}

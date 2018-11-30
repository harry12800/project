package cn.harry12800.scan.service;

import org.springframework.stereotype.Service;

import cn.harry12800.api.util.PropertiesUtil;

@Service
public class AppService {

	public String getVersion() {
		return PropertiesUtil.getValue("app.version");
	}

	public String getAvatarPath() {
		return PropertiesUtil.getValue("app.avatar.path");
	}

	public String getUploadFilePath() {
		return PropertiesUtil.getValue("app.file.upload.path");
	}
}

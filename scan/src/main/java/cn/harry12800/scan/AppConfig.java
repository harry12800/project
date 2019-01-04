package cn.harry12800.scan;

import java.util.Properties;

import org.springframework.context.annotation.Configuration;

import cn.harry12800.tools.FileUtils;

@Configuration
public class AppConfig {
	
	static {
		Properties loadProps = FileUtils.loadProps("scan.properties");
		app_version = loadProps.getProperty("app_version");
		windows_app_avatar_path = loadProps.getProperty("windows.app.avatar.path");
		windows_app_avatar_path = loadProps.getProperty("windows.app.file.upload.path");
		windows_app_file_upload_path = loadProps.getProperty("windows.app.file.upload.path");
		windows_app_file_upload_vchat = loadProps.getProperty("windows.app.file.upload.vchat");
		windows_app_resources_upload_file_path = loadProps.getProperty("windows.app.resources.upload.file.path");
		linux_app_file_upload_vchat = loadProps.getProperty("linux.app.file.upload.vchat");
		linux_app_resources_upload_file_path = loadProps.getProperty("linux.app.resources.upload.file.path");
		linux_app_file_upload_path = loadProps.getProperty("linux.app.file.upload.path");
		linux_app_avatar_path = loadProps.getProperty("linux.app.avatar.path");
		app_imsso_url = loadProps.getProperty("app.imsso.url");
		app_ssoCallback = loadProps.getProperty("app.ssoCallback");
	}
	public static String app_version;
	public static String windows_app_avatar_path;
	public static String windows_app_file_upload_path;
	public static String windows_app_file_upload_vchat;
	public static String windows_app_resources_upload_file_path;
	
	public static String linux_app_file_upload_vchat;
	public static String linux_app_resources_upload_file_path;
	public static String linux_app_file_upload_path;
	public static String linux_app_avatar_path;
		
	public static String app_imsso_url;
	public static String app_ssoCallback;
}

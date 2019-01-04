package cn.harry12800.vchat.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.harry12800.vchat.app.App;

/**
 * Created by harry12800 on 2017/6/11.
 */
public class FileCache {
	private static Logger LOG = LoggerFactory.getLogger(FileCache.class);

	public String FILE_CACHE_ROOT_PATH;
	private DecimalFormat decimalFormat = new DecimalFormat("#.0");

	public FileCache() {
		try {
			// FILE_CACHE_ROOT_PATH = getClass().getResource("/cache").getPath()
			// + "/file";
			FILE_CACHE_ROOT_PATH = App.basePath + "/vchat/cache/file";
			File file = new File(FILE_CACHE_ROOT_PATH);
			if (!file.exists()) {
				file.mkdirs();
				LOG.info("创建文件缓存目录：" + file.getAbsolutePath());
			}
		} catch (Exception e) {
			FILE_CACHE_ROOT_PATH = "./";
		}
	}

	public String tryGetFileCache(String identify, String name) {
		File cacheFile = new File(FILE_CACHE_ROOT_PATH + "/" + identify + "_" + name);
		if (cacheFile.exists()) {
			return cacheFile.getAbsolutePath();
		}

		return null;
	}

	public String cacheFile(String identify, String name, byte[] data) {
		if (data == null || data.length < 1) {
			return null;
		}

		File cacheFile = new File(FILE_CACHE_ROOT_PATH + "/" + identify + "_" + name);
		try {
			FileOutputStream outputStream = new FileOutputStream(cacheFile);
			outputStream.write(data);
			outputStream.close();
			return cacheFile.getAbsolutePath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String fileSizeString(String path) {
		File file = new File(path);
		long size = file.length();
		String retString = "";
		if (size < 1024) {
			retString = size + " 字节";
		} else if (size < 1024 * 1024) {
			retString = decimalFormat.format(size * 1.0F / 1024) + " KB";
		} else {
			retString = decimalFormat.format(size * 1.0F / 1024 / 1024) + " MB";
		}

		return retString;
	}

}

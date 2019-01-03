package cn.harry12800.scan.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.harry12800.db.entity.FileServer;
import cn.harry12800.db.service.FileServerService;
import cn.harry12800.scan.AppConfig;
import cn.harry12800.tools.FileUtils;
import cn.harry12800.tools.OSUtil;
import cn.harry12800.tools.StringUtils;
import io.swagger.annotations.ApiParam;

@Controller
public class FileServerController {
	@Autowired
	FileServerService fileServerService;

	@Autowired
	AppConfig appConfig;
	@RequestMapping(value = "/fileServer/upload", method = RequestMethod.POST)
	@ResponseBody
	public Object upload(HttpServletRequest req, MultipartHttpServletRequest multiReq) {
		// 获取上传文件的路径
		String uploadFilePath = multiReq.getFile("file").getOriginalFilename();
		String suffix = StringUtils.getSuffix(uploadFilePath);
		String uuid = StringUtils.getUUID();
		String header = req.getHeader("author");
		if (!"harry12800".equals(header)) {
			return "不合法！";
		}
		String basePath = "";
		if(OSUtil.isWindows()) {
			basePath = appConfig.windows_app_file_upload_vchat;
		}
		else {
			basePath = appConfig.linux_app_file_upload_vchat;
		}
		File file = new File(basePath + uuid + suffix);
		FileUtils.createFile(basePath + uuid + suffix);
		// 截取上传文件的文件名
		// 截取上传文件的后缀
		InputStream inputStream = null;
		try {
			inputStream = multiReq.getFile("file").getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
			return "未获取的文件流，file属性";
		}
		if (inputStream instanceof FileInputStream) {
			try (FileInputStream fis = (FileInputStream) inputStream;
					FileOutputStream fos = new FileOutputStream(file)) {
				byte[] temp = new byte[9064];
				int len = 0;
				while ((len = fis.read(temp)) != -1) {
					fos.write(temp, 0, len);
				}
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (inputStream instanceof ByteArrayInputStream) {
			try (ByteArrayInputStream fis = (ByteArrayInputStream) inputStream;
					FileOutputStream fos = new FileOutputStream(file)) {
				byte[] temp = new byte[9064];
				int len = 0;
				while ((len = fis.read(temp)) != -1) {
					fos.write(temp, 0, len);
				}
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			return "未知的文件流类型，file属性" + inputStream.getClass();
		}
		FileServer t = new FileServer();
		t.setFileName(uploadFilePath);
		t.setFilePath(basePath + uuid + suffix);
		t.setId(uuid);
		t.setLength(new File(basePath + uuid + suffix).length());
		t.setNilPath("/fileServer/visit/" + uuid + suffix);
		t.setVisitPath("/fileServer/visit/" + uuid + suffix);
		t.setVisitTimes(0L);
		t.setTag(req.getHeader("tag"));
		t.setSuffix(suffix);
		t.setFileType(1);
		fileServerService.save(t);
		return t;
	}

	@RequestMapping(value = "/fileServer/visit/{uuid}", method = RequestMethod.POST)
	public Object visit(@ApiParam @PathVariable("uuid") String id, HttpServletRequest req,
			MultipartHttpServletRequest multiReq, HttpServletResponse resp) {
		// 获取上传文件的路径
		String[] split = id.split("[.]");
		FileServer fileServer = fileServerService.findById(split[0]);
		if (fileServer == null) {
			try {
				resp.sendError(404);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		String fileUrl = fileServer.getFilePath();
		File file = new File(fileUrl);
		String formFileName = fileServer.getFileName();
		try {
			formFileName = filename(req, formFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (fileUrl.endsWith(".png")) {
			resp.setHeader("Content-disposition", String.format("inline; filename=\"%s\"", formFileName));
			resp.setContentType("image/png;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
		} else if (fileUrl.endsWith(".jpg")) {
			resp.setHeader("Content-disposition", String.format("inline; filename=\"%s\"", formFileName));
			resp.setContentType("image/jpeg;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
		} else if (fileUrl.endsWith(".md")) {
			resp.setContentType("text/html;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			return "markdown";
		} else if (fileUrl.endsWith(".java")) {
			resp.setContentType("text/html;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			return "markdown";
		} else if (fileUrl.endsWith(".c")) {
			resp.setContentType("text/html;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			return "markdown";
		} else if (fileUrl.endsWith(".js")) {
			resp.setContentType("text/html;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			return "markdown";
		} else if (fileUrl.endsWith(".css")) {
			resp.setContentType("text/html;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
			return "markdown";
		} else if (fileUrl.endsWith(".txt")) {
			resp.setHeader("Content-disposition", String.format("inline; filename=\"%s\"", formFileName));
			resp.setContentType("text/plain;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
		} else {
			resp.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", formFileName));
			resp.setContentType("application/octet-stream;charset=utf-8");
			resp.setCharacterEncoding("UTF-8");
		}

		resp.addHeader("Content-Length", "" + file.length());
		byte[] buff = new byte[4096];
		try (OutputStream os = resp.getOutputStream();
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
			int i = bis.read(buff);
			while (i != -1) {
				os.write(buff, 0, buff.length);
				os.flush();
				i = bis.read(buff);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("success");
		return null;
	}

	private String filename(HttpServletRequest req, String filename) throws Exception {
		String userAgent = req.getHeader("User-Agent");
		System.out.println(userAgent);
		if (userAgent.contains("Edge")) {
			System.out.println("Edge");
			filename = URLEncoder.encode(filename, "UTF-8");
			filename = filename.replace("+", "%20");
		} else if (userAgent.contains("Chrome")) {
			System.out.println("Chrome");
			filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
		} else if (userAgent.contains("Firefox")) {
			System.out.println("Firefox");
			filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
			// filename = "=?utf-8?b?" +
			// Base64.getEncoder().encode(filename.getBytes("utf-8")) + "?=";
		} else if (userAgent.contains("Trident")) {
			System.out.println("IE");
			filename = URLEncoder.encode(filename, "UTF-8");
			filename = filename.replace("+", "%20");
		} else {
			System.out.println("Others");
			filename = new String(filename.getBytes("gbk"), "ISO8859-1");
		}
		return filename;
	}
}

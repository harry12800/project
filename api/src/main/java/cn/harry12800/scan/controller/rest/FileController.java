package cn.harry12800.scan.controller.rest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.harry12800.db.service.AppService;
import cn.harry12800.db.service.DiaryCatalogService;
import cn.harry12800.db.service.DiaryService;
import cn.harry12800.scan.util.PropertiesUtil;
import cn.harry12800.tools.OSUtil;

@RestController
@RequestMapping("/v1/file")
public class FileController {
	@Autowired
	DiaryService diarySerivce;
	@Autowired
	DiaryCatalogService diaryCatalogservice;
	@Autowired
	AppService appService;

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(HttpServletResponse res, @RequestParam String path) {
		System.out.println(path);
		File file = new File(path);
		res.addHeader("Content-Length", "" + file.length());
		res.setHeader("content-type", "application/octet-stream");
		res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
		byte[] buff = new byte[1024];
		try (OutputStream os = res.getOutputStream();
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
	}

	@RequestMapping(value = "/download-vchat", method = RequestMethod.GET)
	public void download_vchat(HttpServletResponse res) {
		String value = "";
		if (OSUtil.isWindows()) {
			value = PropertiesUtil.getValue("windows.app.file.upload.vchat");
		} else {
			value = PropertiesUtil.getValue("linux.app.file.upload.vchat");
		}
		File file = new File(value + "vchat.rar");
		res.addHeader("Content-Length", "" + file.length());
		res.setHeader("content-type", "application/octet-stream");
		res.setContentType("application/octet-stream");
		res.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
		byte[] buff = new byte[1024];
		try (OutputStream os = res.getOutputStream();
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
	}

	@RequestMapping(value = "/upload-vchat", method = RequestMethod.POST)
	public Object upload_vchat(HttpServletRequest req, MultipartHttpServletRequest multiReq, String username, String pwd) {
		// 获取上传文件的路径
		if (!("周国柱".equals(username) && "866662".equals(pwd))) {
			return "vchat";
		}
		String path = "";
		if (OSUtil.isWindows()) {
			path = PropertiesUtil.getValue("windows.app.file.upload.vchat");
		} else {
			path = PropertiesUtil.getValue("linux.app.file.upload.vchat");
		}
		File file = new File(path + "vchat.rar");
		// 截取上传文件的后缀
		try (FileInputStream fis = (FileInputStream) multiReq.getFile("file").getInputStream();
				FileOutputStream fos = new FileOutputStream(file)) {
			byte[] temp = new byte[1024];
			int i = fis.read(temp);
			while (i != -1) {
				fos.write(temp, 0, temp.length);
				fos.flush();
				i = fis.read(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "vchat";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(HttpServletRequest req, MultipartHttpServletRequest multiReq, String path) {
		// 获取上传文件的路径
		String uploadFilePath = multiReq.getFile("file").getOriginalFilename();
		File file = new File(path);
		System.out.println("uploadFlePath:" + uploadFilePath);
		// 截取上传文件的文件名
		String uploadFileName = uploadFilePath.substring(uploadFilePath.lastIndexOf('\\') + 1,
				uploadFilePath.indexOf('.'));
		System.out.println("multiReq.getFile()" + uploadFileName);
		// 截取上传文件的后缀
		String uploadFileSuffix = uploadFilePath.substring(uploadFilePath.indexOf('.') + 1, uploadFilePath.length());
		System.out.println("uploadFileSuffix:" + uploadFileSuffix);
		try (FileInputStream fis = (FileInputStream) multiReq.getFile("file").getInputStream();
				FileOutputStream fos = new FileOutputStream(file)) {
			byte[] temp = new byte[1024];
			int i = fis.read(temp);
			while (i != -1) {
				fos.write(temp, 0, temp.length);
				fos.flush();
				i = fis.read(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

	@RequestMapping(value = "uploads", method = RequestMethod.POST)
	@ResponseBody
	public void uploads(HttpServletRequest request) {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		MultipartFile file = null;
		for (int i = 0; i < files.size(); ++i) {
			file = files.get(i);
			if (!file.isEmpty()) {
				String uploadFilePath = file.getOriginalFilename();
				System.out.println("uploadFlePath:" + uploadFilePath);
				// 截取上传文件的文件名
				String uploadFileName = uploadFilePath.substring(uploadFilePath.lastIndexOf('\\') + 1,
						uploadFilePath.indexOf('.'));
				System.out.println("multiReq.getFile()" + uploadFileName);
				// 截取上传文件的后缀
				String uploadFileSuffix = uploadFilePath.substring(uploadFilePath.indexOf('.') + 1,
						uploadFilePath.length());
				System.out.println("uploadFileSuffix:" + uploadFileSuffix);
				try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
						new File(".//uploadFiles//" + uploadFileName + "." + uploadFileSuffix)));) {
					byte[] bytes = file.getBytes();
					stream.write(bytes, 0, bytes.length);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("上传文件为空");
			}
		}
		System.out.println("文件接受成功了");
	}
}

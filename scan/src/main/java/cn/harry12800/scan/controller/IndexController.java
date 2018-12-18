package cn.harry12800.scan.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.harry12800.api.util.PropertiesUtil;
import cn.harry12800.api.util.SQLUtils;
import cn.harry12800.db.entity.Diary;
import cn.harry12800.db.entity.DiaryCatalog;
import cn.harry12800.db.entity.Markdown;
import cn.harry12800.db.entity.Resource;
import cn.harry12800.db.entity.UserInfo;
import cn.harry12800.db.mapper.MarkdownMapper;
import cn.harry12800.db.mapper.ResourceMapper;
import cn.harry12800.db.mapper.UserInfoMapper;
import cn.harry12800.db.service.AppService;
import cn.harry12800.db.service.DiaryCatalogService;
import cn.harry12800.db.service.DiaryService;
import cn.harry12800.tools.FileUtils;
import cn.harry12800.tools.Lists;
import cn.harry12800.tools.Maps;
import cn.harry12800.tools.OSUtil;
import cn.harry12800.tools.StringUtils;

@Controller
public class IndexController {
	@Autowired
	DiaryService diarySerivce;
	@Autowired
	DiaryCatalogService diaryCatalogservice;
	@Autowired
	AppService appService;
	@Autowired
	ResourceMapper resourceMapper;
	@Autowired
	UserInfoMapper userInfoMapper;
	@Autowired
	MarkdownMapper markdownMapper;

	@RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
	public String index(@RequestParam(required = false) String key,
			@RequestParam(required = false) String diaryCatalogId, @RequestParam(required = false) String userId,
			Model model) {
		if (StringUtils.isEmpty(userId)) {
			userId = "2";
		}
		Long valueOf = 2L;
		try {
			valueOf = Long.valueOf(userId);
		} catch (Exception e) {

		}
		UserInfo user = userInfoMapper.findById(valueOf);
		if (user == null) {
			user = userInfoMapper.findByUserId("harry12800");
		}
		List<DiaryCatalog> diaryCatalogs = diaryCatalogservice.findAllByUserId(userId);
		List<Diary> top10s = diarySerivce.findTop10();

		String sensitivewords = "飞鸽|imsso|IMSSO|Imsso|服务|平台|网站|系统|开发|fingerchat|xdata|Xdata|公司|xdata-proxy|千君|周亮|礼";
		for (Diary diary : top10s) {
			diary.setTitle(diary.getTitle().replaceAll(sensitivewords, "***"));
		}
		model.addAttribute("diaryCatalogs", diaryCatalogs);
		model.addAttribute("top10s", top10s);
		model.addAttribute("version", appService.getVersion());
		if (!StringUtils.isEmpty(key)) {
			List<Diary> diarys = diarySerivce.findAllByUserIdContainKey(key, userId);
			for (Diary diary : diarys) {
				diary.setTitle(diary.getTitle().replaceAll(sensitivewords, "***"));
			}
			model.addAttribute("diarys", diarys);
			model.addAttribute("key", key);
			return "index";
		} else if (!StringUtils.isEmpty(diaryCatalogId)) {
			List<Diary> diarys = diarySerivce.findAllByCatalogId(diaryCatalogId);

			for (Diary diary : diarys) {
				diary.setTitle(diary.getTitle().replaceAll(sensitivewords, "***"));
			}
			model.addAttribute("diarys", diarys);
			model.addAttribute("key", "Search");
			return "index";
		} else {
			List<Diary> diarys = diarySerivce.findAllByUserId(userId);
			System.out.println(user);
			List<Resource> lists = resourceMapper.findByUserId(user.getUserId());
			System.out.println("资源" + lists);
			Set<Long> ids = new HashSet<Long>();
			Map<Long, Resource> resourceMap = Maps.newHashMap();
			for (Resource resource : lists) {
				switch (resource.getResourceType()) {
				case 1: // markdown
					ids.add(resource.getId());
					resourceMap.put(resource.getId(), resource);
					break;
				}
			}
			String markdownIds = SQLUtils.getSQLInSentenceLong(ids, "id");
			List<Markdown> findByIds = markdownMapper.findByIds(markdownIds);
			for (Markdown markdown : findByIds) {
				Diary e = new Diary();
				e.setContent(markdown.getContent());
				e.setId(markdown.getId() + "");
				e.setTitle(markdown.getName());
				e.setCreateTime(resourceMap.get(markdown.getId()).getCreateTime());
				e.setUpdateTime(resourceMap.get(markdown.getId()).getUpdateTime());
				e.setCipher(1);
				e.setHtml("/v1/doc/viewById/" + e.getId());
				diarys.add(e);
			}
			Collections.sort(diarys, new Comparator<Diary>() {
				@Override
				public int compare(Diary o1, Diary o2) {
					long time = o1.getUpdateTime().getTime();
					long time2 = o2.getUpdateTime().getTime();
					if (time > time2)
						return -1;
					else if (time == time2)
						return 0;
					else
						return 1;
				}
			});
			for (Diary diary : diarys) {
				diary.setTitle(diary.getTitle().replaceAll(sensitivewords, "***"));
			}
			model.addAttribute("diarys", diarys);
			model.addAttribute("key", "Search");
			return "index";
		}
	}

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
	public Object upload_vchat(HttpServletRequest req, MultipartHttpServletRequest multiReq, String username,
			String pwd) {
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
	@ResponseBody
	public Object upload(HttpServletRequest req, MultipartHttpServletRequest multiReq, String path) {
		// 获取上传文件的路径
		String uploadFilePath = multiReq.getFile("file").getOriginalFilename();
		File file = new File(path);
		try {
			boolean startsWith = file.getCanonicalPath().startsWith("/root/scan");
			boolean startsWith1 = file.getCanonicalPath().startsWith("/root/vchat");
			if(!(startsWith||startsWith1)){
				return "upload fail， your path is invalid！";
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			return "upload fail， your path is invalid！";
		} 
		
		FileUtils.createFile(file);
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
		return "upload is successful！";
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

	@SuppressWarnings("unused")
	@RequestMapping(value = "ls", method = RequestMethod.GET)
	@ResponseBody
	public Object ls(@RequestParam String path) {
		System.out.println(path);
		File file_ = new File(path);
		File[] child_ = null;
		if (file_.isDirectory())
			child_ = file_.listFiles();
		File[] child = child_;
		Object o = new Object() {
			public File file = file_;
			public File[] childs = child;
		};
		return o;
	}
	@RequestMapping(value = "md5", method = RequestMethod.GET)
	@ResponseBody
	public Object md5(@RequestParam String path) throws Exception {
		System.out.println(path);
		File file = new File(path);
		if(file.exists()&&file.isFile()){
			String md5 = FileUtils.getMD5(file);
			return md5;
		}else {
			return "error";
		}
	}
	public static class DirMd5{
		public String name;
		public String md5;
		public long size;
	}
	@RequestMapping(value = "dirmd5", method = RequestMethod.GET)
	@ResponseBody
	public Object dirmd5(@RequestParam String path) throws Exception {
		System.out.println(path);
		File file = new File(path);
		List<DirMd5> lists = Lists.newArrayList();
		if(file.exists()&&file.isDirectory()){
			File[] listFiles = file.listFiles();
			for (File file2 : listFiles) {
				if(file2.isFile()) {
					String md5 = FileUtils.getMD5(file2);
					DirMd5 e = new DirMd5();
					e.md5 = md5;
					e.name = file2.getName();
					e.size = file2.length();
					lists.add(e);
				}
			}
			return lists;
		}else {
			return "error";
		}
	}
	@SuppressWarnings("unused")
	@RequestMapping(value = "ll", method = RequestMethod.GET)
	@ResponseBody
	public Object ll(@RequestParam String path) {
		System.out.println(path);
		MyFile file_ = new MyFile(path);
		File[] child_ = null;
		if (file_.isDirectory())
			child_ = file_.listFiles();
		MyFile[] child__ = null;
		if (!Objects.isNull(child_)) {
			MyFile[] childtemp = new MyFile[child_.length];
			int i = 0;
			for (File file : child_) {
				childtemp[i] = new MyFile(file.getAbsolutePath());
				i++;
			}
			child__ = childtemp;
		}
		MyFile[] child = child__;
		Object o = new Object() {
			public MyFile file = file_;
			public MyFile[] childs = child;
		};
		return o;
	}

	@SuppressWarnings("serial")
	static class MyFile extends File {
		public long lastTime;

		public MyFile(String pathname) {
			super(pathname);
			lastTime = lastModified();
		}
	}
}

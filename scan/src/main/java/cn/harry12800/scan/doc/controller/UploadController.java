package cn.harry12800.scan.doc.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.harry12800.db.entity.Application;
import cn.harry12800.db.entity.Directory;
import cn.harry12800.db.entity.Resource;
import cn.harry12800.db.entity.ResourcesUpload;
import cn.harry12800.db.mapper.ApplicationMapper;
import cn.harry12800.db.mapper.DirectoryMapper;
import cn.harry12800.db.mapper.Int64SequenceEntityMapper;
import cn.harry12800.db.mapper.ResourceMapper;
import cn.harry12800.db.mapper.ResourceUploadMapper;
import cn.harry12800.scan.doc.http.EResponseCode;
import cn.harry12800.scan.doc.http.ResponseEntity;
import cn.harry12800.scan.util.PropertiesUtil;
import cn.harry12800.tools.OSUtil;
import io.swagger.annotations.ApiParam;

@Controller
//@PropertySource(value = { "application.properties" })
@RequestMapping("/v1/doc/upload")
public class UploadController {
	@Autowired
	ResourceUploadMapper resourceUploadMapper;
	@Autowired
	Int64SequenceEntityMapper sequenceMapper;
	@Autowired
	ResourceMapper resourceMapper;
	@Autowired
	ApplicationMapper appMapper;
	@Autowired
	DirectoryMapper directoryMapper;
	//	@Autowired
	//	DiaryService diarySerivce;
	//	@Autowired
	//	DiaryCatalogService diaryCatalogservice;
	//	@Autowired
	//	AppService appService;

	//	@RequestMapping(value = "/", method = {RequestMethod.GET,RequestMethod.POST})
	//	public String index(@RequestParam(required = false) String key, @RequestParam(required = false) String diaryCatalogId, @RequestParam(required = false) String userId, Model model) {
	//		if (StringUtils.isEmpty(userId)) {
	//			userId = "2";
	//		}
	//		List<DiaryCatalog> diaryCatalogs = diaryCatalogservice.findAllByUserId(userId);
	//		List<Diary> top10s = diarySerivce.findTop10();
	//
	//		String sensitivewords = "飞鸽|imsso|IMSSO|Imsso|服务|平台|网站|系统|开发|fingerchat|xdata|Xdata|公司|xdata-proxy|千君|贺翔|周亮|礼";
	//		for (Diary diary : top10s) {
	//			diary.setTitle(diary.getTitle().replaceAll(sensitivewords, "***"));
	//		}
	//		model.addAttribute("diaryCatalogs", diaryCatalogs);
	//		model.addAttribute("top10s", top10s);
	//		model.addAttribute("version", appService.getVersion());
	//		if (!StringUtils.isEmpty(key)) {
	//			List<Diary> diarys = diarySerivce.findAllByUserIdContainKey(key, userId);
	//			for (Diary diary : diarys) {
	//				diary.setTitle(diary.getTitle().replaceAll(sensitivewords, "***"));
	//			}
	//			model.addAttribute("diarys", diarys);
	//			model.addAttribute("key", key);
	//			return "index";
	//		} else if (!StringUtils.isEmpty(diaryCatalogId)) {
	//			List<Diary> diarys = diarySerivce.findAllByCatalogId(diaryCatalogId);
	//			for (Diary diary : diarys) {
	//				diary.setTitle(diary.getTitle().replaceAll(sensitivewords, "***"));
	//			}
	//			model.addAttribute("diarys", diarys);
	//			model.addAttribute("key", "Search");
	//			return "index";
	//		} else {
	//			List<Diary> diarys = diarySerivce.findAllByUserId(userId);
	//			for (Diary diary : diarys) {
	//				diary.setTitle(diary.getTitle().replaceAll(sensitivewords, "***"));
	//			}
	//			model.addAttribute("diarys", diarys);
	//			model.addAttribute("key", "Search");
	//			return "index";
	//		}
	//	}

	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public String file() {
		return "file";
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void Download(HttpServletResponse res, @RequestParam String path) {
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

	public class Request_body {
		public Long directoryId;
		public Long appId;
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity upload(@ApiParam @RequestParam Long directoryId, @ApiParam @RequestParam Long appId, HttpServletRequest req, MultipartHttpServletRequest multiReq) {
		if (Objects.isNull(directoryId) || Objects.isNull(appId)) {
			return ResponseEntity.newBadRequest();
		}
		ResponseEntity r = ResponseEntity.newOk();
		Application application = appMapper.findById(appId);
		Directory directory = directoryMapper.findById(directoryId);
		if (Objects.isNull(application) || Objects.isNull(directory)) {
			r.setCode(EResponseCode.BAD_REQUEST.value());
			r.setMessage("所属目录或app应用不存在！！！");
			return r;
		}
		// 获取上传文件的名字
		String uploadFileName = multiReq.getFile("file").getOriginalFilename();
		System.out.println("uploadFlePath:" + uploadFileName);
		//		// 截取上传文件前缀
		//		String uploadFilePrefix = uploadFileName.substring(uploadFileName.lastIndexOf('\\') + 1,
		//				uploadFileName.indexOf('.'));
		//		System.out.println("multiReq.getFile()" + uploadFilePrefix);
		//		// 截取上传文件后缀
		//		String uploadFileSuffix = uploadFileName.substring(uploadFileName.indexOf('.'), uploadFileName.length());
		//		System.out.println("uploadFileSuffix:" + uploadFileSuffix);
		//获取上传文件路径
		//		String path = PropertiesUtils.getPath("resources.upload.file.path");
		String path ="";
		if(OSUtil.isWindows()){
			 path = PropertiesUtil.getValue("windows.app.resources.upload.file.path");
		}
		else{
			 path = PropertiesUtil.getValue("linux.app.resources.upload.file.path");
		}
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		System.out.println("++++++++++++:" + path);
		String uploadName = uuid + "_" + uploadFileName;
		File file = new File(path + uploadName);
		String userId = (String) req.getSession().getAttribute("userId");
		//try()自动释放资源
		try (FileInputStream fis = (FileInputStream) multiReq.getFile("file").getInputStream();
				FileOutputStream fos = new FileOutputStream(file)) {
			byte[] temp = new byte[1024];
			int i = fis.read(temp);
			while (i != -1) {
				fos.write(temp, 0, temp.length);
				fos.flush();
				i = fis.read(temp);
			}
			//上传成功存储数据库
			Long id = sequenceMapper.getNextSequence("fingerchat_dev_docs.resource.id");
			ResourcesUpload upload = new ResourcesUpload();
			upload.setId(id);
			upload.setFileName(uploadFileName);
			upload.setUploadName(uploadName);
			upload.setFileUrl(path + uploadName);
			int save = resourceUploadMapper.save(upload);
			if (save < 1) {
				r.setCode(EResponseCode.NOT_ACCEPTABLE.value());
				r.setMessage("上传数据存储失败");
				r.setContent(new Object() {
					@SuppressWarnings("unused")
					public int result = save;
				});
				return r;
			}
			//同时增加资源数据
			Resource resource = new Resource();
			resource.setId(id);
			resource.setDirectoryId(directoryId);
			resource.setAppId(appId);
			resource.setName(uploadFileName);
			Date date = new Date();
			resource.setCreateTime(date);
			resource.setUpdateTime(date);
			resource.setCreateUser(userId);
			resource.setUpdateUser(userId);
			resource.setResourceType(3);
			resource.setRemark("");
			resource.setReadable(0);
			resource.setOwner(userId);
			resource.setCipher(1);
			resource.setHint(0);
			resource.setSort(0);
			resourceMapper.save(resource);
			resourceMapper.insertUserResource(userId, id);
			r.setContent(resource);
		} catch (IOException e) {
			e.printStackTrace();
			r.setCode(EResponseCode.SERVER_ERROR.value());
			r.setMessage("上传数据存储失败");
		}
		return r;
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

package cn.harry12800.scan.controller.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.harry12800.db.entity.DiaryCatalog;
import cn.harry12800.db.entity.User;
import cn.harry12800.scan.http.MyResponse;
import cn.harry12800.scan.http.MyResponse.EResponseCode;
import cn.harry12800.scan.service.AppService;
import cn.harry12800.scan.service.DiaryCatalogService;
import cn.harry12800.scan.service.DiaryService;
import cn.harry12800.tools.StringUtils;

@SuppressWarnings("unused")
@RestController
@RequestMapping(value = "v1/user")
public class UserRestController {
	@Autowired
	DiaryCatalogService diaryCatalogService;
	@Autowired
	DiaryService diaryService;
	@Autowired
	AppService appService;

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse get(HttpServletResponse res) {
		MyResponse r = MyResponse.newOk();
		try {
			res.setContentType("text/plain");
			List<DiaryCatalog> findAll = diaryCatalogService.findAll();
			r.setContent(findAll);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/validate", headers = "content-type=application/x-www-form-urlencoded", method = RequestMethod.POST, consumes = {}, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public MyResponse validate(
			@RequestParam String id,
			@RequestParam String userName,
			@RequestParam String pwd,
			HttpServletResponse res) {
		MyResponse r = MyResponse.newOk();
		System.out.println(userName);
		System.out.println(pwd);
		try {
			User user = diaryService.findUserByDiaryId(id);
			if (user == null) {
				r.setContent(new Object() {
					public String result = "error";
				});
			} else if (user.getUserName().equals(userName) && user.getPassward().equals(pwd)) {
				r.setContent(diaryService.findById(id));
			} else {
				r.setContent(new Object() {
					public String result = "error";
				});
			}
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	@ResponseBody
	public MyResponse getById(String id, HttpServletResponse res) {
		MyResponse r = MyResponse.newOk();
		try {
			res.setContentType("text/plain");
			DiaryCatalog findById = diaryCatalogService.findById(id);
			r.setContent(findById);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public MyResponse add(Model model, @ModelAttribute() DiaryCatalog diary) {
		MyResponse r = MyResponse.newOk();
		try {
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			diary.setId(id);
			diary.setCreateTime(new Date());
			diary.setUpdateTime(new Date());
			diaryCatalogService.save(diary);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public MyResponse save(Model model, @RequestBody DiaryCatalog diary) {
		MyResponse r = MyResponse.newOk();
		try {
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			diary.setId(id);
			diary.setCreateTime(new Date());
			diary.setUpdateTime(new Date());
			diaryCatalogService.save(diary);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public MyResponse update(Model model, @RequestBody DiaryCatalog diary) {
		MyResponse r = MyResponse.newOk();
		try {
			if (StringUtils.isEmpty(diary.getId())) {
				String id = UUID.randomUUID().toString().replaceAll("-", "");
				diary.setId(id);
			}
			DiaryCatalog data = diaryCatalogService.findById(diary.getId());
			data.setName(diary.getName());
			data.setUpdateTime(new Date());
			diaryCatalogService.update(data);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public MyResponse del(String id) {
		MyResponse r = MyResponse.newOk();
		try {
			diaryCatalogService.deleteByIds(id);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}

	@RequestMapping(value = "/uploadAvatar", method = RequestMethod.POST)
	public MyResponse upload(HttpServletRequest req, MultipartHttpServletRequest multiReq) {
		// 获取上传文件的路径
		MyResponse r = MyResponse.newOk();
		String uploadFilePath = multiReq.getFile("file").getOriginalFilename();
		//		System.out.println(appService.getAvatarPath());
		File file = new File(appService.getAvatarPath() + uploadFilePath);
		//		System.out.println("path:"+file.getAbsolutePath());
		//		System.out.println("uploadFlePath:" + uploadFilePath);
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
		return r;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public MyResponse login(String id) {
		MyResponse r = MyResponse.newOk();
		try {
			diaryCatalogService.deleteByIds(id);
		} catch (Exception e) {
			r.setCode(EResponseCode.SERVER_ERROR.code).setMsg(e.getMessage());
			e.printStackTrace();
		}
		return r;
	}
}

package cn.harry12800.scan.doc.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.harry12800.api.doc.cache.MarkDownCacheUtil;
import cn.harry12800.db.entity.Api;
import cn.harry12800.db.entity.AutoApiMarkdown;
import cn.harry12800.db.entity.FingerChatUser;
import cn.harry12800.db.entity.Markdown;
import cn.harry12800.db.entity.Resource;
import cn.harry12800.db.entity.ResourcesUpload;
import cn.harry12800.db.mapper.ApiMapper;
import cn.harry12800.db.mapper.AutoApiMarkdownMapper;
import cn.harry12800.db.mapper.FingerChatUserMapper;
import cn.harry12800.db.mapper.MarkdownMapper;
import cn.harry12800.db.mapper.ResourceMapper;
import cn.harry12800.db.mapper.ResourceUploadMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("/v1/doc")
public class MarkdownViewPage {
	private static final String API_TAGS = "资源查看/MarkdownViewPage";
	@Autowired
	ResourceMapper resourceMapper;
	@Autowired
	MarkdownMapper mapper;
	@Autowired
	ApiMapper apiMapper;
	@Autowired
	FingerChatUserMapper userMapper;
	@Autowired
	AutoApiMarkdownMapper autoApiMarkdownMapper;
	@Autowired
	ResourceUploadMapper resourceUploadMapper;

	public static Map<String, String> mds = new HashMap<String, String>(0);

	@ApiOperation(httpMethod = "GET", value = "通过资源id查看的资源内容", tags = { API_TAGS })
	@RequestMapping(value = "/viewById/{id}", method = RequestMethod.GET)
	public String viewById(@ApiParam @PathVariable("id") Long id, ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
		Resource re = resourceMapper.findById(id);
		Integer readable = re.getReadable();
		System.err.println(readable);
		if (readable == 0) {
			String userId = (String) req.getSession().getAttribute("userId");
			if (userId == null) {
				return "405";
			}
			List<FingerChatUser> users = userMapper.getResourceUserByResourceId(id);
			boolean hasQ = false;
			for (FingerChatUser user : users) {
				System.err.println(user.getUserId());
				if (user.getUserId().equals(userId)) {
					hasQ = true;
					break;
				}
			}
			if (!hasQ) {
				return "405";
			}
		}
		Integer resourceType = re.getResourceType();
		System.out.println(resourceType);
		switch (resourceType) {
		case 1: // markdown
			Markdown findById = mapper.findById(re.getId());
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			mds.put(uuid, findById.getContent());
			model.addAttribute("name", "v1/doc/app/" + uuid);
			model.addAttribute("title", findById.getName());
			System.out.println(findById);
			break;
		case 2: // auto_api_markdown
			AutoApiMarkdown findById2 = autoApiMarkdownMapper.findById(re.getId());
			List<Api> list = apiMapper.findByAutoApiMarkdownId(findById2.getId());
			StringBuilder sb = new StringBuilder();
			sb.append(findById2.getPrefixContent());
			sb.append("\r\n");
			sb.append("\r\n");
			for (Api api : list) {
				String md = MarkDownCacheUtil.getMarkdownByApiPath(api.getAppId(), api.getType(), api.getPath());
				sb.append(md);
			}
			sb.append(findById2.getSuffixContent());
			uuid = UUID.randomUUID().toString().replaceAll("-", "");
			mds.put(uuid, sb.toString());
			model.addAttribute("name", "v1/doc/app/" + uuid);
			model.addAttribute("title", findById2.getName());
			break;
		case 3:
			ResourcesUpload resourcesUpload = resourceUploadMapper.findById(id);
			String fileUrl = resourcesUpload.getFileUrl();
			File file = new File(fileUrl);
			String formFileName = resourcesUpload.getFileName();
			try {
				formFileName = filename(req, formFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (fileUrl.endsWith(".png")) {
				resp.setHeader("Content-disposition",
						String.format("inline; filename=\"%s\"", formFileName));
				resp.setContentType("image/png;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
			} else if (fileUrl.endsWith(".jpg")) {
				resp.setHeader("Content-disposition",
						String.format("inline; filename=\"%s\"", formFileName));
				resp.setContentType("image/jpeg;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
			} else if (fileUrl.endsWith(".md")) {
				resp.setContentType("text/html;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
				mds.put(uuid, getSrcByFilePath(file, "UTF-8"));
				model.addAttribute("name", "v1/doc/app/" + uuid);
				model.addAttribute("title", resourcesUpload.getFileName());
				return "markdown";
			} else if (fileUrl.endsWith(".java")) {
				resp.setContentType("text/html;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
				mds.put(uuid, "```java\n" + getSrcByFilePath(file, "UTF-8") + "\n```");
				model.addAttribute("name", "v1/doc/app/" + uuid);
				model.addAttribute("title", resourcesUpload.getFileName());
				return "markdown";
			} else if (fileUrl.endsWith(".c")) {
				resp.setContentType("text/html;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
				mds.put(uuid, "```c\n" + getSrcByFilePath(file, "UTF-8") + "\n```");
				model.addAttribute("name", "v1/doc/app/" + uuid);
				model.addAttribute("title", resourcesUpload.getFileName());
				return "markdown";
			} else if (fileUrl.endsWith(".js")) {
				resp.setContentType("text/html;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
				mds.put(uuid, "```javascript\n" + getSrcByFilePath(file, "UTF-8") + "\n```");
				model.addAttribute("name", "v1/doc/app/" + uuid);
				model.addAttribute("title", resourcesUpload.getFileName());
				return "markdown";
			} else if (fileUrl.endsWith(".css")) {
				resp.setContentType("text/html;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
				uuid = UUID.randomUUID().toString().replaceAll("-", "");
				mds.put(uuid, "```css\n" + getSrcByFilePath(file, "UTF-8") + "\n```");
				model.addAttribute("name", "v1/doc/app/" + uuid);
				model.addAttribute("title", resourcesUpload.getFileName());
				return "markdown";
			} else if (fileUrl.endsWith(".txt")) {
				resp.setHeader("Content-disposition",
						String.format("inline; filename=\"%s\"", formFileName));
				resp.setContentType("text/plain;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
			} else {
				resp.setHeader("Content-disposition",
						String.format("attachment; filename=\"%s\"", formFileName));
				resp.setContentType("application/octet-stream;charset=utf-8");
				resp.setCharacterEncoding("UTF-8");
			}

			resp.addHeader("Content-Length", "" + file.length());
			byte[] buff = new byte[1024];
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
		return "markdown";
	}

	/**
	 * 获取文件中的文本
	 * 
	 * @param path
	 * @return
	 */
	public static String getSrcByFilePath(File file, String charset) {
		String line;
		StringBuffer html = new StringBuffer("");
		try (InputStreamReader read = new InputStreamReader(
				new FileInputStream(file), charset);
				BufferedReader reader = new BufferedReader(read);) {
			while ((line = reader.readLine()) != null) {
				html = html.append(line + "\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return html.toString();
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
			//			filename = "=?utf-8?b?" + Base64.getEncoder().encode(filename.getBytes("utf-8")) + "?=";
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

	@ResponseBody
	@RequestMapping(value = "/app/{uuid}", method = { RequestMethod.GET })
	public String app(@PathVariable("uuid") String uuid) {
		String string = mds.get(uuid);
		mds.remove(uuid);
		return string;
	}
}

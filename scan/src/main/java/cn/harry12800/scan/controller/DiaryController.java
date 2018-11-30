package cn.harry12800.scan.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.harry12800.db.entity.Diary;
import cn.harry12800.db.entity.DiaryCatalog;
import cn.harry12800.scan.service.DiaryCatalogService;
import cn.harry12800.scan.service.DiaryService;

@Controller
@RequestMapping(value = "/diary")
public class DiaryController {
	@Autowired
	DiaryService service;
	@Autowired
	DiaryCatalogService diaryCatalogService;

	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public String get(Model model) {
		List<Diary> findAll = service.findAll();
		model.addAttribute("diaryList", findAll);
		return "diary/show";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Model model, @ModelAttribute() Diary diary, String userId) {
		System.out.println(diary);
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		diary.setId(id);
		diary.setCreateTime(new Date());
		diary.setUpdateTime(new Date());
		service.save(diary, userId);
		return "redirect:/diary/get";
	}

	@RequestMapping(value = "/addDiary", method = RequestMethod.GET)
	public String addpage(Model model) {
		List<DiaryCatalog> DiaryCatalogs = diaryCatalogService.findAll();
		model.addAttribute("DiaryCatalogs", DiaryCatalogs);
		return "diary/add";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modify(String id, Model model) {
		System.out.println("修改文章：" + id);
		Diary diary = service.findById(id);
		model.addAttribute("diary", diary);
		return "diary/modify";
	}

	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(String id) {
		System.out.println("del---------" + id);
		service.deleteById(id);
		return "redirect:/diary/get";
	}
}

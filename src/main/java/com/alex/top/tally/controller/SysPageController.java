package com.alex.top.tally.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alex.top.tally.entity.AlexUserEntity;
import com.alex.top.tally.service.AlexUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

/**
 * 系统页面视图
 */
@Controller
public class SysPageController {

	@Autowired
	private AlexUserService alexUserService;
	
	@RequestMapping(value = { "/", "index.html" })
	public String index() {
		return "index";
	}

	@RequestMapping("login.html")
	public String login() {
		return "login";
	}

	@RequestMapping("user.html")
	public String user(Model model) {
		Wrapper<AlexUserEntity> wrapper = new EntityWrapper<AlexUserEntity>().ne("id", 1);
		AlexUserEntity currentUser = (AlexUserEntity) SecurityUtils.getSubject().getPrincipal();
		if (null != currentUser) {
			wrapper.eq("company_id", currentUser.getCompanyId());
			model.addAttribute("currentUser", currentUser);
		}
		List<String> desc = new ArrayList<String>(1);
		desc.add("create_time");
		wrapper.orderDesc(desc);
		model.addAttribute("users", alexUserService.selectList(wrapper));
		return "user";
	}

}

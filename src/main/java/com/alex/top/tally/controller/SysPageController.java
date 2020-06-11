package com.alex.top.tally.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 */
@Controller
public class SysPageController {

	@RequestMapping(value = { "/", "index.html" })
	public String index() {
		return "index";
	}

	@RequestMapping("login.html")
	public String login() {
		return "login";
	}

	@RequestMapping("user.html")
	public String user() {
		return "user";
	}

}

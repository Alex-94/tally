package com.alex.top.tally.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alex.top.tally.entity.AlexUserEntity;
import com.alex.top.tally.service.AlexUserService;
import com.alex.top.tally.utils.R;
import com.alex.top.tally.utils.ShiroUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;

@RestController
public class UserController {

	@Autowired
	private AlexUserService alexUserService;

	@PostMapping("/addUser")
	@ResponseBody
	public R addUser(String userName, String password, Integer userType) {

		int exist = alexUserService.selectCount(new EntityWrapper<AlexUserEntity>().eq("user_name", userName));
		if (exist > 0) {
			return R.error("用户名已存在");
		}
		
		AlexUserEntity alexUser = new AlexUserEntity();
		alexUser.setUserName(userName);
		alexUser.setUserType(userType);
		
		AlexUserEntity currentUser = (AlexUserEntity) SecurityUtils.getSubject().getPrincipal();
		if (null != currentUser) {
			alexUser.setCompanyId(currentUser.getCompanyId());
		}
		
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		alexUser.setPasswordsalt(salt);
		alexUser.setPassword(ShiroUtils.sha256(password, salt));
		alexUser.setUserState(1); //后台添加的用户默认是审核通过的直接可以微信登录绑定openid
		
		alexUserService.insert(alexUser);
		
		return R.ok();
	}

	@PostMapping("/checkUser")
	@ResponseBody
	public R checkUser(Integer userId, Integer userState) {
		AlexUserEntity userEntity = alexUserService.selectOne(new EntityWrapper<AlexUserEntity>().eq("id", userId));
		if (null == userEntity) {
			return R.error("未查询到该用户信息, 请刷新页面!");
		}
		userEntity.setUserState(userState);
		alexUserService.insertOrUpdate(userEntity);
		return R.ok();
	}
	
	@PostMapping("/deleteUserById")
	@ResponseBody
	public R deleteUserById(Integer userId) {
		alexUserService.deleteById(userId);
		return R.ok();
	}
}

package com.alex.top.tally.controller;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public R deleteUserById(Integer userId) {
		alexUserService.deleteById(userId);
		return R.ok();
	}
	
	@RequestMapping("/resetPwd")
	public R resetPwd(String userId){
		AlexUserEntity user = alexUserService.selectById(userId);
		if (null == user) {
			return R.error("该用户不存在, 请刷新页面!");
		}
		user.setPassword(ShiroUtils.sha256("666666", user.getPasswordsalt()));
		alexUserService.updateById(user);
		return R.ok();
	}
	
	@RequestMapping("/password")
	public R password(String password, String newPassword){
		if (password.equals(newPassword)) {
			return R.error("新密码不能和原密码相同");
		}
		AlexUserEntity user = (AlexUserEntity) SecurityUtils.getSubject().getPrincipal();
		//原密码
		password = ShiroUtils.sha256(password, user.getPasswordsalt());
		//新密码
		newPassword = ShiroUtils.sha256(newPassword, user.getPasswordsalt());
				
		//更新密码
		AlexUserEntity userEntity = new AlexUserEntity();
        userEntity.setPassword(newPassword);
        boolean flag = alexUserService.update(userEntity, new EntityWrapper<AlexUserEntity>().eq("id", user.getId()).eq("password", password));
        
		if(!flag){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
}

package com.alex.top.tally.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.alex.top.tally.entity.AlexUserEntity;

/**
 * Shiro工具类
 */
public class ShiroUtils {
	/** 加密算法 */
	public final static String HASH_ALGORITHM_NAME = "SHA-256";
	/** 循环次数 */
	public final static int HASH_ITERATIONS = 16;

	public static String sha256(String password, String salt) {
		return new SimpleHash(HASH_ALGORITHM_NAME, password, salt, HASH_ITERATIONS).toString();
	}

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static AlexUserEntity getUserEntity() {
		return (AlexUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	public static Integer getUserId() {
		return getUserEntity().getId();
	}

	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}

}

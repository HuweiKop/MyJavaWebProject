package com.myproject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * Description: 获取登陆验证码 All Rights Reserved.
 * 
 * @version 1.0 2015年6月1日 下午5:09:25 by 代鹏（daipeng@dangdang.com）创建
 */
public class GetCaptchaProcessor {

	private final static String CAPTCHA_CACHE_PREFIX = "captcha:cache:%s:%s";

	protected Map<String, String> process(String type, String phoneNum) throws Exception {
		Map<String, String> result = new HashMap<>();

		String randomCode = getDDLoginRandomCode();
		SCaptcha instance = new SCaptcha(randomCode);
		// 获取待输入的验证码
		String code = instance.getCode();
		// 待输入的验证码入缓存，登录输入时做校验
		putCode2Cache(type, phoneNum, code);
		List<String> letters = getCodeAndLetters(code);
		String ls = StringUtils.join(letters, ",");

		//ajax无法用流的形式解析图片，只能返回string了
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(instance.getBuffImg(), "png", baos);
		byte [] bytes = baos.toByteArray();
		result.put("img", Base64.encodeBase64String(bytes));
		result.put("codes", ls);

		return result;
	}

	public String getDDLoginRandomCode() {
		return CaptchaUtil.getRandomCode();
	}

	public void putCode2Cache(String type, String phoneNum, String code) {
		String.format(CAPTCHA_CACHE_PREFIX,type, phoneNum);
	}

	public List<String> getCodeAndLetters(String code) {
		return CaptchaUtil.getCodeAndLetters(code);
	}
}

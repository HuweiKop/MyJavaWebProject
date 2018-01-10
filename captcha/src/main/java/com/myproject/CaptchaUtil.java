package com.myproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

/**
 * 验证码工具类 Description: All Rights Reserved.
 * @version 1.0 2015-2-2 下午04:23:54 by 王冠华（wangguanhua@dangdang.com）创建
 */
public class CaptchaUtil {

	// 验证码字符个数
//	private static int codeCount = ConfigReaderUtils.getInteger("captcha.codeCount", 4);
	private static int codeCount = 4;
	
	// 验证码 + 混淆字符个数
//	private static int totalCount = ConfigReaderUtils.getInteger("captcha.letterCount", 12);
	private static int totalCount = 12;

	private static String[] CAPTCHA_CODES = null;

	private static String[] CAPTCHA_LETTERS = null;

	/**
	 * 获取随机验证码
	 * Description: 
	 * @Version1.0 2015-2-2 下午04:42:46 by 王冠华（wangguanhua@dangdang.com）创建
	 * @return
	 */
	public static String getRandomCode() {
		if (CAPTCHA_CODES == null) {
//			LoginService loginService = ApplicationContextUtils.getBean("loginService");
//			SysProperty codeProperty = loginService.getByKey("captcha.codes");
			String codes = "";
			if (StringUtils.isNotBlank(codes)) {
				CAPTCHA_CODES = codes.split(",");
			}
		}
		int length = CAPTCHA_CODES.length;
		int index = (new Random()).nextInt(length);
		String code = CAPTCHA_CODES[index];
		if (code.length() == codeCount) {
			return code;
		}
		return null;
	}

	/**
	 * 获取混淆字符
	 * Description: 
	 * @Version1.0 2015-2-2 下午04:46:25 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param letterCount
	 * @return
	 */
	public static String[] getLetters() {
		if (CAPTCHA_LETTERS == null) {
//			LoginService loginService = ApplicationContextUtils.getBean("loginService");
//			SysProperty codeProperty = loginService.getByKey("captcha.letters");
			String codes = "";
			if (StringUtils.isNotBlank(codes)) {
				CAPTCHA_LETTERS = codes.split(",");
			}
		}
		return CAPTCHA_LETTERS;
	}
	
	/**
	 * 获取验证码与混淆字符的集合
	 * Description: 
	 * @Version1.0 2015-2-2 下午05:40:26 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param code
	 * @return
	 */
	public static List<String> getCodeAndLetters(String code){
		if (StringUtils.isBlank(code) || getLetters() == null) {
			return null;
		}
		
		List<String> letters = new ArrayList<String>();
		char [] cs = code.toCharArray();
		for (int i = 0; i < cs.length; i++) {
			letters.add(String.valueOf(cs[i]));
		}
		
		Random r = new Random();
		while (letters.size() != totalCount) {
			int index = r.nextInt(CAPTCHA_LETTERS.length);
			letters.add(CAPTCHA_LETTERS[index]);
		}
		Collections.shuffle(letters);
		return letters;
	}
	
	/**
	 * 从缓存取出code
	 * Description: 
	 * @Version1.0 2015-2-2 下午03:30:18 by 王冠华（wangguanhua@dangdang.com）创建
	 * @param deviceNo
	 * @return
	 */
	public static String getCodeFromCache(String deviceNo){
//		RedisService redisService = ApplicationContextUtils.getBean("redisService");
//		return redisService.slaveGet(ApiConstant.CAPTCHA_CACHE_PREFIX + deviceNo);
		return null;
	}
	
	public static void clearCaptchaCode(String deviceNo){
//		RedisService redisService = ApplicationContextUtils.getBean("redisService");
//		redisService.masterDeleteByKey(ApiConstant.CAPTCHA_CACHE_PREFIX + deviceNo);
	}
	
	/**
	 * Description: 校验验证码有效性
	 * @Version1.0 2015年6月12日 下午4:51:18 by 代鹏（daipeng@dangdang.com）创建
	 * @param code
	 * @param deviceNo
	 * @return
	 */
	public static boolean checkCaptchaCode(String code, String deviceNo){
		if(StringUtils.isBlank(code) || StringUtils.isBlank(deviceNo)){
			return false;
		}
		String cacheCode = getCodeFromCache(deviceNo);
		if(code.equals(cacheCode)){
			//校验成功，清楚缓存中验证码
			//clearCaptchaCode(deviceNo);
			return true;
		}
		return false;
	}
}

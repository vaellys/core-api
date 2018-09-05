package com.combanc.core.api.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ApiSigner {
	
	/**
	 * 对所有传入参数按照字段名的 ASCII 码从小到大排序
	 * 
	 * @param paraMap
	 * @param keyToLower
	 * @return
	 */
	public static String formatUrlMap(Map<String, Object> paraMap, boolean keyToLower) {
		String buff = "";
		Map<String, Object> tmpMap = paraMap;
		try {
			List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(tmpMap.entrySet());
			// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
			Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
				public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			// 构造URL 键值对的格式
			StringBuilder buf = new StringBuilder();
			for (Map.Entry<String, Object> item : infoIds) {
				if (item.getKey() != null) {
					String key = item.getKey();
					Object val = item.getValue();
					if (keyToLower) {
						buf.append(key.toLowerCase() + "=" + val);
					} else {
						buf.append(key + "=" + val);
					}
					buf.append("&");
				}
			}
			buff = buf.toString();
			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			return null;
		}
		return buff;
	}
	
	/**
	 * 生成签名
	 * @param paramsMap
	 * @param secret
	 * @return
	 * @throws Exception
	 */
	public static String buildSign(Map<String, Object> paramsMap, String secret) throws Exception {
		String signStr = formatUrlMap(paramsMap, false);
		String source = secret + signStr + secret;
		return md5(source);
	}

	public static void main(String[] args) {
		Map<String,Object> paras = new HashMap<String, Object>();
		paras.put("appKey","test");
		paras.put("userName","eng");
		paras.put("passWord","1234");
		String signStr = formatUrlMap(paras, false);
		String source = 123 + signStr + 123;
		String sing = md5(source);
		System.out.println(sing);
	}



	/**
	 * 判断加密字符串
	 * 
	 * @param signStr
	 * @param sign
	 * @return
	 */
	public static boolean signCheck(String signStr, String sign) {
		boolean flag = false;
		signStr = md5(signStr);
		if (signStr.equals(sign)) {
			flag = true;
		}
		return flag;
	}
	
	public static boolean verify(String signStr, String sign) {
		boolean flag = false;
		if (signStr.equals(sign)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * md5加密。
	 * 
	 * @param strInputText
	 * @return
	 */
	public static String md5(String strInputText) {
		return encrypt(strInputText, "md5");
	}

	/**
	 * md5或者sha-1加密。
	 * 
	 * @param strInputText
	 *            要加密的内容
	 * @param strAlgorithmName
	 *            加密算法名称：md5或者sha-1，不区分大小写
	 * @return
	 */
	private static String encrypt(String strInputText, String strAlgorithmName) {
		String strEncryptText = null;
		if (strInputText == null || "".equals(strInputText.trim())) {
			throw new IllegalArgumentException("请输入要加密的内容");
		}
		if (strAlgorithmName == null || "".equals(strAlgorithmName.trim())) {
			strAlgorithmName = "md5";
		}
		try {
			MessageDigest objM = MessageDigest.getInstance(strAlgorithmName);
			byte arrayS[];
			objM.update(strInputText.getBytes("UTF8"));
			arrayS = objM.digest();
			return hex(arrayS);
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return strEncryptText;
	}

	/**
	 * 返回十六进制字符串。
	 * 
	 * @param arrayArr
	 * @return
	 */
	private static String hex(byte[] arrayArr) {
		StringBuffer sbufSb = new StringBuffer();
		for (int i = 0; i < arrayArr.length; ++i) {
			sbufSb.append(Integer.toHexString((arrayArr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sbufSb.toString();
	}
	
}

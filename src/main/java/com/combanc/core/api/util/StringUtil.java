package com.combanc.core.api.util;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils {
	
	private StringUtil() {
	}

	public static boolean checkNullOrEmpty(Object o) {
		if (null == o) {
			return true;
		} else {
			return StringUtils.isBlank(String.valueOf(o)) ? true : false;
		}
	}

}

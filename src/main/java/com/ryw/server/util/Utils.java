package com.ryw.server.util;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

public class Utils {
	
	/**
	 * 数组转字符串，以逗号分隔
	 * @param values
	 * @return
	 */
	public static String Array2String(Object[] values) {
		String result = "";
		if (values == null) {
			return result;
		}
		int length = values.length;
		for (int i = 0; i < length; i++) {
			result += values[i] + ",";
		}
		if (result.endsWith(",")) {
			result = result.substring(result.length() - 1);
		}
		return result;
	}
	
	/**
	 * List集合转字符串，以逗号分隔
	 * @param values
	 * @return
	 */
	public static String List2String(List<Object> values) {
		String result = "";
		if (values == null) {
			return result;
		}
		int size = values.size();
		for (int i = 0; i < size; i++) {
			result += values.get(i).toString() + ",";
		}
		if (result.endsWith(",")) {
			result = result.substring(result.length() - 1);
		}
		return result;
	}
	
	/**
	 * 数组转List集合
	 * @param values
	 * @return
	 */
	public final static List Array2List(Object[] values) {
		List list = Lists.newArrayList();
		if (null != values && values.length > 0) {
			list = Arrays.asList(values);
		}
		return list;
	}

}

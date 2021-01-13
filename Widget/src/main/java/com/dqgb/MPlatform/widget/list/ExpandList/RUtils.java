package com.dqgb.MPlatform.widget.list.ExpandList;

import java.lang.reflect.Field;

/**
 * @Description:
 * @author: ragkan
 * @time: 2016/11/1 14:38
 */
public  class RUtils {

	public static int getId(String name, Class<?> cls) {
		try {
			Field f = cls.getDeclaredField(name);
			f.setAccessible(true);
			return f.getInt(cls);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

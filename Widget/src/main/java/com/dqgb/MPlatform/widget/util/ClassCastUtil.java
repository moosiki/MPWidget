package com.dqgb.MPlatform.widget.util;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.lang.reflect.Field;


/**
 * @author : yangqiang
 * @Description: 使用反射复制对象（子类对象到父类，及父类对象复制到子类对象）
 * @Date: 10:06 2019/6/28
 */
public class ClassCastUtil {
	private ClassCastUtil() {
	}

    /**
     * 父类对象复制到子类对象
     * @param father 父类对象
     * @param child 子类对象
     * @param <T> 泛型
     */
	public static <T>void fatherToChild(T father, T child, Context context)  {
        if (child.getClass().getSuperclass() != father.getClass()) {
            LogUtils.e("类型错误,对象没有继承关系");
            ToastUtils.showLong("数据设置错误：类型错误,对象没有继承关系");
            return;
        }
        Class<?> fatherClass = father.getClass();
        
        Field[] declaredFields = fatherClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (!"serialVersionUID".equals(declaredField.getName())) {
				try {
                    declaredField.setAccessible(true);
                    declaredField.set(child, declaredField.get(father));        //父类对象字段赋值给子类对象
				} catch (IllegalAccessException e) {
                    LogUtils.e(e.getMessage());
				} catch (IllegalArgumentException e) {
                    LogUtils.e("不合法参数：" + e.getMessage());
				}
            }
        }
    }

    /**
     * 子类对象复制到父类对象
     * @param father 父类对象
     * @param child 子类对象
     * @param <T> 泛型
     */
    public static <T>void childToFather(T child,T father, Context context)  {
        if (child.getClass().getSuperclass() != father.getClass()) {
            LogUtils.e("类型错误,对象没有继承关系");
            ToastUtils.showLong("数据设置错误：类型错误,对象没有继承关系");
            return;
        }
        Class<?> fatherClass = father.getClass();
        Field[] declaredFields = fatherClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String fieldName = declaredField.getName();
            if (!"serialVersionUID".equals(fieldName)) {
                try {
                    declaredField.setAccessible(true);
                    declaredField.set(father, declaredField.get(child));        //子类对象字段赋值给父类对象
                } catch (IllegalAccessException e) {
                    LogUtils.e(e.getMessage());
                } catch (IllegalArgumentException e) {
                    LogUtils.e("不合法参数：" + e.getMessage());
                }
            }
        }
    }
    /**
     * 首字母大写，in:deleteDate，out:DeleteDate
     */
    private static String upperHeadChar(String in) {
        String head = in.substring(0, 1);
        return head.toUpperCase() + in.substring(1, in.length());
    }
    public static <T extends Object>T copyFather(T child){
        return child;
    }
}
package com.dqgb.MPlatform.widget.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.dqgb.MPlatform.widget.common.CommonModule;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 *
 * @Description:    视图与数据进行双向绑定的工具类
 * @author:         yangqiang-ds
 * @date:           2020/4/10 11:49
 * @Version:        1.0
 */
public class ViewModelUtil {

    /**
     * 将控件数据注入到数据model容器中
     * 使用反射获取model私有属性，控件在xml文件中使用“module_bind_field”绑定model的其中一个属性，
     * 然后在此方法中使用反射将控件的值设置到该属性中
     * 当前版本待改进的地方：1.一个控件对应多个属性暂未处理
     * @param model 需要设值的对象
     * @param container 包含某页面所有可编辑控件的父级容器
     */
    public static void injectValue(Object model, ViewGroup container, Context context){
        View childWidget;                                                                       //控件
        String  modelClassName = model.getClass().getSimpleName();                              //数据model容器的类名称
        Set<Field> fields = new HashSet<Field>(Arrays.asList(model.getClass().getDeclaredFields()));    //获取需要注入数据的对象的所有私有属性
        Map<String ,Field> fieldMap= new HashMap<>();
        for (Field field : fields) {
            fieldMap.put(modelClassName + "." + field.getName(), field);                            //私有属性放入map，方便后面设值
        }
        for (int i = 0; i < container.getChildCount(); i++) {                                       //遍历container下的所有控件
            childWidget = container.getChildAt(i);
            if (childWidget instanceof CommonModule){
                String bindField = ((CommonModule)childWidget).getBindField();                      //获取控件绑定的属性
                Field field = fieldMap.get(bindField);                                              //在map找到该属性
                if (field != null) {
                    try {
                        field.setAccessible(true);
                        field.set(model, ((CommonModule) childWidget).getData());                   //使用反射设置数据
                    } catch (Exception e) {
                        ToastUtils.showLong("数据设置异常：" + e.getMessage());
                    }
                }
            }
        }
    }

    /**
     * 刷新页面数据，将后台获取的数据绑定设置到对应的控件中
     * 使用反射获取数据的所有字段属性，控件在xml文件中使用“module_value_field”绑定数据的其中一个属性，
     * 在此方法中使用反射将数据对应字段的值设置到控件中
     * 存在的问题：需要使用多个字段对某一控件设置的情况暂未处理
     * @param data 后台获取的数据
     * @param container 包含某页面某一组控件的父级容器
     * @param context 上下文
     */
    public static void refreshView(Object data, ViewGroup container, Context context){
        View childWidget;
        String  modelClassName = data.getClass().getSimpleName();
        Set<Field> fields = new HashSet<Field>(Arrays.asList(data.getClass().getDeclaredFields()));
        if (data.getClass().getSuperclass() != null) {
            fields.addAll(Arrays.asList(data.getClass().getSuperclass().getDeclaredFields()));
        }
        Map<String ,Field> fieldMap= new HashMap<>();
        for (Field field : fields) {
            fieldMap.put(field.getName(), field);
        }
        for (int i = 0; i < container.getChildCount(); i++) {
            childWidget = container.getChildAt(i);
            if (childWidget instanceof CommonModule){
                String[] bindFields =  ((CommonModule)childWidget).getValueField().split("\\.");            //使用“.”分割字段属性
                if (bindFields.length != 0){
                    setValue(0, bindFields, fieldMap.get(bindFields[0]), childWidget, data, context);       //递归设值
                }
            }
        }
    }

    /**
     * 递归设置控件值
     * @param index 设置绑定字段的编号
     * @param bindFields 绑定字段数组
     * @param valueField 设置数据的字段
     * @param childWidget 控件
     * @param data 数据
     * @param context 上下文
     */
    public static void setValue(int index, String[] bindFields, Field valueField, View childWidget, Object data, Context context){
        if (valueField != null){
            try {
                if (bindFields.length == index + 1){                    //最后一位绑定字段
                    valueField.setAccessible(true);
                    ((CommonModule) childWidget).setData(valueField.get(data));
                }else {                                                 //不是最后一位绑定字段，继续递归
                        valueField.setAccessible(true);
                        Object value = valueField.get(data);           //获取当前字段的数据
                        Field field = value.getClass().getDeclaredField(bindFields[index + 1]);
                        setValue(index + 1, bindFields, field, childWidget, value, context);    //递归
                }
            } catch (Exception e) {
                ToastUtils.showLong("【" + ((CommonModule) childWidget).getModuleTitle() + "】控件数据设置异常：" + e.getMessage());
            }
        }
    }
}

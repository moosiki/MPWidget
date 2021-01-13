package com.dqgb.MPlatform.widget.core;

import android.view.View;

/**
 * 交互型组件基类
 * @author Mryang
 * @Date 2021/1/11
 */
public interface ActiveModule {
    /**
     * 获取布局资源
     */
    int getInflateResource();

    /**
     * 设置是否可编辑
     * @param editable 是否可编辑
     */
    void setEnable(boolean editable);

    /**
     * 初始化控件
     */
    void initModule();

    /**
     * 此项是否必填
     * @return 此项是否必填
     */
    boolean isRequired();

    /**
     * 用户录入信息是否正确
     * @return 录入正确返回null，
     * 录入有误返回对应错误组件视图（一般返回自身）
     */
    View isValuesRight();

    /**
     * 输入内容错误时执行的操作
     */
    void onValueError();

    /**
     * 输入内容正确时执行的操作
     */
    void onValueRight();

    /**
     * 获取控件数据
     * @return 控件数据
     */
    Object getData();

    /**
     * 设置控件数据
     * @param data 控件数据
     */
    void setData(Object data);
}

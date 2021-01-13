package com.dqgb.MPlatform.widget.core;

import android.view.View;

/**
 * 交互型控件装饰器基类
 * @author yangqiang-ds
 * @version 1.0
 * @date：2021/1/11 14:38
 */
public abstract class BaseDecorate<T extends ActiveModule> implements ActiveModule {

    protected T mModule;

    public BaseDecorate(T activeModule) {
        this.mModule = activeModule;
    }

    /**
     * 获取布局资源
     */
    @Override
    public int getInflateResource() {
        return mModule.getInflateResource();
    }

    /**
     * 设置是否可编辑
     *
     * @param editable 是否可编辑
     */
    @Override
    public void setEnable(boolean editable) {
        mModule.setEnable(editable);
    }

    /**
     * 初始化控件
     */
    @Override
    public void initModule() {
        mModule.initModule();
        decorateModule();
    }

    /**
     * 装饰控件
     */
    protected abstract void decorateModule();



    /**
     * 此项是否必填
     *
     * @return 此项是否必填
     */
    @Override
    public boolean isRequired() {
        return mModule.isRequired();
    }

    /**
     * 用户录入信息是否正确
     *
     * @return 录入正确返回null，
     * 录入有误返回对应错误组件视图（一般返回自身）
     */
    @Override
    public View isValuesRight() {
        return mModule.isValuesRight();
    }

    /**
     * 输入内容错误时执行的操作
     */
    @Override
    public void onValueError() {
        mModule.onValueError();
    }

    /**
     * 输入内容正确时执行的操作
     */
    @Override
    public void onValueRight() {
        mModule.isValuesRight();
    }

    /**
     * 获取控件数据
     *
     * @return 控件数据
     */
    @Override
    public Object getData() {
        return mModule.getData();
    }

    /**
     * 设置控件数据
     *
     * @param data 控件数据
     */
    @Override
    public void setData(Object data) {
        mModule.setData(data);
    }
}

package com.dqgb.MPlatform.widget.core;

/**
 * 展示型组件基类
 */
public interface DisplayModule extends Module{
    /**
     * 获取布局资源
     */
    int getInflateResource();

    /**
     * 渲染视图
     */
    void inflateView();

    /**
     * 初始化控件
     */
    void initModule();

}

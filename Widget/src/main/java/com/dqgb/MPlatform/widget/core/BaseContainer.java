package com.dqgb.MPlatform.widget.core;

import android.widget.ScrollView;

/**
 * 组件容器，包裹组件，实现基础组件操作
 * @author yangqiang-ds
 * @version 1.0
 * @date：2021/1/13 17:25
 */
public interface BaseContainer<T extends Module>{
    T getModule();
}

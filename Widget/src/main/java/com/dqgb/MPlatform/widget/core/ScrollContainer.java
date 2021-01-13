package com.dqgb.MPlatform.widget.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * 可滑动的组件容器
 * @author yangqiang-ds
 * @version 1.0
 * @date：2021/1/13 17:39
 */
public class ScrollContainer extends ScrollView implements BaseContainer{

    public static final String SCROLL_POSITION_TOP = "TOP";
    public static final String SCROLL_POSITION_CENTER = "CENTER";
    public static final String SCROLL_POSITION_BOTTOM = "BOTTOM";

    public ScrollContainer(Context context) {
        super(context);
    }

    public ScrollContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Module getModule() {
        return null;
    }

    /**
     * 滑动控件到屏幕指定位置
     * @param module 控件
     * @param position 位置，顶部，居中，底部
     */
    public void scrollModule(final Module module, String position) {
        int offSetY = 0;
        View module1;
        if (module !=null && module instanceof View) {
            module1 = (View) module;
        }else {
            return;
        }
        switch (position){
            case SCROLL_POSITION_TOP:
                offSetY = module1.getHeight() > getHeight() ? getHeight()  : module1.getHeight();
                break;
            case SCROLL_POSITION_CENTER:
                offSetY = module1.getHeight() > getHeight()  / 2 ? getHeight() / 2 : module1.getHeight() / 2;
                break;
            case SCROLL_POSITION_BOTTOM:
                offSetY = module1.getHeight() > getHeight() ? module1.getHeight() : getHeight();
                break;
        }
        int[] location = new int[2];
        module1.getLocationInWindow(location);
        int viewposy = location[1] - getHeight() / 2;
        viewposy += offSetY;
        getLocationInWindow(location);
        viewposy -= location[1];
        viewposy += getScrollY();
        smoothScrollTo(0, viewposy);
    }

}

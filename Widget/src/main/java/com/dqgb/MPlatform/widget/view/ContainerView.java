package com.dqgb.MPlatform.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.dqgb.MPlatform.widget.core.DisplayModule;

public abstract class ContainerView extends ViewGroup implements DisplayModule {
    public ContainerView(Context context) {
        super(context);
    }

    public ContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置布局参数
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}

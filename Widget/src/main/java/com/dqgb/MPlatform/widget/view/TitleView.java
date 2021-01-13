package com.dqgb.MPlatform.widget.view;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.dqgb.MPlatform.widget.core.DisplayModule;

import butterknife.ButterKnife;

public  abstract  class TitleView extends GridLayout implements DisplayModule {
    //组件视图
    protected View mView;
    //是否必填
    protected boolean required = false;
    //组件标题
    protected String  title = "";

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateView();
        initModule();
    }

    public TitleView(Context context) {
        super(context);
    }

    @Override
    public void inflateView() {
        mView = LayoutInflater.from(getContext()).inflate(getInflateResource(), this);
        ButterKnife.bind(mView);
    }


    /**
     * 此项是否必填
     * @return 此项是否必填
     */
    public boolean isRequired(){
        return required;
    }
}

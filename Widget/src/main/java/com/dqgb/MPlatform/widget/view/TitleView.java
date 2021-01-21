package com.dqgb.MPlatform.widget.view;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dqgb.MPlatform.widget.core.DisplayModule;

import butterknife.ButterKnife;

public  abstract  class TitleView extends LinearLayout implements DisplayModule {
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

/*    *//**
     * 计算所有子布局的宽度和高度，然后根据childView的计算结果，设置自己的宽度和高度
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     *//*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取此viewGroup上上级容器为其推荐的宽和高，以及计算模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        //计算所有的子布局的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //

    }*/




    /**
     * 此项是否必填
     * @return 此项是否必填
     */
    public boolean isRequired(){
        return required;
    }
}

package com.dqgb.MPlatform.widget.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.dqgb.MPlatform.widget.R;

import butterknife.ButterKnife;

/**
 * 交互组件基础实现类
 * @author yangqiang-ds
 * @version 1.0
 * @date：2021/1/13 14:48
 */
public abstract class BaseActModule extends LinearLayout implements ActiveModule {
    //组件视图
    protected View mView;
    //是否必填
    protected boolean required = false;
    //组件标题
    protected String  moduleTitle = "";


    public BaseActModule(Context context) {
        super(context);
    }

    public BaseActModule(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        applyAttrs(context,attrs);
    }

    public BaseActModule(ModuleBuilder builder, Context context) {
        super(context);
        this.required = builder.required;
        this.moduleTitle = builder.moduleTitle;
    }

    public void applyAttrs(Context context, @Nullable AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.common_module_option);
        required = typedArray.getBoolean(R.styleable.common_module_option_required, false);
        moduleTitle = typedArray.getString(R.styleable.common_module_option_module_title);
    }

    /**
     * 渲染视图
     */
    @Override
    public void inflateView() {
        mView = LayoutInflater.from(getContext()).inflate(getInflateResource(), this);
        ButterKnife.bind(mView);
    }

    /**
     * 此项是否必填
     *
     * @return 此项是否必填
     */
    @Override
    public boolean isRequired() {
        return required;
    }


    /**.
     * 基础交互组件构建器
     * @author: Mryang
     * @Date : 2021/1/13
     */
    public static class ModuleBuilder{
        //上下文
        protected  Context mContext;
        //是否必填
        protected boolean required = false;
        //组件标题
        protected String  moduleTitle = "";

        public ModuleBuilder(Context mContext) {
            this.mContext = mContext;
        }

        //构建基础控件
      /*  public BaseActModule build(){
            return new BaseActModule(this, mContext);
        }*/

        public ModuleBuilder required(boolean required) {
            this.required = required;
            return this;
        }

        public ModuleBuilder moduleTitle(String moduleTitle) {
            this.moduleTitle = moduleTitle;
            return this;
        }
    }
}

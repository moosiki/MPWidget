package com.dqgb.MPlatform.widget.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.dqgb.MPlatform.widget.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 信息组件组件父类
 *
 * @author yangqiang-ds
 * @date 2019-7-15
 */
public abstract class CommonModule<T> extends LinearLayout implements View.OnClickListener{

    ViewGroup rootLayout;    //根布局

    protected TextView titleView;         //标题控件

    List<T> values;             //组件数据
    Context context;

    boolean isChanged = false;
    boolean required = false;       //是否必填
    protected String  errorTips = "";       //组件输入错误时的提示信息
    protected String  moduleTitle = "";       //组件标题
    protected float textSize;       //自定义字体大小
    protected int textColor;        //自定义字体颜色
    protected String  hint = "";       //提示信息
    protected int  hintColor;       //提示信息字体颜色
    protected int  textIcon;       //自定义右侧图标
    protected String  bindField = "";       //绑定的数据实体类字段
    protected String  valueField = "";       //绑定的设置数据的vo类字段

    public CommonModule(Context context) {
        super(context);
        this.context = context;
    }

    public CommonModule(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(getInflateResource(), this);
        ButterKnife.bind(view);
    //    setOnClickListener(this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.common_module_option);
        required = typedArray.getBoolean(R.styleable.common_module_option_required, false);
        errorTips = typedArray.getString(R.styleable.common_module_option_error_tips);
        moduleTitle = typedArray.getString(R.styleable.common_module_option_module_title);
        textSize=typedArray.getDimension(R.styleable.common_module_option_module_text_size,getContext().getResources().getDimension(R.dimen.font_16));
        textColor=typedArray.getColor(R.styleable.common_module_option_module_text_color, getContext().getResources().getColor(R.color.black_85));
        textIcon=typedArray.getResourceId(R.styleable.common_module_option_text_icon, R.drawable.svg_icon_arrows_right);
        hint = typedArray.getString(R.styleable.common_module_option_module_hint);
        hintColor=typedArray.getColor(R.styleable.common_module_option_module_hint_color, getContext().getResources().getColor(R.color.black_65));
        bindField = typedArray.getString(R.styleable.common_module_option_module_bind_field);
        valueField = typedArray.getString(R.styleable.common_module_option_module_value_field);
        setTitleView();
        setRequiredView();
        setTitle(moduleTitle);
    }

    /**
     * 设置必填控件的提示图标（*号）
     */
    private void setRequiredView() {
        View requiredView = findViewById(getRequirdViewResource());
        if (requiredView != null){
            requiredView.setVisibility(isRequired()?VISIBLE:INVISIBLE);
        }
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        if (titleView != null && !"".equals(title))
            titleView.setText(title);
    }


    @Override
    public void onClick(View v) {
        /*if (context instanceof FormFastFillView){
            if (this instanceof BottomDialogWidget) {
                ((FormFastFillView) context).openBottomDialog((BottomDialogWidget)this);
            }
        }*/
    }

    /**
     * 获取布局资源
     */
    protected abstract int getInflateResource();

    /**
     * 获取必填图标视图的布局资源
     */
    protected abstract int getRequirdViewResource();


    /**
     * 获取标题控件id
     */
    protected abstract int getTitleResource();

    /**
     * @return 用户录入信息是否正确
     */
    public abstract View isValuesRight();

    /**
     * 设置是否可编辑
     */
    public abstract void setEditable(boolean editable);

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public void setTitleView() {
        titleView = findViewById(getTitleResource());
    }

    public List<T> getValues() {
        return values;
    }


    public void setValues(List<T> values) {
        this.values = (values == null ? new ArrayList<T>() : values);
    }

    public List<T> getChangedValue() {
        if (isChanged()) {
            return this.values;
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 获取控件数据，用于更新后台数据
     * @return 控件数据
     */
    public Object getData(){
        return null;
    }

    /**
     * 设置控件数据,将后台获取的数据设置到控件上展示
     */
    public void setData(Object data){
    }


    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getErrorTips() {
        return errorTips;
    }

    public void setErrorTips(String errorTips) {
        this.errorTips = errorTips;
    }

    public String getBindField() {
        return bindField;
    }

    public String getValueField() {
        return valueField;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }
}

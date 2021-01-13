package com.dqgb.MPlatform.widget.core;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.dqgb.MPlatform.widget.R;
import com.dqgb.MPlatform.widget.group.SubOberver;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 输入型组件
 */
public  abstract class InputModule extends LinearLayout implements ActiveModule{

    protected List<SubOberver> obervers;

    EditText mEditText;

    boolean required = true;       //是否必填

    public InputModule(Context context,boolean NotInit) {
        super(context);
    }

    public InputModule(Context context) {
        super(context);
        initModule();
    }

    public InputModule(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initModule();
    }

    @Override
    public void initModule() {
//        List<String > d = new ArrayList<>();
//        d.forEach(dg -> System.out.println(dg));
//        new Predicate<>()
//        d.stream().filter()
        View view = LayoutInflater.from(getContext()).inflate(getInflateResource(), this);
        mEditText = getEditWidget();
        mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ToastUtils.showLong("编辑框被点击：" + mEditText.getId());
            }
        });
        decorateEdit();
    }

    /**
     * 装饰输入框控件
     */
    protected abstract void decorateEdit();


    @Override
    public boolean isRequired() {
        return required;
    }

    public abstract EditText getEditWidget();

    @Override
    public void setEnable(boolean editable) {

    }

    /**
     * 用户录入信息是否正确
     *
     * @return 录入正确返回null，
     * 录入有误返回对应错误组件视图（一般返回自身）
     */
    @Override
    public View isValuesRight() {
        if (isRequired() && "".equals(getData())) {
            ToastUtils.showLong("此项必填");
            return this;
        }
        return null;
    }

    @Override
    public Object getData() {
        return mEditText.getText().toString();
    }

    /**
     * 设置控件数据
     */
    @Override
    public void setData(Object data) {
        mEditText.setText((String) data);
    }

    /**
     * 手动打开软键盘
     */
    public void clickOpenSoft() {
        InputMethodManager inputManager =
                (InputMethodManager) mEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(mEditText, 0);
    }

    @Override
    public void onValueError() {
        ToastUtils.showShort("请先输入此内容");
        mEditText.requestFocusFromTouch();
        mEditText.requestFocus();
        clickOpenSoft();
        mEditText.setBackgroundResource(R.color.cc_red);
    }

    @Override
    public void onValueRight() {
        mEditText.setBackground(null);
    }

    public void putObserver(SubOberver oberver){
        if (obervers == null){
            obervers = new ArrayList<>();
        }
        this.obervers.add(oberver);
    }
}

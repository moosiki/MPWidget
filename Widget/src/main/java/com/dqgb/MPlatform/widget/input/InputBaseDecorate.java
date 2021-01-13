package com.dqgb.MPlatform.widget.input;

import android.content.Context;
import android.widget.EditText;

import com.dqgb.MPlatform.widget.core.InputModule;


/**
 * 输入控件装饰器基类
 * @author: yangqiang-ds
 * @Date : 2021/1/11
 */
public abstract class InputBaseDecorate extends InputModule {

    /**
     * 持有被装饰的输入控件
     */
    protected InputModule mInputModule;

    public InputBaseDecorate(Context context, InputModule inputModule) {
        super(context,false);
        this.mInputModule = inputModule;
        initModule();
    }

    /**
     * 装饰输入框控件
     */
    @Override
    protected abstract void decorateEdit();

    @Override
    public EditText getEditWidget() {
        return mInputModule.getEditWidget();
    }

    /**
     * 获取布局资源
     */
    @Override
    public int getInflateResource() {
        return mInputModule.getInflateResource();
    }
}

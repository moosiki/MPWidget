package com.dqgb.MPlatform.widget.input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.dqgb.MPlatform.widget.R;
import com.dqgb.MPlatform.widget.core.InputModule;
import com.dqgb.MPlatform.widget.group.PriorObservable;
import com.dqgb.MPlatform.widget.group.SubOberver;

/**
 * 横向输入框
 */
public class InputRowModule extends InputModule implements PriorObservable<InputRowModule> {


    public InputRowModule(Context context) {
        super(context);
    }

    public InputRowModule(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 获取布局资源
     */
    @Override
    public int getInflateResource() {
        return R.layout.widget_edit;
    }

    @Override
    public void setEnable(boolean editable) {

    }

    @Override
    public EditText getEditWidget() {
        return findViewById(R.id.editText);
    }

    @Override
    public void initModule() {
        super.initModule();
        getEditWidget().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateInput();
                updateObservers();
            }
        });
    }

    /**
     * 装饰输入框控件
     */
    @Override
    protected void decorateEdit() {

    }

    private void validateInput() {
        if (isValuesRight() == null){
            onValueRight();
        }
    }

    public void updateObservers(){
        if (obervers != null) {
            for (SubOberver oberver : obervers) {
                oberver.update(this, isValuesRight() == null);
            }
        }
    }

    @Override
    public void addObserver(SubOberver oberver) {
        putObserver(oberver);
    }

    @Override
    public void removeSubObserver(SubOberver oberver) {
    }
}

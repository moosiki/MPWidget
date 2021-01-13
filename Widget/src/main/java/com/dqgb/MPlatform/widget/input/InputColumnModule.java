package com.dqgb.MPlatform.widget.input;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.CollectionUtils;
import com.dqgb.MPlatform.widget.R;
import com.dqgb.MPlatform.widget.core.InputModule;
import com.dqgb.MPlatform.widget.group.PriorObservable;
import com.dqgb.MPlatform.widget.group.SubOberver;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 纵向输入框
 */
public class InputColumnModule extends InputModule implements SubOberver {


    private Map<PriorObservable, Boolean> priorMap;

    public InputColumnModule(Context context) {
        super(context);
    }

    public InputColumnModule(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 获取布局资源
     */
    @Override
    public int getInflateResource() {
        return R.layout.widget_edit_multy_line;
    }

    @Override
    public EditText getEditWidget() {
        return findViewById(R.id.editText);
    }

    @Override
    public void initModule() {
        super.initModule();
      /*  getEditWidget().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                validatePriors();
            }
        });*/
        getEditWidget().setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    validatePriors();
                }
            }
        });
    }

    /**
     * 装饰输入框控件
     */
    @Override
    protected void decorateEdit() {

    }

    private void validatePriors() {
        if (priorMap != null) {
            for (Map.Entry<PriorObservable, Boolean> entry : priorMap.entrySet()) {
                if (!entry.getValue()) {
                    entry.getKey().getCurrenWidget().onValueError();
                    break;
                }
            }
        }
    }

    @Override
    public void update(PriorObservable prior, boolean inputCorrect) {
        this.priorMap.put(prior, inputCorrect);
        setData(prior.currentValue());
    }

    @Override
    public void addPrior(PriorObservable prior) {
        if (priorMap == null){
            priorMap = new LinkedHashMap<>();
        }
        this.priorMap.put(prior, false);
    }
}

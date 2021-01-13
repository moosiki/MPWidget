package com.dqgb.MPlatform.widget.input;

import android.content.Context;
import android.view.View;

import com.dqgb.MPlatform.widget.core.ActiveModule;
import com.dqgb.MPlatform.widget.core.InputModule;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 输入框装饰器，装饰后输入框控件可与其它控件联动
 * @author yangqiang-ds
 * @version 1.0
 * @date：2021/1/11 14:07
 */
public class InputPriorDecorate extends InputBaseDecorate{


    public InputPriorDecorate(Context context, InputModule inputModule) {
        super(context, inputModule);
    }

    public InputPriorDecorate(Context context, InputModule mModule, WeakReference<InputModule>... validateModules) {
        super(context, mModule);
        priorModules.addAll(Arrays.asList(validateModules));
    }

    /**
     * 装饰输入框控件
     */
    @Override
    protected void decorateEdit() {

        getEditWidget().setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                checkPriorModule();
            }
        });


    }

    //需要进行前置验证的控件（弱引用，防止泄露）
    private final List<WeakReference<InputModule>> priorModules = new ArrayList<>();


    /**
     * 验证有关联的前置控件是否完成输入
     */
    private void checkPriorModule(){
        for (WeakReference<InputModule> priorModule : priorModules) {
            if (priorModule.get() != null){
                if (priorModule.get().isValuesRight() != null){
                    priorModule.get().onValueError();
                    return;
                }
            }
        }
    }

}

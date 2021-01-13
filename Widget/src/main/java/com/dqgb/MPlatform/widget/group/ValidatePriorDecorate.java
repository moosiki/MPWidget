package com.dqgb.MPlatform.widget.group;

import android.view.View;

import com.dqgb.MPlatform.widget.core.ActiveModule;
import com.dqgb.MPlatform.widget.core.BaseDecorate;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 验证有关联的前置控件是否完成输入
 * @author yangqiang-ds
 * @version 1.0
 * @date：2021/1/11 14:46
 */

public class ValidatePriorDecorate extends BaseDecorate<ActiveModule> {

    //需要进行前置验证的控件（弱引用，防止泄露）
    private final List<WeakReference<ActiveModule>> priorModules = new ArrayList<>();
    public ValidatePriorDecorate(ActiveModule mModule, WeakReference<ActiveModule>... validateModules) {
        super(mModule);
        priorModules.addAll(Arrays.asList(validateModules));
    }

    /**
     * 装饰控件，进行输入前置验证
     */
    @Override
    protected void decorateModule() {
        if (mModule instanceof View){
            ((View) mModule).setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus){
                    checkPriorModule();
                }
            });
        }

    }

    /**
     * 验证有关联的前置控件是否完成输入
     */
    private void checkPriorModule(){
        for (WeakReference<ActiveModule> priorModule : priorModules) {
            if (priorModule.get() != null){
                if (priorModule.get().isValuesRight() != null){
                    priorModule.get().onValueError();
                    return;
                }
            }
        }
    }

}

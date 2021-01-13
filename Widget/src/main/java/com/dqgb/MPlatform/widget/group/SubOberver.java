package com.dqgb.MPlatform.widget.group;

/**
 * 依赖于其它组件，使用观察者模式进行交互
 */
public interface SubOberver {
    void update(PriorObservable prior, boolean inputCorrect);

    void addPrior(PriorObservable prior);

}

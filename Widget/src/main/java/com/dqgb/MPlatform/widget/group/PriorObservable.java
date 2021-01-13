package com.dqgb.MPlatform.widget.group;


import com.dqgb.MPlatform.widget.core.ActiveModule;

/**
 * 被其它组件所依赖的组件，被观察者
 */
public interface PriorObservable<T extends ActiveModule> {

    default void addSubObserver(SubOberver oberver){
        addObserver(oberver);
        oberver.addPrior(this);
    };

    void addObserver(SubOberver oberver);
    void removeSubObserver(SubOberver oberver);

    default ActiveModule getCurrenWidget(){
        return (T)this;
    }

    default Object currentValue(){
        return ((T)this).getData();
    };
}

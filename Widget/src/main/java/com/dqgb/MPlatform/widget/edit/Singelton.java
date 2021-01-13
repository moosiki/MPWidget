package com.dqgb.MPlatform.widget.edit;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 改进的枚举类实现单例，封装了枚举类
 *
 * 调用：Singleton singleton = SingeltonFactory.getInstance();
 */
public class Singelton{

    private enum SingletonEnum {
        factory;
        private Singleton singleton;

        SingletonEnum() {
            singleton = new Singleton();
        }

        //枚举类的构造方法在类加载时被实例化
        public Singleton getInstance() {
            List list = new ArrayList();
            list.add(5);
            return singleton;
        }
    }

    public static Singleton getInstance(){
        return SingletonEnum.factory.getInstance();
    }

    public static void main(String[] args){
        int i = 888;
        System.out.println(i);
    }
}

//需要获实现单例的类，比如数据库连接Connection
class Singleton{
    public Singleton() {
    }
}

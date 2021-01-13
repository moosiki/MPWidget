package com.dqgb.MPlatform.widget.list.treeview.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 树形列表节点类 label注解 标记model中父id的字段
 *
 * @author yangqiang-ds
 * @date 2019/12/21
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TreeParentId {
}

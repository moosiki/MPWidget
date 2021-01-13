package com.dqgb.MPlatform.widget.list.treeview.util;

import android.content.Context;

import com.dqgb.MPlatform.widget.list.treeview.annotation.TreeLabel;
import com.dqgb.MPlatform.widget.list.treeview.annotation.TreeNodeId;
import com.dqgb.MPlatform.widget.list.treeview.annotation.TreeParentId;
import com.dqgb.MPlatform.widget.list.treeview.holder.SelectableItemHolderT;
import com.dqgb.MPlatform.widget.list.treeview.model.TreeNodeT;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 树形列表帮助类
 *
 * @author yangqiang-ds
 * @date 2019/12/21
 */
public class TreeHelper {

    /**
     * 将数据转换为节点数据
     * @param <T>   数据类型
     * @return  节点数据
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static <T> List<TreeNodeT> convertData2Node(List<T> datas) throws
            IllegalArgumentException, IllegalAccessException {
        List<TreeNodeT> result = new ArrayList<>();
        TreeNodeT node = null;

        //利用注解和反射将数据转换到node中
        for (T datum : datas) {
            String id = null;
            String pId = null;
            Object data = null;
            Class<? extends Object> clazz = datum.getClass();
            Field[] declaredField = clazz.getDeclaredFields();
            for (Field f : declaredField) {
                if (f.getAnnotation(TreeNodeId.class) != null){
                    f.setAccessible(true);
                    id = (String)f.get(datum);
                }
                if (f.getAnnotation(TreeParentId.class) != null){
                    f.setAccessible(true);
                    pId = (String)f.get(datum);
                }
                if (f.getAnnotation(TreeLabel.class) != null){
                    f.setAccessible(true);
                    data = f.get(datum);
                }
                if (id != null && pId != null && data != null){
                    break;
                }
            }
            node = new TreeNodeT(id, pId, data);
            result.add(node);
        }

        //设置节点的父子级关系
        for (int i = 0; i < result.size(); i++) {
            TreeNodeT n = result.get(i);
            for (int j = i + 1; j < result.size(); j++) {
                TreeNodeT m = result.get(j);
                if (n.getId().equals(m.getmParentId())){
                    n.getChildren().add(m);
                    m.setmParent(n);
                }else if (m.getId().equals(n.getmParentId())){
                    m.getChildren().add(n);
                    n.setmParent(m);
                }
            }
        }
        return result;
    }

    /**
     * 获取根节点
     * @param nodes
     * @return
     */
    public static List<TreeNodeT> getRootNodes(List<TreeNodeT> nodes){
        List<TreeNodeT> root = new ArrayList<>();
        for (TreeNodeT node : nodes) {
            if (node.isRoot()){
                root.add(node);
            }
        }
        return root;
    }

    /**
     * 设置可被勾选的层级
     * @param nodes 节点
     * @param enablelevel   可被勾选的最高层级，该层级以下的节点可被勾选，如果为-1，则只有叶子节点可被勾选
     */
    public static void setEnableSelectLevel(List<TreeNodeT> nodes, int enablelevel){
        for (TreeNodeT node : nodes) {
            if (enablelevel == -1){
                node.setSelectable(node.getChildren().size() == 0);
            }else {
                node.setSelectable(node.getLevel() >= enablelevel);
            }
        }
    }

    /**
     * 设置勾选当前节点后是否勾选子节点
     * @param nodes 节点
     * @param isSelectChildren   是否勾选子节点
     */
    public static void setIsSelectChildren(List<TreeNodeT> nodes, boolean isSelectChildren){
        for (TreeNodeT node : nodes) {
           node.getViewHolder().setSelectChildren(isSelectChildren);
        }
    }

    /**
     * 设置是否启用多选
     * @param nodes 节点
     * @param isMultySelect   是否启用多选
     */
    public static void setIsMultySelect(List<TreeNodeT> nodes, boolean isMultySelect){
        for (TreeNodeT node : nodes) {
            node.getViewHolder().setMultySelect(isMultySelect);
        }
    }


    public static void setViewHolders(List<TreeNodeT> nodes, List<Class<? extends TreeNodeT.BaseNodeViewHolder>> holderClazzs, Context context){
        if (holderClazzs == null || holderClazzs.size() == 0){
            setSameHoder(nodes, context);
        }else{
            setHolderByLevel(nodes, holderClazzs, context);
        }
    }

    /**
     * 所有节点设置相同的视图holder
     * @param nodes 节点
     */
    public static void setSameHoder(List<TreeNodeT> nodes, Context context){
        for (TreeNodeT node : nodes) {
            node.setViewHolder(new SelectableItemHolderT(context));
        }
    }

    /**
     * 根据层级设置视图holder
     * @param nodes 节点
     * @param holderClazzs 视图holder类型，根据节点层级设置，如第一级节点设置holder集合中的第一个，
     *                第二级节点设置第二个，以此类推，如果节点层级大于holder数量，则多出的
     *                节点层级均设置最后一个holder
     */
    private static void setHolderByLevel(List<TreeNodeT> nodes, List<Class<? extends TreeNodeT.BaseNodeViewHolder>> holderClazzs, Context context){
        for (TreeNodeT node : nodes) {
            int position;
            if (node.getLevel() >= holderClazzs.size()){
                position = holderClazzs.size() - 1;
     //           node.setViewHolder(holders.get(holders.size() -1));

            }else {
                position = node.getLevel();
      //          node.setViewHolder(holders.get(node.getLevel()));
            }
            try {
                final Object object = holderClazzs.get(position).getConstructor(Context.class).newInstance(context);
                node.setViewHolder((TreeNodeT.BaseNodeViewHolder) object);
            } catch (Exception e) {
                throw new RuntimeException("初始化对象异常 " + holderClazzs.get(holderClazzs.size() - 1));
            }
        }
    }
}

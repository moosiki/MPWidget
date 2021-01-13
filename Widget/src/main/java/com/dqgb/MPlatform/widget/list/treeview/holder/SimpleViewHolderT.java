package com.dqgb.MPlatform.widget.list.treeview.holder;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.dqgb.MPlatform.widget.list.treeview.model.TreeNodeT;

/**
 * Created by Bogdan Melnychuk on 2/11/15.
 */
public class SimpleViewHolderT extends TreeNodeT.BaseNodeViewHolder<Object> {

    public SimpleViewHolderT(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNodeT node, Object value) {
        final TextView tv = new TextView(context);
        tv.setText(String.valueOf(value));
        return tv;
    }

    @Override
    public void toggle(boolean active) {

    }

    /**
     * 指定勾选框   如果未指定,将无法获取已勾选选项
     *
     * @return 勾选框
     */
    @Override
    public CheckBox specifyCheckBox() {
        return null;
    }

    /**
     * 指定箭头视图  如果未指定,将无法展示收缩状态的箭头指示图标
     *
     * @return 勾选框
     */
    @Override
    public ImageView specifyArrowView() {
        return null;
    }
}

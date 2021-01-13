package com.dqgb.MPlatform.widget.list.treeview.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.dqgb.MPlatform.widget.R;
import com.dqgb.MPlatform.widget.list.treeview.model.TreeNodeT;

/**
 * Created by Bogdan Melnychuk on 2/15/15.
 */
public class SelectableItemHolderT extends TreeNodeT.BaseNodeViewHolder<String> {
    private TextView tvValue;
    private CheckBox nodeSelector;
    private ImageView arrow;

    public SelectableItemHolderT(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(final TreeNodeT node, String value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.layout_selectable_item, null, false);

        tvValue = (TextView) view.findViewById(R.id.node_value);
        tvValue.setText(value);


        nodeSelector = (CheckBox) view.findViewById(R.id.node_selector);
        arrow = (ImageView) view.findViewById(R.id.iv_arrow);
    /*    nodeSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                node.setSelected(isChecked);
            }
        });
        nodeSelector.setChecked(node.isSelected());*/

        if (node.isLastChild()) {
            view.findViewById(R.id.bot_line).setVisibility(View.INVISIBLE);
        }

        return view;
    }


    @Override
    public void toggleSelectionMode(boolean editModeEnabled) {
        nodeSelector.setVisibility(editModeEnabled ? View.VISIBLE : View.GONE);
        nodeSelector.setChecked(mNode.isSelected());
    }

    /**
     * 指定选择框
     *
     * @return
     */
    @Override
    public CheckBox specifyCheckBox() {
        return nodeSelector;
    }

    /**
     * 指定箭头视图  如果未指定,将无法展示收缩状态的箭头指示图标
     *
     * @return 勾选框
     */
    @Override
    public ImageView specifyArrowView() {
        return arrow;
    }
}

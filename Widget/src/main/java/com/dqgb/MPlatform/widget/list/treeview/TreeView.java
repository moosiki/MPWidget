package com.dqgb.MPlatform.widget.list.treeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dqgb.MPlatform.widget.R;
import com.dqgb.MPlatform.widget.list.treeview.model.TreeNodeT;
import com.dqgb.MPlatform.widget.list.treeview.util.TreeHelper;
import com.dqgb.MPlatform.widget.list.treeview.view.AndroidTreeViewT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 树形列表封装控件
 *
 * @author yangqiang-ds
 * @date 2019/12/21
 */
public class TreeView<T> extends LinearLayout implements TreeNodeT.TreeNodeClickListener{
    AndroidTreeViewT treeView;

    NodeClickListener nodeListener;

    public List<TreeNodeT> mData = new ArrayList<>();
    public List<T> sourceData;

    private boolean selectionModeEnabled = false;     //是否可以勾选模
    private int selectionLevel = 0;             //可勾选的层级
    private boolean enable2D = false;           //是否启用2D
    private boolean selectChildren = false;           //是否启用级联勾：选勾选当前节点后是否勾选子节点
    private boolean isMultySelect = false;           //是否启用多选
    private boolean isAutoExpand = false;           //是否自动展开

    public interface NodeClickListener{
        /**
         * 节点点击事件
         */
        void onNodeClick();

        /**
         * 叶子节点点击事件
         */
        void onLeafClick();
    }

    public TreeView(Context context) {
        super(context);
    }

    public TreeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_tree, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.tree_view);
        selectionModeEnabled = typedArray.getBoolean(R.styleable.tree_view_SelectionModeEnabled,false);
        selectionLevel = typedArray.getInteger(R.styleable.tree_view_SelectionLevel,0);
        enable2D = typedArray.getBoolean(R.styleable.tree_view_enable2D,false);
        selectChildren = typedArray.getBoolean(R.styleable.tree_view_selectChildren,false);
        isMultySelect = typedArray.getBoolean(R.styleable.tree_view_isMultySelect,false);
        isAutoExpand = typedArray.getBoolean(R.styleable.tree_view_isAutoExpand,false);
    }

    /**
     * 树形列表构造方法
     * @param context
     * @param attrs
     * @param sourceData
     * @param viewHolders
     */
    public TreeView(Context context, @Nullable AttributeSet attrs, List<T> sourceData, List<Object> lastSelectNode, Bundle savedInstanceState, Class<? extends TreeNodeT.BaseNodeViewHolder>... viewHolders) {
        super(context, attrs);
        this.sourceData = sourceData;
        LayoutInflater.from(context).inflate(R.layout.widget_tree, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.tree_view);
        selectionModeEnabled = typedArray.getBoolean(R.styleable.tree_view_SelectionModeEnabled,false);
        selectionLevel = typedArray.getInteger(R.styleable.tree_view_SelectionLevel,0);
        enable2D = typedArray.getBoolean(R.styleable.tree_view_enable2D,false);
        selectChildren = typedArray.getBoolean(R.styleable.tree_view_selectChildren,false);
        isMultySelect = typedArray.getBoolean(R.styleable.tree_view_isMultySelect,false);
        isAutoExpand = typedArray.getBoolean(R.styleable.tree_view_isAutoExpand,false);
        initModule(sourceData, lastSelectNode, savedInstanceState, viewHolders);
    }

    /**
     * 初始化树级列表
     * @param sourceData
     * @param lastSelectNode
     * @param savedInstanceState
     * @param viewHolders
     */
    public void initModule( List<T> sourceData, List<Object> lastSelectNode,Bundle savedInstanceState, Class<? extends TreeNodeT.BaseNodeViewHolder>... viewHolders) {
        RelativeLayout mainLayout = (RelativeLayout)findViewById(R.id.widget_tree_container);
        try {
            this.mData = TreeHelper.convertData2Node(sourceData);               //初始化节点数据
        }catch (Exception e){
            e.printStackTrace();
        }
        List<Class<? extends TreeNodeT.BaseNodeViewHolder>> viewHolderClazzs = new ArrayList<>(Arrays.asList(viewHolders));
        TreeHelper.setEnableSelectLevel(mData, selectionLevel);                            //初始化可勾选的层级
        TreeHelper.setViewHolders(mData, viewHolderClazzs, getContext());                            //初始化节点视图holder
        TreeHelper.setIsSelectChildren(mData, selectChildren);                            //设置是否启用级联勾选
        TreeHelper.setIsMultySelect(mData, isMultySelect);                            //设置是否启用多选
        List<TreeNodeT> rootTree = TreeHelper.getRootNodes(mData);
        TreeNodeT root = TreeNodeT.root();
        root.addToChild(rootTree);
        treeView = new AndroidTreeViewT(getContext(),root);
        treeView.setDefaultAnimation(false);                                             //是否使用默认动画
        treeView.setDefaultContainerStyle(R.style.TreeNodeStyleCustom);
        treeView.setDefaultNodeClickListener(this);
        /* 设置自定义属性 */
        treeView.setSelectionModeEnabled(selectionModeEnabled);
        treeView.setUse2dScroll(enable2D);

        mainLayout.addView(treeView.getView());
        treeView.loadSelectedNode(lastSelectNode);
        if (isAutoExpand){
            treeView.expandAll();
        }
    }

    /**
     * @return 获取已勾选节点的数据
     */
    public List<T> getSelectedValue(){
        List<T> selectedValue = new ArrayList<>();
        List<TreeNodeT> nodes = treeView.getSelected();
        for (TreeNodeT node : nodes) {
            selectedValue.add((T)node.getValue());
        }
        return selectedValue;
    }

    /**
     * @return 获取已勾选节点的id
     */
    public List<T> getSelectedNodeId(){
        List<T> selectedNodeId = new ArrayList<>();
        List<TreeNodeT> nodes = treeView.getSelected();
        for (TreeNodeT node : nodes) {
            selectedNodeId.add((T)node.getId());
        }
        return selectedNodeId;
    }

    //清除所选
    public void deSelectAll(){
        treeView.deselectAll();
    }

    /**
     * 设置item点击事件，将点击与勾选框绑定
     * @param node
     * @param value
     */
    @Override
    public void onClick(TreeNodeT node, Object value) {
        CheckBox checkBox = node.getViewHolder().getmCheckBox();
        if (checkBox != null && node.isLeaf()) {
            //有勾选框且是叶子节点，将勾选框选中
            checkBox.performClick();
        }
        if (nodeListener != null){
            nodeListener.onNodeClick();
            if (node.isLeaf()){
                nodeListener.onLeafClick();
            }
        }
    }

    public void setNodeListener(NodeClickListener nodeListener) {
        this.nodeListener = nodeListener;
    }

    public void setSourceData(List<T> sourceData) {
        this.sourceData = sourceData;
    }

    public void setSelectionModeEnabled(boolean selectionModeEnabled) {
        this.selectionModeEnabled = selectionModeEnabled;
    }

    public void setSelectionLevel(int selectionLevel) {
        this.selectionLevel = selectionLevel;
    }

    public void setEnable2D(boolean enable2D) {
        this.enable2D = enable2D;
    }

    public void setSelectChildren(boolean selectChildren) {
        this.selectChildren = selectChildren;
    }

    public void setMultySelect(boolean multySelect) {
        isMultySelect = multySelect;
    }
}

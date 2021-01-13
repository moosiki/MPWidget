package com.dqgb.MPlatform.widget.list.treeview.model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dqgb.MPlatform.widget.R;
import com.dqgb.MPlatform.widget.list.treeview.view.AndroidTreeViewT;
import com.unnamed.b.atv.view.TreeNodeWrapperView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 树形列表节点model
 *
 * @author yangqiang-ds
 * @date 2019/12/21
 */
public class TreeNodeT {
    public static final String NODES_ID_SEPARATOR = ":";
    /**
     * 节点id
     */
    private Object mId;
    /**
     * 父节点id
     */
    private Object mParentId;
    private Object mLastId;
    /**
     * 父节点
     */
    private TreeNodeT mParent;
    /**
     * 当前节点是否被选中
     */
    private boolean mSelected;
    /**
     * 当前节点是否可被选中
     */
    private boolean mSelectable = true;
    /**
     * 字节点
     */
    private final List<TreeNodeT> children;
    /**
     * 视图适配器
     */
    private BaseNodeViewHolder mViewHolder;
    /**
     * 点击监听时间
     */
    private TreeNodeClickListener mClickListener;
    /**
     * 长按监听事件
     */
    private TreeNodeLongClickListener mLongClickListener;
    /**
     * 节点数据
     */
    private Object mValue;
    /**
     * 当前节点是否展开
     */
    private boolean mExpanded;

    public static TreeNodeT root() {
        TreeNodeT root = new TreeNodeT(null);
        root.setSelectable(false);
        return root;
    }

    private Object generateId() {
        if (mLastId instanceof Integer){
            int gId = Integer.parseInt(String.valueOf(mLastId));
            return ++gId;
        }else if (mLastId instanceof String){
            String lastId = String.valueOf(mLastId);
            if ('0' == (lastId.charAt(lastId.length() - 1) )){
                return lastId.substring(0,lastId.length()-1) + "1";
            }else {
                return lastId.substring(0,lastId.length()-1) + "0";
            }
        }else {
            return mLastId;
        }
    }


    public TreeNodeT(Object mId, Object mParentId, Object mValue) {
        this.mId = mId;
        this.mParentId = mParentId;
        this.mValue = mValue;
        children = new ArrayList<>();
    }

    public TreeNodeT(Object value) {
        children = new ArrayList<>();
        mValue = value;
    }

    public void addToChild(TreeNodeT childNode){
       children.add(childNode);
    }

    public TreeNodeT addChild(TreeNodeT childNode) {
        childNode.mParent = this;
        childNode.mId = generateId();
        children.add(childNode);
        return this;
    }

    public TreeNodeT addChildren(TreeNodeT... nodes) {
        for (TreeNodeT n : nodes) {
            addChild(n);
        }
        return this;
    }

    public void addToChild(Collection<TreeNodeT> nodes) {
        children.addAll(nodes);
    }

    public TreeNodeT addChildren(Collection<TreeNodeT> nodes) {
        for (TreeNodeT n : nodes) {
            addChild(n);
        }

        return this;
    }

    public int deleteChild(TreeNodeT child) {
        for (int i = 0; i < children.size(); i++) {
            if (child.mId.equals(children.get(i).mId)) {
                children.remove(i);
                return i;
            }
        }
        return -1;
    }

    public List<TreeNodeT> getChildren() {
        return children;
    }

    public int size() {
        return children.size();
    }

    public TreeNodeT getParent() {
        return mParent;
    }

    public Object getId() {
        return mId;
    }

    public void setmId(Object mId) {
        this.mId = mId;
    }

    public Object getmParentId() {
        return mParentId;
    }

    public void setmParentId(Object mParentId) {
        this.mParentId = mParentId;
    }

    public TreeNodeT getmParent() {
        return mParent;
    }

    public void setmParent(TreeNodeT mParent) {
        this.mParent = mParent;
    }

    public boolean isLeaf() {
        return size() == 0;
    }

    public Object getValue() {
        return mValue;
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public TreeNodeT setExpanded(boolean expanded) {
        mExpanded = expanded;
        return this;
    }

    public void setSelected(boolean selected) {
        mSelected = selected;
    }

    public boolean isSelected() {
        return mSelectable && mSelected;
    }

    public void setSelectable(boolean selectable) {
        mSelectable = selectable;
    }

    public boolean isSelectable() {
        return mSelectable;
    }

    public String getPath() {
        final StringBuilder path = new StringBuilder();
        TreeNodeT node = this;
        while (node.mParent != null) {
            path.append(node.getId());
            node = node.mParent;
            if (node.mParent != null) {
                path.append(NODES_ID_SEPARATOR);
            }
        }
        return path.toString();
    }


    /**
     * 获取当前节点的层级
     * @return  层级
     */
    public int getLevel() {
        int level = 0;
        TreeNodeT root = this;
        while (root.mParent != null) {
            root = root.mParent;
            level++;
        }
        return level;
    }

    public boolean isLastChild() {
        if (!isRoot()) {
            int parentSize = mParent.children.size();
            if (parentSize > 0) {
                final List<TreeNodeT> parentChildren = mParent.children;
                return parentChildren.get(parentSize - 1).mId.equals(mId);
            }
        }
        return false;
    }

    public TreeNodeT setViewHolder(BaseNodeViewHolder viewHolder) {
        mViewHolder = viewHolder;
        if (viewHolder != null) {
            viewHolder.mNode = this;
        }
        return this;
    }

    public TreeNodeT setClickListener(TreeNodeClickListener listener) {
        mClickListener = listener;
        return this;
    }

    public TreeNodeClickListener getClickListener() {
        return this.mClickListener;
    }

    public TreeNodeT setLongClickListener(TreeNodeLongClickListener listener) {
        mLongClickListener = listener;
        return this;
    }

    public TreeNodeLongClickListener getLongClickListener() {
        return mLongClickListener;
    }

    public BaseNodeViewHolder getViewHolder() {
        return mViewHolder;
    }

    public boolean isFirstChild() {
        if (!isRoot()) {
            List<TreeNodeT> parentChildren = mParent.children;
            return parentChildren.get(0).mId.equals(mId);
        }
        return false;
    }

    public boolean isRoot() {
        return mParent == null;
    }

    public TreeNodeT getRoot() {
        TreeNodeT root = this;
        while (root.mParent != null) {
            root = root.mParent;
        }
        return root;
    }

    public interface TreeNodeClickListener {
        void onClick(TreeNodeT node, Object value);
    }

    public interface TreeNodeLongClickListener {
        boolean onLongClick(TreeNodeT node, Object value);
    }

    public static abstract class BaseNodeViewHolder<E> implements Serializable {
        protected AndroidTreeViewT tView;
        protected TreeNodeT mNode;
        private View mView;
        protected int containerStyle;
        protected Context context;
        protected CheckBox mCheckBox;
        protected ImageView arrowView;
        protected boolean isSelectChildren; // 是否启用级联勾选
        protected boolean isMultySelect; // 是否启用多选

        public BaseNodeViewHolder(Context context) {
            this.context = context;
        }

        public View getView() {
            if (mView != null) {
                return mView;
            }
            final View nodeView = getNodeView();
            final TreeNodeWrapperView nodeWrapperView = new TreeNodeWrapperView(nodeView.getContext(), getContainerStyle());
            nodeWrapperView.insertNodeView(nodeView);
            mView = nodeWrapperView;
            arrowView = specifyArrowView();
            if (arrowView != null){
                arrowView.setVisibility(mNode.isLeaf() ? View.INVISIBLE : View.VISIBLE);
            }
            mCheckBox = specifyCheckBox();
            if (mCheckBox != null) {
                mCheckBox.setEnabled(mNode.isSelectable());

                mCheckBox.setVisibility(mNode.isSelectable() ? View.VISIBLE : View.GONE);
                if (mNode.isSelectable()) {
                    mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isMultySelect){                         //可多选
                                mNode.setSelected(isChecked);
                                if (isSelectChildren) {
                                    for (TreeNodeT child : mNode.getChildren()) {
                                        getTreeView().selectNode(child, isChecked);
                                    }
                                }
                            }else {                                     //单选
                                mNode.setSelected(isChecked);
                                if (isChecked){                    //勾选
                                    tView.deselectAll(mNode);
                                    if (isSelectChildren && mNode.getChildren().size() != 0) {
                                        Toast.makeText(context, "单选模式下无法进行级联勾选！",Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                    });
                }
            }
            return mView;
        }

        public void setTreeViev(AndroidTreeViewT treeViev) {
            this.tView = treeViev;
        }

        public AndroidTreeViewT getTreeView() {
            return tView;
        }

        public void setContainerStyle(int style) {
            containerStyle = style;
        }

        public View getNodeView() {
            return createNodeView(mNode, (E) mNode.getValue());
        }

        public ViewGroup getNodeItemsView() {
            return (ViewGroup) getView().findViewById(com.unnamed.b.atv.R.id.node_items);
        }

        public boolean isInitialized() {
            return mView != null;
        }

        public int getContainerStyle() {
            return containerStyle;
        }

        /**
         * 指定勾选框   如果未指定,将无法获取已勾选选项
         * @return 勾选框
         */
        public abstract CheckBox specifyCheckBox();

        /**
         * 指定箭头视图  如果未指定,将无法展示收缩状态的箭头指示图标
         * @return 勾选框
         */
        public abstract ImageView specifyArrowView();

        protected int getExpandArrow(){
            return R.drawable.svg_arrow_down;
        }

        protected int getCollapseArrow(){
            return R.drawable.svg_arrow_up;
        }

        public void setSelectChildren(boolean selectChildren) {
            isSelectChildren = selectChildren;
        }

        public void setMultySelect(boolean multySelect) {
            isMultySelect = multySelect;
        }

        public abstract View createNodeView(TreeNodeT node, E value);

        public CheckBox getmCheckBox() {
            return mCheckBox;
        }

        public void toggle(boolean active) {
            if (arrowView != null){
                arrowView.setImageResource(active ? getExpandArrow() : getCollapseArrow());
            }
        }

        public void toggleSelectionMode(boolean editModeEnabled) {
            if (mCheckBox != null && editModeEnabled) {
                mCheckBox.setChecked(mNode.isSelected());
            }
        }
    }
}

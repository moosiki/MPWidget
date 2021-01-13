package com.dqgb.MPlatform.widget.list.treeview.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.dqgb.MPlatform.widget.list.treeview.holder.SimpleViewHolderT;
import com.dqgb.MPlatform.widget.list.treeview.model.TreeNodeT;
import com.unnamed.b.atv.view.TwoDScrollView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Bogdan Melnychuk on 2/10/15.
 */
public class AndroidTreeViewT {
    private static final String NODES_PATH_SEPARATOR = ";";

    protected TreeNodeT mRoot;
    private Context mContext;
    private boolean applyForRoot;
    private int containerStyle = 0;
    private Class<? extends TreeNodeT.BaseNodeViewHolder> defaultViewHolderClass = SimpleViewHolderT.class;
    private TreeNodeT.TreeNodeClickListener nodeClickListener;
    private TreeNodeT.TreeNodeLongClickListener nodeLongClickListener;
    private boolean mSelectionModeEnabled;
    private boolean mUseDefaultAnimation = false;
    private boolean use2dScroll = false;
    private boolean enableAutoToggle = true;

    public AndroidTreeViewT(Context context) {
        mContext = context;
    }

    public void setRoot(TreeNodeT mRoot) {
        this.mRoot = mRoot;
    }

    public AndroidTreeViewT(Context context, TreeNodeT root) {
        mRoot = root;
        mContext = context;
    }

    public void setDefaultAnimation(boolean defaultAnimation) {
        this.mUseDefaultAnimation = defaultAnimation;
    }

    public void setDefaultContainerStyle(int style) {
        setDefaultContainerStyle(style, false);
    }

    public void setDefaultContainerStyle(int style, boolean applyForRoot) {
        containerStyle = style;
        this.applyForRoot = applyForRoot;
    }

    public void setUse2dScroll(boolean use2dScroll) {
        this.use2dScroll = use2dScroll;
    }

    public boolean is2dScrollEnabled() {
        return use2dScroll;
    }

    public void setUseAutoToggle(boolean enableAutoToggle) {
        this.enableAutoToggle = enableAutoToggle;
    }

    public boolean isAutoToggleEnabled() {
        return enableAutoToggle;
    }

    public void setDefaultViewHolder(Class<? extends TreeNodeT.BaseNodeViewHolder> viewHolder) {
        defaultViewHolderClass = viewHolder;
    }

    public void setDefaultNodeClickListener(TreeNodeT.TreeNodeClickListener listener) {
        nodeClickListener = listener;
    }

    public void setDefaultNodeLongClickListener(TreeNodeT.TreeNodeLongClickListener listener) {
        nodeLongClickListener = listener;
    }

    public void expandAll() {
        expandNode(mRoot, true);
    }

    public void collapseAll() {
        for (TreeNodeT n : mRoot.getChildren()) {
            collapseNode(n, true);
        }
    }


    public View getView(int style) {
        final ViewGroup view;
        if (style > 0) {
            ContextThemeWrapper newContext = new ContextThemeWrapper(mContext, style);
            view = use2dScroll ? new TwoDScrollView(newContext) : new ScrollView(newContext);
        } else {
            view = use2dScroll ? new TwoDScrollView(mContext) : new ScrollView(mContext);
        }

        Context containerContext = mContext;
        if (containerStyle != 0 && applyForRoot) {
            containerContext = new ContextThemeWrapper(mContext, containerStyle);
        }
        final LinearLayout viewTreeItems = new LinearLayout(containerContext, null, containerStyle);

        viewTreeItems.setId(com.unnamed.b.atv.R.id.tree_items);
        viewTreeItems.setOrientation(LinearLayout.VERTICAL);
        view.addView(viewTreeItems);

        mRoot.setViewHolder(new TreeNodeT.BaseNodeViewHolder(mContext) {
            @Override
            public View createNodeView(TreeNodeT node, Object value) {
                return null;
            }

            @Override
            public ViewGroup getNodeItemsView() {
                return viewTreeItems;
            }

            @Override
            public CheckBox specifyCheckBox() {
                return null;
            }

            @Override
            public ImageView specifyArrowView() {
                return null;
            }
        });

        expandNode(mRoot, false);
        return view;
    }

    public View getView() {
        return getView(-1);
    }


    public void expandLevel(int level) {
        for (TreeNodeT n : mRoot.getChildren()) {
            expandLevel(n, level);
        }
    }

    private void expandLevel(TreeNodeT node, int level) {
        if (node.getLevel() <= level) {
            expandNode(node, false);
        }
        for (TreeNodeT n : node.getChildren()) {
            expandLevel(n, level);
        }
    }

    public void expandNode(TreeNodeT node) {
        expandNode(node, false);
    }

    public void collapseNode(TreeNodeT node) {
        collapseNode(node, false);
    }

    public String getSaveState() {
        final StringBuilder builder = new StringBuilder();
        getSaveState(mRoot, builder);
        if (builder.length() > 0) {
            builder.setLength(builder.length() - 1);
        }
        return builder.toString();
    }

    private void loadSelectedNode(TreeNodeT node, List<Object> selectedNodeId) {
        if (selectedNodeId.contains(node.getId())){
            node.setSelected(true);
        }
        for (TreeNodeT child : node.getChildren()) {
            loadSelectedNode(child, selectedNodeId);
        }
    }

    /**
     * 加载上次勾选的节点
     * @param selectedNodeId 上次勾选的节点id集合
     */
    public void loadSelectedNode(List<Object> selectedNodeId) {
        if (selectedNodeId != null && selectedNodeId.size() != 0) {
            for (TreeNodeT child : mRoot.getChildren()) {
                loadSelectedNode(child, selectedNodeId);
            }
        }
    }


    public void restoreState(String saveState) {
        if (!TextUtils.isEmpty(saveState)) {
            collapseAll();
            final String[] openNodesArray = saveState.split(NODES_PATH_SEPARATOR);
            final Set<String> openNodes = new HashSet<>(Arrays.asList(openNodesArray));
            restoreNodeState(mRoot, openNodes);
        }
    }

    private void restoreNodeState(TreeNodeT node, Set<String> openNodes) {
        for (TreeNodeT n : node.getChildren()) {
            if (openNodes.contains(n.getPath())) {
                expandNode(n);
                restoreNodeState(n, openNodes);
            }
        }
    }

    private void getSaveState(TreeNodeT root, StringBuilder sBuilder) {
        for (TreeNodeT node : root.getChildren()) {
            if (node.isExpanded()) {
                sBuilder.append(node.getPath());
                sBuilder.append(NODES_PATH_SEPARATOR);
                getSaveState(node, sBuilder);
            }
        }
    }

    public void toggleNode(TreeNodeT node) {
        if (node.isExpanded()) {
            collapseNode(node, false);
        } else {
            expandNode(node, false);
        }

    }

    private void collapseNode(TreeNodeT node, final boolean includeSubnodes) {
        node.setExpanded(false);
        TreeNodeT.BaseNodeViewHolder nodeViewHolder = getViewHolderForNode(node);

        if (mUseDefaultAnimation) {
            collapse(nodeViewHolder.getNodeItemsView());
        } else {
            nodeViewHolder.getNodeItemsView().setVisibility(View.GONE);
        }
        nodeViewHolder.toggle(false);
        if (includeSubnodes) {
            for (TreeNodeT n : node.getChildren()) {
                collapseNode(n, includeSubnodes);
            }
        }
    }

    private void expandNode(final TreeNodeT node, boolean includeSubnodes) {
        node.setExpanded(true);
        final TreeNodeT.BaseNodeViewHolder parentViewHolder = getViewHolderForNode(node);
        parentViewHolder.getNodeItemsView().removeAllViews();


        parentViewHolder.toggle(true);

        for (final TreeNodeT n : node.getChildren()) {
            addNode(parentViewHolder.getNodeItemsView(), n);

            if (n.isExpanded() || includeSubnodes) {
                expandNode(n, includeSubnodes);
            }

        }
        if (mUseDefaultAnimation) {
            expand(parentViewHolder.getNodeItemsView());
        } else {
            parentViewHolder.getNodeItemsView().setVisibility(View.VISIBLE);
        }

    }

    private void addNode(ViewGroup container, final TreeNodeT n) {
        final TreeNodeT.BaseNodeViewHolder viewHolder = getViewHolderForNode(n);
        final View nodeView = viewHolder.getView();
        container.addView(nodeView);
        if (mSelectionModeEnabled) {
            viewHolder.toggleSelectionMode(mSelectionModeEnabled);
        }

        nodeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (n.getClickListener() != null) {
                    n.getClickListener().onClick(n, n.getValue());
                } else if (nodeClickListener != null) {
                    nodeClickListener.onClick(n, n.getValue());
                }
                if (enableAutoToggle) {
                    toggleNode(n);
                }
            }
        });

        nodeView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (n.getLongClickListener() != null) {
                    return n.getLongClickListener().onLongClick(n, n.getValue());
                } else if (nodeLongClickListener != null) {
                    return nodeLongClickListener.onLongClick(n, n.getValue());
                }
                if (enableAutoToggle) {
                    toggleNode(n);
                }
                return false;
            }
        });
    }

    //------------------------------------------------------------
    //  Selection methods

    public void setSelectionModeEnabled(boolean selectionModeEnabled) {
        if (!selectionModeEnabled) {
            // TODO fix double iteration over tree
            deselectAll();
        }
        mSelectionModeEnabled = selectionModeEnabled;

        for (TreeNodeT node : mRoot.getChildren()) {
            toggleSelectionMode(node, selectionModeEnabled);
        }

    }

    public <E> List<E> getSelectedValues(Class<E> clazz) {
        List<E> result = new ArrayList<>();
        List<TreeNodeT> selected = getSelected();
        for (TreeNodeT n : selected) {
            Object value = n.getValue();
            if (value != null && value.getClass().equals(clazz)) {
                result.add((E) value);
            }
        }
        return result;
    }

    public boolean isSelectionModeEnabled() {
        return mSelectionModeEnabled;
    }

    private void toggleSelectionMode(TreeNodeT parent, boolean mSelectionModeEnabled) {
        toogleSelectionForNode(parent, mSelectionModeEnabled);
        if (parent.isExpanded()) {
            for (TreeNodeT node : parent.getChildren()) {
                toggleSelectionMode(node, mSelectionModeEnabled);
            }
        }
    }

    public List<TreeNodeT> getSelected() {
        if (mSelectionModeEnabled) {
            return getSelected(mRoot);
        } else {
            return new ArrayList<>();
        }
    }

    // TODO Do we need to go through whole tree? Save references or consider collapsed nodes as not selected
    private List<TreeNodeT> getSelected(TreeNodeT parent) {
        List<TreeNodeT> result = new ArrayList<>();
        for (TreeNodeT n : parent.getChildren()) {
            if (n.isSelected()) {
                result.add(n);
            }
            result.addAll(getSelected(n));
        }
        return result;
    }

    public void selectAll(boolean skipCollapsed) {
        makeAllSelection(true, skipCollapsed, null);
    }

    public void deselectAll() {
        makeAllSelection(false, false, null);
    }

    //去除所有勾选，此节点除外
    public void deselectAll(TreeNodeT exclud) {
        makeAllSelection(false, false, exclud);
    }

    /**
     * 全选或全不选
     * @param selected  是否全选
     * @param skipCollapsed 是否跳过展开
     * @param excludNode 排除的节点
     */
    private void makeAllSelection(boolean selected, boolean skipCollapsed, TreeNodeT excludNode) {
        if (mSelectionModeEnabled) {
            for (TreeNodeT node : mRoot.getChildren()) {

                selectNode(node, selected, skipCollapsed, excludNode);

            }
        }
    }

    public void selectNode(TreeNodeT node, boolean selected) {
        if (mSelectionModeEnabled) {
            node.setSelected(selected);
            toogleSelectionForNode(node, true);
        }
    }

    private void selectNode(TreeNodeT parent, boolean selected, boolean skipCollapsed, TreeNodeT excludNode) {
        if (!parent.equals(excludNode)) {
            parent.setSelected(selected);
        }
        toogleSelectionForNode(parent, true);
        boolean toContinue = skipCollapsed ? parent.isExpanded() : true;
        if (toContinue) {
            for (TreeNodeT node : parent.getChildren()) {
                selectNode(node, selected, skipCollapsed, excludNode);

            }
        }
    }

    private void toogleSelectionForNode(TreeNodeT node, boolean makeSelectable) {
        TreeNodeT.BaseNodeViewHolder holder = getViewHolderForNode(node);
        if (holder.isInitialized()) {
            getViewHolderForNode(node).toggleSelectionMode(makeSelectable);
        }
    }

    private TreeNodeT.BaseNodeViewHolder getViewHolderForNode(TreeNodeT node) {
        TreeNodeT.BaseNodeViewHolder viewHolder = node.getViewHolder();
        if (viewHolder == null) {
            try {
                final Object object = defaultViewHolderClass.getConstructor(Context.class).newInstance(mContext);
                viewHolder = (TreeNodeT.BaseNodeViewHolder) object;
                node.setViewHolder(viewHolder);
            } catch (Exception e) {
                throw new RuntimeException("Could not instantiate class " + defaultViewHolderClass);
            }
        }
        if (viewHolder.getContainerStyle() <= 0) {
            viewHolder.setContainerStyle(containerStyle);
        }
        if (viewHolder.getTreeView() == null) {
            viewHolder.setTreeViev(this);
        }
        return viewHolder;
    }

    private static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    //-----------------------------------------------------------------
    //Add / Remove

    public void addNode(TreeNodeT parent, final TreeNodeT nodeToAdd) {
        parent.addChild(nodeToAdd);
        if (parent.isExpanded()) {
            final TreeNodeT.BaseNodeViewHolder parentViewHolder = getViewHolderForNode(parent);
            addNode(parentViewHolder.getNodeItemsView(), nodeToAdd);
        }
    }

    public void removeNode(TreeNodeT node) {
        if (node.getParent() != null) {
            TreeNodeT parent = node.getParent();
            int index = parent.deleteChild(node);
            if (parent.isExpanded() && index >= 0) {
                final TreeNodeT.BaseNodeViewHolder parentViewHolder = getViewHolderForNode(parent);
                parentViewHolder.getNodeItemsView().removeViewAt(index);
            }
        }
    }
}

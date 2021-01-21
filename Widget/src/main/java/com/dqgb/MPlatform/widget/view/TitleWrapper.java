package com.dqgb.MPlatform.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.dqgb.MPlatform.widget.R;
import com.dqgb.MPlatform.widget.core.BaseActModule;
import com.dqgb.MPlatform.widget.core.Module;
import com.dqgb.MPlatform.widget.input.InputRowModule;

public class TitleWrapper extends ContainerView {

    private static String TITLE_POS_TOP = "top";
    private static String TITLE_POS_LEFT = "left";
    String titlePosition = TITLE_POS_TOP;
    public TitleWrapper(Context context) {
        super(context);
    }

    public TitleWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.view_title_wrapper);
        titlePosition = typedArray.getString(R.styleable.view_title_wrapper_title_position);
    }


    /**
     * 计算所有ChildView的宽度和高度 然后根据ChildView的计算结果，设置自己的宽和高
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);


        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        int width = 0;
        int height = 0;

        int cCount = getChildCount();

        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;

        // 用于计算左边两个childView的高度
        int lHeight = 0;
        // 用于计算右边两个childView的高度，最终高度取二者之间大值
        int rHeight = 0;

        // 用于计算上边两个childView的宽度
        int tWidth = 0;
        // 用于计算下面两个childiew的宽度，最终宽度取二者之间大值
        int bWidth = 0;

        /**
         * 根据childView计算的出的宽和高，以及设置的margin计算容器的宽和高，主要用于容器是warp_content时
         */
        for (int i = 0; i < cCount; i++)
        {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            // 上面两个childView
            if (i == 0 || i == 1)
            {
                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 2 || i == 3)
            {
                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 0 || i == 2)
            {
                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

            if (i == 1 || i == 3)
            {
                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

        }

        width = Math.max(tWidth, bWidth);
        height = Math.max(lHeight, rHeight);

        /**
         * 如果是wrap_content设置为我们计算的值
         * 否则：直接设置为父容器计算的值
         */
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : width, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : height);
    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        MarginLayoutParams cParams = null;
        int childCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        TitleView titleView = null;
        TextView textView = null;
        BaseActModule module = null;
        for (int j = 0; j < childCount; j++) {
            View childView = getChildAt(j);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams)childView.getLayoutParams();
            if (childView instanceof BaseActModule){
                module = (BaseActModule) childView;
                module.layout(0,0,cWidth,cHeight);
                continue;
            }
            if (childView instanceof TextView){
                textView = (TextView) childView;
                textView.layout(0,0,cWidth,cHeight);

            }
        }

    }

    @Override
    public int getInflateResource() {
        return 0;
    }

    @Override
    public void inflateView() {

    }

    @Override
    public void initModule() {

    }
}

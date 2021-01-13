package com.dqgb.MPlatform.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.dqgb.MPlatform.widget.R;
import com.dqgb.MPlatform.widget.R2;

import butterknife.BindView;

public class HorizontalTitle extends TitleView {

    @BindView(R2.id.moduleContainer)
    public LinearLayout layout;

    public HorizontalTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalTitle(Context context) {
        super(context);
    }

    @Override
    public int getInflateResource() {
        return R.layout.widget_display_title_horizontal;
    }


    @Override
    public void initModule() {
        View child = getChildAt(0);
    //    removeViewAt(0);
     //   layout.addView(child);
    }

    public void insertContent(View v){
        layout.addView(v);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }
}

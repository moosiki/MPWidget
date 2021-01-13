package com.dqgb.MPlatform.widget.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.dqgb.MPlatform.widget.R;
import com.dqgb.MPlatform.widget.R2;
import com.dqgb.MPlatform.widget.common.CommonModule;
import com.dqgb.MPlatform.widget.common.WidgetConst;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**     
  * 
  * @Description:    状态角标
  * @author:         yangqiang-ds
  * @date:           2020/2/18 15:16
  * @Version:        1.0
 */
public class StatusBarText extends CommonModule {

    @BindView(R2.id.tvState)
    TextView tvStatus;

    private Map<Integer, String > statusAndBg = new HashMap<>();
    private Map<String, Integer> bgToResourceId = new HashMap<>();

    public StatusBarText(Context context) {
        super(context);
        initStatusBar();
    }

    public StatusBarText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initStatusBar();
    }

    /**
     * 初始化状态编码与背景的对应关系
     */
    private void initStatusBar() {
        bgToResourceId.put(WidgetConst.BLUE, R.drawable.bg_status_blue);
        bgToResourceId.put(WidgetConst.GREEN, R.drawable.bg_status_green);
        bgToResourceId.put(WidgetConst.GREY, R.drawable.bg_status_grey);
        bgToResourceId.put(WidgetConst.RED, R.drawable.bg_status_red);
        bgToResourceId.put(WidgetConst.YELLOW, R.drawable.bg_status_yellow);
    }

    //获取布局资源
    @Override
    protected int getInflateResource() {
        return R.layout.widget_status_bar_text;
    }

    //获取获取必填图标视图的布局资源
    @Override
    protected int getRequirdViewResource() {
        return 0;
    }

    //获取标题控件id
    @Override
    protected int getTitleResource() {
        return 0;
    }

    //设置状态值、设置状态背景颜色
    public void setValue(StausModel nodeInfo){
        tvStatus.setText(nodeInfo.getStatusName());
        int statusNum = nodeInfo.getStatusNum();    //状态编码
        int bgResourceId;
        int textColor = R.color.white;      //默认字体白色
        String bgCode = statusAndBg.get(statusNum);
        bgResourceId = bgToResourceId.get(bgCode);
        if (bgResourceId == 0){
            bgResourceId = R.drawable.bg_status_grey;
        }
        if (bgResourceId == R.drawable.bg_status_grey){
            //灰色背景，设置黑色字体
            textColor = R.color.black_100;
        }
        tvStatus.setBackgroundResource(bgResourceId);
        tvStatus.setTextColor(getContext().getResources().getColor(textColor));
    }

    //用户录入信息是否正确
    @Override
    public View isValuesRight() {
        return null;
    }

    //设置是否可编辑
    @Override
    public void setEditable(boolean editable) {

    }

    /**
     * 添加角标背景
     */
    private void addBarBackgroud(String bgCode, int drawableId) {
        bgToResourceId.put(bgCode, drawableId);
    }

    /**
     * 设置状态编号对应的背景颜色
     *
     * @param statusAndBg (0, WidgetConst.GREY)
     */
    public void setStatusAndBg(@NonNull Map<Integer, String> statusAndBg) {
        this.statusAndBg = statusAndBg;
    }
}

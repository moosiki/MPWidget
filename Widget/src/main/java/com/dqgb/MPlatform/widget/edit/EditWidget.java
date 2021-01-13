package com.dqgb.MPlatform.widget.edit;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dqgb.MPlatform.widget.R;
import com.dqgb.MPlatform.widget.R2;
import com.dqgb.MPlatform.widget.common.CommonModule;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 *
 * @Description:    单行编辑框组件
 * @author:         yangqiang-ds
 * @date:           2020/2/12 16:24
 * @Version:        1.0
 */
public class EditWidget extends CommonModule {

    //必选*
    @BindView(R2.id.tv_info_module_datepick_requird)
    TextView requirdText;
    //编辑框
    @BindView(R2.id.editText)
    EditText editText;
    //弹窗里的编辑框控件
    EditText dialogText;
    //输入长度
    private int textLength;
    //输入类型
    private int textType;      // 0:普通字符；1：正整数；2：整数（正负数）3：正数（小数）；4自然数（正负）
    private int decimalDigits;  //小数位数
    //组件标题
    private String title;
    private String hint = "";
    //是否自动弹出输入框
    private boolean autoFocus = false;
    //输入回调
    EditCallBack callBack;

    public EditWidget(Context context) {
        super(context);
    }

    public EditWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取attrs资源
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.widget_edit);
        textLength = typedArray.getInt(R.styleable.widget_edit_textLenght, 100);
        textType = typedArray.getInt(R.styleable.widget_edit_textType, 0);
        decimalDigits = typedArray.getInt(R.styleable.widget_edit_dicimal_digits, 2);
        hint = typedArray.getString(R.styleable.widget_edit_textHint);
        autoFocus = typedArray.getBoolean(R.styleable.widget_edit_auto_focus, false);
        //限制输入内容
        initEditText(editText);
    }

    public interface EditCallBack{
        void onInputDone(String inputValue);
    }

    /**
     * 获取布局资源
     */
    @Override
    protected int getInflateResource() {
        return R.layout.widget_edit;
    }

    /**
     * 获取必填图标视图的布局资源
     */
    @Override
    protected int getRequirdViewResource() {
        return R.id.tv_info_module_datepick_requird;
    }

    /**
     * 获取标题控件id
     */
    @Override
    protected int getTitleResource() {
        return R.id.tv_widget_edit_title;
    }

    /**
     * @return 用户录入信息是否正确
     */
    @Override
    public View isValuesRight() {
        if (isRequired() && "".equals(getContent())) {
            ToastUtils.showLong(errorTips);
            return this;
        }
        return null;
    }

    /**
     * 设置是否可编辑
     */
    @Override
    public void setEditable(boolean editable) {
        if (editable){
            editText.setEnabled(editable);
        }else {
            setTextEditable(editable);
        }
        if (editable){
            titleView.setTextColor(getResources().getColor(R.color.black_85));
            editText.setTextColor(getResources().getColor(R.color.black_65));
        }else{
            titleView.setTextColor(getResources().getColor(R.color.black_65));
            editText.setTextColor(getResources().getColor(R.color.black_85));
            requirdText.setVisibility(INVISIBLE);
        }
    }

    //设置输入框不可编辑，但可以响应点击事件
    public void setTextEditable(boolean editable) {
        editText.setCursorVisible(editable);        //不显示光标
        editText.setFocusable(editable);                //失去焦点
        editText.setTextIsSelectable(editable);         //文字不可选
        if (!editable){
            editText.setHint(null);
        }

    }

    //获取用户输入内容
    public String getContent() {
        return editText.getText().toString();
    }
    //设置输入内容
    public void setContent(String content) {
        editText.setText(content);
    }

    //限制输入内容
    private void initEditText(final EditText editWidget) {
        //设置是否自动弹出输入框
        if (autoFocus){
            editWidget.setFocusable(true);
            editWidget.setFocusableInTouchMode(true);
            editWidget.requestFocus();
            Timer timer = new Timer();
            timer.schedule(new TimerTask()
                           {
                               public void run()
                               {
                                   InputMethodManager inputManager =
                                           (InputMethodManager)editWidget.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                   inputManager.showSoftInput(editWidget, 0);
                               }
                           },
                    500);
        }
        setTitle(moduleTitle);
        editWidget.setTextColor(textColor);
        editWidget.getPaint().setTextSize(textSize);
        if (!StringUtils.isEmpty(hint)){
            editWidget.setHint(hint);
            editWidget.setHintTextColor(hintColor);
        }
        if (textType == 1) {                        //设置只能输入正整数
            editWidget.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else if (textType == 2) {                 //设置只能输入整数（正负数）
            editWidget.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        }else if (textType == 3) {                 //设置只能输入正数（小数）
            editWidget.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }else if (textType == 4) {                 //设置只能输入4自然数（正负）
            editWidget.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        }
        editWidget.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //限制输入内容的长度
                if (charSequence.length() > textLength) {
                    charSequence = charSequence.toString().subSequence(0, textLength);
                    editWidget.setText(charSequence);
                    editWidget.setSelection(charSequence.length());
                    ToastUtils.showLong("输入字数不能超过" + textLength);
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {
                //整数
                if (textType == 1) {
                    String input=editable.toString();
                    //最高位为0时，继续输入则删除最高位
                    if (input.length() >=2) {
                        if ("0".equals(input.substring(0, 1))) {
                            editable.delete(0, 1);
                        }
                    }

                }
                //小数或自然数
                if (textType == 3 || textType == 4) {
                    String editStr = editable.toString().trim();
                    int posDot = editStr.indexOf(".");
                    //不允许输入3位小数,超过三位就删掉
                    if (posDot < 0) {
                        return;
                    }
                    if (editStr.length() - posDot - 1 > decimalDigits) {
                        editable.delete(posDot + decimalDigits + 1, posDot + decimalDigits + 2);
                    }
                }
            }
        });
        editWidget.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (callBack != null){
                        callBack.onInputDone(editWidget.getText().toString().trim());
                    }
                }
            }
        });
    }
    //设置输入数字的范围
    public void setNumberRange(int min, int max){
        editText.setFilters(new InputFilter[]{new InputFilterMinMax(min,max)});
    }

    /**
     * 获取控件数据
     * @return 控件数据
     */
    @Override
    public Object getData() {
        return editText.getText().toString();
    }

    /**
     * 设置控件数据
     */
    @Override
    public void setData(Object data) {
        editText.setText((String) data);
    }

    public void  addOnClickListener(View.OnClickListener listener){
        editText.setOnClickListener(listener);
    }

    public void setCallBack(EditCallBack callBack) {
        this.callBack = callBack;
    }

    public void setAutoFocus(boolean autoFocus) {
        this.autoFocus = autoFocus;
    }
}

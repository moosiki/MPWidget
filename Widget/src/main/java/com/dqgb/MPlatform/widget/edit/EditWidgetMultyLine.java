package com.dqgb.MPlatform.widget.edit;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dqgb.MPlatform.widget.R;
import com.dqgb.MPlatform.widget.R2;
import com.dqgb.MPlatform.widget.common.CommonModule;

import butterknife.BindView;

/**
 *
 * @Description:    多行编辑框组件
 * @author:         yangqiang-ds
 * @date:           2020/2/12 16:24
 * @Version:        1.0
 */
public class EditWidgetMultyLine extends CommonModule {

    //必选*
    @BindView(R2.id.tv_info_module_datepick_requird)
    TextView requirdText;
    //编辑框
    @BindView(R2.id.editText)
    EditText editText;

    @BindView(R2.id.tv_widget_edit_title)
    TextView textView;


    //输入长度
    private int textLength;
    //输入类型
    private int textType;      // 0:普通字符；1：正整数；2：整数（正负数）3：正数（小数）；4自然数（正负）
    private int decimalDigits;  //小数位数
    //组件标题
    private String title;
    private String hint = "";
    //弹窗里的编辑框控件
    EditText dialogText;

    public EditWidgetMultyLine(Context context) {
        super(context);
    }

    public EditWidgetMultyLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取attrs资源
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.widget_edit);
        textLength = typedArray.getInt(R.styleable.widget_edit_textLenght, 100);
        textType = typedArray.getInt(R.styleable.widget_edit_textType, 0);
        decimalDigits = typedArray.getInt(R.styleable.widget_edit_dicimal_digits, 2);
        title = typedArray.getString(R.styleable.widget_edit_title_edit);
        hint = typedArray.getString(R.styleable.widget_edit_textHint);
        //限制输入内容
        initEditText(editText);
    }

    /**
     * 获取布局资源
     */
    @Override
    protected int getInflateResource() {
        return R.layout.widget_edit_multy_line;
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
    }

    //获取用户输入内容
    public String getContent() {
        return editText.getText().toString();
    }
    //设置输入内容
    public void setContent(String content) {
        editText.setText(content);
    }

    /**
     * 获取控件数据
     * @return 输入数据
     */
    @Override
    public Object getData() {
        return editText.getText().toString();
    }

    /**
     * 设置控件数据,将后台获取的数据设置到控件上展示
     *
     * @param data
     */
    @Override
    public void setData(Object data) {
        editText.setText((String)data);
    }

    //限制输入内容
    private void initEditText(EditText editWidget) {
        if (StringUtils.isEmpty(title)){
            requirdText.setVisibility(GONE);
            textView.setVisibility(GONE);
        }
        setTitle(title);
        editWidget.setTextColor(textColor);
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
                    editText.setText(charSequence);
                    editText.setSelection(charSequence.length());
                    ToastUtils.showLong("输入字数不能超过" + textLength);
                }

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (textType == 1) {
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
    }
    //设置输入数字的范围
    public void setNumberRange(int min, int max){
        editText.setFilters(new InputFilter[]{new InputFilterMinMax(min,max)});
    }

    public void  addOnClickListener(View.OnClickListener listener){
        editText.setOnClickListener(listener);
    }


}

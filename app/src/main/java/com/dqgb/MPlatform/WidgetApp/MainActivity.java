package com.dqgb.MPlatform.WidgetApp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.dqgb.MPlatform.widget.core.ActiveModule;
import com.dqgb.MPlatform.widget.core.InputModule;
import com.dqgb.MPlatform.widget.edit.EditWidget;
import com.dqgb.MPlatform.widget.group.ValidatePriorDecorate;
import com.dqgb.MPlatform.widget.input.InputColumnModule;
import com.dqgb.MPlatform.widget.input.InputPriorDecorate;
import com.dqgb.MPlatform.widget.input.InputRowModule;
import com.dqgb.MPlatform.widget.view.HorizontalTitle;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    EditWidget editWidget;
    LinearLayout other;
    InputModule inputRowModule1;
    InputModule inputRowModule2;
    InputModule inputRowModule3;
    InputModule inputColumnModule;

    HorizontalTitle titleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    /*    inputRowModule1 = findViewById(R.id.row_input1);
        inputRowModule2 = findViewById(R.id.row_input2);
        titleView = findViewById(R.id.ht_1);*/
//        inputRowModule2 = findViewById(R.id.row_input2);
//        inputRowModule3 = findViewById(R.id.row_input3);
//        inputColumnModule = findViewById(R.id.col_input);

/*        inputRowModule1.initModule();
        inputRowModule2.initModule();*/

     /*   View v = titleView.getChildAt(1);
        titleView.removeViewAt(1);
        titleView.insertContent(v);*/


       /* other = findViewById(R.id.ll_container_other);

        InputModule inputModule = new InputRowModule(this,null);
        InputPriorDecorate priorDecorate = new InputPriorDecorate(this, inputModule, new WeakReference<InputModule>(inputRowModule2));
        other.addView(priorDecorate);*/

    }


}

package com.cesecsh.shoppingcar.helpview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cesecsh.shoppingcar.R;

import java.util.ArrayList;
import java.util.List;

public class HelpDialogActivity extends AppCompatActivity {

    private HelpView helpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_dialog);
        List<HelpContent> helps = new ArrayList<>();
        HelpContent helpContent = new HelpContent("+", "点击“+”，可以增加家庭，这样不仅可以给自己家缴费还可以帮父母家缴费。");
        helps.add(helpContent);
        helpContent = new HelpContent("停车卡", "在“我的-我的车辆”里添加车牌号，就可以在缴费里缴停车费了，缴完费就可以在首页看到停车卡了，停车卡是一卡一车。");
        helps.add(helpContent);
        helpContent = new HelpContent("停车卡", "在“我的-我的车辆”里添加车牌号，就可以在缴费里缴停车费了，缴完费就可以在首页看到停车卡了，停车卡是一卡一车。");
        helps.add(helpContent);
        helpContent = new HelpContent("停车卡", "在“我的-我的车辆”里添加车牌号，就可以在缴费里缴停车费了，缴完费就可以在首页看到停车卡了，停车卡是一卡一车。");
        helps.add(helpContent);
        helpView = new HelpView(helps, this);
    }

    public void onClick(View view) {
        helpView.show();
    }

    public void onDismiss(View view) {
        if (helpView.isShowing()) {
            helpView.dismiss();
        }
    }
}

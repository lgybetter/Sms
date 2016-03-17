package com.lgybetter.smsproject.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import com.lgybetter.smsproject.R;

public class SmsMainActivity extends TabActivity {

    private TabHost tabhost;
    private RadioGroup main_radiogroup;
    private RadioButton tab_message , tab_address_book , tab_mail , tab_settring;
    private Button add_sms , setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.sms_main_view);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.sms_message_title);


        //获取主界面上的所有按钮
        main_radiogroup = (RadioGroup)findViewById(R.id.main_radiogroup);
        tab_message = (RadioButton)findViewById(R.id.tab_messsage);
        tab_address_book = (RadioButton)findViewById(R.id.tab_address_book);
        tab_mail = (RadioButton)findViewById(R.id.tab_mail);
        add_sms =  (Button)findViewById(R.id.add_sms);

        //向TabWidget添加Tab
        tabhost = getTabHost();
        tabhost.addTab(tabhost.newTabSpec("tag1").setIndicator("0").setContent(new Intent(this,SmsMessageActivity.class)));
        tabhost.addTab(tabhost.newTabSpec("tag2").setIndicator("1").setContent(new Intent(this,SmsAddressBookActivity.class)));
        tabhost.addTab(tabhost.newTabSpec("tag3").setIndicator("2").setContent(new Intent(this, SmsMailActivity.class)));

        //设置监听事件
        main_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                //setCurrentTab 通过标签索引设置当前显示的内容
                //setCurrentTabByTag 通过标签名设置当前显示的内容
                switch (checkedId) {
                    case R.id.tab_messsage:
                        tabhost.setCurrentTab(0);
                        break;
                    case R.id.tab_address_book:
                        tabhost.setCurrentTab(1);
                        break;
                    case R.id.tab_mail:
                        tabhost.setCurrentTab(2);
                        break;
                }
            }
        });
        add_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SmsMainActivity.this , SmsAddNewMessageActivity.class);
                startActivity(intent);
            }
        });
    }
}

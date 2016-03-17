package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.lgybetter.smsproject.R;

import beanclass.MailUser;

/**
 * Created by Administrator on 2016/3/17.
 */
public class SmsMailListActivity extends Activity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail_view);
        listView = (ListView)findViewById(R.id.mail_list);
    }
}

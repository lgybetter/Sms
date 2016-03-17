package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.lgybetter.smsproject.R;

/**
 * Created by Administrator on 2015/12/8.
 */
public class SmsDefinedGroupActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_defined_group_view);
    }
}

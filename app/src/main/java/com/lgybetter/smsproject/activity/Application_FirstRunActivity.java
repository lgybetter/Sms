package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.lgybetter.smsproject.R;

/**
 * Created by Administrator on 2016/2/22.
 */
public class Application_FirstRunActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.application_run_view);
        //判断程序是否是第一次运行，是的话加载数据库
        final SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Intent activity;
                if (isFirstRun) {
                    Log.d("debug", "第一次运行");
                    editor.putBoolean("isFirstRun", false);
                    editor.commit();
                    activity = new Intent(Application_FirstRunActivity.this, GuideActivity.class);
                }
                else {
                    activity = new Intent(Application_FirstRunActivity.this, SmsMainActivity.class);
                }
                Application_FirstRunActivity.this.startActivity(activity);
                Application_FirstRunActivity.this.finish();
            }
        }, 400);
    }
}

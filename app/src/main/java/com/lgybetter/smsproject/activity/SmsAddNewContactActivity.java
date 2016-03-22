package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.lgybetter.smsproject.R;

/**
 * Created by Administrator on 2016/3/22.
 */
public class SmsAddNewContactActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sms_add_new_contact_view);
        Button  bt_return = (Button)findViewById(R.id.bt_return);
        Button add_data = (Button)findViewById(R.id.add_data);
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}

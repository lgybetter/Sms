package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lgybetter.smsproject.R;

/**
 * Created by Administrator on 2016/3/22.
 */
public class SmsSendMailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sms_send_mail_view);
        EditText text = (EditText)findViewById(R.id.et_send_mail_text);
        EditText subject = (EditText)findViewById(R.id.et_send_mail_subject);
        EditText address = (EditText)findViewById(R.id.et_send_mail_address);
        Button cancle = (Button)findViewById(R.id.cancel_action);
        Button send = (Button)findViewById(R.id.bt_send_mail);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "邮件已发送", Toast.LENGTH_LONG).show();
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;

import com.lgybetter.smsproject.R;

/**
 * Created by Administrator on 2016/3/18.
 */
public class SmsMailContentActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mail_content_view);
        Button bt_return = (Button)findViewById(R.id.bt_return);
        WebView webView = (WebView)findViewById(R.id.webView);
        Intent data = getIntent();
        String text = data.getStringExtra("mail_text");
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.loadData(text, "text/html;charset=UTF-8", null);
        bt_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

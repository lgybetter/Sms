package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;

import com.lgybetter.smsproject.R;
import com.util.mail.Email;
import com.util.mail.MailProject;

import java.util.List;

/**
 * Created by Administrator on 2015/12/7.
 */
public class SmsMailActivity extends Activity {
    List<Email> emailList;
    int  nowemail,emailn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mail_view);
//        Button bt=(Button)findViewById(R.id.button);
//        Button bt2 = (Button)findViewById(R.id.button2);
//        Button bt3 = (Button)findViewById(R.id.button3);
//        Button bt4 = (Button)findViewById(R.id.button4);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MailProject mp=new MailProject(getApplicationContext());
//                mp.setUser("15521132074@163.com", "lgy6695464");
//                mp.sendMail("1007942185@qq.com","龙腾oba","快点找到腾嫂");
//                mp.setListFlashListener(new MailProject.OnListFlashListener() {
//                    @Override
//                    public void onFlash(List<Email> emails) {
//                        for(int i = 0; i < emails.size(); i ++)
//                        {
//                            Log.i("info", emails.get(i).getAddresser());
//                            Log.i("info",emails.get(i).getDate());
//                            Log.i("info",emails.get(i).getSubject());
//                            Log.i("info",emails.get(i).getText());
//                            Log.i("info",emails.get(i).getUid());
//                        }
//                        emailList.addAll(emails);
//                        emailn=emailList.size();
//                    }
//                });
//                emailList=mp.getList();
//                emailn=emailList.size();
//            }
//        });
//
//        bt2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nowemail = Math.max(0, nowemail - 1);
//                WebView wv = (WebView) findViewById(R.id.webView);
//                wv.getSettings().setDefaultTextEncodingName("utf-8");
//                wv.loadData(emailList.get(nowemail).getText(), "text/html;charset=UTF-8", null);
//            }
//        });
//
//        bt3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nowemail = Math.min(emailn - 1, nowemail + 1);
//                WebView wv = (WebView) findViewById(R.id.webView);
//                wv.getSettings().setDefaultTextEncodingName("utf-8");
//                wv.loadData(emailList.get(nowemail).getText(), "text/html;charset=UTF-8", null);
//            }
//        });
//
//        bt4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MailProject mp = new MailProject(getApplicationContext());
//                emailList = mp.search("您好");
//                emailn = emailList.size();
//            }
//        });
    }
}


package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.lgybetter.smsproject.R;
import com.util.mail.Email;
import com.util.mail.MailDBHelper;
import com.util.mail.MailProject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import beanclass.MailUser;

/**
 * Created by Administrator on 2015/12/7.
 */
public class SmsMailActivity extends Activity {
    private List<Email> emails;
    private List<HashMap<String,Object>> emails_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mail_view);
        Button bt_send_mail = (Button)findViewById(R.id.bt_send_mail);
        emails = new ArrayList<>();
        emails_items = new ArrayList<>();
        ListView listView = (ListView) findViewById(R.id.mail_list);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        MailDBHelper mailDBHelper = new MailDBHelper(getApplicationContext());
        MailUser mailUser = mailDBHelper.getUser();
        if(mailUser != null) {
            progressBar.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            MailProject mp = new MailProject(getApplicationContext());
            emails = mp.getList();
            SimpleAdapter simpleAdapter = new SimpleAdapter(this,getMailData(),R.layout.mail_list_item,new String[]{"address","subject","title","date"},new int[]{R.id.mail_address,R.id.mail_subject,R.id.mail_title,R.id.mail_date});
            listView.setAdapter(simpleAdapter);
        }else {
            progressBar.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(getApplicationContext(), SmsMailLoginAcitivty.class);
            startActivity(intent);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),SmsMailContentActivity.class);
                intent.putExtra("mail_text",emails.get(position).getText());
                startActivity(intent);
            }
        });
        bt_send_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SmsSendMailActivity.class);
                startActivity(intent);
            }
        });
    }
    List<HashMap<String,Object>> getMailData() {
        for(int i = 0; i < emails.size(); i ++)
        {
            HashMap<String, Object> m = new HashMap<>();
            m.put("address",emails.get(i).getAddresser());
            m.put("subject",emails.get(i).getSubject());
            m.put("title",emails.get(i).getUid());
            m.put("date",emails.get(i).getDate());
            emails_items.add(m);
        }
        return emails_items;
    }
}


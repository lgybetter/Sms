package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.lgybetter.smsproject.R;
import com.util.mail.MailProject;


/**
 * Created by Administrator on 2016/3/17.
 */
public class SmsMailLoginAcitivty extends Activity {
    private EditText Account,Password;
    private CheckBox SavePassword;
    private Button Login;
    private String password , account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mail_login_view);
        Account = (EditText)findViewById(R.id.et_mail_account);
        Password = (EditText)findViewById(R.id.et_password);
        SavePassword = (CheckBox)findViewById(R.id.save_password);
        Login = (Button)findViewById(R.id.bt_mail_login);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = Password.getText().toString();
                account = Account.getText().toString();
                if(password.length() == 0 || account.length() == 0) {
                    Toast.makeText(getApplicationContext(), "请输入邮箱帐号和密码",Toast.LENGTH_SHORT).show();
                } else {
                    MailProject mp=new MailProject(getApplicationContext());
                    if(mp.setUser(account, password)) {
                        Intent intent = new Intent(getApplicationContext(),SmsMailListActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),"输入的帐号或密码不正确，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}

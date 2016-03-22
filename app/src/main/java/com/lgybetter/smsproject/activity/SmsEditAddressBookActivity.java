package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lgybetter.smsproject.R;

import beanclass.Person;

/**
 * Created by Administrator on 2016/2/20.
 */
public class SmsEditAddressBookActivity extends Activity {
    private EditText et_name , et_number;
    private TextView tv_name , tv_number;
    private Button edit_data;
    private int ID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit_address_book_view);
        et_name = (EditText)findViewById(R.id.et_name);
        et_number = (EditText)findViewById(R.id.et_number);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_number = (TextView)findViewById(R.id.tv_number);
        edit_data = (Button)findViewById(R.id.edit_data);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final Person person = (Person)bundle.getSerializable("person_data");
        assert person != null;
        ID = person.getID();
        tv_name.setText(person.getName());
        tv_number.setText(person.getNumber());
        edit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_name.length() == 0 && et_number.length() ==0) {
                    Toast.makeText(SmsEditAddressBookActivity.this,"请输入所要编辑的信息",Toast.LENGTH_SHORT).show();
                }
                else {
//                    final SQLiteDatabase db = openOrCreateDatabase("MailUser.db",MODE_PRIVATE,null);
//                    String name = et_name.getText().toString();
//                    String[] pinyin = PinyinHelper.toHanyuPinyinStringArray('刘');
                    Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

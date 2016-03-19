package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lgybetter.smsproject.R;


import java.text.SimpleDateFormat;
import java.util.Date;

import beanclass.AddContactsReturnData;
import beanclass.AddMessageListReturn;
import beanclass.Message;
import beanclass.TemplatePosition;

/**
 * Created by Administrator on 2015/12/7.
 */
public class SmsAddNewMessageActivity extends Activity {
    private EditText et_AddContacts;
    private Button bt_AddContacts;
    private EditText et_WriteMessage;
    private Button bt_SendMessage;
    private Button choose_template;
    private Button returnButton;
    private String name;
    private String number;
    private AddMessageListReturn addMessageListReturn = null;
    private String [][][] template = new String[][][]{
            /**
             * 商务工作
             * */
        {
            {"您好,","您的快递已送达",},{"你好","请所有员工于,","麻烦相互通知，若有不便或其他理由无法出席者，请另外联系。"}
        },
            /**
             * 校园生活
             * */
        {
            {""},{},{}
        },
            /**
             * 节日祝福
             * */
        {
            {""},{},{}
        },
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_new_message_view);
        returnButton = (Button)findViewById(R.id.bt_return);
        et_AddContacts = (EditText)findViewById(R.id.et_add_contacts);
        bt_AddContacts = (Button)findViewById(R.id.bt_add_contacts);
        et_WriteMessage = (EditText)findViewById(R.id.et_write_message);
        bt_SendMessage = (Button)findViewById(R.id.bt_send_message);
        choose_template = (Button)findViewById(R.id.choose_template);
        bt_AddContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SmsAddNewMessageActivity.this,AddNewContactsToSendActivity.class);
                startActivityForResult(intent, 200);
            }
        });

        bt_SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_AddContacts.getText()))
                {
                    Toast.makeText(SmsAddNewMessageActivity.this,"请输入发送的号码",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    final SQLiteDatabase db = openOrCreateDatabase("MailUser.db", MODE_PRIVATE, null);
                    String message_text = et_WriteMessage.getText().toString();
                    SmsManager smsManager = SmsManager.getDefault();
                    String[] name_array = name.split(";");
                    String[] number_array = number.split(";");
                    addMessageListReturn = new AddMessageListReturn();
                    for(int i=0; i<number_array.length; i++){
                        String text = "你好" + name_array[i] + message_text;
//                        smsManager.sendTextMessage(number_array[i], null, text, null, null);
                        ContentValues contentValues = new ContentValues();
                        ContentValues contentValues1 = new ContentValues();
                        contentValues1.put("date", System.currentTimeMillis());
                        contentValues1.put("read",1);
                        contentValues1.put("type",2);
                        contentValues1.put("address",number_array[i]);
                        contentValues1.put("body", text);
                        getContentResolver().insert(Uri.parse("content://sms"), contentValues1);
                        contentValues.put("person_phoneNum", number_array[i]);
                        contentValues.put("person_name", name_array[i]);
                        contentValues.put("date", String.valueOf(System.currentTimeMillis()));
                        contentValues.put("message_body", text);
                        contentValues.put("type", 2);
                        contentValues.put("read", 1);
                        contentValues.put("contact_Id",0);
                        contentValues.put("message_id", 0);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date(Long.parseLong(String.valueOf(System.currentTimeMillis())));
                        String message_date = dateFormat.format(date);
                        Message message = new Message(0,0,number_array[i],0,text,message_date,2,1,name_array[i]);
                        addMessageListReturn.addMessage(message);
                        db.insert("messagetb", null, contentValues);
                        contentValues.clear();
                        contentValues1.clear();
                    }
                    db.close();
                    Intent data = new Intent();
                    data.putExtra("return_message_list", addMessageListReturn);
                    setResult(60, data);
                    Toast.makeText(SmsAddNewMessageActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    et_AddContacts.setText("");
                    et_WriteMessage.setText("");
                }
            }
        });
        choose_template.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SmsAddNewMessageActivity.this,SelectTemplateActivity.class);
                startActivityForResult(intent, 200);
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("return_message_list",addMessageListReturn);
                setResult(60,data);
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setTemplateReturn(data);
        setContactsReturn(data);
    }
    void setContactsReturn(Intent data)
    {
        AddContactsReturnData returnContactsData = (AddContactsReturnData) data.getSerializableExtra("result");
        if(returnContactsData != null)
        {
            if(TextUtils.isEmpty(et_AddContacts.getText()))
            {
                number = returnContactsData.getNumber();
                name = returnContactsData.getName();
            }
            else if(et_AddContacts.getText().charAt(et_AddContacts.getText().length() - 1) == ';')
            {
                number = et_AddContacts.getText() + returnContactsData.getNumber();
                name = et_AddContacts.getText() + returnContactsData.getName();
            }
            else
            {
                number = et_AddContacts.getText() + ";" + returnContactsData.getNumber();
                name = et_AddContacts.getText() + ";" + returnContactsData.getName();
            }
            et_AddContacts.setText(name);
        }
    }

    public void setTemplateReturn(Intent data) {
        TemplatePosition templateposition = (TemplatePosition)data.getSerializableExtra("template_result");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            Intent data = new Intent();
            data.putExtra("return_message_list", addMessageListReturn);
            setResult(60, data);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

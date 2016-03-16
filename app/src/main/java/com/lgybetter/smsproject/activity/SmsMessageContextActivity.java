package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.lgybetter.smsproject.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import beanclass.Message;


/**
 * Created by Administrator on 2015/12/9.
 */
public class SmsMessageContextActivity extends Activity {
    private List<Map<String,Object>> listItems;
    private List<Message>Message_List;
    private SimpleAdapter simpleAdapter;
    private ListView listView;
    private EditText WritteSms;
    private Button SendSms;
    private Message message_add = null;
    private Button returnButton;
    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.message_context_view);
        listView = (ListView)findViewById(R.id.message_context);
        WritteSms = (EditText)findViewById(R.id.et_write_message);
        SendSms = (Button)findViewById(R.id.bt_send_message);
        returnButton = (Button)findViewById(R.id.bt_return);
        listItems = new ArrayList<>();
        Message_List = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final Message message = (Message)bundle.getSerializable("message_data");
        assert message != null;
        final SQLiteDatabase db = openOrCreateDatabase("user.db", MODE_PRIVATE, null);
        Cursor cursor = db.query("messagetb", null, "person_phoneNum=?", new String[]{message.getPerson_number()}, null, null, "date desc");
        if(cursor.moveToFirst())
        {
            do
            {
                String person_number = cursor.getString(cursor.getColumnIndex("person_phoneNum"));
                int ID = cursor.getInt(cursor.getColumnIndex("_id"));
                int message_id = cursor.getInt(cursor.getColumnIndex("message_id"));
                int person_id = cursor.getInt(cursor.getColumnIndex("contact_Id"));
                String message_body = cursor.getString(cursor.getColumnIndex("message_body"));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndex("date"))));
                String message_date = dateFormat.format(date);
                int message_type = cursor.getInt(cursor.getColumnIndex("type"));
                int message_read = cursor.getInt(cursor.getColumnIndex("read"));
                String person_name = cursor.getString(cursor.getColumnIndex("person_name"));
                Message message_item = new Message(ID,message_id,person_number,person_id,message_body,message_date,message_type,message_read,person_name);
                Message_List.add(message_item);
                Map<String,Object>m = new HashMap<>();
                m.put("date",message_date);
                m.put("body",message_body);
                listItems.add(m);
            }while(cursor.moveToNext());
        }
        simpleAdapter = new SimpleAdapter(this,listItems,R.layout.message_context_item,new String[]{"date","body"},new int[]{R.id.message_date,R.id.message_body});
        listView.setAdapter(simpleAdapter);
        SendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                if (TextUtils.isEmpty(WritteSms.getText())) {
                    Toast.makeText(SmsMessageContextActivity.this, "请输入发送的号码", Toast.LENGTH_SHORT).show();
                } else {
                    final SQLiteDatabase db = openOrCreateDatabase("user.db", MODE_PRIVATE, null);
                    String message_text = WritteSms.getText().toString();
                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(message.getPerson_number(), null,message_text, null, null);
//                    Log.i("info","发送号码为：" + message.getPerson_number() + " 发送的内容为：" + message_text);
                    ContentValues contentValues = new ContentValues();
                    ContentValues contentValues1 = new ContentValues();
                    contentValues1.put("date", String.valueOf(System.currentTimeMillis()));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = new Date(Long.parseLong(String.valueOf(System.currentTimeMillis())));
                    String message_date = dateFormat.format(date);
                    contentValues1.put("read", 1);
                    contentValues1.put("type", 2);
                    contentValues1.put("address", message.getPerson_number());
                    contentValues1.put("body", message_text);
                    getContentResolver().insert(Uri.parse("content://sms"), contentValues1);
                    contentValues.put("person_phoneNum", message.getPerson_number());
                    contentValues.put("person_name", message.getPerson_name());
                    contentValues.put("date", String.valueOf(System.currentTimeMillis()));
                    contentValues.put("message_body", message_text);
                    contentValues.put("type", 2);
                    contentValues.put("read", 1);
                    contentValues.put("contact_Id", 0);
                    contentValues.put("message_id", 0);
                    db.insert("messagetb", null, contentValues);
                    contentValues.clear();
                    contentValues1.clear();
                    Map<String, Object> m = new HashMap<>();
                    m.put("date", message_date);
                    m.put("body", message_text);
                    listItems.add(0, m);
                    simpleAdapter.notifyDataSetChanged();
//public Message(int ID,int message_id,String person_number,int person_id,String message_body,String message_date,int message_type,int message_read,String person_name)
                    message_add = new Message(0, 0, message.getPerson_number(), message.getPerson_id(), message_text, message_date, 2, 1, message.getPerson_name());
                    Toast.makeText(SmsMessageContextActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    WritteSms.setText("");
//                    AddContactsReturnData returnData = new AddContactsReturnData(name_return,number_return,id_return);
                    data.putExtra("context_result", message_add);
                    setResult(50, data);
                }
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("context_result",message_add);
                setResult(60,data);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            Intent data = new Intent();
            data.putExtra("context_result",message_add);
            setResult(60,data);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

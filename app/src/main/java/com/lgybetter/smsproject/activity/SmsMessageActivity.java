package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lgybetter.smsproject.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beanclass.Message;

/**
 * Created by Administrator on 2015/12/7.
 */
public class SmsMessageActivity extends Activity {
    private List<Message> messagesList = new ArrayList<>();
    private SimpleAdapter simpleAdapter;
    private List<Map<String , Object>> listItems;
    private Button addSms;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sms_view);
        addSms = (Button)findViewById(R.id.add_sms);
        ListView listView = (ListView) findViewById(R.id.listView);
        listItems = new ArrayList<>();
        simpleAdapter = new SimpleAdapter(this,getData(),R.layout.message_item,new String[]{"name","phonenumber","body","date"} , new int[]{R.id.name , R.id.number , R.id.body , R.id.date});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SmsMessageActivity.this, SmsMessageContextActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("message_data", messagesList.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 100);
            }
        });
        addSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , SmsAddNewMessageActivity.class);
                startActivity(intent);
            }
        });
    }
    public List<Map<String,Object>> getData()
    {
        Map<String , Object> m;
        final SQLiteDatabase db = openOrCreateDatabase("MailUser.db",MODE_PRIVATE,null);
        Cursor cursor = db.query("messagetb", null, "_id>?", new String[]{"0"}, null, null, "date desc");
        if (cursor.moveToFirst())
        {
            //db.execSQL("create table if not exists messagetb(_id integer primary key autoincrement,person_name text not null,contact_Id integer not null,message_id integer not null,message_body text not null,person_phoneNum text not null,date text not null,type integer not null,read integer not null)");
            //public Message(int ID,int message_id,int person_number,int person_id,String message_body,String message_date,int message_type,int message_read,String person_name)
            do {
                String person_number = cursor.getString(cursor.getColumnIndex("person_phoneNum"));
                if(!number_exit(person_number))
                {
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
                    Message message = new Message(ID,message_id,person_number,person_id,message_body,message_date,message_type,message_read,person_name);
                    m = new HashMap<>();
                    m.put("name",person_name);
                    m.put("phonenumber",person_number);
                    m.put("body",message_body);
                    m.put("date",message_date);
                    listItems.add(m);
                    messagesList.add(message);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listItems;
    }

    private boolean number_exit(String number)
    {
        for(int i = 0; i < messagesList.size(); i++)
        {
            if(number.equals(messagesList.get(i).getPerson_number()))
            {
                System.out.println(number + "存在等于" + messagesList.get(i).getPerson_number());
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Message messageResult = (Message)data.getSerializableExtra("context_result");
//        Log.i("info",messageResult.getPerson_number());
        getResult(data);
    }
    /** AddContactsReturnData returnContactsData = (AddContactsReturnData) data.getSerializableExtra("result");
     if(returnContactsData != null)
     {

     }
     * */
    private void getResult(Intent data) {
        Message messageResult = (Message)data.getSerializableExtra("context_result");
        if(messageResult != null)
        {
            Log.i("info",messageResult.getPerson_number());
            int index = getListIndex(messageResult.getPerson_number());
            HashMap<String , Object> m = new HashMap<>();
            m.put("name",messageResult.getPerson_name());
            m.put("phonenumber",messageResult.getPerson_number());
            m.put("body",messageResult.getMessage_body());
            m.put("date",messageResult.getMessage_date());
            if(index != -1)
            {
                listItems.remove(index);
                messagesList.remove(index);
                listItems.add(0, m);
                messagesList.add(0, messageResult);
                simpleAdapter.notifyDataSetChanged();
            }
            else
            {
                listItems.add(0,m);
                messagesList.add(0,messageResult);
                simpleAdapter.notifyDataSetChanged();
            }
        }
    }

    private int getListIndex(String number)
    {
        for(int i = 0; i < messagesList.size(); i++)
        {
            if(number.equals(messagesList.get(i).getPerson_number()))
            {
                System.out.println(number + "存在等于" + messagesList.get(i).getPerson_number());
                return i;
            }
        }
        return -1;
    }
}

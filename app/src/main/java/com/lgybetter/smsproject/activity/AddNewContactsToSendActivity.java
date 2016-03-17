package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AlphabetIndexer;
import android.widget.Button;
import android.widget.ListView;
import com.lgybetter.smsproject.R;
import java.util.ArrayList;
import java.util.List;


import adapter.SelectContactsAdapter;
import beanclass.AddContactsReturnData;
import beanclass.Person;

/**
 * Created by Administrator on 2016/2/23.
 */
public class AddNewContactsToSendActivity extends Activity {
    private String name_return;
    private String number_return;
    private String id_return;
    /**
     * 联系人ListView
     */
    private ListView contactsListView;

    /**
     * 联系人列表适配器
     */
    private SelectContactsAdapter adapter;

    /**
     * 用于进行字母表分组
     */
    private AlphabetIndexer indexer;

    /**
     * 存储所有手机中的联系人
     */
    private List<Person> contacts = new ArrayList<Person>();

    /**
     * 定义字母表的排序规则
     */
    private String alphabet = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //private List<Map<String,String>> listItems;
    private Button No , Yes;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_new_contacts_to_send_view);
        contactsListView = (ListView)findViewById(R.id.add_contacts_sms_list);
        No = (Button)findViewById(R.id.bt_no_select);
        Yes = (Button)findViewById(R.id.bt_yes_select);
        name_return = "";
        number_return = "";
        id_return = "";
        adapter = new SelectContactsAdapter(AddNewContactsToSendActivity.this,R.layout.add_new_contacts_item,contacts);
        final SQLiteDatabase db = openOrCreateDatabase("MailUser.db",MODE_PRIVATE,null);
        Cursor cursor = db.query("persontb", null, "_id>?", new String[]{"0"}, null, null, "_id");
        if (cursor.moveToFirst())
        {
            do {
                String name = cursor.getString(cursor.getColumnIndex("displayName"));
                String sortKey = getSortKey(cursor.getString(cursor.getColumnIndex("sortKey")));
                int ID = cursor.getInt(cursor.getColumnIndex("_id"));
                int ContactID = cursor.getInt(cursor.getColumnIndex("contactId"));
                String number = cursor.getString(cursor.getColumnIndex("phoneNum"));
                Person contact = new Person(name,sortKey,number,ID,ContactID,false);
                contacts.add(contact);
            } while (cursor.moveToNext());
        }
        startManagingCursor(cursor);
        indexer = new AlphabetIndexer(cursor, cursor.getColumnIndex("sortKey"), alphabet);
        adapter.setIndexer(indexer);
        if (contacts.size() > 0) {
            setupContactsListView();
        }
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                for(int i = 0; i < contacts.size(); i++)
                {
                    if(contacts.get(i).isStatus())
                    {
                        name_return = name_return + contacts.get(i).getName() + ";";
                        number_return = number_return + contacts.get(i).getNumber() + ";";
                        id_return = id_return + contacts.get(i).getID() + ";";
                    }
                }
                AddContactsReturnData returnData = new AddContactsReturnData(name_return,number_return,id_return);
                data.putExtra("result", returnData);
                setResult(30, data);
                finish();
            }
        });
        No.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                AddContactsReturnData returnData = new AddContactsReturnData(name_return,number_return,id_return);
                data.putExtra("result", returnData);
                setResult(40, data);
                finish();
            }
        });
    }
    private void setupContactsListView() {
        contactsListView.setAdapter(adapter);
    }

    /**
     * 获取sort key的首个字符，如果是英文字母就直接返回，否则返回#。
     *
     * @param sortKeyString
     *            数据库中读取出的sort key
     * @return 英文字母或者#
     */
    private String getSortKey(String sortKeyString) {
        String key = sortKeyString.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        }
        return "#";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            Intent data = new Intent();
            AddContactsReturnData returnData = new AddContactsReturnData(name_return,number_return,id_return);
            data.putExtra("result", returnData);
            setResult(40, data);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

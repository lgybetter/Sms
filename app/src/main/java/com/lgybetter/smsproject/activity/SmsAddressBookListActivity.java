package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AlphabetIndexer;
import android.widget.ListView;

import com.lgybetter.smsproject.R;

import java.util.ArrayList;
import java.util.List;

import adapter.PersonAdapter;
import beanclass.Person;


/**
 * Created by Administrator on 2016/1/20.
 */
public class SmsAddressBookListActivity extends Activity {

    /**
     * 联系人ListView
     */
    private ListView contactsListView;

    /**
     * 联系人列表适配器
     */
    private PersonAdapter adapter;

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


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_book_list_view);
        adapter = new PersonAdapter(this, R.layout.contact_item, contacts);
        contactsListView = (ListView) findViewById(R.id.contacts_list_view);
        final SQLiteDatabase db = openOrCreateDatabase("user.db",MODE_PRIVATE,null);
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
    }

    private void setupContactsListView() {
        contactsListView.setAdapter(adapter);
        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SmsAddressBookListActivity.this ,SmsEditAddressBookActivity.class );
                Bundle bundle = new Bundle();
                bundle.putSerializable("person_data",contacts.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

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
}



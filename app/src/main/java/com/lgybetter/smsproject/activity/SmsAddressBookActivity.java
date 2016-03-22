package com.lgybetter.smsproject.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;

import com.lgybetter.smsproject.R;

/**
 * Created by Administrator on 2015/12/7.
 */
public class SmsAddressBookActivity extends TabActivity {
    private TabHost tabhost;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.address_book_view);
        RadioGroup book_select = (RadioGroup) findViewById(R.id.book_select);
        RadioButton address_book = (RadioButton) findViewById(R.id.address_book);
        RadioButton define_group = (RadioButton) findViewById(R.id.defined_group);
        Button add = (Button)findViewById(R.id.bt_add_new_contact);
        tabhost = getTabHost();
        tabhost.addTab(tabhost.newTabSpec("tag1").setIndicator("0").setContent(new Intent(this,SmsAddressBookListActivity.class)));
        tabhost.addTab(tabhost.newTabSpec("tag2").setIndicator("1").setContent(new Intent(this,SmsDefinedGroupListActivity.class)));

        book_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            /**
             * <p>Called when the checked radio button has changed. When the
             * selection is cleared, checkedId is -1.</p>
             *
             * @param group     the group in which the checked radio button has changed
             * @param checkedId the unique identifier of the newly checked radio button
             */
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.address_book:
                        tabhost.setCurrentTab(0);
                        break;
                    case R.id.defined_group:
                        tabhost.setCurrentTab(1);
                        break;
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SmsAddNewContactActivity.class);
                startActivity(intent);
            }
        });
    }
}

package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ExpandableListView;

import com.lgybetter.smsproject.R;

import adapter.TemplateAdapter;
import beanclass.AddContactsReturnData;
import beanclass.TemplatePosition;

/**
 * Created by Administrator on 2016/3/1.
 */
public class SelectTemplateActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.select_template_view);
        TemplateAdapter adapter = new TemplateAdapter(getApplicationContext());
        ExpandableListView expandListView = (ExpandableListView) findViewById(R.id.expandablelist);
        expandListView.setAdapter(adapter);
        expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent data = new Intent();
                TemplatePosition templatePosition = new TemplatePosition(groupPosition,childPosition);
                data.putExtra("template_result", templatePosition);
                setResult(40, data);
                finish();
                return true;
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
            Intent data = new Intent();
            TemplatePosition templatePosition = null;
            data.putExtra("result", templatePosition);
            setResult(40, data);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}


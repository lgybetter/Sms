package com.lgybetter.smsproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.lgybetter.smsproject.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/1/20.
 */
public class SmsDefinedGroupListActivity extends Activity {
    private ListView listView;
    private List<HashMap<String,Object>> listitems;
    private String [] name_group = {"凯彬，教授，鱼仔","爸，妈","张柯淳，杨明明，小婷"};
    private String [] count_number = {"2","1","0"};
    private String [] message_body = {};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_defined_group_view);
        listView = (ListView)findViewById(R.id.defined_group);
        listitems = new ArrayList<>();
        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(),getData(),R.layout.group_item,new String[]{"name_group","count_number"},new int[]{R.id.tv_group_name,R.id.tv_count_number});
        listView.setAdapter(simpleAdapter);
    }

    public List<HashMap<String,Object>> getData() {
        for(int i = 0; i < name_group.length; i ++) {
            HashMap<String,Object> m = new HashMap<>();
            m.put("name_group",name_group[i]);
            m.put("count_number",count_number[i]);
            listitems.add(m);
        }
        return listitems;
    }
}

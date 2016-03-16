package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.lgybetter.smsproject.R;

import java.util.List;

import beanclass.Person;

/**
 * Created by Administrator on 2016/2/23.
 */

public class  SelectContactsAdapter extends ArrayAdapter<Person> {

    /**
     * 需要渲染的item布局文件
     */
    private int resource;

    /**
     * 字母表分组工具
     */
    private SectionIndexer mIndexer;

    public SelectContactsAdapter(Context context, int textViewResourceId, List<Person> objects) {
        super(context, textViewResourceId, objects);
        resource = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Person contact = getItem(position);
        LinearLayout layout;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(resource, null);
            viewHolder.name = (TextView)layout.findViewById(R.id.name);
            viewHolder.sortKey = (TextView)layout.findViewById(R.id.add_sort_key);
            viewHolder.checkBox = (CheckBox)layout.findViewById(R.id.item_checkbox);
            viewHolder.sortKeyLayout = (LinearLayout)layout.findViewById(R.id.add_sort_key_layout);
            viewHolder.number = (TextView)layout.findViewById(R.id.number);
            layout.setTag(viewHolder);
        } else {
            layout = (LinearLayout) convertView;
            viewHolder = (ViewHolder)layout.getTag();
        }
        viewHolder.name.setText(contact.getName());
        viewHolder.number.setText(contact.getNumber());
        viewHolder.checkBox.setChecked(contact.isStatus());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact.setStatus(true);
            }
        });
        int section = mIndexer.getSectionForPosition(position);
        if (position == mIndexer.getPositionForSection(section)) {
            viewHolder.sortKey.setText(contact.getSortKey());
            viewHolder.sortKeyLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.sortKeyLayout.setVisibility(View.GONE);
        }
        return layout;
    }
    //通过ViewHolder避免重复获取控件的ID
    class ViewHolder
    {
        public TextView name;
        public LinearLayout sortKeyLayout;
        public TextView sortKey;
        public TextView number;
        public CheckBox checkBox;
    }
    /**
     * 给当前适配器传入一个分组工具。
     *
     * @param indexer
     */
    public void setIndexer(SectionIndexer indexer)
    {
        mIndexer = indexer;
    }
}


package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.lgybetter.smsproject.R;

import java.util.List;

import beanclass.Person;

/**
 * Created by Administrator on 2016/1/21.
 */
public class PersonAdapter extends ArrayAdapter<Person> implements SectionIndexer{

    /**
     * 需要渲染的item布局文件
     */
    private int resource;

    /**
     * 字母表分组工具
     */
    private SectionIndexer mIndexer;

    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public PersonAdapter(Context context, int textViewResourceId, List<Person> objects) {
        super(context, textViewResourceId, objects);
        resource = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person contact = getItem(position);
        LinearLayout layout;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(resource, null);
            viewHolder.name = (TextView)layout.findViewById(R.id.contact_name);
            viewHolder.sortKey = (TextView)layout.findViewById(R.id.sort_key);
            viewHolder.sortKeyLayout = (LinearLayout)layout.findViewById(R.id.sort_key_layout);
            layout.setTag(viewHolder);
        } else {
            layout = (LinearLayout) convertView;
            viewHolder = (ViewHolder)layout.getTag();
        }
        viewHolder.name.setText(contact.getName());
        int section = mIndexer.getSectionForPosition(position);
        if (position == mIndexer.getPositionForSection(section)) {
            viewHolder.sortKey.setText(contact.getSortKey());
            viewHolder.sortKeyLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.sortKeyLayout.setVisibility(View.GONE);
        }
        return layout;
    }

    /**
     * Returns an array of objects representing sections of the list. The
     * returned array and its contents should be non-null.
     * <p/>
     * The list view will call toString() on the objects to get the preview text
     * to display while scrolling. For example, an adapter may return an array
     * of Strings representing letters of the alphabet. Or, it may return an
     * array of objects whose toString() methods return their section titles.
     *
     * @return the array of section objects
     */
    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    /**
     * Given the index of a section within the array of section objects, returns
     * the starting position of that section within the adapter.
     * <p/>
     * If the section's starting position is outside of the adapter bounds, the
     * position must be clipped to fall within the size of the adapter.
     *
     * @param sectionIndex the index of the section within the array of section
     *                     objects
     * @return the starting position of that section within the adapter,
     * constrained to fall within the adapter bounds
     */
    @Override
    public int getPositionForSection(int sectionIndex) {
        // If there is no item for current section, previous section will be selected
        for (int i = sectionIndex; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                if (i == 0) {
                    // For numeric section
                    for (int k = 0; k <= 9; k++) {
                        if (StringMatcher.match(String.valueOf(getItem(j).getSortKey().charAt(0)), String.valueOf(k)))
                            return j;
                    }
                } else {
                    if (StringMatcher.match(String.valueOf(getItem(j).getSortKey().charAt(0)), String.valueOf(mSections.charAt(i))))
                        return j;
                }
            }
        }
        return 0;
    }

    /**
     * Given a position within the adapter, returns the index of the
     * corresponding section within the array of section objects.
     * <p/>
     * If the section index is outside of the section array bounds, the index
     * must be clipped to fall within the size of the section array.
     * <p/>
     * For example, consider an indexer where the section at array index 0
     * starts at adapter position 100. Calling this method with position 10,
     * which is before the first section, must return index 0.
     *
     * @param position the position within the adapter for which to return the
     *                 corresponding section index
     * @return the index of the corresponding section within the array of
     * section objects, constrained to fall within the array bounds
     */
    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    //通过ViewHolder避免重复获取控件的ID
    class ViewHolder
    {
        public TextView name;
        public LinearLayout sortKeyLayout;
        public TextView sortKey;
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

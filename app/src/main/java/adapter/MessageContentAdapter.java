package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lgybetter.smsproject.R;
import java.util.List;
import beanclass.Message;

/**
 * Created by Administrator on 2016/3/19.
 */
public class MessageContentAdapter extends BaseAdapter {
    private List<Message> messageList;
    private LayoutInflater inflater;
    private float mDensity;
    private Context context;
    public MessageContentAdapter(Context context, List<Message> messageList) {
        this.messageList = messageList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return messageList.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        int type = getItemViewType(position);
        if (convertView == null) {
            if(type == 0) {
                convertView = inflater.inflate(R.layout.message_context_item_left,null);
            } else {
                convertView = inflater.inflate(R.layout.message_context_item_right,null);
            }
            viewHolder = new ViewHolder();
            viewHolder.messsage_body = (TextView)convertView.findViewById(R.id.message_body);
            mDensity = context.getResources().getDisplayMetrics().density;
            viewHolder.messsage_body.setMaxWidth((int) (200 * mDensity));

            viewHolder.messsage_date = (TextView)convertView.findViewById(R.id.message_date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.messsage_date.setText(messageList.get(position).getMessage_date());
        viewHolder.messsage_body.setText(messageList.get(position).getMessage_body());
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        int type = messageList.get(position).getMessage_type();
        if(type == 1) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    class ViewHolder {
        TextView messsage_date;
        TextView messsage_body;
    }
}

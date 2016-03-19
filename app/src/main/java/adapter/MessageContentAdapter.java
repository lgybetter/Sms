package adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lgybetter.smsproject.R;
import java.util.List;
import beanclass.Message;

/**
 * Created by Administrator on 2016/3/19.
 */
public class MessageContentAdapter extends ArrayAdapter<Message> {
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    private int resource;
    public MessageContentAdapter(Context context, int textViewResourceId,  List<Message> objects) {
        super(context, textViewResourceId, objects);
        resource = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = getItem(position);
        LinearLayout layout;
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(resource, null);
            viewHolder.messsage_date = (TextView)layout.findViewById(R.id.message_date);
            viewHolder.messsage_body = (TextView)layout.findViewById(R.id.message_body);
            viewHolder.message_context = (LinearLayout)layout.findViewById(R.id.ly_message_context);
            layout.setTag(viewHolder);
        } else {
            layout = (LinearLayout) convertView;
            viewHolder = (ViewHolder)layout.getTag();
        }
        viewHolder.messsage_date.setText(message.getMessage_date());
        viewHolder.messsage_body.setText(message.getMessage_body());
        int type = message.getMessage_type();
        //收到
        if(type == 1) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //此处相当于布局文件中的Android:layout_gravity属性
            lp.gravity = Gravity.RIGHT;
            viewHolder.message_context.setLayoutParams(lp);
        }
        //发出
        else if(type == 2) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            //此处相当于布局文件中的Android:layout_gravity属性
            lp.gravity = Gravity.LEFT;
            viewHolder.message_context.setLayoutParams(lp);
        }
        return layout;
    }

    class ViewHolder {
        LinearLayout message_context;
        TextView messsage_date;
        TextView messsage_body;
    }
}

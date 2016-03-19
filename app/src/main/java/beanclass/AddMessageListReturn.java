package beanclass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/3/19.
 */
public class AddMessageListReturn implements Serializable {
    private List<Message> list = new ArrayList<>();
    public void addMessage(Message message) {
        list.add(message);
    }
    public List<Message> getList() {
        return list;
    }
}

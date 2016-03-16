package beanclass;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/15.
 */
public class TemplatePosition implements Serializable {
    public int groupPosition;
    public int childPosition;
    public TemplatePosition(int groupPosition,int childPosition) {
        this.childPosition = childPosition;
        this.groupPosition = groupPosition;
    }
}

package beanclass;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/23.
 */
public class AddContactsReturnData implements Serializable {
    public AddContactsReturnData(String name,String number,String id)
    {
        this.name = name;
        this.number = number;
        this.id = id;
    }
    private String name;
    private String number;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

package beanclass;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/21.
 */
public class Person implements Serializable{
    public Person(String name,String sortKey,String number,int ID,int ContactId,boolean status)
    {
        this.name = name;
        this.sortKey = sortKey;
        this.number = number;
        this.ID = ID;
        this.ContactId = ContactId;
        this.status = status;
    }
    private boolean status;
    /**
     * 联系人姓名
     */
    private String name;

    /**
     * 排序字母
     */
    private String sortKey;

    /**
     *联系人电话号码
     */
    private String number;

    /**
     *联系人在本地数据库中的ID
     */
    private int ID;
    /**
     *联系人在原生数据库中的ID
     */
    private int ContactId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getContactId() {
        return ContactId;
    }

    public void setContactId(int contactId) {
        ContactId = contactId;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

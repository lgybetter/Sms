package beanclass;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/22.
 */
public class Message implements Serializable{
    public Message(int ID,int message_id,String person_number,int person_id,String message_body,String message_date,int message_type,int message_read,String person_name)
    {
        this.ID = ID;
        this.message_id = message_id;
        this.person_number = person_number;
        this.person_id = person_id;
        this.message_body = message_body;
        this.message_date = message_date;
        this.message_type = message_type;
        this.message_read = message_read;
        this.person_name = person_name;
    }
    /**
     * 短信对应的ID
     */
    private int ID;
    /**
     * 短信在原生数据库对应的ID
     */
    private int message_id;
    /**
     * 短信对应的联系人号码
     */
    private String person_number;
    /**
     * 短信对应的联系人ID
     */
    private int person_id;
    /**
     * 短信对应的内容
     */
    private String message_body;
    /**
     * 短信对应的日期
     */
    private String message_date;
    /**
     * 短信对应的类型（接收/发送 1是接收到的，2是已发出)
     */
    private int message_type;
    /**
     * 短信对应的查看类型（是/否阅读 0未读， 1已读）
     */
    private int message_read;
    /**
     * 短信对应的联系人名称
     */
    private String person_name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPerson_number() {
        return person_number;
    }

    public void setPerson_number(String person_number) {
        this.person_number = person_number;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public String getMessage_body() {
        return message_body;
    }

    public void setMessage_body(String message_body) {
        this.message_body = message_body;
    }

    public String getMessage_date() {
        return message_date;
    }

    public void setMessage_date(String message_date) {
        this.message_date = message_date;
    }

    public int getMessage_type() {
        return message_type;
    }

    public void setMessage_type(int message_type) {
        this.message_type = message_type;
    }

    public int getMessage_read() {
        return message_read;
    }

    public void setMessage_read(int message_read) {
        this.message_read = message_read;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }
}

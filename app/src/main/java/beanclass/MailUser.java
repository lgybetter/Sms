package beanclass;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/3/17.
 */
public class MailUser implements Serializable {
    String address;
    String password;
    String smtp;
    String smtpprot;
    String pop;
    String popprot;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPopprot() {
        return popprot;
    }

    public void setPopprot(String popprot) {
        this.popprot = popprot;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getSmtpprot() {
        return smtpprot;
    }

    public void setSmtpprot(String smtpprot) {
        this.smtpprot = smtpprot;
    }
}


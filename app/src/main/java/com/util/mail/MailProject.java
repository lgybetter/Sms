package com.util.mail;

import android.content.Context;

import com.sun.mail.pop3.POP3Folder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

import beanclass.MailUser;

/**
 * Created by ttopi_000 on 2016/3/1.
 */
public class MailProject implements Serializable {
    Context context;
    OnListFlashListener listFlashListener;

    public MailProject(Context context){
        this.context=context;
    }

    public boolean setUser(String address, String password){
        int ati=address.lastIndexOf('@');
        if(ati<0) return false;
        MailDBHelper mdh=new MailDBHelper(this.context);
        String mailhost=address.substring(ati+1);
        switch (mailhost){
            case "qq.com":
                mdh.setUser(address,password,"smtp.qq.com","587","pop.qq.com","110");
                break;
            case "163.com":
                mdh.setUser(address,password,"smtp.163.com","25","pop.163.com","110");
                break;
            case "126.com":
                mdh.setUser(address,password,"smtp.126.com","25","pop.126.com","110");
                break;
            default: return false;
        }
        return true;
    }

    public boolean sendMail(String toAddress ,String subject, String text){
        MailDBHelper mdh=new MailDBHelper(this.context);
        MailUser u=mdh.getUser();
        if(u==null)
            return false;
        class sendMailThread extends Thread{
            String toAddress , subject,  text;
            MailUser u;
            public  sendMailThread(String toAddress ,String subject, String text, MailUser u){
                this.toAddress=toAddress;
                this.subject=subject;
                this.text=text;
                this.u=u;
            }

            @Override
            public void run(){
                System.out.println("mail test"+u.getAddress()+u.getPassword()+u.getSmtp()+u.getSmtpprot());
                try {
                    MailSenderInfo mailInfo = new MailSenderInfo();
                    mailInfo.setMailServerHost(u.getSmtp());
                    mailInfo.setMailServerPort(u.getSmtpprot());
                    mailInfo.setValidate(true);
                    mailInfo.setUserName(u.getAddress());
                    mailInfo.setPassword(u.getPassword());
                    mailInfo.setFromAddress(u.getAddress());
                    mailInfo.setToAddress(toAddress);
                    mailInfo.setSubject(subject);
                    mailInfo.setContent(text);
                    //这个类主要来发送邮件
                    SimpleMailSender sms = new SimpleMailSender();
                    sms.sendTextMail(mailInfo);//发送文体格式
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        sendMailThread thread = new sendMailThread(toAddress,subject,text,u);
        thread.start();
        return true;
    }

    public List<Email> search(String keyword){
        MailDBHelper mdh=new MailDBHelper(this.context);
        MailUser u=mdh.getUser();
        if(u==null) return null;
        return mdh.searchEmail(keyword);
    }

    public List<Email> getList() {
        MailDBHelper mdh=new MailDBHelper(this.context);
        MailUser u=mdh.getUser();
        if(u==null)
            return null;
        class getListThread extends Thread {
            OnListFlashListener lfl;
            MailUser u;
            MailDBHelper mdh;

            public getListThread(OnListFlashListener lfl, MailDBHelper mdh, MailUser u) {
                this.lfl = lfl;
                this.mdh=mdh;
                this.u = u;
            }

            @Override
            public void run() {
                try {
                        // 创建一个有具体连接信息的Properties对象
                        Properties props = new Properties();
                        props.setProperty("mail.store.protocol", "pop3");
                        props.setProperty("mail.pop3.host", u.getPop());
                        // 使用Properties对象获得Session对象
                        Session session = Session.getInstance(props);
                        session.setDebug(true);
                        // 利用Session对象获得Store对象，并连接pop3服务器
                        Store store = null;
                        store = session.getStore();
                        store.connect(u.getPop(), u.getAddress(), u.getPassword());
                        // 获得邮箱内的邮件夹Folder对象，以"只读"打开
                    POP3Folder  folder = (POP3Folder) store.getFolder("inbox");
                        folder.open(Folder.READ_ONLY);
                        // 获得邮件夹Folder内的所有邮件Message对象
                    List<Email> emailList = new ArrayList<>();
                        Message[] messages = folder.getMessages();
                        int mailCounts = messages.length;
                        for (int i = mailCounts - 1; i> mailCounts-10; i--) {
                            Email e=new Email();
                            MimeMessage mimeMessage = (MimeMessage) messages[i];
                            e.setUid(folder.getUID(mimeMessage));
                            if(mdh.existEmail(e.getUid()))
                                break;
                            e.setSubject(messages[i].getSubject());
                            e.setAddresser((messages[i].getFrom()[0]).toString());
                            e.setDate(messages[i].getSentDate().toString());
                            ReciveMail rm = new ReciveMail((MimeMessage) messages[i]);
                            e.setText( rm.recive(messages[i], 1));
                            emailList.add(e);
                            mdh.addEmail(e);
                        }
                        folder.close(false);
                        store.close();
                        //new POP3MailReceiverTest(messages);
                    if(lfl!=null) lfl.onFlash(emailList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        getListThread glt=new getListThread(listFlashListener,new MailDBHelper(context),u);
        glt.start();
        return mdh.getEmail();
    }

    public void setListFlashListener(OnListFlashListener fl) {
                listFlashListener = fl;
            }

    public interface OnListFlashListener {
        void onFlash(List<Email> emailList);
    }

}

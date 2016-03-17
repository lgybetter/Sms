package com.util.mail;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ttopi_000 on 2016/3/1.
 */
public class MailDBHelper extends SQLiteOpenHelper {

    public MailDBHelper(Context context) {
        super(context, "mail.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1 = "create table user(address varchar(400) not null , password varchar(400) not null ," +
                " smtp varchar(400) not null, smtpprot varchar(400) not null,  " +
                " pop varchar(400) not null, popprot varchar(400) not null);";
        String sql2 = "create table mail( uid varchar(400) not null, address varchar(400) not null, " +
                "subject varchar(400) not null , text varchar(1000000) not null , date varchar(50) not null );";
        db.execSQL(sql1);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean existEmail(String uid){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor = db.query("mail", null, "uid = ?", new String[]{uid}, null, null, null);
        return cursor.moveToFirst();
    }

    public void addEmail(Email e){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("insert into mail values(?,?,?,?,?);",new String[]{e.getUid(),e.getAddresser(),e.getSubject(),e.getText(),e.getDate()});
    }

    public List<Email> getEmail(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.query("mail", null, null, null, null, null, null);
        List<Email> emailList=new ArrayList<>();
        while (cursor.moveToNext()){
            Email e=new Email();
            e.setUid(cursor.getString(cursor.getColumnIndex("uid")));
            e.setAddresser(cursor.getString(cursor.getColumnIndex("address")));
            e.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
            e.setText(cursor.getString(cursor.getColumnIndex("text")));
            e.setDate(cursor.getString(cursor.getColumnIndex("date")));
            emailList.add(e);
        }
        return emailList;
    }

    public List<Email> searchEmail(String keyword){
        SQLiteDatabase db=this.getReadableDatabase();
        String pkeyword="%"+keyword+"%";
        Cursor cursor = db.query("mail", null, "subject like ? or text like ?", new String[]{pkeyword,pkeyword}, null, null, null);
        List<Email> emailList=new ArrayList<>();
        while (cursor.moveToNext()){
            Email e=new Email();
            e.setUid(cursor.getString(cursor.getColumnIndex("uid")));
            e.setAddresser(cursor.getString(cursor.getColumnIndex("address")));
            e.setSubject(cursor.getString(cursor.getColumnIndex("subject")));
            e.setText(cursor.getString(cursor.getColumnIndex("text")));
            e.setDate(cursor.getString(cursor.getColumnIndex("date")));
            emailList.add(e);
        }
        return emailList;
    }

    public void setUser(String address, String password, String smtp, String smtpprot, String pop, String popprot){
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM user");
        db.execSQL("insert into user values(?,?,?,?,?,?);",new String[]{address,password,smtp,smtpprot,pop,popprot});
    }

    public user getUser(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.query("user", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            user u=new user();
            u.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            u.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            u.setSmtp(cursor.getString(cursor.getColumnIndex("smtp")));
            u.setSmtpprot(cursor.getString(cursor.getColumnIndex("smtpprot")));
            u.setPop(cursor.getString(cursor.getColumnIndex("pop")));
            u.setPopprot(cursor.getString(cursor.getColumnIndex("popprot")));
            return u;
        }else return null;
    }

    public class user{

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
}

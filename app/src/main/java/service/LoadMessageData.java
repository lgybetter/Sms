package service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/2/14.
 */
public class LoadMessageData extends Service{
    final private String SMS_URI_ALL = "content://sms/"; 		//所有短信
    /**
     * Return the communication channel to the service.  May return null if
     * clients can not bind to the service.  The returned
     * {@link IBinder} is usually for a complex interface
     * that has been <a href="{@docRoot}guide/components/aidl.html">described using
     * aidl</a>.
     * <p/>
     * <p><em>Note that unlike other application components, calls on to the
     * IBinder interface returned here may not happen on the main thread
     * of the process</em>.  More information about the main thread can be found in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html">Processes and
     * Threads</a>.</p>
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return an IBinder through which clients can call on to the
     * service.
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate()
    {
        super.onCreate();
        final SQLiteDatabase db = openOrCreateDatabase("MailUser.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists messagetb(_id integer primary key autoincrement,person_name text not null,contact_Id integer not null,message_id integer not null,message_body text not null,person_phoneNum text not null,date text not null,type integer not null,read integer not null)");
        Uri uri = Uri.parse(SMS_URI_ALL);
        String[] projection = new String[] { "_id", "address", "person", "body", "date", "type" ,"read"};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, "date desc");
        if (cursor.moveToFirst()) {
            do {
                ContentValues contentValues = new ContentValues();
                contentValues.put("message_id", cursor.getInt(0));
                contentValues.put("person_phoneNum", cursor.getString(1));
                contentValues.put("contact_Id", cursor.getInt(2));
                contentValues.put("message_body", cursor.getString(3));
                contentValues.put("date", cursor.getString(4));
                contentValues.put("type", cursor.getInt(5));
                contentValues.put("read", cursor.getInt(6));
                String person_name = getPeopleNameFromPerson(cursor.getString(1));
                if(person_name == "null")
                    contentValues.put("person_name","陌生人");
                else
                    contentValues.put("person_name",person_name);
                db.insert("messagetb",null,contentValues);
                contentValues.clear();
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    //关联联系人获取姓名
    private String getPeopleNameFromPerson(String address){
        if(address == null || address == ""){
            return "( no address )\n";
        }

        String strPerson = "null";
        String[] projection = new String[] {Phone.DISPLAY_NAME, Phone.NUMBER};

        Uri uri_Person = Uri.withAppendedPath(Phone.CONTENT_FILTER_URI, address);  // address 手机号过滤
        Cursor cursor = getContentResolver().query(uri_Person, projection, null, null, null);

        if(cursor.moveToFirst()){
            int index_PeopleName = cursor.getColumnIndex(Phone.DISPLAY_NAME);
            String strPeopleName = cursor.getString(index_PeopleName);
            strPerson = strPeopleName;
        }
        cursor.close();

        return strPerson;
    }
}

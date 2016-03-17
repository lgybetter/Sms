package service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.IBinder;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2016/2/14.
 */
public class LoadAddressBookData extends Service {
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
    public void onCreate() {
        super.onCreate();
        final SQLiteDatabase db = openOrCreateDatabase("MailUser.db",MODE_PRIVATE,null);
        db.execSQL("create table if not exists persontb(_id integer primary key autoincrement,contactId integer not null,displayName text not null,phoneNum text not null,sortKey text not null)");
        Uri uri = Phone.CONTENT_URI;
        String[] projection = { Phone._ID, Phone.DISPLAY_NAME, Phone.DATA1, "sort_key", Phone.PHOTO_ID };
        Cursor cursor = getContentResolver().query(uri , projection , null , null , "sort_key COLLATE LOCALIZED asc");
        if (cursor.moveToFirst()) {
            do {
                ContentValues contentValues = new ContentValues();
                contentValues.put("contactId",cursor.getInt(0));
                contentValues.put("displayName",cursor.getString(1));
                String number = cursor.getString(2);
                if (number.contains("+86")) {
                    contentValues.put("phoneNum",number.substring(3,number.length()));
                } else {
                    contentValues.put("phoneNum",number);
                }
                contentValues.put("sortKey", cursor.getString(3));
                db.insert("persontb",null,contentValues);
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
}

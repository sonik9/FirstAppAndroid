package upir.firstappandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by vital on 27/10/2015.
 */
public class DbHelper extends SQLiteOpenHelper {
    final String LOG_TAG = "myLogs";
    public DbHelper(Context context) {
        // конструктор суперкласса
        super(context, "store", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(LOG_TAG, "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table store ("
                + "id integer primary key autoincrement,"
                + "name text not null,"
                + "price double not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

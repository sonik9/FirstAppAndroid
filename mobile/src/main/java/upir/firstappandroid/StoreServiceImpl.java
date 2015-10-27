package upir.firstappandroid;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vital on 27/10/2015.
 */
public class StoreServiceImpl implements StoreService {

    SQLiteDatabase db;

    public StoreServiceImpl(SQLiteDatabase db) {
        this.db = db;
    }


    final String LOG_TAG = "myLogs";

    //dbHelper = new DbHelper();
    @Override
    public List<Store> getAllStore() {
        Cursor c = db.query("store", null, null, null, null, null, null);
        List<Store> stores = new ArrayList<>();
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int priceColIndex = c.getColumnIndex("price");

            do {

                // получаем значения по номерам столбцов и пишем все в лог
                stores.add(new Store(c.getString(nameColIndex),c.getDouble(priceColIndex),c.getInt(idColIndex)));
                /*Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", name = " + c.getString(nameColIndex) +
                                ", email = " + c.getString(emailColIndex));*/


                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
        return stores;
    }

    @Override
    public Store getStoreById(int id) {
        return null;
    }

    @Override
    public Store getStoreByName(String name) {
        return null;
    }

    @Override
    public int save() {
        return 0;
    }

    @Override
    public void delete(int id) {

    }
}

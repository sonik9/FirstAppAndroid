package upir.firstappandroid;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.math.BigDecimal;
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
    public List<Product> getAllStore() {
        Cursor c = db.query("store", null, null, null, null, null, null);
        List<Product> products = new ArrayList<>();
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int priceColIndex = c.getColumnIndex("price");

            do {

                // получаем значения по номерам столбцов и пишем все в лог
                products.add(new Product(c.getString(nameColIndex), c.getDouble(priceColIndex), c.getInt(idColIndex)));
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
        return products;
    }

    @Override
    public Product getStoreById(long id) {
        return null;
    }

    @Override
    public Product getStoreByName(String name) {
        return null;
    }

    @Override
    public long save(Product product) {

        // создаем объект для данных
        ContentValues cv = new ContentValues();
        Log.d(LOG_TAG, "--- Insert in mytable: ---");
        // подготовим данные для вставки в виде пар: наименование столбца - значение

        cv.put("name", product.getName());
        cv.put("price", BigDecimal.valueOf(product.getPrice()).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
        // вставляем запись и получаем ее ID
        long rowID = db.insert("store", null, cv);
        Log.d(LOG_TAG, "row inserted, ID = " + rowID);
        return rowID;
    }

    @Override
    public int update(Product product) {
        ContentValues cv = new ContentValues();
        Log.d(LOG_TAG, "--- Update mytabe: ---");
        // подготовим значения для обновления
        cv.put("name", product.getName());
        cv.put("price", BigDecimal.valueOf(product.getPrice()).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
        // обновляем по id
        int updCount = db.update("store", cv, "id="+product.getId(),null);
        Log.d(LOG_TAG, "updated rows count = " + updCount);
        return updCount;
    }

    @Override
    public int delete(long id) {
        int delCount = db.delete("store", "id = " + id, null);
        Log.d(LOG_TAG, "row deleted, ID = " + id);
        return delCount;

    }
}

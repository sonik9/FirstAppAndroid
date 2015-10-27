package upir.firstappandroid;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
   /* String[] names = { "Иван", "Марья", "Петр", "Антон", "Даша", "Борис",
            "Костя", "Игорь", "Анна", "Денис", "Андрей","Rudnev","Rudnev","Rudnev","Rudnev","Rudnev","Rudnev","Rudnev" };*/

    DbHelper dbHelper;
    List<Store> products = new ArrayList<Store>();
    BoxAdapter boxAdapter;
    final String LOG_TAG = "myLogs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
       /*         Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                FragmentManager fragmentManager = getSupportFragmentManager();
                ProductAddDialog productAddDialog = new ProductAddDialog();
                productAddDialog.show(fragmentManager, "Hello");
            }
        });

        ListView listProductView = (ListView)findViewById(R.id.listView);


        // создаем объект для данных
        ContentValues cv = new ContentValues();

        Log.d(LOG_TAG, "--- Rows in mytable: ---");
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        StoreService stroreService = new StoreServiceImpl(db);


        // создаем адаптер
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        //List<Store> storeList = new ArrayList<>();
        products=stroreService.getAllStore();
        boxAdapter = new BoxAdapter(this, products);


        //ArrayAdapter adapter = new ArrayAdapter(this, R.layout.itemstore, storeList);
        //SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(this, R.layout.itemstore, storeList, from, to);
        // присваиваем адаптер списку
        listProductView.setAdapter(boxAdapter);
    }

}

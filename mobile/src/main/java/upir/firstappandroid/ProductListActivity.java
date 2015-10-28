package upir.firstappandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductListActivity extends AppCompatActivity {

    DbHelper dbHelper;
    List<Product> products = new ArrayList<Product>();
    BoxAdapter boxAdapter;
    final String LOG_TAG = "myLogs";

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
       /* DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/


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

        final ListView listProductView = (ListView) findViewById(R.id.listView);
        CheckBox checkBox = (CheckBox) findViewById(R.id.cbBox);


        // создаем объект для данных
        ContentValues cv = new ContentValues();

        Log.d(LOG_TAG, "--- Rows in mytable: ---");
        // делаем запрос всех данных из таблицы mytable, получаем Cursor
        dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        StoreService stroreService = new StoreServiceImpl(db);


        // создаем адаптер
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        //List<Product> storeList = new ArrayList<>();
        products = stroreService.getAllStore();
        boxAdapter = new BoxAdapter(this, products);

        listProductView.setAdapter(boxAdapter);

        listProductView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "
                        + id);
            }
        });

        /*listProductView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox chkbox = (CheckBox)listProductView.getChildAt(position).findViewById(R.id.cbBox);
                if(!chkbox.isChecked()){
                    chkbox.setChecked(true);
                }else{
                    chkbox.setChecked(false);
                }
                Toast.makeText(view.getContext(),position,Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        ListView listProductView = (ListView) findViewById(R.id.listView);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_select_all) {
            checkAll();
        }
        if (id == R.id.action_delete_selected) {
            deleteChecked();
        }
        if (id == R.id.action_update) {
            updateChecked();
        }

        if (id == R.id.action_random_fill) {
            fillData();
        }


        return super.onOptionsItemSelected(item);
    }

    private void updateChecked() {
        for (Product p : boxAdapter.getBox()) {
            if (p.isCheck()) {
                Toast.makeText(this, "" + p.getId(), Toast.LENGTH_SHORT).show();
                android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ProductAddDialog productAddDialog = new ProductAddDialog();
                Bundle args = new Bundle();
                args.putString("name", p.getName());
                args.putLong("id", p.getId());
                args.putDouble("price", p.getPrice());
                productAddDialog.setArguments(args);
                productAddDialog.show(ft, "Hello");
            }
            break;
        }
    }


    public void checkAll() {
        CheckBox chkbox;
        final ListView lv = (ListView) findViewById(R.id.listView);

        for (int x = 0; x < lv.getChildCount(); x++) {
            chkbox = (CheckBox) lv.getChildAt(x).findViewById(R.id.cbBox);
            if (!chkbox.isChecked()) {
                chkbox.setChecked(true);
            } else {
                chkbox.setChecked(false);
            }
        }
    }

    // выводим информацию о корзине
    private void showResult() {
        String result = "Products checked:";
        for (Product p : boxAdapter.getBox()) {
            if (p.isCheck())
                result += "\n" + p.getName();
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    private void deleteChecked() {
        String result = "Products was deleted:";
        dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        StoreService storeService = new StoreServiceImpl(db);
        int count = 0;
        for (Product p : boxAdapter.getBox()) {
            if (p.isCheck()) {
                count += storeService.delete(p.getId());
                result += "\n" + p.getName();
            }
        }
        Toast.makeText(this, result + "\n count:" + count, Toast.LENGTH_LONG).show();
        this.recreate();
    }


    private void fillData() {
        double start = 100;
        double end = 902;
        double random = new Random().nextDouble();
        double result = start + (random * (end - start));
        dbHelper = new DbHelper(this);
        String result1 = "Products added:";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        StoreService storeService = new StoreServiceImpl(db);
        for (int i = 1; i <= 20; i++) {
            products.add(new Product("Product " + i, result));
            storeService.save(new Product("Product " + i, result));
        }
        for (Product p : products) {
            result1 += "\n" + p.getName();
        }
        Toast.makeText(this, result1, Toast.LENGTH_LONG).show();
    }

}

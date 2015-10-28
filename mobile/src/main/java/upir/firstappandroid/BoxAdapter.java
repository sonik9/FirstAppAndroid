package upir.firstappandroid;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class BoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    List<Product> objects;

    SharedPreferences pref;

    BoxAdapter(Context context, List<Product> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

  /*  static class ViewHolder {
        protected TextView text;
        protected CheckBox checkbox;
    }*/

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_product, parent, false);
        }

        final Product p = getProduct(position);
        pref = ctx.getSharedPreferences(null, 0);
        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        if (pref.getString("show_id", "") == "true") {
            ((TextView) view.findViewById(R.id.txtId)).setText(Long.toString(p.getId()));
        }
        ((TextView) view.findViewById(R.id.txtName)).setText(p.getName() + "");
        ((TextView) view.findViewById(R.id.txtPrice)).setText(p.getPrice().toString());


        int color = Integer.parseInt(pref.getString("show_color", ""));

        ((TextView) view.findViewById(R.id.txtId)).setTextColor(color);
        ((TextView) view.findViewById(R.id.txtName)).setTextColor(color);
        ((TextView) view.findViewById(R.id.txtPrice)).setTextColor(color);

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        // пишем позицию
        cbBuy.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        cbBuy.setChecked(p.isCheck());

        final View finalView = view;
        final Product p1 = p;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckBox ch = (CheckBox) v.findViewById(R.id.cbBox);
                if (!ch.isChecked())
                    ch.setChecked(true);
                else
                    ch.setChecked(false);
            }
        });
        //final View finalView1 = view;
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "" + p1.getId(), Toast.LENGTH_SHORT).show();
                android.support.v4.app.FragmentTransaction ft = ((FragmentActivity) ctx).getSupportFragmentManager().beginTransaction();
                ProductAddDialog productAddDialog = new ProductAddDialog();
                Bundle args = new Bundle();
                args.putString("name", p1.getName());
                args.putLong("id", p1.getId());
                args.putDouble("price", p1.getPrice());
                productAddDialog.setArguments(args);
                productAddDialog.show(ft, "Hello");
                return true;
            }
        });
        return view;
    }

    // товар по позиции
    Product getProduct(int position) {
        return ((Product) getItem(position));
    }

    // содержимое корзины
    List<Product> getBox() {
        List<Product> box = new ArrayList<Product>();
        for (Product p : objects) {
            // если в корзине
            if (p.isCheck())
                box.add(p);
        }
        return box;
    }

    // обработчик для чекбоксов
    CompoundButton.OnCheckedChangeListener myCheckChangList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товара (в корзине или нет)
            getProduct((Integer) buttonView.getTag()).setCheck(isChecked);
        }
    };


}
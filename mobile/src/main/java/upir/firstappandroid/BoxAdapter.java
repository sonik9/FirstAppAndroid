package upir.firstappandroid;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import upir.firstappandroid.R;
import upir.firstappandroid.Store;

public class BoxAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    List<Store> objects;

    BoxAdapter(Context context, List<Store> stores) {
        ctx = context;
        objects = stores;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

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
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.itemstore, parent, false);
        }

        Store p = getProduct(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.txtId)).setText(Integer.toString(p.getId()));
        ((TextView) view.findViewById(R.id.txtName)).setText(p.getName() + "");
        ((TextView) view.findViewById(R.id.txtPrice)).setText(p.getPrice().toString());

        /*CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        // пишем позицию
        cbBuy.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        cbBuy.setChecked(p.box);*/
        return view;
    }

    // товар по позиции
    Store getProduct(int position) {
        return ((Store) getItem(position));
    }

    // содержимое корзины
    /*ArrayList<Store> getBox() {
        ArrayList<Store> box = new ArrayList<Store>();
        for (Store p : objects) {
            // если в корзине
            if (p.box)
                box.add(p);
        }
        return box;
    }*/

    // обработчик для чекбоксов
    /*OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товара (в корзине или нет)
            getProduct((Integer) buttonView.getTag()).box = isChecked;
        }
    };*/
}
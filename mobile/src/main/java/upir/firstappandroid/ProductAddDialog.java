package upir.firstappandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.text.AlteredCharSequence;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by vital on 27/10/2015.
 */
public class ProductAddDialog extends DialogFragment {
    //LinearLayout view;
    DbHelper dbHelper;
    final String LOG_TAG = "myLogs";


    String getedName = "";
    double getedPrice = 0;
    long getedId = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Bundle args = getArguments();
        if (args != null) {
            getedName = args.get("name").toString();
            getedPrice = (Double)args.get("price");
            getedId = (long)args.get("id");
        }
        /*builder.setMessage(R.string.app_name)
                .setPositiveButton(R.string.Add, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });*/

        // создаем view из dialog.xml
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_product, null);
        builder.setView(view)
                .setPositiveButton(R.string.Save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        ((Button) view.findViewById(R.id.button_cancel)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ((Button) view.findViewById(R.id.button_save)).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dbHelper = new DbHelper(view.getContext());


                // получаем данные из полей ввода
                String name = ((EditText) view.findViewById(R.id.txtProductName)).getText().toString();
                Double price = Double.parseDouble(((EditText) view.findViewById(R.id.txtPrice)).getText().toString());

                if (getedId > 0) {
                    if (name.length() > 2 && price > 0) {
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        StoreService storeService = new StoreServiceImpl(db);
                        int id = storeService.update(new Product(name, price, getedId));
                        dismiss();
                        getActivity().recreate();
                        Toast.makeText(getContext(), "Product was updated:" + getedId, Toast.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(view, "Please enter all fields", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } else {
                    if (name.length() > 2 && price > 0) {

                        // подключаемся к БД
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        StoreService storeService = new StoreServiceImpl(db);
                        long id = storeService.save(new Product(name, price));
                        dismiss();
                        getActivity().recreate();
                        Toast.makeText(getContext(), "Product has added with id:" + id, Toast.LENGTH_LONG).show();


                    } else {
                        Snackbar.make(view, "Please enter all fields", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });


        if (getedId > 0 && getedName.length() > 1 && getedPrice > 0) {
            ((EditText) view.findViewById(R.id.txtProductName)).setText(getedName);
            ((EditText) view.findViewById(R.id.txtPrice)).setText(Double.toString(getedPrice));
        }


        // Create the AlertDialog object and return it
        return builder.create();
    }


}

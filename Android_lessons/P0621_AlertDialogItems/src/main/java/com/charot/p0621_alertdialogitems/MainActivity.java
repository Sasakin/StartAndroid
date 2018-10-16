package com.charot.p0621_alertdialogitems;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "myLogs";

    static final int DIALOG_ITEMS = 1;
    static final int DIALOG_ADAPTER = 2;
    static final int DIALOG_CURSOR = 3;
    int cnt = 0;

    DB db;
    Cursor cursor;

    String[] data = new String[] {"one", "two", "there", "four"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //открываем подключеник
        db = new DB(this);
        db.open();

        cursor = db.getAllData();
        startManagingCursor(cursor);
    }

    public void onClick(View v) {
        changeCount();
        switch (v.getId()){
            case R.id.btnItems:
                showDialog(DIALOG_ITEMS);
                break;
            case  R.id.btnAdapter:
                showDialog(DIALOG_ADAPTER);
                break;
            case R.id.btnCursor:
                showDialog(DIALOG_CURSOR);
                break;
            default:
                break;
        }
    }

    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        switch (id) {
            //массив
            case DIALOG_ITEMS:
                adb.setTitle(R.string.items);
                adb.setItems(data, myClickListener);
                break;
            case DIALOG_ADAPTER:
                adb.setTitle(R.string.adapter);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.select_dialog_item, data);
                adb.setAdapter(adapter, myClickListener);
                break;
            // курсор
            case DIALOG_CURSOR:
                adb.setTitle(R.string.cursor);
                adb.setCursor(cursor, myClickListener, DB.COL_TXT);
                break;
        }

        return adb.create();
    }


    protected void onPrepareDialog(int id, Dialog dialog) {
        AlertDialog aDialog = (AlertDialog) dialog;
        ListAdapter lAdapter = aDialog.getListView().getAdapter();

        switch(id) {
            case DIALOG_ITEMS:
            case DIALOG_ADAPTER:
                // проверка возможности преобразования
                if (lAdapter instanceof BaseAdapter) {
                    // преобразование и вызов метода-уведомления о новых данных
                    BaseAdapter bAdapter = (BaseAdapter) lAdapter;
                    bAdapter.notifyDataSetChanged();
                }
                break;
            case DIALOG_CURSOR:
                break;
            default:
                break;
        }
    }



    DialogInterface.OnClickListener myClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // выводим в лог позицию нажатого элемента
            Log.d(LOG_TAG, "which="+which);
        }
    };



    void changeCount() {
        cnt++;
        // обновляем массив
        data[3] = String.valueOf(cnt);
        // обновляем БД
        db.changeRec(4, String.valueOf(cnt));
        cursor.requery();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}

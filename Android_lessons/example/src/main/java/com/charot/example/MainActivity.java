package com.charot.example;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ExpandableListView elView;

    DB db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        db = new DB(this);

        db.open();


        Cursor cursor = db.getCompanyData();
        startManagingCursor(cursor);

        String[] grupFrom = new String[]{DB.COMPANY_COLUMN_NAME};
        int[] groupTo = new int[]{android.R.id.text2};

        String[] chaildFrom = new String[]{DB.PHONE_COLUMN_NAME};
        int[] chaildTo = new int[]{android.R.id.text2};

        MyAdapter adapter = new MyAdapter(this, cursor, android.R.layout.simple_expandable_list_item_2,
                grupFrom,groupTo, android.R.layout.simple_list_item_2, chaildFrom, chaildTo);

        elView = (ExpandableListView) findViewById(R.id.elvMain);
        elView.setAdapter(adapter);
    }

    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    class MyAdapter extends SimpleCursorTreeAdapter {

        public MyAdapter(Context context, Cursor cursor, int groupLayout, String[] groupFrom, int[] groupTo, int childLayout, String[] childFrom, int[] childTo) {
            super(context, cursor, groupLayout, groupFrom, groupTo, childLayout, childFrom, childTo);
        }

        @Override
        protected Cursor getChildrenCursor(Cursor groupCursor) {
            int idColumn = groupCursor.getColumnIndex(DB.COMPANY_COLUMN_ID);
            return db.getPhonesData(groupCursor.getInt(idColumn));
        }
    }
}

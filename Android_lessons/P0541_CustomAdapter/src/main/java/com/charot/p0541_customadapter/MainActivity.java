package com.charot.p0541_customadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Product> products = new ArrayList<Product>();
    BoxAdapter boxAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        fillData();

        //создаем адаптер
        boxAdapter = new BoxAdapter(this, getLayoutInflater(), products);

        // настраиваем список
        ListView lv = (ListView) findViewById(R.id.lvMain);
        lv.setAdapter(boxAdapter);
    }


    // генерируем данные для адаптера
    void fillData() {
        for(int i = 0; i < 20; i++) {
            products.add(new Product("Product "+i, i*1000, R.drawable.ic_launcher_background,false));
        }
    }

    public void showResult(View v) {
        String result = "Товары в корзине:";
        for (Product p : boxAdapter.getBox()) {
            if (p.box)
                result += "\n" + p.name;
        }
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }
}

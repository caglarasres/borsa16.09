package com.example.deneme13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ImageButton plus;
    Dialog ekleme;
    Button addbutton;
    RecyclerView recyclerView;
    EditText enstruman;
    EditText lotnum;
    EditText purchaseprice;
    SqlLiteHelper myDB;
    ArrayList<String> hisse;
    ArrayList<String> sayi;
    ArrayList<String> fiyat;
   // ArrayList<Double> denge;
    customadapter customadapter;
    HashMap<String, String> gelenveri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plus = findViewById(R.id.imageButton);
        recyclerView = findViewById(R.id.recview);
        ekleme = new Dialog(MainActivity.this);
        ekleme.setContentView(R.layout.eklemepage);
        addbutton = ekleme.findViewById(R.id.buttonadd);
        enstruman = ekleme.findViewById(R.id.enstruman);
        lotnum = ekleme.findViewById(R.id.lotNUM);
        purchaseprice = ekleme.findViewById(R.id.price);

        // RecycleView ve Adapter'ı oluştur
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hisse = new ArrayList<>();
        sayi = new ArrayList<>();
        fiyat = new ArrayList<>();
       // denge= new ArrayList<Double>();
        customadapter = new customadapter(MainActivity.this, hisse, sayi, fiyat);
        recyclerView.setAdapter(customadapter);

        // SQLite veritabanı bağlantısını oluştur
        myDB = new SqlLiteHelper(MainActivity.this);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ekleme.show();
            }
        });

        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BorsaVerisiTask().execute();
            }
        });
    }

    void takeDataArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                hisse.add(cursor.getString(0));
                sayi.add(cursor.getString(1));
                fiyat.add(cursor.getString(2));
            }
        }
        cursor.close();
    }

    private class BorsaVerisiTask extends AsyncTask<Void, Void, HashMap<String, String>> {
        @Override
        protected HashMap<String, String> doInBackground(Void... params) {
            return borsaEndeks.borsaveri();
        }

        @Override
        protected void onPostExecute(HashMap<String, String> result) {
            // İşlem tamamlandığında sonuçları işleme
            if (result != null){
                gelenveri=result;
                if (gelenveri.containsKey(enstruman.getText().toString().trim())){
                    System.out.println(gelenveri.get(enstruman.getText().toString().trim()));
                    myDB.addStock(enstruman.getText().toString().trim(), Integer.valueOf(lotnum.getText().toString().trim()), Float.valueOf(purchaseprice.getText().toString().trim()));
                   // denge.add(kar);
                   // System.out.println("dengemain"+denge);
                   // System.out.println(denge);
                    takeDataArrays();
                    customadapter.notifyDataSetChanged();
                    ekleme.dismiss();
                    enstruman.setText("");
                    lotnum.setText("");
                    purchaseprice.setText("");
                } else {
                    System.out.println("yanlis");
                    ekleme.dismiss();
                    enstruman.setText("");
                    lotnum.setText("");
                    purchaseprice.setText("");
                }
            }
        }
    }
}


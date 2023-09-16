package com.example.deneme13;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class MyBackgroundService extends Service{
    public HashMap<String,String> gelenveri;
    EditText enstruman,purchaseprice,lotnum;
    MyBackgroundService(EditText enstruman,EditText purchaseprice,EditText lotnum){
        this.enstruman=enstruman;
        this.lotnum=lotnum;
        this.purchaseprice=purchaseprice;
    }
    @Override
    public void onCreate() {
        super.onCreate();
       gelenveri=borsaEndeks.borsaveri();
    }
    MainActivity.
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Arka plandaki işlemleri burada gerçekleştirin.
        Double kar=(Double.valueOf(gelenveri.get(enstruman.getText().toString().trim()))-Double.valueOf(purchaseprice.getText().toString().trim()))*Integer.valueOf(lotnum.getText().toString().trim());
        System.out.println("karımız"+kar);
        // İşlem tamamlandığında servisi durdurabilirsiniz.
        return START_STICKY; // Servis yeniden başlatıldığında otomatik olarak çalışmaya devam eder.
    }


}

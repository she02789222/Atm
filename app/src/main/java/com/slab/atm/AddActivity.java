package com.slab.atm;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    EditText edDate;
    EditText edAmount;
    EditText edInfo;
    private MyDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        findViews();
        helper = MyDBHelper.getInstance(this);
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    0);
        }
        else {
            Toast.makeText(this, "good",Toast.LENGTH_LONG);
        }
    }


    public void add(View v) {
        String cdate = edDate.getText().toString();
        String info = edInfo.getText().toString();
        int amount = Integer.parseInt(edAmount.getText().toString());
        ContentValues values = new ContentValues();
        values.put("cdate", cdate);
        values.put("info", info);
        values.put("amount", amount);

        long id = helper.getWritableDatabase().insert("exp", null, values);
        Log.e("ADD", id+"");
    }

    private void findViews() {
        edDate = findViewById(R.id.ed_date);
        edAmount = findViewById(R.id.ed_amount);
        edInfo = findViewById(R.id.ed_info);
    }
}

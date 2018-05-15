package com.slab.atm;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText edUserid = findViewById(R.id.ed_userid);
        SharedPreferences idSP = getSharedPreferences("ATM", MODE_PRIVATE);
        edUserid.setText(idSP.getString("UID",""));
    }

    

    class LoginTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            boolean logon = false;
            try {
                URL url = new URL(strings[0]);
                InputStream is = url.openStream();
                int data = is.read();
                Log.e("HTTP", String.valueOf(data));
                if(data == 49) {
                    logon = true;
                }
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return logon;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean) {
                Toast.makeText(LoginActivity.this, "登入成功", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, getIntent());
                finish();
            } else {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Atm")
                        .setMessage("登入失敗")
                        .setPositiveButton("OK", null)
                        .show();
            }
        }
    }

    public void login(View v) {
        EditText ed_userid = findViewById(R.id.ed_userid);
        EditText ed_passwd = findViewById(R.id.ed_passwd);
        String uid = ed_userid.getText().toString();
        String pw = ed_passwd.getText().toString();

        String url = new StringBuilder(
                "http://atm201605.appspot.com/login?uid=")
                .append(uid)
                .append("&pw=")
                .append(pw)
                .toString();
        new LoginTask().execute(url);

        /*if(uid.equals("jack")&&pw.equals("1234")) {
            Toast.makeText(this, "登入成功", Toast.LENGTH_SHORT).show();
            getIntent().putExtra("EXTRA_USERID",uid);
            getIntent().putExtra("EXTRA_PASSWD",pw);
            setResult(RESULT_OK,getIntent());
            SharedPreferences pref = getSharedPreferences("ATM", MODE_PRIVATE);
            pref.edit()
                    .putString("UID",uid)
                    .putString("PW",pw)
                    .commit();
            finish();
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Atm")
                    .setMessage("登入失敗")
                    .setPositiveButton("OK",null)
                    .show();
        }
        */

    }
    public void cancel(View v) {

    }
}

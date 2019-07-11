package com.example.classmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.model.Account;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String DATABASE_NAME="AccountList.db";
    private static final String DB_PATH_SUFFIX ="/databases/";
    SQLiteDatabase database = null;

    ArrayList<Account> accountList = new ArrayList<>();


    EditText txtUsername, txtPassword;
    Button  btnLogin;
//    String  username = "khoa";
//    String  password = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        loadDatabaseSQLiteFromAsset();

        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        Cursor cursor = database.query("account", null, null, null, null, null,null);
//        Cursor cursor = database.rawQuery("select * from account", null);
        accountList.clear();
        while(cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            accountList.add(new Account(username,password));
        }
        cursor.close();

    }

    private void loadDatabaseSQLiteFromAsset() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists())
        {
            try{
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Successful copy", Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void CopyDataBaseFromAsset() {
        try{

            InputStream accountInput = getAssets().open(DATABASE_NAME);
            String outFileName = getStoragePath();
            File f = new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX);
            if(!f.exists())
            {
                f.mkdir();
            }
            OutputStream accountOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while((length = accountInput.read(buffer))>0)
            {
                accountOutput.write(buffer, 0, length);
            }
            accountOutput.flush();
            accountOutput.close();
            accountInput.close();
        }
        catch (Exception e)
        {
            Log.e("Error copying", e.toString());
        }
    }

    private String getStoragePath()
    {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }
    public void controlLogin(View view) {
        String u = txtUsername.getText().toString();
        String p = txtPassword.getText().toString();
        if(validLogin(u,p))
        {
            Intent intent = new Intent(MainActivity.this, ClassListActivity.class);
            intent.putExtra(Identification.USERNAME, u);
            startActivity(intent);

            this.finish();
        }
    }

    private boolean validLogin(String u, String p) {
        for(Account acc : accountList)
        {
            if(acc.getUsername().equals(u))
            {
                if(acc.getPassword().equals(p))
                {
                    return true;
                }
                else return false;
            }
        }
        return false;
    }
}

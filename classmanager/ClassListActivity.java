package com.example.classmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.AdapterClassList;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class ClassListActivity extends AppCompatActivity {

    private static final String DB_PATH_SUFFIX ="/databases/";
    public static SQLiteDatabase database = null;

    ImageButton btnAddClass;
    EditText    txtAddClass;

    ListView    lvClassList;
    AdapterClassList adapterClassList;
    ArrayList<String> classList;

    public static String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        Intent intent = getIntent();
        username = intent.getStringExtra(Identification.USERNAME);
        Toast.makeText(ClassListActivity.this, "Logged in "+username, Toast.LENGTH_SHORT).show();
        addControls();
        classList = new ArrayList<>();
//        loadClassList();
        loadDatabaseSQLiteFromAsset();
        database = openOrCreateDatabase(username+".db", MODE_PRIVATE, null);
        loadDatabaseTableName();
        adapterClassList = new AdapterClassList(ClassListActivity.this, R.layout.item_class, classList);
        lvClassList.setAdapter(adapterClassList);
    }

    private void loadDatabaseTableName() {
        Cursor cursor = database.rawQuery("select name from sqlite_master where type='table'", null);
        while(cursor.moveToNext())
        {
            String tableName = cursor.getString(0);
            if(tableName.equals("android_metadata"))
                continue;
            else
                classList.add(tableName);
        }
        cursor.close();
    }

    private void loadDatabaseSQLiteFromAsset() {
        File dbFile = getDatabasePath(username+".db");
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

            InputStream accountInput = getAssets().open(username+".db");
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
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + username+".db";
    }

    //**********************************************************************************************
    //
    //! This function is auto called when the Delete Message finished, it is a result.
    //! It will delete the item at selected position if user choose "yes"
    //! else no deletion
    //
    //***********************************************************************************************
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Identification.DELETE_CLASS_ASK &&
            resultCode == Identification.DELETE_FEEDBACK)
        {
            //If user choosed yes to delete, this code will run
            if(data.getBooleanExtra(Identification.DELETE_CHOICE,false) == true){
                int position = data.getIntExtra(Identification.DELETE_POSITION, 0);//Receive the position that is sent by the Adapter when we chooose to delete
                classList.remove(position);
                adapterClassList.notifyDataSetChanged();
            }

        }
    }

    private void addControls() {

        btnAddClass = findViewById(R.id.btnAddClass);
        txtAddClass = findViewById(R.id.txtAddClass);
        lvClassList = findViewById(R.id.lvClassList);

    }

    private void loadClassListSQL() {

    }

    private void loadClassList() {
        classList.add("9");
        classList.add("8");
        classList.add("7");
        classList.add("6");
    }

    public void controlAddClass(View view) {
        classList.add(txtAddClass.getText().toString());
        adapterClassList.notifyDataSetChanged();
    }
}

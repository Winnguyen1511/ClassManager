package com.example.classmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.AdapterClassList;

import java.util.ArrayList;

public class ClassListActivity extends AppCompatActivity {
    ImageButton btnAddClass;
    EditText    txtAddClass;

    ListView    lvClassList;
    AdapterClassList adapterClassList;
    ArrayList<String> classList;

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_list);
        Intent intent = getIntent();
        username = intent.getStringExtra(Identification.USERNAME);
        Toast.makeText(ClassListActivity.this, "Logged in "+username, Toast.LENGTH_SHORT).show();
        addControls();
        classList = new ArrayList<>();
        loadClassList();
//        loadClassListSQL();
        adapterClassList = new AdapterClassList(ClassListActivity.this, R.layout.item_class, classList);
        lvClassList.setAdapter(adapterClassList);

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

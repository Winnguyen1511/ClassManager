package com.example.classmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DeleteMessage extends AppCompatActivity implements View.OnClickListener
{
    Intent intent;
    TextView txtDeleteMessage;

    Button btnYesDelete, btnNoDelete;
    boolean delete_or_not = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_message);
        btnYesDelete = findViewById(R.id.btnYesDelete);
        btnNoDelete = findViewById(R.id.btnNoDelete);
        txtDeleteMessage = findViewById(R.id.txtDeleteMessage);
        btnYesDelete.setOnClickListener(this);
        btnNoDelete.setOnClickListener(this);
        intent = getIntent();
        final String type = intent.getStringExtra(Identification.MESSAGE_TYPE);
        if(type.equals(Identification.DELETE_CLASS_TYPE)) {
            final String className = intent.getStringExtra(Identification.DELETE_CLASSNAME);
            txtDeleteMessage.setText("Do you want to delete class \"" + className + "\" ?");
        }
        else if(type.equals(Identification.DELETE_STUDENT_TYPE))
        {
            final String stuName = intent.getStringExtra(Identification.DELETE_STUDENT);
            txtDeleteMessage.setText("Do you want to delete student \"" + stuName + "\" ?");
        }
        else if(type.equals(Identification.DELETE_SELECTED_STUDENT_TYPE))
        {
            txtDeleteMessage.setText("Do you want to delete these student ?");
        }

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnYesDelete.getId())
        {
            delete_or_not = true;
//            intent.putExtra("POSITION",pos);
        }
        else if(view.getId() == btnNoDelete.getId())
        {
            delete_or_not = false;
        }
        else;
        intent.putExtra(Identification.DELETE_CHOICE, delete_or_not);
//        intent.
        setResult(Identification.DELETE_FEEDBACK, intent);
        this.finish();
    }
}

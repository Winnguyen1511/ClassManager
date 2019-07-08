package com.example.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classmanager.DeleteMessage;
import com.example.classmanager.Identification;
import com.example.classmanager.R;
import com.example.classmanager.StudentActivity;

import java.util.List;


public class AdapterClassList extends ArrayAdapter<String>
{
    Activity context;
    int resource;
    List<String> objects;
    View row;
    TextView txtClass;
    ImageButton btnEditClass, btnDelClass;


    public AdapterClassList(Activity context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }


    //**********************************************************************************************
    //
    //! Setting the getView function
    //! create an inflater, a row View
    //! add all controls of the Class List Item: txtClass , btnEditClass, btnDelClass
    //! get the object at position of the list
    //! set On click listener for 2 buttons on the item.
    //
    //**********************************************************************************************
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = this.context.getLayoutInflater();
        row = inflater.inflate(this.resource, null);
        addControls();
        final String className = objects.get(position);

        btnEditClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Opening class "+className, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, StudentActivity.class);
                intent.putExtra(Identification.EDIT_CLASSNAME, className);
//                context.startActivityForResult();
                context.startActivity(intent);
            }
        });
        btnDelClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Deleting class "+className, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DeleteMessage.class);//Send the type of deletion, here we delete a class
                intent.putExtra(Identification.MESSAGE_TYPE,Identification.DELETE_CLASS_TYPE );
                intent.putExtra(Identification.DELETE_POSITION, position);//Send position of selected item, for deletion in ClassListActivity
                intent.putExtra(Identification.DELETE_CLASSNAME, className);//Send className for display on Delete Message alert.
                context.startActivityForResult(intent, Identification.DELETE_CLASS_ASK);
            }
        });
        txtClass.setText(className);

        return row;
    }



    private void addControls() {
        txtClass = row.findViewById(R.id.txtClass);
        btnEditClass = row.findViewById(R.id.btnEditClass);
        btnDelClass = row.findViewById(R.id.btnDelClass);
    }


}

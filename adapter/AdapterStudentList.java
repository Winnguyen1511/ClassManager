package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.classmanager.DeleteMessage;
import com.example.classmanager.Identification;
import com.example.classmanager.R;
import com.example.model.Student;

import java.util.List;

public class AdapterStudentList extends ArrayAdapter<Student>
{
    Activity context;
    int resource;
    List<Student> objects;
    public AdapterStudentList(Activity context, int resource, List<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        TextView txtStudent = row.findViewById(R.id.txtStudent);
        ImageButton btnCall = row.findViewById(R.id.btnCall);
        ImageButton btnMessage = row.findViewById(R.id.btnMessage);
        ImageButton btnDeleteStudent = row.findViewById(R.id.btnDeleteStudent);

        final Student stu = this.objects.get(position);
        txtStudent.setText(stu.getName()+"\n"+stu.getPhone());

        //******************************************************************************************
        //
        //! Handling 3 buttons on each student item: Call, message, trash
        //
        //******************************************************************************************
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call(stu.getPhone());
            }
        });
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendMessage(stu.getPhone());
            }
        });
        btnDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DeleteMessage.class);
                intent.putExtra(Identification.MESSAGE_TYPE,Identification.DELETE_STUDENT_TYPE );//Send the type of deletion, here we delete a student
                intent.putExtra(Identification.DELETE_POSITION, position);//Send position of selected item, for deletion in ClassListActivity
                intent.putExtra(Identification.DELETE_STUDENT, stu.getName());//Send student name for display on Delete Message alert.
                context.startActivityForResult(intent, Identification.DELETE_STUDENT_ASK);
            }
        });


        return row;

    }

    private void SendMessage(String phone) {
        Uri uri = Uri.parse("tel:"+phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(uri);
        this.context.startActivity(intent);
    }

    private void Call(String phone) {
        Uri uri = Uri.parse("tel:"+phone);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(uri);
        this.context.startActivity(intent);
    }
}

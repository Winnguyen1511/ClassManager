package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.model.Student;

import java.util.List;

public class AdapterChooseStudent extends ArrayAdapter<Student> {

    Activity context;
    int resource;
    List<Student> objects;
    public AdapterChooseStudent(Activity context, int resource, List<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);

        return row;

    }
}

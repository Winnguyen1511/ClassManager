package com.example.classmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.model.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ChooseStudentActivity extends AppCompatActivity {

//    CheckedTextView tmp;
    private final char CHECKED_ALL = 0x2;
    private final char CHECKED_NONE = 0x0;
    private final char CHECKED_SELECTED = 0x1;
    Intent intent;
    ImageButton btnMessageSeleted, btnSeleteAll, btnDelSeleted;
    TextView txtNumStudent;
    ListView lvChooseStudent;
    ArrayAdapter<String> adapter;
    ArrayList<String>stuList = new ArrayList<>();
    ArrayList<Student>dataList = new ArrayList<>();
    char checkAll = CHECKED_NONE;
    ArrayList<Integer> selectedPositions = new ArrayList<>();
    String className;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_student);
        intent = getIntent();
        className = intent.getStringExtra(Identification.CHOOSE_STUDENT_CLASSNAME);
        addControls();
        loadData(className);
//        loadDataSQL(className);
        lvChooseStudent.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        adapter = new ArrayAdapter<>(this,R.layout.item_choose_student,R.id.txtChooseStudent,stuList);
        lvChooseStudent.setAdapter(adapter);

        addEvents();
    }


    //***************************************************************************************************************
    //
    //! ON ACTIVITY RESULT , mostly for delete alert
    //
    //****************************************************************************************************************
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Identification.DELETE_SELECTED_STUDENT_ASK && resultCode == Identification.DELETE_FEEDBACK )
        {
            if(data.getBooleanExtra(Identification.DELETE_CHOICE,false) == true){
                if (checkAll == CHECKED_ALL)
                    setResult(Identification.CHOOSE_STUDENT_DELETE_ALL_FEEDBACK,intent );
                else {
                    int count = 0;
                    int []buffer = new int[selectedPositions.size()];
                    Collections.sort(selectedPositions);
                    for(Integer i :selectedPositions)
                    {
                        buffer[count++] = i;
                    }

                    intent.putExtra(Identification.CHOOSE_STUDENT_DELETE,buffer );
                    setResult(Identification.CHOOSE_STUDENT_DELETE_FEEDBACK, intent);

                }
                finish();
            }
        }
    }

    private void addEvents() {
        //******************************************************************************************
        //
        //! MESSAGE button
        //
        //******************************************************************************************
        btnMessageSeleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    int count = 0;
                    int[] buffer = new int[selectedPositions.size()];
                    Collections.sort(selectedPositions);
                    for (Integer i : selectedPositions) {
                        buffer[count++] = i;
                    }

                    intent.putExtra(Identification.CHOOSE_STUDENT_MESSAGE, buffer);
                    setResult(Identification.CHOOSE_STUDENT_MESSAGE_FEEDBACK, intent);
                    finish();

            }
        });
        //******************************************************************************************
        //
        //! DELETE buttons
        //
        //******************************************************************************************
        btnDelSeleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent delIntent = new Intent(ChooseStudentActivity.this, DeleteMessage.class);
                delIntent.putExtra(Identification.MESSAGE_TYPE, Identification.DELETE_SELECTED_STUDENT_TYPE);
                startActivityForResult(delIntent, Identification.DELETE_SELECTED_STUDENT_ASK);
            }
        });
        //******************************************************************************************
        //
        //! SELECT ALL button
        //! Note: add all selected position to avoid the bug for messaging (see line 140)
        //          clear all position to avoid bug for messaging (see line 149)
        //
        //******************************************************************************************
        btnSeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAll != CHECKED_ALL)
                {
                    checkAll = CHECKED_ALL;
                    selectedPositions.clear();
                    for(int i = 0; i < stuList.size(); i++)
                    {
                        lvChooseStudent.setItemChecked(i,true);
                        selectedPositions.add(i);
                    }
                }
                else{
                    checkAll= CHECKED_NONE;
                    for(int i = 0; i < stuList.size(); i++)
                    {
                        lvChooseStudent.setItemChecked(i,false);
                    }
                    selectedPositions.clear();
                }
                txtNumStudent.setText(Integer.toString(selectedPositions.size()));

            }
        });
//************************************************************************************************************
//
//! ITEM CLICK LISTENER
//
//************************************************************************************************************
        lvChooseStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                checkAll = CHECKED_SELECTED;

                if(selectedPositions.contains((Integer) i))
                {
                    selectedPositions.remove((Integer) i);
                }
                else{
                    selectedPositions.add(i);
                }
                txtNumStudent.setText(Integer.toString(selectedPositions.size()));
            }
        });
    }

    private void loadDataSQL(String s) {

    }

    private void loadData(String s) {
        if(className.equals("9"))
        {
//            dataList = class1.getList();
            dataList.add(new Student("Nguyen Huynh Dang Khoa", "0775434699"));
            dataList.add(new Student("Nguyen Huu Duc", "0905502523"));
            dataList.add(new Student("Huynh Thi Phuong Lang", "0905502524"));
//            dataList.add(new Student(""))
        }
        else if(className.equals("8"))
        {
//            dataList = class2.getList();
            dataList.add(new Student("Phan Duy Linh", "0905732447"));
            dataList.add(new Student("Nguyen Duc Khai","0774378865"));
            dataList.add(new Student("Nguyen Thi Minh Hien", "0354390055"));
            dataList.add(new Student("Nguyen Huynh Dang Khoa", "0775434699"));
        }
        else if(className.equals("7"))
        {
//            dataList = class3.getList();
        }
        else if(className.equals("6"))
        {
//            dataList = class4.getList();
        }
        else
        {

        }
        for(Student stu: dataList)
        {
            stuList.add(stu.getName()+"\n"+stu.getPhone());
        }
    }

    private void addControls() {
        btnMessageSeleted = findViewById(R.id.btnMessageSeleted);
        btnSeleteAll = findViewById(R.id.btnSelectAll);
        btnDelSeleted = findViewById(R.id.btnDelSeleted);
        txtNumStudent = findViewById(R.id.txtNumStudent);
        lvChooseStudent = findViewById(R.id.lvChooseStudent);
//        lvChooseStudent.set
    }
}

package com.example.classmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.AdapterStudentList;
import com.example.model.ClassStudent;
import com.example.model.Student;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {


    ListView lvStudent;
    AdapterStudentList adapterStudentList;
    ArrayList<Student> dataList = new ArrayList<>();
    String className;
    String username;
    ClassStudent class1, class2, class3, class4;

    ImageButton btnAddStudent, btnChooseStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Intent intent = getIntent();
        username = intent.getStringExtra(Identification.USERNAME);
        className = intent.getStringExtra(Identification.EDIT_CLASSNAME);
        Toast.makeText(StudentActivity.this, "Editing " + className, Toast.LENGTH_SHORT).show();
        addControls();
//        loadStudentList(className);
        loadStudentListSQL(className);
        adapterStudentList = new AdapterStudentList(StudentActivity.this, R.layout.item_student, dataList);
        lvStudent.setAdapter(adapterStudentList);

    }

    //****************************************************************************************************************
    //
    //! ON ACTIVITY RESULT
    //
    //************************************************************************************************************
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //******************************************************************************************************
        //
        //! Listen to Delete 1 student action
        //
        //******************************************************************************************************
        if (requestCode == Identification.DELETE_STUDENT_ASK && resultCode == Identification.DELETE_FEEDBACK) {
            if (data.getBooleanExtra(Identification.DELETE_CHOICE, false) == true) {
                int position = data.getIntExtra(Identification.DELETE_POSITION, 0);//Receive the position that is sent by the Adapter when we chooose to delete
                dataList.remove(position);
                adapterStudentList.notifyDataSetChanged();
//                ClassListActivity.database.delete()
//                updateStudentListSQL();
            }
        }

        //******************************************************************************************************
        //
        //! Listen to Add 1 student action after open a add student form.
        //
        //******************************************************************************************************
        if (requestCode == Identification.ADD_STUDENT_ASK && resultCode == Identification.ADD_STUDENT_FEEDBACK) {
            if (data.getBooleanExtra(Identification.ADD_STUDENT_CHOICE, false) == true) {
                Student stu = (Student) data.getSerializableExtra(Identification.ADD_STUDENT_SERIALIZABLE);
                dataList.add(stu);
                adapterStudentList.notifyDataSetChanged();
            }
        }

        //******************************************************************************************************************
        //
        //! Listen to delete selected student
        //
        //******************************************************************************************************************
        if (requestCode == Identification.CHOOSE_STUDENT_ASK && resultCode == Identification.CHOOSE_STUDENT_DELETE_FEEDBACK) {
            int[] buffer = data.getIntArrayExtra(Identification.CHOOSE_STUDENT_DELETE);
            if (buffer.length > 0) {
                for (int i = buffer.length - 1; i >= 0; i--) {
                    dataList.remove(buffer[i]);
                }
            }
            adapterStudentList.notifyDataSetChanged();
//                updateStudentListSQL();
        }
        if (requestCode == Identification.CHOOSE_STUDENT_ASK && resultCode == Identification.CHOOSE_STUDENT_DELETE_ALL_FEEDBACK) {
            dataList.clear();
            adapterStudentList.notifyDataSetChanged();
//                updateStudentListSQL();
        }


        //*********************************************************************************************************************
        //
        //! Listen to message selected student
        //! Note: Only message when size of buffer >0
        // Mean that only when user choose a number to message, else no operation will occur
        //
        //*********************************************************************************************************************
        if (requestCode == Identification.CHOOSE_STUDENT_ASK && resultCode == Identification.CHOOSE_STUDENT_MESSAGE_FEEDBACK) {
            int[] buffer = data.getIntArrayExtra(Identification.CHOOSE_STUDENT_MESSAGE);
            if (buffer.length > 0) {
                String s = dataList.get(buffer[0]).getPhone();
                for (int i = 1; i < buffer.length; i++) {
                    s += ";" + dataList.get(buffer[i]).getPhone();
                }
                Uri uri = Uri.parse("tel:" + s);
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(uri);
                startActivity(intent);
            }
        }


    }

    //******************************************************************************************************************************
    //
    //! Simply copy all data from the dataList to the database
    //
    //*******************************************************************************************************************************
    private void updateStudentListSQL() {
        //TODO: Delete all the table
        ClassListActivity.database.delete(className,null, null);
        //TODO: add all item in the dataList back to the table database
        for(Student stu: dataList)
        {
            ContentValues row = new ContentValues();
            row.put("Name",stu.getName());
            row.put("Phone",stu.getPhone());
            ClassListActivity.database.insert(className, null, row);
        }


    }

    private void loadStudentList(String className) {
        dataList = new ArrayList<>();
        class1 = new ClassStudent();
        class2 = new ClassStudent();
        class3 = new ClassStudent();
        class4 = new ClassStudent();
        class1.setClassName("9");
        class2.setClassName("8");
        class3.setClassName("7");
        class4.setClassName("6");
        if (className.equals(class1.getClassName())) {
//            dataList = class1.getList();
            dataList.add(new Student("Nguyen Huynh Dang Khoa", "0775434699"));
            dataList.add(new Student("Nguyen Huu Duc", "0905502523"));
            dataList.add(new Student("Huynh Thi Phuong Lang", "0905502524"));
//            dataList.add(new Student(""))
        } else if (className.equals(class2.getClassName())) {
//            dataList = class2.getList();
            dataList.add(new Student("Phan Duy Linh", "0905732447"));
            dataList.add(new Student("Nguyen Duc Khai", "0774378865"));
            dataList.add(new Student("Nguyen Thi Minh Hien", "0354390055"));
            dataList.add(new Student("Nguyen Huynh Dang Khoa", "0775434699"));
        } else if (className.equals(class3.getClassName())) {
//            dataList = class3.getList();
        } else if (className.equals(class4.getClassName())) {
//            dataList = class4.getList();
        } else {

        }
    }


    //**********************************************************************************************************
    //
    //! Load list of student to arraylist
    //
    //**********************************************************************************************************

    private void loadStudentListSQL(String className) {
        Cursor cursor = ClassListActivity.database.rawQuery("select * from " + className, null);
        dataList.clear();
        while ((cursor.moveToNext())) {
            String name = cursor.getString(0);
            String phone = cursor.getString(1);
            dataList.add(new Student(name, phone));
        }
        cursor.close();

    }


    private void addControls() {
        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnChooseStudent = findViewById(R.id.btnChooseStudent);
        lvStudent = findViewById(R.id.lvStudent);
    }

    public void controlAddStudent(View view) {
        Intent intent = new Intent(StudentActivity.this, AddStudentForm.class);
//        intent.putExtra(Identification.)
        startActivityForResult(intent, Identification.ADD_STUDENT_ASK);
    }

    public void controlCheckStudent(View view) {
        Intent intent = new Intent(StudentActivity.this, ChooseStudentActivity.class);
        intent.putExtra(Identification.CHOOSE_STUDENT_CLASSNAME, className);
        intent.putExtra(Identification.CHOOSE_STUDENT_LIST, dataList);
        startActivityForResult(intent, Identification.CHOOSE_STUDENT_ASK);
    }
    //**********************************************************************************************
    //
    //! Update the database before closing or switching to other activity
    //
    //**********************************************************************************************

    @Override
    protected void onPause() {
        super.onPause();
        updateStudentListSQL();
    }
}
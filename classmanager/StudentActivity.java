package com.example.classmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    ArrayList<Student> dataList;
    String className;
    ClassStudent class1, class2, class3, class4;

    ImageButton btnAddStudent, btnChooseStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Intent intent = getIntent();
        className = intent.getStringExtra(Identification.EDIT_CLASSNAME);
        Toast.makeText(StudentActivity.this, "Editing "+className, Toast.LENGTH_SHORT).show();
        addControls();
        loadStudentList(className);
        adapterStudentList = new AdapterStudentList(StudentActivity.this, R.layout.item_student, dataList);
        lvStudent.setAdapter(adapterStudentList);
        //loadStudentListSQL(className);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Identification.DELETE_STUDENT_ASK && resultCode ==Identification.DELETE_FEEDBACK)
        {
            if(data.getBooleanExtra(Identification.DELETE_CHOICE,false) == true){
                int position = data.getIntExtra(Identification.DELETE_POSITION, 0);//Receive the position that is sent by the Adapter when we chooose to delete
                dataList.remove(position);
                adapterStudentList.notifyDataSetChanged();
//                updateStudentListSQL();
            }
        }
        if(requestCode == Identification.ADD_STUDENT_ASK && resultCode ==Identification.ADD_STUDENT_FEEDBACK)
        {
            if(data.getBooleanExtra(Identification.ADD_STUDENT_CHOICE,false) == true)
            {
                Student stu = (Student) data.getSerializableExtra(Identification.ADD_STUDENT_SERIALIZABLE);
                dataList.add(stu);
                adapterStudentList.notifyDataSetChanged();
            }
        }
    }

    private void updateStudentListSQL() {

    }

    private void loadStudentList(String className)
    {
        dataList = new ArrayList<>();
        class1 = new ClassStudent();
        class2 = new ClassStudent();
        class3 = new ClassStudent();
        class4 = new ClassStudent();
        class1.setClassName("9");
        class2.setClassName("8");
        class3.setClassName("7");
        class4.setClassName("6");
        if(className.equals(class1.getClassName()))
        {
//            dataList = class1.getList();
            dataList.add(new Student("Nguyen Huynh Dang Khoa", "0775434699"));
            dataList.add(new Student("Nguyen Huu Duc", "0905502523"));
            dataList.add(new Student("Huynh Thi Phuong Lang", "0905502524"));
//            dataList.add(new Student(""))
        }
        else if(className.equals(class2.getClassName()))
        {
//            dataList = class2.getList();
            dataList.add(new Student("Phan Duy Linh", "0905732447"));
            dataList.add(new Student("Nguyen Duc Khai","0774378865"));
            dataList.add(new Student("Nguyen Thi Minh Hien", "0354390055"));
            dataList.add(new Student("Nguyen Huynh Dang Khoa", "0775434699"));
        }
        else if(className.equals(class3.getClassName()))
        {
//            dataList = class3.getList();
        }
        else if(className.equals(class4.getClassName()))
        {
//            dataList = class4.getList();
        }
        else
        {

        }
    }
    private void loadStudentListSQL(String className)
    {

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

    }
}

package com.example.classmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.model.Student;

public class AddStudentForm extends AppCompatActivity implements View.OnClickListener {


    Intent intent;

    EditText txtAddNameStudent, txtAddPhoneStudent;
    Button btnOkAddStudent, btnNoAddStudent;
    boolean add_or_not = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student_form);
        txtAddNameStudent = findViewById(R.id.txtAddNameStudent);
        txtAddPhoneStudent = findViewById(R.id.txtAddPhoneStudent);
        btnOkAddStudent = findViewById(R.id.btnOkAddStudent);
        btnNoAddStudent = findViewById(R.id.btnNoAddStudent);
        btnOkAddStudent.setOnClickListener(this);
        btnNoAddStudent.setOnClickListener(this);
        intent = getIntent();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == btnOkAddStudent.getId())
        {
            add_or_not = true;
            Student stu = new Student(txtAddNameStudent.getText().toString(), txtAddPhoneStudent.getText().toString());
            intent.putExtra(Identification.ADD_STUDENT_SERIALIZABLE, stu);
//            intent.put
        }
        else if(view.getId() == btnNoAddStudent.getId())
        {
            add_or_not = false;
        }
        else;

        intent.putExtra(Identification.ADD_STUDENT_CHOICE, add_or_not);
        setResult(Identification.ADD_STUDENT_FEEDBACK, intent);
        this.finish();

    }
}

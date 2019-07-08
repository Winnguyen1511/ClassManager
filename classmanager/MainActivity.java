package com.example.classmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txtUsername, txtPassword;
    Button  btnLogin;
    String  username = "khoa";
    String  password = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

    }

    public void controlLogin(View view) {
        String u = txtUsername.getText().toString();
        String p = txtPassword.getText().toString();
        if(u.equals(username) && p.equals(password))
        {
            Intent intent = new Intent(MainActivity.this, ClassListActivity.class);
            intent.putExtra(Identification.USERNAME, username);
            startActivity(intent);

            this.finish();
        }
    }
}

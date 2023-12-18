package com.example.swasthyamitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText username, password, conformpassword, answer;
    Button register;
    TextView login;
    DBhelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)  findViewById(R.id.etRegUsername);
        password = (EditText) findViewById(R.id.etRegPassword);
        conformpassword = (EditText) findViewById(R.id.etRegPassword2);
        register = (Button) findViewById(R.id.btnRegister);
        login = (TextView) findViewById(R.id.tvLogin);
        answer = (EditText) findViewById(R.id.etRegQuestion);
        DB = new DBhelper(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String confpass = conformpassword.getText().toString();
                String answers = answer.getText().toString();

                if (user.equals("") || pass.equals("") || confpass.equals("") || answers.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please Enter all fields", Toast.LENGTH_SHORT).show();
                else if (pass.equals(confpass)) {
                    Boolean checkuser = DB.checkUsername(user);
                    if (checkuser == false) {
                        Boolean insert = DB.insertData(user, pass, answers);
                        if (insert == true) {
                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Passwords are not matching ", Toast.LENGTH_SHORT).show();
                }
            }

        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}
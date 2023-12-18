package com.example.swasthyamitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText ForgotUsername, NewPassword, NewConformPassword, ForgotAnswer;

    Button ResetPasswordbtn;

    DBhelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);



        ForgotUsername  = (EditText)  findViewById(R.id.etForgotUserName);
        NewPassword = (EditText)  findViewById(R.id.etForgotPassword);
        NewConformPassword = (EditText)  findViewById(R.id.etForgotConfPassword);
        ForgotAnswer = (EditText)  findViewById(R.id.etForgotAnswer);
        ResetPasswordbtn = (Button) findViewById(R.id.btnResetPassword);

        DB = new DBhelper(this);

        ResetPasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = ForgotUsername.getText().toString();
                String newpass = NewPassword.getText().toString();
                String connewpass = NewConformPassword.getText().toString();
                String ans = ForgotAnswer.getText().toString();

                Boolean checkuser = DB.checkUsername(user);
                if (checkuser == true){
                    Boolean checkans = DB.checkAnswer(ans);
                    if (checkans == true) {
                        if (newpass.equals(connewpass)){
                            Boolean checkpasswordupdate = DB.updatePassword(user, newpass);
                        if (checkpasswordupdate == true) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(ForgotPasswordActivity.this, "Password updated", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "failed to update password", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                            Toast.makeText(ForgotPasswordActivity.this, "password does not match", Toast.LENGTH_SHORT).show();
                        }


                    }else {
                        Toast.makeText(ForgotPasswordActivity.this, "incorrect Answer", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ForgotPasswordActivity.this, "User does not exists", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
package com.example.chitchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogActivity extends AppCompatActivity {
    EditText email,pass;
    Button log;
    String Email,Pass;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pass);
        log=(Button)findViewById(R.id.login);
        mAuth=FirebaseAuth.getInstance();
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=email.getText().toString().trim();
                Pass=pass.getText().toString().trim();

                login(Email,Pass);
            }
        });

    }
    public void login(String Email,String Pass){

        if(Email.equals("")||Pass.equals(""))
        {
            Toast.makeText(LogActivity.this,"Fill the Email and Password fields",Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        //checkEmailverification();
                        startActivity(new Intent(LogActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(LogActivity.this, "unsuccessfull to login", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

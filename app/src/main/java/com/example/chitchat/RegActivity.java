package com.example.chitchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegActivity extends AppCompatActivity {
    EditText email,pass,username;
    Button register;
    String Email,Pass,Username;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        email=(EditText) findViewById(R.id.email);
        pass=(EditText) findViewById(R.id.pass);
        username=findViewById(R.id.username);
        register=(Button)findViewById(R.id.register);
        mAuth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email=email.getText().toString().trim();
                Pass=pass.getText().toString().trim();
                Username=username.getText().toString().trim();
                Toast.makeText(RegActivity.this,"registering",Toast.LENGTH_SHORT).show();
                Register(Username,Email,Pass);

            }
        });

    }
    public void Register(final String username,String Email,String Pass){
        if(Email.equals("")||Pass.equals(""))
        {
            Toast.makeText(RegActivity.this,"Fill the Email and Password fields",Toast.LENGTH_SHORT).show();
        }else {
            mAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser=mAuth.getCurrentUser();
                        String userid=firebaseUser.getUid();
                        reference=FirebaseDatabase.getInstance().getReference("Users").child(userid);
                        HashMap<String,String>hashMap=new HashMap<>();
                        hashMap.put("id",userid);
                        hashMap.put("username",username);
                        Toast.makeText(RegActivity.this, "email successfull", Toast.LENGTH_SHORT).show();
                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegActivity.this, "data successfull", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(RegActivity.this, "unsuccessfull", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}

package com.wheic.EazyLearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class registerActivity extends AppCompatActivity {
    TextView haveaccount;
    EditText nameinput,emailinput,passinput,cpassinput;
    Button btnregister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        haveaccount=findViewById(R.id.haveaccount);

        nameinput=findViewById(R.id.name);
        emailinput=findViewById(R.id.email);
        passinput=findViewById(R.id.password);
        cpassinput=findViewById(R.id.cpassword);
        btnregister=findViewById(R.id.registerbutton);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerforAuth();
            }
        });
        haveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(registerActivity.this,login_activity.class);
                startActivity(intent);
            }
        });
    }

    private void PerforAuth() {
        String name= nameinput.getText().toString();
        String email= emailinput.getText().toString();
        String password=passinput.getText().toString();
        String cpassword= cpassinput.getText().toString();


        if (name.isEmpty()){
            nameinput.setError("Enter name");
            nameinput.requestFocus();
        }else if (!email.matches(emailPattern))
        {
            emailinput.setError("Enter valid emailaddress");
            emailinput.requestFocus();
    }else if (password.isEmpty()||password.length()<6) {
        passinput.setError("Enter a valid password");
        passinput.requestFocus();
        }else if (!password.equals(cpassword)){
            cpassinput.setError("Passwords doesn't match");
            cpassinput.requestFocus();
        }else{
            progressDialog.setMessage("Please wait");
            progressDialog.setTitle("Registering");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        adduser();
                    }else {
                        Toast.makeText(registerActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }

                private void adduser() {
                    User user=new User(name,email,0,0,0,0,0,0,0);
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog.dismiss();
                                Intent intent= new Intent(registerActivity.this,login_activity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                Toast.makeText(registerActivity.this, "Successfuly Registered", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });

        }
    }
}
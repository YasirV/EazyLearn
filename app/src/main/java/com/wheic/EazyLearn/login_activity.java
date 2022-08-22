package com.wheic.EazyLearn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class login_activity extends AppCompatActivity {

    TextView resetpassword,newaccount;
    EditText emailinput2,passinput2;
    Button btnlogin;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser != null) {
            // User is signed in
            Intent i = new Intent(login_activity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d("#########", "onAuthStateChanged:signed_out");


            setContentView(R.layout.activity_login);

            resetpassword = findViewById(R.id.resetpass);
            newaccount = findViewById(R.id.newaccount);

            emailinput2 = findViewById(R.id.email2);
            passinput2 = findViewById(R.id.password2);
            btnlogin = findViewById(R.id.loginbutton);
            progressDialog = new ProgressDialog(this);


            newaccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(login_activity.this, registerActivity.class));
                }
            });

            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = emailinput2.getText().toString();
                    String password = passinput2.getText().toString();

                    if (!email.matches(emailPattern)) {
                        emailinput2.setError("Enter valid emailaddress");
                        emailinput2.requestFocus();
                    } else if (password.isEmpty() || password.length() < 6) {
                        passinput2.setError("Enter a valid password");
                        passinput2.requestFocus();
                    } else {
                        progressDialog.setMessage("Please wait");
                        progressDialog.setTitle("Logging In");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(login_activity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    Toast.makeText(login_activity.this, "Successfuly logged in", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(login_activity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }
            });
            resetpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(login_activity.this, forgotPassword.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }

    }
    }

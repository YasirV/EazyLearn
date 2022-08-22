package com.wheic.EazyLearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    EditText emailId;
    Button resetButton;
    TextView resetResult;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        setTitle("Forgot Password");

        emailId = findViewById(R.id.email3);
        resetButton = findViewById(R.id.resetbutton2);
        resetResult = findViewById(R.id.resetresult);



        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailId.getText().toString();
                if(!email.matches(emailPattern)){
                    emailId.setError("Enter valid emailaddress");
                    emailId.requestFocus();
                }
                else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        resetResult.setText("Mail sent Successfully");
                                        Toast.makeText(forgotPassword.this, "++++", Toast.LENGTH_SHORT).show();
                                    } else {
                                        resetResult.setText(task.getException().toString());
                                        resetResult.setTextColor(Color.RED);
                                    }
                                }
                            });
                }
            }
        });
    }
}
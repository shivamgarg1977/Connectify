package com.shivamgarg1977.connectify.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shivamgarg1977.connectify.R;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText muserEmailET, muserPasswordET;
    ExtendedFloatingActionButton mLoginBtn;
    TextView mSignUpNavigator;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        muserEmailET = findViewById(R.id.user_emailET);
        muserPasswordET = findViewById(R.id.user_passwordST);
        mLoginBtn = findViewById(R.id.login_btn);
        mSignUpNavigator = findViewById(R.id.signupNavigator);
        mAuth= FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = muserEmailET.getText().toString().trim();
                String password = muserPasswordET.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    muserEmailET.setError("Invalid Email");
                    muserEmailET.setFocusable(true);
                } else if (password.length() < 8) {
                    muserPasswordET.setError("Password length at least 8 characters");
                    muserPasswordET.setFocusable(true);

                } else {
                    validateUser(email,password);
                }
            }
        });
        mSignUpNavigator.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View view){
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
            });
        }

    private void validateUser(String email, String password) {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login Successful",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Authentication failed."+ e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
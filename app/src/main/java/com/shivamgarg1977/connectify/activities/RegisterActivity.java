package com.shivamgarg1977.connectify.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
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

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText mfullNameET,muserEmailET,muserPasswordET;
    ExtendedFloatingActionButton mRegisterBtn;
    ProgressDialog progressDialog;

    //firebase auth
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mfullNameET=findViewById(R.id.full_nameET);
        muserEmailET=findViewById(R.id.user_emailET);
        muserPasswordET=findViewById(R.id.user_passwordST);
        mRegisterBtn=findViewById(R.id.register_btn);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Registering User....");

        mAuth = FirebaseAuth.getInstance();

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullName= mfullNameET.getText().toString().trim();
                String email=muserEmailET.getText().toString().trim();
                String password = muserPasswordET.getText().toString().trim();

                //validate
                if(fullName.isEmpty()){
                    mfullNameET.setError("Name can;t be Empty");
                    mfullNameET.setFocusable(true);
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    muserEmailET.setError("Invalid Email");
                    muserEmailET.setFocusable(true);
                } else if(password.length()<8){
                    muserPasswordET.setError("Password length at least 8 characters");
                    muserPasswordET.setFocusable(true);
                } else{
                    registerUser(email,password);
                }

            }
        });

    }

    private void registerUser(String email, String password) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegisterActivity.this, "Registration Successful..."+user.getEmail(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Authentication failed."+e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();// navigate to previous activity
        return super.onSupportNavigateUp();
    }
}
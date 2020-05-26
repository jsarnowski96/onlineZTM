package com.example.onlineztm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class UserRegistrationActivity extends AppCompatActivity {

    Button cancelButton;
    Button registerButton;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private EditText emailField;
    private EditText passwordField;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_registration);

        mAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!emailField.getText().toString().matches("") && !passwordField.getText().toString().matches("")) {
                    email = emailField .getText().toString().trim();
                    password = passwordField .getText().toString().trim();
                    registerUser(email, password);
                }
                else {
                    Toast.makeText(UserRegistrationActivity.this, "You did not enter e-mail and/or password. Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void registerUser(String email, String password) {
        if(email != null && password != null) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("CreateUserWithEmail", "SUCCESS");
                                mUser = mAuth.getCurrentUser();
                                Toast.makeText(UserRegistrationActivity.this, "User registration complete - welcome, " + mUser.getEmail(), Toast.LENGTH_SHORT).show();
                                launchMainActivity();
                            } else {
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    passwordField.setError("Use more complex password");
                                } catch (FirebaseAuthUserCollisionException e) {
                                    emailField.setError("There is already a user with this e-mail");
                                } catch (Exception e) {
                                    Log.e("LoginUserWithEmail", e.getMessage());
                                }
                                Log.e("CreateUserWithEmail", "FAILURE", task.getException());
                            }
                        }
                    });
        }
        else {
            Toast.makeText(UserRegistrationActivity.this, "You did not enter e-mail and/or password. Try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}

package com.example.onlineztm.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineztm.MainActivity;
import com.example.onlineztm.R;
import com.example.onlineztm.UserRegistrationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private EditText emailField;
    private EditText passwordField;
    private String email;
    private String password;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);

        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText emailField = findViewById(R.id.emailField);
        final EditText passwordField = findViewById(R.id.passwordField);
        final Button loginButton = findViewById(R.id.loginButton);
        final Button skipLoginButton = findViewById(R.id.skipLoginButton);
        final Button registerButton = findViewById(R.id.registerButton);
        final Button exitButton = findViewById(R.id.exitButton);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    emailField.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordField.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUI(mUser);
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                //finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(emailField.getText().toString(),
                        passwordField.getText().toString());
            }
        };
        emailField.addTextChangedListener(afterTextChangedListener);
        passwordField.addTextChangedListener(afterTextChangedListener);
        passwordField.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(emailField.getText().toString(),
                            passwordField.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                if(emailField.getText().toString() != "" && passwordField.getText().toString() != "" && emailField != null && passwordField != null) {
                    email = emailField .getText().toString().trim();
                    password = passwordField .getText().toString().trim();
                    loginViewModel.login(email,
                            password);
                    loginUser(email, password);
                }
                else {
                    Toast.makeText(LoginActivity.this, "You did not enter e-mail and/or password. Try again", Toast.LENGTH_SHORT).show();
                }
                loadingProgressBar.setVisibility(View.GONE);
            }
        });

        skipLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anonymousAccess();
                launchMainActivity();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchRegisterActivity();
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }
    }

    private void updateUI(FirebaseUser account) {
        if(account != null){
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    private void launchRegisterActivity() {
        Intent intent = new Intent(this, UserRegistrationActivity.class);
        startActivity(intent);
    }

    private void loginUser(String email, String password) {
        if(email != null && password != null) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mUser = mAuth.getCurrentUser();
                                String userId = mUser.getUid();
                                String userEmail = emailField.getText().toString().trim();
                                sharedPreferences = getPreferences(MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("FireBaseUid", userId);
                                editor.putString("UserEmail", mUser.getEmail());
                                editor.commit();
                            } else {
                                Log.w("SignInWithEmail", "FAILURE", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed - check your e-mail or password",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("LoginUserException", e.toString());
                        }
                    });

            /*FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth mAuth) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        String userId = firebaseUser.getUid();
                        String userEmail = emailField.getText().toString().trim();
                        sharedPreferences = getPreferences(MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("FireBaseUid", userId);
                        editor.putString("UserEmail", userEmail);
                        editor.commit();
                    }
                }
            };*/
        }
        else {
            Toast.makeText(LoginActivity.this, "You did not enter e-mail and/or password. Try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void anonymousAccess() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("signInAnonymously", "SUCCESS");
                            //mAuth = FirebaseAuth.getInstance();
                            mUser = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "Signed as Anonymous", Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("signInAnonymously", "FAILURE", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

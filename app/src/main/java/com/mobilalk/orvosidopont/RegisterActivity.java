package com.mobilalk.orvosidopont;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    TextView mLoginButton;
    TextInputEditText mPasswordEditText, mEmailEditText;
    MaterialButton mRegisterButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        mLoginButton = findViewById(R.id.loginTextView);
        mEmailEditText = findViewById(R.id.emailEditText);
        mPasswordEditText = findViewById(R.id.passwordEditText);
        mRegisterButton = findViewById(R.id.registerButton);

        mLoginButton.setOnClickListener(v -> finish());

        mRegisterButton.setOnClickListener(v -> {
            mAuth.createUserWithEmailAndPassword(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isComplete()){
                        Log.d("SIKER", "sikeres regisztracio");
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }else
                        Log.d("HIBA", task.getException().toString());
                }
            });
        });
    }
}
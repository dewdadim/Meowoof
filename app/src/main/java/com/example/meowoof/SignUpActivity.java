package com.example.meowoof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView signinLink;
        EditText name, username, phoneNumber, password, confirmPassword;
        Button signup_button;

        name = findViewById(R.id.name_input);
        username = findViewById(R.id.username_input);
        phoneNumber = findViewById(R.id.phone_input);
        password = findViewById(R.id.password_input);
        confirmPassword = findViewById(R.id.confirmPassword_input);
        signup_button = findViewById(R.id.edit_button);
        SessionManager sessionManager = new SessionManager(SignUpActivity.this);

        if(sessionManager.getSignIn()){
            startActivity(new Intent(SignUpActivity.this, MainMenuActivity.class));
            finish();
        }

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(SignUpActivity.this);

                if(name.getText().toString().isEmpty()
                        || username.getText().toString().isEmpty()
                        || phoneNumber.toString().isEmpty()
                        || password.toString().isEmpty()
                        || confirmPassword.toString().isEmpty()){
                    Toast.makeText(SignUpActivity.this,"All fields need to be filled in!", Toast.LENGTH_SHORT).show();
                } else {

                    if(db.checkUsername(username.getText().toString().trim())){
                        Toast.makeText(SignUpActivity.this, "User already exist!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (!password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())) {
                        Toast.makeText(SignUpActivity.this, "Password not match!", Toast.LENGTH_SHORT).show();
                    } else {
                        db.addUser(username.getText().toString().trim(), password.getText().toString().trim(), name.getText().toString().trim(), phoneNumber.getText().toString().trim());
                        Toast.makeText(SignUpActivity.this, "Sign Up Successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
                    }
                }
            }
        });

        signinLink = findViewById(R.id.cancelButton);
        signinLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, SignInActivity.class));
            }
        });
    }
}
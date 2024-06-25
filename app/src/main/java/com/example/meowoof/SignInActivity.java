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

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button signinButton = findViewById(R.id.signin_button);
        EditText username = findViewById(R.id.username_input);
        EditText password = findViewById(R.id.password_input);
        SessionManager sessionManager = new SessionManager(SignInActivity.this);

        if(sessionManager.getSignIn()){
            startActivity(new Intent(SignInActivity.this, MainMenuActivity.class));
            finish();
        }

        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(SignInActivity.this);
                boolean isSignedIn = db.checkUser(username.getText().toString().trim(), password.getText().toString().trim());

                if(isSignedIn){
                    sessionManager.setUsername(username.getText().toString().trim());
                    sessionManager.setSignIn(true);

                    Toast.makeText(SignInActivity.this,"Sign In Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignInActivity.this, MainMenuActivity.class));
                } else {
                    Toast.makeText(SignInActivity.this,"Invalid Crendentials!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView signupLink = (TextView)findViewById(R.id.cancelButton);
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
            }
        });
    }
}
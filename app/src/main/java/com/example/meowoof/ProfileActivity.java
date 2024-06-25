package com.example.meowoof;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    private Button signoutButton, contactButton;
    private TextView name, username, phoneNumber, editProfileButton;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        signoutButton = findViewById(R.id.signout_button);
        contactButton = findViewById(R.id.contactButton);
        editProfileButton = findViewById(R.id.editProfileButton);
        SessionManager sessionManager = new SessionManager(this);
        db = new DatabaseHelper(this);
        setData();

        if(!sessionManager.getSignIn()){
            startActivity(new Intent(ProfileActivity.this, SignInActivity.class));
            finish();
        }

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                i.putExtra("name", name.getText().toString());
                i.putExtra("username", username.getText().toString());
                i.putExtra("phoneNumber", phoneNumber.getText().toString());
                startActivity(i);
            }
        });

        contactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "01121736504"));
                startActivity(intent);
            }
        });

        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setUsername("");
                sessionManager.setSignIn(false);
                startActivity(new Intent(ProfileActivity.this, SignInActivity.class));
            }
        });

        ImageView back  = findViewById(R.id.imageView);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, MainMenuActivity.class));
            }
        });
    }

    private void setData() {
        SessionManager sessionManager = new SessionManager(this);
        String user = sessionManager.getUsername();
        Cursor data = db.getUserByUsername(user);

        if(data.getCount() == 0){
            Toast.makeText(this,"User doesn't exist", Toast.LENGTH_LONG).show();
        }else{
            name = findViewById(R.id.name);
            username = findViewById(R.id.username);
            phoneNumber = findViewById(R.id.phoneNumber);

            while (data.moveToNext()){
                username.setText(data.getString(0));
                name.setText(data.getString(2));
                phoneNumber.setText(data.getString(3));
            }
        }
    }
}
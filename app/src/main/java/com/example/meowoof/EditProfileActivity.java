package com.example.meowoof;

import android.content.Intent;
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

public class EditProfileActivity extends AppCompatActivity {
    private EditText name, phoneNumber;
    private TextView cancelButton;
    private Button editButton;
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.name_input);
        phoneNumber = findViewById(R.id.phone_input);
        editButton = findViewById(R.id.edit_button);
        cancelButton = findViewById(R.id.cancelButton);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            name.setText(extras.getString("name"), TextView.BufferType.EDITABLE);
            phoneNumber.setText(extras.getString("phoneNumber"), TextView.BufferType.EDITABLE);
        }

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DatabaseHelper(EditProfileActivity.this);
                SessionManager sessionManager = new SessionManager(EditProfileActivity.this);

                String username = sessionManager.getUsername();

                db.updateUser(username, name.getText().toString().trim(), phoneNumber.getText().toString().trim());

                Toast.makeText(EditProfileActivity.this, "Edit Profile Successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
            }
        });

        ImageView back  = findViewById(R.id.imageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
            }
        });
    }
}
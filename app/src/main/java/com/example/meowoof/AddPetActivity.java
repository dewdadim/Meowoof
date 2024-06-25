package com.example.meowoof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddPetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_pet);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText name = findViewById(R.id.name_input);
        RadioGroup genderGroup = findViewById(R.id.genderGroup);
        RadioGroup categoryGroup = findViewById(R.id.categoryGroup);
        Button addPetButton = findViewById(R.id.addPet_button);
        SessionManager sessionManager = new SessionManager(AddPetActivity.this);

        String owner = sessionManager.getUsername();

        if(!sessionManager.getSignIn()){
            startActivity(new Intent(AddPetActivity.this, SignInActivity.class));
            finish();
        }

        addPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(AddPetActivity.this);
                int genderId = genderGroup.getCheckedRadioButtonId();

                int categoryId = categoryGroup.getCheckedRadioButtonId();

                if(name.getText().toString().isEmpty() || categoryId == -1 || genderId == -1){

                    Toast.makeText(AddPetActivity.this,"All fields need to be filled in!", Toast.LENGTH_SHORT).show();
                } else {
                    RadioButton selectedGender = findViewById(genderId);
                    RadioButton selectedCategory = findViewById(categoryId);
                    db.addPet(name.getText().toString().trim(), selectedCategory.getText().toString().trim(), selectedGender.getText().toString().trim(), owner);
                    Toast.makeText(AddPetActivity.this, "Add Pet Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddPetActivity.this, MyPetsActivity.class));
                }

            }
        });

        ImageView back  = findViewById(R.id.imageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddPetActivity.this, MyPetsActivity.class));
            }
        });

    }
}
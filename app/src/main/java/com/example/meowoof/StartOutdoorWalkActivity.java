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

public class StartOutdoorWalkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_outdoor_walk);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText petName = findViewById(R.id.txtPetName);
        Button startButton = findViewById(R.id.button);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper db = new DatabaseHelper(StartOutdoorWalkActivity.this);
                if(db.checkPet(petName.getText().toString().trim())){
                    Intent i = new Intent(StartOutdoorWalkActivity.this, OutdoorWalkActivity.class);
                    i.putExtra("petName", petName.getText().toString().trim());
                    startActivity(i);
                }else{
                    Toast.makeText(StartOutdoorWalkActivity.this, petName.getText().toString() + " doesn't exist!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        ImageView back  = findViewById(R.id.imageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartOutdoorWalkActivity.this, MainMenuActivity.class));
            }
        });

        TextView walkHistoryButton = findViewById(R.id.walkHistoryButton);
        walkHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartOutdoorWalkActivity.this, WalkHistoryActivity.class));
            }
        });
    }
}
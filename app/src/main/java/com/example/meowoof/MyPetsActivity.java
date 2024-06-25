package com.example.meowoof;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPetsActivity extends AppCompatActivity {

    private ArrayList<Pet> petArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_pets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView petsRecyclerView = findViewById(R.id.petsRecyclerView);
        petArrayList = new ArrayList<>();
        PetAdapter adapter = new PetAdapter(this, petArrayList);
        petsRecyclerView.setAdapter(adapter);
        petsRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        createListData();

        petsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageButton addButton = (ImageButton)findViewById(R.id.imageButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyPetsActivity.this, AddPetActivity.class));
            }
        });

        ImageView back  = findViewById(R.id.imageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyPetsActivity.this, MainMenuActivity.class));
            }
        });


    }

    private void createListData() {
        DatabaseHelper db = new DatabaseHelper(this);
        SessionManager sessionManager = new SessionManager(this);
        String owner = sessionManager.getUsername();
        Cursor data = db.getAllPetsByOwner(owner);

        if(data.getCount() == 0){
            Toast.makeText(MyPetsActivity.this,"No Pets in the List!", Toast.LENGTH_LONG).show();
        }else{
            while (data.moveToNext()){
                petArrayList.add(new Pet(data.getInt(0), data.getString(1), data.getString(3),  data.getString(2)));
            }
        }
    }

    public void refreshActivtiy(){
        recreate();
    }

}
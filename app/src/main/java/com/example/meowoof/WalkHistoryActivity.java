package com.example.meowoof;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class WalkHistoryActivity extends AppCompatActivity {
    private ArrayList<WalkHistory> walkHistories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_walk_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView walksRecyclerView = findViewById(R.id.walksRecyclerView);
        walkHistories = new ArrayList<>();
        WalkHistoryAdapter adapter = new WalkHistoryAdapter(this, walkHistories);
        walksRecyclerView.setAdapter(adapter);
        walksRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        createListData();

        walksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageView back  = findViewById(R.id.imageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WalkHistoryActivity.this, MainMenuActivity.class));
            }
        });
    }

    private void createListData() {
        DatabaseHelper db = new DatabaseHelper(this);
        SessionManager sessionManager = new SessionManager(this);
        String owner = sessionManager.getUsername();
        Cursor data = db.getAllWalkHistoryByOwner(owner);

        if(data.getCount() == 0){
            Toast.makeText(WalkHistoryActivity.this,"No Walk History in the List!", Toast.LENGTH_LONG).show();
        }else{
            Date date = null;
            String dateString;

            while (data.moveToNext()){
                dateString = data.getString(4);
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                try {
                    date = format.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                walkHistories.add(new WalkHistory(data.getInt(0), data.getString(1), data.getString(2), data.getInt(3), date));
            }
        }
    }
}
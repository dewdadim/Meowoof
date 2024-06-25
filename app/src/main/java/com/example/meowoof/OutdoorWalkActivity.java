package com.example.meowoof;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OutdoorWalkActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private TextView txtSteps, petName;
    private  Button button;
    private int totalSteps = 0;
    private int previewsTotalSteps = 0;
    private boolean isStepSensorAvailable;
    DatabaseHelper db;
    private SessionManager sessionManager;
    private String owner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_outdoor_walk);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        petName = findViewById(R.id.txtPetName);
        txtSteps = findViewById(R.id.txtSteps);
        button = findViewById(R.id.button);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null){
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isStepSensorAvailable = true;
        }else{
            txtSteps.setText("Not Available");
            isStepSensorAvailable = false;
        }

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            petName.setText("Walking with " + extras.getString("petName"), TextView.BufferType.EDITABLE);
        }

        loadData();
        stopWalks();


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        txtSteps = findViewById(R.id.txtSteps);
        if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            totalSteps = (int) event.values[0];
            int currentSteps = totalSteps - previewsTotalSteps;
            txtSteps.setText(String.valueOf(currentSteps));
        }
    }

    private void stopWalks(){
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OutdoorWalkActivity.this, "Hold the Button to stop", Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                petName = findViewById(R.id.txtPetName);
                txtSteps = findViewById(R.id.txtSteps);
                sessionManager = new SessionManager(OutdoorWalkActivity.this);
                owner = sessionManager.getUsername();
                previewsTotalSteps = totalSteps;
                Bundle extras = getIntent().getExtras();

                assert extras != null;
                if(isStepSensorAvailable){
                    saveWalk(extras.getString("petName"), owner, Integer.parseInt(txtSteps.getText().toString()));
                }else{
                    saveWalk(extras.getString("petName"), owner, 0);
                }
                txtSteps.setText("0");
                saveData();

                startActivity(new Intent(OutdoorWalkActivity.this, StartOutdoorWalkActivity.class));
                finish();

                return true;
            }
        });
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("key1", previewsTotalSteps);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE);
        previewsTotalSteps = (int) sharedPreferences.getInt("key1", 0);
    }

    private void saveWalk(String pet, String owner, int stepCount){
        db = new DatabaseHelper(OutdoorWalkActivity.this);

        Cursor data = db.getPetByName(pet);
        int petId = -1;
        db.addWalkHistory(pet, owner, stepCount);


//        if(data.getCount() == 0){
//            Toast.makeText(this,"Pet doesn't exist", Toast.LENGTH_LONG).show();
//        }else{
//            while (data.moveToNext()){
//                petId = data.getInt(0);
//            }
//
//            db.addWalkHistory(pet, owner, stepCount);
//        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isStepSensorAvailable){
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }else{
            Toast.makeText(this, "This device has no sensor", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
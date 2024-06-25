package com.example.meowoof;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PetsCareActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor tempSensor, humiditySensor;
    private TextView txtTemp, txtHumidity;
    private boolean isTemperatureSensorAvailable, isHumiditySensorAvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pets_care);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        txtTemp = findViewById(R.id.txtTemp);
        txtHumidity = findViewById(R.id.txtHumidity);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemperatureSensorAvailable = true;
        }else{
            txtTemp.setText("Not Available");
            isTemperatureSensorAvailable = false;
        }

        if(sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null){
            humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            isHumiditySensorAvailable = true;
        }else{
            txtHumidity.setText("Not Available");
            isHumiditySensorAvailable = false;
        }

        Button outdoorWalkButton = findViewById(R.id.button);

        outdoorWalkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetsCareActivity.this, StartOutdoorWalkActivity.class));
            }
        });

        ImageView back  = findViewById(R.id.imageView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PetsCareActivity.this, MainMenuActivity.class));
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            txtTemp.setText(event.values[0] + " °C");
        }
        if (event.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
            txtHumidity.setText(event.values[0] + " %");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isTemperatureSensorAvailable){
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if(isHumiditySensorAvailable){
            sensorManager.registerListener(this, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isTemperatureSensorAvailable){
            sensorManager.unregisterListener(this);
        }

        if(isHumiditySensorAvailable){
            sensorManager.unregisterListener(this);
        }
    }
}
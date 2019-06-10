package com.example.hw3_kula;

import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    private ArrayList<String> answersList;
    private double sensorX, X;
    private boolean shaking;
    ImageView image;
    TextView answer;
    AnimationDrawable animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        image = findViewById(R.id.ball);
        image.setBackgroundResource(R.drawable.ball_front);

        createAnswers();
        shaking=false;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        sensorX = sensorEvent.values[0];
        X += sensorX;

        image = findViewById(R.id.ball);

        if (Math.abs(sensorX)>1.5)
        {
            image.setBackgroundResource(R.drawable.animacja);
            animation = (AnimationDrawable) image.getBackground();
            animation.start();

            answer = findViewById(R.id.answer);
            answer.setText("");

            shaking = true;
        }
        else if(shaking)
        {
            shaking = false;
            generateAnswer(X);
            X = 0.0;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //not in use
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (sensor != null)
        {
            sensorManager.registerListener(this,sensor,100000);
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (sensor!=null)
        {
            sensorManager.unregisterListener(this);
        }
    }

    private void createAnswers() {
        answersList = new ArrayList<>();
        //affirmative
        answersList.add("It is certain.");
        answersList.add("It is decidedly so.");
        answersList.add("Without a doubt.");
        answersList.add("Yes - definitely.");
        answersList.add("You may rely on it.");
        answersList.add("As I see it, yes.");
        answersList.add("Most likely.");
        answersList.add("Outlook good.");
        answersList.add("Yes.");
        answersList.add("Signs point to yes.");
        //non-committal
        answersList.add("Reply hazy, try again.");
        answersList.add("Ask again later.");
        answersList.add("Better not tell you now.");
        answersList.add("Cannot predict now.");
        answersList.add("Concentrate and ask again.");
        //negative
        answersList.add("Don't count on it.");
        answersList.add("My reply is no.");
        answersList.add("My sources say no.");
        answersList.add("Outlook not so good.");
        answersList.add("Very doubtful.");
    }


    private void generateAnswer(double x)
    {
        image = findViewById(R.id.ball);
        animation.stop();
        image.setBackgroundResource(R.drawable.ball_empty);
        answer = findViewById(R.id.answer);
        int answerNumber = ((int) Math.abs(Math.round(x*100)))%20;
        answer.setText(answersList.get(answerNumber));
    }

}

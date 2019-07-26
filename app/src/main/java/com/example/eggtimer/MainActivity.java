package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar timerSeekBar;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    Button controllerButton;

    public void resetTimer(){

        timerTextView.setText("0 : 30");
        timerSeekBar.setEnabled(true);
        timerSeekBar.setProgress(30);
        countDownTimer.cancel();
        counterIsActive = false;
        controllerButton.setText("Go!");

    }

    public void updateTimer(int i){
        int minutes = i / 60;
        int seconds = i - minutes * 60;
        String secondsLeft = Integer.toString(seconds);

        if(seconds <= 9){
            secondsLeft = "0" + secondsLeft;

            if(minutes == 0 && seconds == 0) {
                timerTextView.setText("0 : 30");
            }
        }
        timerTextView.setText(minutes + " : " + secondsLeft);
    }

    public void controllerButton(View view){

        if(!counterIsActive){

            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton = findViewById(R.id.controllerButton);
            controllerButton.setText("Stop!");

            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 +100, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) l / 1000);
                }

                @Override
                public void onFinish() {

                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();

                }
            }.start();
        }
        else {

            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerSeekBar.setProgress(30);
        timerSeekBar.setMax(600);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}

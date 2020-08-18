package com.example.appeggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timeChanger;
    TextView textView;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;
    Button controlButtonText;

    public void resetTime(){
        textView.setText("00 : 30");
        timeChanger.setProgress(30);
        counterIsActive = false;
        timeChanger.setEnabled(true);
        controlButtonText.setText("Go!");
        countDownTimer.cancel();
    }

    public void updateTime(int i){
        int minutes = i/60;
        int seconds = i - minutes * 60;
        String minuteString = Integer.toString(minutes);
        String secondString = Integer.toString(seconds);

        if(minutes <= 9){
            minuteString = "0" + minuteString;
        }

        if (seconds <= 9){
            secondString = "0" + secondString;
        }
        textView.setText(minuteString + " " + ":" + " " + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeChanger = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.textView);
        controlButtonText = (Button) findViewById(R.id.button);

        timeChanger.setMax(600);
        timeChanger.setProgress(30);
        timeChanger.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTime(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void startTime(View view) {
        if(counterIsActive == false) {
            counterIsActive = true;
            timeChanger.setEnabled(false);
            controlButtonText.setText("Stop");

            countDownTimer = new CountDownTimer(timeChanger.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    updateTime((int) l / 1000);
                }

                @Override
                public void onFinish() {
                    resetTime();
                    MediaPlayer myPlayer = MediaPlayer.create(getApplicationContext(), R.raw.air_horn);
                    myPlayer.start();
                }
            }.start();
        }else {
            resetTime();
        }
    }
}
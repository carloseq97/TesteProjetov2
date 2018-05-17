package com.example.joovitormatos.testeprojeto1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = MediaPlayer.create(MainActivity.this, R.raw.show);

        new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished)
            {
                    player.release();
                    player = MediaPlayer.create(MainActivity.this, R.raw.show);
                    player.start();
            }
            public void onFinish()
            {
                player.stop();
                Intent tela = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(tela);
            }
        }.start();
    }
}

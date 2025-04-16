package com.zain.myapp1;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Media_page extends AppCompatActivity {
    MediaPlayer media;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_media_page);
        Button playBtn = findViewById(R.id.playButton);
        Button pauseBtn = findViewById(R.id.pauseButton);
        Button stopBtn = findViewById(R.id.stopButton);
        media = MediaPlayer.create(this, R.raw.music1);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                media.start();
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (media.isPlaying()) {
                    media.pause();
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (media != null) {
                    media.stop();
                    media = MediaPlayer.create(Media_page.this, R.raw.music1);
                }}
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    protected void onDestroy() {
        if (media != null) {
            media.release();
            media = null;
        }
        super.onDestroy();
    }
}
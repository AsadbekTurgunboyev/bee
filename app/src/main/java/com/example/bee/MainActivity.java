package com.example.bee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bee.preferenData.PreferenceData;

public class MainActivity extends AppCompatActivity {
    Animation logoanim,nametext;
    ImageView imageView;
    TextView textView;
    PreferenceData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new PreferenceData(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(data.getData("key").isEmpty()){

                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(MainActivity.this,HomeA.class));
                    finish();
                }
            }
        },4000);

        setContentView(R.layout.activity_main);
        initViews();
        logoanim = AnimationUtils.loadAnimation(this,R.anim.logoanimayshn);
        nametext = AnimationUtils.loadAnimation(this,R.anim.textenimayshn);
        imageView.setAnimation(logoanim);
        textView.setAnimation(nametext);

    }

    private void initViews() {
        imageView = findViewById(R.id.log);
        textView = findViewById(R.id.name);

    }
}
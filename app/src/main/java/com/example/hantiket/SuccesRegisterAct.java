package com.example.hantiket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccesRegisterAct extends AppCompatActivity {

    Button btn_explore;
    Animation app_splash, btt, ttb;
    ImageView icon_success;
    TextView app_title, app_subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_succes_register);

        icon_success = findViewById(R.id.icon_success);
        app_title = findViewById(R.id.app_title);
        app_subtitle = findViewById(R.id.app_subtitle);
        btn_explore = findViewById(R.id.btn_explore);

        // load animation
        app_splash = AnimationUtils.loadAnimation(this,R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);
        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb);

        //run animation
        btn_explore.startAnimation(btt);
        icon_success.startAnimation(app_splash);
        app_title.startAnimation(ttb);
        app_subtitle.startAnimation(ttb);


        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(SuccesRegisterAct.this,HomeAct.class);
                startActivity(gotohome);
            }
        });
    }
}

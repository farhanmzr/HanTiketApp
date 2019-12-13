package com.example.hantiket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class GetStartedAct extends AppCompatActivity {
    Button btn_sign_in, btn_sign_up;
    Animation ttb, btt; //ttb top to bottom
    TextView intro_app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        //load animation
        ttb = AnimationUtils.loadAnimation(this,R.anim.ttb);
        btt = AnimationUtils.loadAnimation(this,R.anim.btt);

        intro_app = findViewById(R.id.intro_app);
        btn_sign_in = findViewById(R.id.btn_sign_in);
        btn_sign_up = findViewById(R.id.btn_sign_up);

        //run animation
        intro_app.startAnimation(ttb);
        btn_sign_in.startAnimation(btt);
        btn_sign_up.startAnimation(btt);

        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosign = new Intent(GetStartedAct.this,SignInAct.class);
                startActivity(gotosign);
            }
        });
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoregisterone = new Intent(GetStartedAct.this,RegisterOneAct.class);
                startActivity(gotoregisterone);
            }
        });
    }
}

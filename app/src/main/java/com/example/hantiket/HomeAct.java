package com.example.hantiket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class
HomeAct extends AppCompatActivity {

    LinearLayout btn_ticket_london,
            btn_ticket_maldives, btn_ticket_borobudur,
            btn_ticket_eiffel, btn_ticket_japan,
            btn_ticket_newyork;

    View btn_to_profile;
    ImageView photo_home_user;
    TextView user_balance, nama_lengkap, bio;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        btn_ticket_london = findViewById(R.id.btn_ticket_london);
        btn_to_profile = findViewById(R.id.btn_to_profile);

        btn_ticket_maldives = findViewById(R.id.btn_ticket_maldives);
        btn_ticket_borobudur = findViewById(R.id.btn_ticket_borobudur);
        btn_ticket_eiffel = findViewById(R.id.btn_ticket_eiffel);
        btn_ticket_japan = findViewById(R.id.btn_ticket_japan);
        btn_ticket_newyork = findViewById(R.id.btn_ticket_newyork);

        photo_home_user = findViewById(R.id.photo_home_user);
        user_balance = findViewById(R.id.user_balance);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);

        reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                user_balance.setText("US$ " + dataSnapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeAct.this)
                        .load(dataSnapshot.child("url_photo_profile")
                                .getValue().toString()).centerCrop().fit()
                        .into(photo_home_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(HomeAct.this,MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });


        btn_ticket_london.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this,TicketDetailAct.class);
                // meletakan data kepada intent
                gotopisaticket.putExtra("jenis_tiket", "London");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_maldives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this,TicketDetailAct.class);
                gotopisaticket.putExtra("jenis_tiket", "Maldives");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_borobudur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this,TicketDetailAct.class);
                gotopisaticket.putExtra("jenis_tiket", "Borobudur");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_eiffel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this,TicketDetailAct.class);
                gotopisaticket.putExtra("jenis_tiket", "Eiffel");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_japan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this,TicketDetailAct.class);
                gotopisaticket.putExtra("jenis_tiket", "Japan");
                startActivity(gotopisaticket);
            }
        });

        btn_ticket_newyork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this,TicketDetailAct.class);
                gotopisaticket.putExtra("jenis_tiket", "New York");
                startActivity(gotopisaticket);
            }
        });

    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}


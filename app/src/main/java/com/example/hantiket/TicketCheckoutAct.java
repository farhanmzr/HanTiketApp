package com.example.hantiket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class TicketCheckoutAct extends AppCompatActivity {

    Button btn_buy_ticket, btn_minus, btn_plus;
    LinearLayout btn_back;
    TextView textjumlahtiket, texttotalharga, textmybalance, nama_wisata, lokasi, ketentuan;
    Integer valueJumlahTiket = 1;
    Integer mybalance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargatiket = 0;
    ImageView notice_uang;
    Integer sisa_balance = 0;

    DatabaseReference reference, reference2, reference3, reference4;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String date_wisata ="";
    String time_wisata ="";

    // generate nomor integer secara random, karena ingin membuat transaksi secara unik
    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_check_out);
        getUsernameLocal();

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        btn_minus = findViewById(R.id.btn_minus);
        btn_plus = findViewById(R.id.btn_plus);
        btn_back = findViewById(R.id.btn_back);
        textjumlahtiket = findViewById(R.id.textjumlahtiket);
        textmybalance = findViewById(R.id.textmybalance);
        texttotalharga = findViewById(R.id.texttotalharga);
        notice_uang = findViewById(R.id.notice_uang);

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        texttotalharga = findViewById(R.id.texttotalharga);
        textmybalance = findViewById(R.id.textmybalance);

        // setting value baru untuk komponen
        textjumlahtiket.setText(valueJumlahTiket.toString());


        // secara default, hide btn_minus
        btn_minus.animate().alpha(0).setDuration(300).start();
        btn_minus.setEnabled(false);
        notice_uang.setVisibility(View.GONE);

        // mengambil data user dari firebase
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mybalance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textmybalance.setText("US$ " + mybalance+"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // mengambil data dari firebase bedasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // menimpa data yang ada dengan data yang baru
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());

                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();

                valuehargatiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());

                valuetotalharga = valuehargatiket * valueJumlahTiket;
                texttotalharga.setText("US$ " + valuetotalharga+"");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahTiket+=1;
                textjumlahtiket.setText(valueJumlahTiket.toString());
                if (valueJumlahTiket > 1) {
                    btn_minus.animate().alpha(1).setDuration(300).start();
                    btn_minus.setEnabled(true);
                }
                valuetotalharga = valuehargatiket * valueJumlahTiket;
                texttotalharga.setText("US$ " + valuetotalharga+"");
                if (valuetotalharga > mybalance) {
                    btn_buy_ticket.animate().translationY(250)
                            .alpha(0).setDuration(350).start();
                    btn_buy_ticket.setEnabled(false);
                    textmybalance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);
                }
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueJumlahTiket-=1;
                textjumlahtiket.setText(valueJumlahTiket.toString());
                if (valueJumlahTiket < 2) {
                    btn_minus.animate().alpha(0).setDuration(300).start();
                    btn_minus.setEnabled(false);
                }
                valuetotalharga = valuehargatiket * valueJumlahTiket;
                texttotalharga.setText("US$ " + valuetotalharga+"");
                if (valuetotalharga < mybalance) {
                    btn_buy_ticket.animate().translationY(0)
                            .alpha(1).setDuration(350).start();
                    btn_buy_ticket.setEnabled(true);
                    textmybalance.setTextColor(Color.parseColor("#203DD1"));
                    notice_uang.setVisibility(View.GONE);
                }
            }
        });




        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // menyimpan data user kepada firebase dan membuat tabel baru "MyTickets"
                reference3 = FirebaseDatabase.getInstance()
                        .getReference().child("MyTickets")
                        .child(username_key_new).child(nama_wisata.getText().toString() + nomor_transaksi);

                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString() + nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(valueJumlahTiket.toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);


                        Intent gotosuccessticket = new
                                Intent(TicketCheckoutAct.this,SuccesBuyTicketAct.class);
                        startActivity(gotosuccessticket);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                // update data balance kepada users (yg saat ini login)
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = mybalance - valuetotalharga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoback = new Intent(TicketCheckoutAct.this,TicketDetailAct.class);
                startActivity(gotoback);
            }
        });

    }
    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}

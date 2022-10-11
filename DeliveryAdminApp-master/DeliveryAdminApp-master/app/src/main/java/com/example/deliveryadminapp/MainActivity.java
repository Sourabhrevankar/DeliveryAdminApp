package com.example.deliveryadminapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;

    Button recylerviewbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recylerviewbtn = findViewById(R.id.recyclerviewbtn);
        recylerviewbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                Intent i;
                if(mFirebaseUser!=null){
                    i = new Intent(MainActivity.this, userlist.class);
                }
                else{
                    i = new Intent(MainActivity.this, Login.class);

                }
                startActivity(i);
                finish();


            }
        });
    }

}
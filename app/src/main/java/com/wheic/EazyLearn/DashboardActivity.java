package com.wheic.EazyLearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    String alphabetslearned,rhymeswatched,storieswatched,highscore,numgames,writinglearned,wordspronounced;
    TextView dashalphabet,dashrhymes,dashstories,dashhighscore,dashnumgames,dashwrite,dashpronounce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setTitle("Your Activity");
        dashalphabet=findViewById(R.id.dashalphabet);
        dashwrite=findViewById(R.id.dashwrite);
        dashrhymes=findViewById(R.id.dashrhymes);
        dashstories=findViewById(R.id.dashstories);
        dashhighscore=findViewById(R.id.dashhighscore);
        dashnumgames=findViewById(R.id.dashgames);
        dashpronounce=findViewById(R.id.dashpronounce);
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                alphabetslearned= snapshot.child("AlphabetsLearned").getValue().toString();
                dashalphabet.setText(alphabetslearned);
                writinglearned= snapshot.child("WritingWatched").getValue().toString();
                dashwrite.setText(writinglearned);
                rhymeswatched= snapshot.child("RhymesWatched").getValue().toString();
                dashrhymes.setText(rhymeswatched);
                storieswatched= snapshot.child("StoriesWatched").getValue().toString();
                dashstories.setText(storieswatched);
                numgames= snapshot.child("GamesPlayed").getValue().toString();
                dashnumgames.setText(numgames);
                highscore= snapshot.child("Highscore").getValue().toString();
                dashhighscore.setText(highscore);
                wordspronounced= snapshot.child("PronounceHighscore").getValue().toString();
                dashpronounce.setText(wordspronounced);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}
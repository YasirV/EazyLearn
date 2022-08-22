package com.wheic.EazyLearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    StringBuilder DictionaryDB = new StringBuilder();
    String DictionaryData ="";
    ArrayList<String> prevwords=new ArrayList<String>();
    int chances =3;
    int tot_points = 0;
    int positive_points = 5;
    int negative_points = -1;
    int Highscore;
    int gamesPlayed;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Game");
        chances =3;
        tot_points = 0;
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gamesPlayed = Integer.parseInt(snapshot.child("GamesPlayed").getValue().toString());
                Highscore = Integer.parseInt(snapshot.child("Highscore").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        positive_points = 5;
        negative_points = -1;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        TextView lblResult = (TextView) findViewById(R.id.lblResult);
        try {
            InputStream is = getApplicationContext().getResources().openRawResource(R.raw.words);
            byte[] contents = new byte[is.available()];
            is.read(contents);
            is.close();
            DictionaryData =new String(contents,"UTF-8");
            // lblResult.setText(DictionaryData);
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void createWord(View view){
        Button button = (Button) view;
        TextView lblWord = (TextView) findViewById(R.id.lblWord);
        lblWord.setText(lblWord.getText().toString()+button.getText().toString());
    }

    public void clearWord(View view) {
        TextView lblWord = (TextView) findViewById(R.id.lblWord);
        TextView lblResult = (TextView) findViewById(R.id.lblResult);
        lblWord.setText("");
        lblResult.setText("");
    }
    boolean isPresent(String query, String s) {
        String [] deli = s.split("[.\\s,?;]+");

        for(int i=0;i<deli.length;i++)
            if(query.equals(deli[i]))
                return true;

        return false;
    }
    public void checkWord(View view) {
        TextView lblWord = (TextView) findViewById(R.id.lblWord);
        TextView lblResult = (TextView) findViewById(R.id.lblResult);
        TextView lblPoints = (TextView) findViewById(R.id.lblPoints);
        String word=lblWord.getText().toString().toLowerCase();
        if(!prevwords.contains(word)) {
            prevwords.add(word);
            if (isPresent(lblWord.getText().toString().toLowerCase(), DictionaryData.toLowerCase())) {
                tot_points += positive_points;
                if (chances == 0) {
                    lblResult.setText("Game Over!!!");
                    lblResult.setTextColor(Color.RED);
                } else{
                    lblResult.setText("Success!!!");
                    lblResult.setTextColor(Color.parseColor("#36AE7C"));
                }
            } else {
                tot_points += negative_points;
                lblResult.setText("Wrong Word!!!");
                lblResult.setTextColor(Color.RED);
                chances--;
                if(chances==0){
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("GamesPlayed").setValue(++gamesPlayed);
                    if(tot_points>Highscore){
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Highscore").setValue(tot_points);
                    }
                    builder= new AlertDialog.Builder(GameActivity.this);
                    builder.setMessage("Game Over!\n Your score is:"+tot_points);
                    builder.setCancelable(false);
                    builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(GameActivity.this, GameActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(GameActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                    builder.show();
//                    Toast.makeText(getApplicationContext(),"Game Over. Please Try Again",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(GameActivity.this, GameActivity.class);
//                    startActivity(intent);
                }
            }
            lblPoints.setText("Points : " + tot_points + " | Chances : " + chances);
            lblWord.setText("");
        }
        else{
            lblResult.setText("Word already entered!!");
            lblResult.setTextColor(Color.RED);
        }

    }
}
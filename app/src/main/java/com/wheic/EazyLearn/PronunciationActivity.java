package com.wheic.EazyLearn;

import static androidx.appcompat.app.AlertDialog.*;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

public class PronunciationActivity extends AppCompatActivity {

    ImageButton hearButton, micButton;
    TextView wordTxt, resultTxt,wordCountTxt, pointCountTxt;
    TextToSpeech textToSpeech;
    String randomLine;
    int word =0;
    int points =0;
    int highscore;
    List<String> listLines = new ArrayList<>();
    AlertDialog.Builder builder;

    int randomNumber;
    ActivityResultLauncher<Intent> speechToTextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Learn to Speak");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("pronounce.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                listLines.add(mLine);
            }

            randomNumber = getRandomNo();
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        speechToTextActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        String text = wordTxt.getText().toString();
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            ArrayList<String> resultList = result.getData().getStringArrayListExtra(
                                    RecognizerIntent.EXTRA_RESULTS);
                            text = text.replaceAll("[^a-zA-Z ]", "");
                            String resultVal = Objects.requireNonNull(resultList).get(0);
                            Toast.makeText(PronunciationActivity.this, resultVal, Toast.LENGTH_SHORT).show();
                            resultTxt.setVisibility(View.VISIBLE);
                            if (text.equalsIgnoreCase(resultVal)) {
                                resultTxt.setTextColor(Color.parseColor("#00FF00"));
                                resultTxt.setText("Congratulations \nYour Pronunciation is correct");

                                listLines.remove(text);

                                word++;
                                points = points+5;
                                wordCountTxt.setText(""+word);
                                pointCountTxt.setText(""+points);
                                Log.i("TEST_VALUES", listLines.toString());
                                if (listLines.size() <= 0) {
                                    Log.i("TEST_VALUES", "aegra");
                                    Toast.makeText(PronunciationActivity.this, "You have won the game", Toast.LENGTH_SHORT).show();
//                                    PronunciationActivity.this.finish();
                                    Intent intent = new Intent(PronunciationActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } else {

                                    randomNumber = getRandomNo();

                                    randomLine = listLines.get(randomNumber);


                                    wordTxt.setText(randomLine);
                                }


                            } else {

                                resultTxt.setTextColor(Color.parseColor("#FF0000"));
                                resultTxt.setText("Please try again!");
                                if(points>highscore){
                                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("PronounceHighscore").setValue(points);

                                }

                                builder= new AlertDialog.Builder(PronunciationActivity.this);
                                builder.setMessage("Game Over!\n Your score is:"+points);
                                builder.setCancelable(false);
                                builder.setPositiveButton("Try Again", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(PronunciationActivity.this, PronunciationActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                });
                                builder.setNegativeButton("Exit", new OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(PronunciationActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                });
                                builder.show();


                            }

                        }
                    }
                });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronunciation);

        hearButton = findViewById(R.id.hear_btn);
        micButton = findViewById(R.id.mic_btn);
        wordTxt = findViewById(R.id.text);
        wordCountTxt = findViewById(R.id.wordcount);
        pointCountTxt = findViewById(R.id.pointcount);
        resultTxt = findViewById(R.id.resultTxt);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                highscore = Integer.parseInt(snapshot.child("PronounceHighscore").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

//        while(flag==0) {
        randomLine = listLines.get(randomNumber);
        wordTxt.setText(randomLine);

        hearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.speak(wordTxt.getText(), TextToSpeech.QUEUE_FLUSH, null, null);
            }
        });

        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = wordTxt.getText().toString();

                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say : " + text);

                try {
                    speechToTextActivity.launch(intent);
                } catch (Exception e) {
                    Toast
                            .makeText(PronunciationActivity.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    private int getRandomNo() {
        int r_no = 0;
        if (listLines.size() > 0) {
            Random rand = new Random();
            r_no = rand.nextInt(listLines.size());
        }
        return r_no;
    }

}
//}
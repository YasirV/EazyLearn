package com.wheic.EazyLearn;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CardView alphabetsCard, rhymesCard, storiesCard,writeCard,pronunciationCard,gameCard;
    TextToSpeech t1;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        t1 = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = t1.setLanguage(Locale.UK);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                        Toast.makeText(context, "This language is not supported", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Initialization failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        NavigationView navigationView=findViewById(R.id.navview);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if (id==R.id.nav_dashboard){
                    Intent intent=new Intent(MainActivity.this,DashboardActivity.class);
                    startActivity(intent);
                }
                else if (id==R.id.nav_logout){
                    FirebaseAuth.getInstance().signOut();
                    Intent intent=new Intent(MainActivity.this,login_activity.class);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        alphabetsCard = findViewById(R.id.alphabetsCard);
        rhymesCard = findViewById(R.id.rhymesCard);
        storiesCard =findViewById(R.id.storiesCard);
        writeCard=findViewById(R.id.writeCard);
        pronunciationCard=findViewById(R.id.speakCard);
        gameCard=findViewById(R.id.gamesCard);

        alphabetsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.speak("LEARN ALPHABETS",TextToSpeech.QUEUE_FLUSH,null,null);
                Intent intent=new Intent(MainActivity.this,Alphabets.class);
                startActivity(intent);

            }
        });

        rhymesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.speak("RHYMES",TextToSpeech.QUEUE_FLUSH,null,null);
                Intent intent=new Intent(MainActivity.this,VideoActivity.class);
                intent.putExtra("mode","Rhymes");
                startActivity(intent);
            }
        });

        storiesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.speak("STORIES",TextToSpeech.QUEUE_FLUSH,null,null);
                Intent intent=new Intent(MainActivity.this,VideoActivity.class);
                intent.putExtra("mode","Stories");
                startActivity(intent);
            }
        });

        writeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.speak("LEARN TO WRITE",TextToSpeech.QUEUE_FLUSH,null,null);
                Intent intent=new Intent(MainActivity.this,VideoActivity.class);
                intent.putExtra("mode","Writing");
                startActivity(intent);
            }
        });

        pronunciationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.speak("LEARN TO SPEAK",TextToSpeech.QUEUE_FLUSH,null,null);
                Intent intent=new Intent(MainActivity.this,PronunciationActivity.class);
                startActivity(intent);
            }
        });

        gameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.speak("GAME",TextToSpeech.QUEUE_FLUSH,null,null);
                Intent intent=new Intent(MainActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });
    }




    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    }


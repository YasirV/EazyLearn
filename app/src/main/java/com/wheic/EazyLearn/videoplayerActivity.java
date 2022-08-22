package com.wheic.EazyLearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class videoplayerActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String uri1, vname, mode, TAG = "######";
    int rhymesWatched;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoplayer);
        getSupportActionBar().hide();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rhymesWatched = Integer.parseInt(snapshot.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(mode+"Watched").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        VideoView videoView = (VideoView) findViewById(R.id.video1);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        uri1 = getIntent().getStringExtra("url");
        vname = getIntent().getStringExtra("vname");
        mode = getIntent().getStringExtra("mode");


        Uri uri = Uri.parse(uri1);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
//                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(mode).child(vname).child("isWatched").setValue("True");
//                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("RhymesWatched").setValue(rhymesWatched);
//                Toast.makeText(videoplayerActivity.this, "rhymes++++"+rhymesWatched, Toast.LENGTH_SHORT).show();

                String uid = FirebaseAuth.getInstance().getUid();

                databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DataSnapshot snap = snapshot.child(mode).child(vname);

//                        boolean iswatched = Boolean.parseBoolean(snapshot.child(mode).child(vname).child("isWatched").getValue().toString());
                        if (!snap.exists()) {
                            int rhymeswatched = Integer.parseInt(snapshot.child(mode+"Watched").getValue().toString());
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(mode).child(vname).child("isWatched").setValue("True");
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(mode+"Watched").setValue(++rhymeswatched);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }

}
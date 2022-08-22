package com.wheic.EazyLearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {

    RecyclerView videoRecycler;
    ArrayList<VideoModel> arrayList;

    FirebaseDatabase database;
    DatabaseReference reference;
    String mode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        database = FirebaseDatabase.getInstance();
        mode = getIntent().getStringExtra("mode");


        setTitle(mode);

        reference = database.getReference(mode);

        arrayList = new ArrayList<>();

        videoRecycler = findViewById(R.id.videorecycler);
        videoRecycler.setHasFixedSize(true);
        videoRecycler.setItemAnimator(new DefaultItemAnimator());
        videoRecycler.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(videoRecycler.getContext(),LinearLayoutManager.VERTICAL);
        videoRecycler.addItemDecoration(dividerItemDecoration);

        populateDataset();

    }

    private void populateDataset() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot snap :  snapshot.getChildren()) {
                        String v_name = snap.getKey();
                        String v_url = snap.child("url").getValue().toString();
                        arrayList.add(new VideoModel(v_name,v_url));
                    }

                    VideoAdapter adapter = new VideoAdapter(VideoActivity.this,arrayList,mode);
                    videoRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
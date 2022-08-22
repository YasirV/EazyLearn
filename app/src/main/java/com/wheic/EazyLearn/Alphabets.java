package com.wheic.EazyLearn;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Alphabets extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<AlphabetModel> alphabetList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabets);
        setTitle("Alphabets");

        recyclerView=findViewById(R.id.alphabetRecycler);


        alphabetList = new ArrayList<>();
        alphabetList.add(new AlphabetModel("A",R.drawable.lettera,R.raw.apple1));
        alphabetList.add(new AlphabetModel("B",R.drawable.letterb,R.raw.bike));
        alphabetList.add(new AlphabetModel("C",R.drawable.letterc,R.raw.car1));
        alphabetList.add(new AlphabetModel("D",R.drawable.letterd,R.raw.dog));
        alphabetList.add(new AlphabetModel("E",R.drawable.lettere,R.raw.elephant));
        alphabetList.add(new AlphabetModel("F",R.drawable.letterf,R.raw.fan));
        alphabetList.add(new AlphabetModel("G",R.drawable.letterg,R.raw.guitar));
        alphabetList.add(new AlphabetModel("H",R.drawable.letterh,R.raw.helicopter));
        alphabetList.add(new AlphabetModel("I",R.drawable.letteri,R.raw.icecream));
        alphabetList.add(new AlphabetModel("J",R.drawable.letterj,0));
        alphabetList.add(new AlphabetModel("K",R.drawable.letterk,0));
        alphabetList.add(new AlphabetModel("L",R.drawable.letterl,R.raw.lion));
        alphabetList.add(new AlphabetModel("M",R.drawable.letterm,R.raw.monitor));
        alphabetList.add(new AlphabetModel("N",R.drawable.lettern,0));
        alphabetList.add(new AlphabetModel("O",R.drawable.lettero,0));
        alphabetList.add(new AlphabetModel("P",R.drawable.letterp,0));
        alphabetList.add(new AlphabetModel("Q",R.drawable.letterq,0));
        alphabetList.add(new AlphabetModel("R",R.drawable.letterr,R.raw.robot));
        alphabetList.add(new AlphabetModel("S",R.drawable.letters,R.raw.sofa));
        alphabetList.add(new AlphabetModel("T",R.drawable.lettert,R.raw.table));
        alphabetList.add(new AlphabetModel("U",R.drawable.letteru,0));
        alphabetList.add(new AlphabetModel("V",R.drawable.letterv,R.raw.van));
        alphabetList.add(new AlphabetModel("W",R.drawable.letterw,0));
        alphabetList.add(new AlphabetModel("X",R.drawable.letterx,0));
        alphabetList.add(new AlphabetModel("Y",R.drawable.lettery,0));
        alphabetList.add(new AlphabetModel("Z",R.drawable.letterz,R.raw.zebra));

        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new AlphabetAdapter(alphabetList,this));



    }
}
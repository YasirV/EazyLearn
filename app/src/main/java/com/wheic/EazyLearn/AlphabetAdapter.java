package com.wheic.EazyLearn;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class AlphabetAdapter extends RecyclerView.Adapter<AlphabetAdapter.AlphabetViewHolder> {

    ArrayList<AlphabetModel> alphabetList;
    Context context;
    String WordsToSpeach="", Wordstospeech1;
    TextToSpeech t1;
    AlphabetAdapter(ArrayList<AlphabetModel> alphabetList, Context context) {
        this.alphabetList = alphabetList;
        this.context = context;
    }
    @NonNull
    @Override
    public AlphabetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_alphabet, parent, false);
        AlphabetViewHolder viewHolder = new AlphabetViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlphabetViewHolder holder, int position) {
        AlphabetModel alphabet = alphabetList.get(position);
        holder.imageView.setImageResource(alphabet.getImage());
        t1 = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = t1.setLanguage(Locale.UK);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                        Toast.makeText(context, "This language is not supported", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Initialization failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
                reference.child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            DataSnapshot snapshot1=snapshot.child("Alphabets").child(alphabet.getName());

                            if (!snapshot1.exists()) {
                                Log.i("++++", "workig");
                                int AlphabetsLearned = Integer.parseInt(snapshot.child("AlphabetsLearned").getValue().toString());
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Alphabets").child(alphabet.getName()).child("isLearned").setValue("True");
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("AlphabetsLearned").setValue(++AlphabetsLearned);
                            }
                        }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                switch(alphabet.getName()){
                    case "A":
                        Wordstospeech1="A FOR APPLE";
                        WordsToSpeach = "AN APPLE";
                        break;
                    case "B":
                        Wordstospeech1="B FOR BIKE";
                        WordsToSpeach = "A BIKE";
                        break;
                    case "C":
                        Wordstospeech1="C FOR CAR";
                        WordsToSpeach = "A CAR";
                        break;
                    case "D":
                        Wordstospeech1="D FOR DOG";
                        WordsToSpeach = "A DOG";
                        break;
                    case "E":
                        Wordstospeech1="E FOR ELEPHANT";
                        WordsToSpeach = "AN ELEPHANT";
                        break;
                    case "F":
                        WordsToSpeach = "FISH";
                        break;
                    case "G":
                        Wordstospeech1="G FOR GUITAR";
                        WordsToSpeach = "A GUITAR";
                        break;
                    case "H":
                        Wordstospeech1="H FOR HELICOPTER";
                        WordsToSpeach = "A HELICOPTER";
                        break;
                    case "I":
                        Wordstospeech1="I FOR ICECREAM";
                        WordsToSpeach = "AN ICECREAM";
                        break;
                    case "J":
                        WordsToSpeach = "JEEP";
                        break;
                    case "K":
                        WordsToSpeach = "KANGAROO";
                        break;
                    case "L":
                        Wordstospeech1="L FOR LION";
                        WordsToSpeach = "A LION";
                        break;
                    case "M":
                        WordsToSpeach = "MONKEY";
                        break;
                    case "N":
                        WordsToSpeach = "NAIL POLISH";
                        break;
                    case "O":
                        WordsToSpeach = "ORANGE";
                        break;
                    case "P":
                        WordsToSpeach = "PANDA";
                        break;
                    case "Q":
                        WordsToSpeach = "QUESTION";
                        break;
                    case "R":
                        Wordstospeech1="R FOR ROBOT";
                        WordsToSpeach = "A ROBOT";
                        break;
                    case "S":
                        Wordstospeech1="S FOR SOFA";
                        WordsToSpeach = "A SOFA";
                        break;
                    case "T":
                        Wordstospeech1="T FOR TRUCK";
                        WordsToSpeach = "A TRUCK";
                        break;
                    case "U":
                        WordsToSpeach = "UMBRELLA";
                        break;
                    case "V":
                        Wordstospeech1="V FOR VAN";
                        WordsToSpeach = "A VAN";
                        break;
                    case "W":
                        WordsToSpeach = "WATCH";
                        break;
                    case "X":
                        WordsToSpeach = "XMAS TREE";
                        break;
                    case "Y":
                        WordsToSpeach = "CUP";
                        break;
                    case "Z":
                        Wordstospeech1="Z FOR ZEBRA";
                        WordsToSpeach = "A ZEBRA";
                        break;

                    default:
                        WordsToSpeach="";
                }
                t1.setSpeechRate(0.6F);
                t1.speak(Wordstospeech1,TextToSpeech.QUEUE_FLUSH,null,null);
                t1.playSilentUtterance(5000,TextToSpeech.QUEUE_ADD,null);
                t1.speak("This is "+WordsToSpeach, TextToSpeech.QUEUE_ADD,null,null);
                Intent intent = new Intent(context,ARactivity.class);
                intent.putExtra("armodel",alphabet.getModelRes());
                context.startActivity(intent);
                Toast.makeText(context, "The Selected alphabet is "+alphabet.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return alphabetList.size();
    }

    static class AlphabetViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        public AlphabetViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.letterCard);
            imageView = itemView.findViewById(R.id.letterImg);
        }
    }
}

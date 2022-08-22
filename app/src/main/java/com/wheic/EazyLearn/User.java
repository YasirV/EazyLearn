package com.wheic.EazyLearn;

import android.icu.lang.UCharacter;

public class User {

    public String username;
    public String email;
    public int AlphabetsLearned;
    public int RhymesWatched;
    public int StoriesWatched;
    public int WritingWatched;
    public int GamesPlayed;
    public int Highscore;
    public int Wordspronounced;


    public User(String username, String email,int AlphabetsLearned, int RhymesWatched, int StoriesWatched, int WritingWatched, int GamesPlayed, int Highscore, int WordsPronounced) {
        this.username = username;
        this.email = email;
        this.AlphabetsLearned=AlphabetsLearned;
        this.RhymesWatched = RhymesWatched;
        this.StoriesWatched = StoriesWatched;
        this.WritingWatched = WritingWatched;
        this.GamesPlayed = GamesPlayed;
        this.Highscore = Highscore;
        this.Wordspronounced= WordsPronounced;
    }


    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
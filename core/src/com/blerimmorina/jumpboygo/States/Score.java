package com.blerimmorina.jumpboygo.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by Igor on 3/26/16.
 */
public class Score {

    private Preferences pref;

    private int score = 0;

    public Score() {
        pref = Gdx.app.getPreferences("HighScore");
    }

    public void increase() {
        this.score++;
    }

    public void saveHighscore() {
        if(score > pref.getInteger("highScore"))
        {
            pref.putInteger("highScore", score);
            pref.flush();
        }
    }

    public String getScore() { return Integer.toString(score); }

    public String getHighScore() {
        Integer highscore = pref.getInteger("highScore", 1);
        return Integer.toString(highscore);
    }
}

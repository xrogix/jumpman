package com.blerimmorina.jumpboygo.States;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blerimmorina.jumpboygo.handlers.GameStateManager;
import com.blerimmorina.jumpboygo.main.Game;

/**
 * Created by Igor on 3/20/16.
 */
public abstract class GameState {

    protected GameStateManager gsm;
    protected Game game;

    protected SpriteBatch sb;
    protected SpriteBatch fb;
    protected OrthographicCamera cam;
    protected OrthographicCamera hudCam;
    protected Score score;

    public GameState(GameStateManager gsm) {
        this.gsm = gsm;
        game = gsm.game();
        sb = game.getBatch();
        fb = game.getFontBatch();
        cam = game.getCam();
        hudCam = game.getHudCam();
        score = new Score();
    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();

}

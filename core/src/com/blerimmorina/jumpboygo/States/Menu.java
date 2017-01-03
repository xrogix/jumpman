package com.blerimmorina.jumpboygo.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Igor on 4/21/16.
 */
public class Menu extends GameState {

    private Texture background;
    private Texture playBtn;

    public Menu(com.blerimmorina.jumpboygo.handlers.GameStateManager gsm) {
        super(gsm);

        background = new Texture("background.png");

        if(Gdx.graphics.getWidth() >= 1000) {
            playBtn = new Texture("PlayButtonDouble.png");
        } else {
            playBtn = new Texture("PlayButtonReduced.png");
        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.setState(com.blerimmorina.jumpboygo.handlers.GameStateManager.PLAY);
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render() {
        sb.begin();

        //background
        Sprite bground = new Sprite(background);
        bground.setSize(Gdx.graphics.getWidth(), background.getHeight() + background.getHeight() / 2);
        bground.setPosition(0, 0);
        bground.draw(sb);

       sb.draw(playBtn, Gdx.graphics.getWidth() / 2 - playBtn.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        sb.end();
    }

    @Override
    public void dispose() {
        playBtn.dispose();
        background.dispose();
    }
}

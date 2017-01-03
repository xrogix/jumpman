package com.blerimmorina.jumpboygo.handlers;

import com.blerimmorina.jumpboygo.States.GameState;
import com.blerimmorina.jumpboygo.States.Menu;
import com.blerimmorina.jumpboygo.States.Play;
import com.blerimmorina.jumpboygo.main.Game;

import java.util.Stack;

/**
 * Created by Igor on 3/14/16.
 */
public class GameStateManager {

    private Game game;

    private Stack<GameState> gameStates;

    public static final int MENU = 958492;
    public static final int PLAY = 912837;

    public GameStateManager(Game game) {
        this.game = game;
        gameStates = new Stack<GameState>();
        pushState(MENU);
    }

    public Game game() {
        return game;
    }

    public void update(float dt) {
        gameStates.peek().update(dt);
    }

    public void render() {
        gameStates.peek().render();
    }

    public void dispose() {
    }

    private GameState getState(int state) {
        if(state == MENU) return new Menu(this);
        else if(state == PLAY) return new Play(this);
        return null;
    }

    public void setState(int state) {
        popState();
        pushState(state);
    }

    public void pushState(int state) {
        gameStates.push(getState(state));
    }

    public void popState() {
        GameState g = gameStates.pop();
        g.dispose();
    }
}

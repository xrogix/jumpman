package com.blerimmorina.jumpboygo.handlers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

/**
 * Created by Igor on 3/26/16.
 */
public class WorldContactListener implements ContactListener {


    private Boolean gameOver = false;

    @Override
    public void beginContact(Contact contact) {

        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if((fixA.getUserData() == "head" || fixA.getUserData() == "belly") || (fixB.getUserData() == "head" || fixB.getUserData() == "belly")) {
            gameOver = true;

        }
    }

    public Boolean getIsGameOver() {
        return gameOver;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}

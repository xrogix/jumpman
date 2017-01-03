package com.blerimmorina.jumpboygo.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.blerimmorina.jumpboygo.handlers.B2DVars;


/**
 * Created by Igor on 3/19/16.
 */
public class Ground extends Sprite {
    public World world;
    public Body b2body;

    private Sprite sprite;

    public Ground(World world) {
        this.world = world;
        defineGround();
    }

    public void defineGround() {
        BodyDef bdef = new BodyDef();
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        Float PPM = B2DVars.PPM;

        if(Gdx.graphics.getWidth() > 1000) {
            PPM = PPM/2;
        }

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Gdx.graphics.getWidth(), getGroundTexture().getHeight() / PPM);
        fdef.shape = shape;

        b2body.createFixture(fdef);
        sprite = new Sprite(getGroundTexture());

        sprite.setSize(getGroundTexture().getWidth() / PPM, getGroundTexture().getHeight() / PPM);
        b2body.setUserData(sprite);

        shape.dispose();
    }

    public Object getGroundSprite() {
        return b2body.getUserData();
    }

    public Texture getGroundTexture() {
        return new Texture("ground_03.png");
    }
}

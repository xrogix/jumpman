package com.blerimmorina.jumpboygo.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.blerimmorina.jumpboygo.handlers.B2DVars;

/**
 * Created by Igor on 3/19/16.
 */
public class Megaman extends Sprite {
    public World world;
    public Body b2body;

    private Texture texture;
    private Animation megamanAnimation;

    private Sound sound;

    public Megaman(World world) {
        this.world = world;
        defineMegaman();
    }

    public void defineMegaman() {
        BodyDef bdef = new BodyDef();

        Float PPM = B2DVars.PPM;

        if(Gdx.graphics.getWidth() > 1000) {
            PPM = PPM/2;
        }

        bdef.position.set(50 / PPM, 100 / PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(10 / PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

        texture = new Texture("running_man.png");
        megamanAnimation = new Animation(new TextureRegion(texture), 7, 0.8f);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-10 / PPM / 2, 11 / PPM), new Vector2(25 / PPM / 2, 11 / PPM));
        fdef.shape = head;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("head");

        EdgeShape belly = new EdgeShape();
        belly.set(new Vector2(12 / PPM, 10 / PPM), new Vector2(12 / PPM, 0 / PPM));
        fdef.shape = belly;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("belly");

        sound = Gdx.audio.newSound(Gdx.files.internal("jump.wav"));

        shape.dispose();
    }

    public void update(float dt) {
        megamanAnimation.update(dt);
    }

    public TextureRegion getTextureRegion() {
        return megamanAnimation.getFrame();
    }

    public float getWidth(){
        return texture.getWidth();
    }

    public float getHeight(){
        return texture.getHeight();
    }

    public void run() {

        int vel = 2;

        if(Gdx.graphics.getWidth() >= 1000)
            vel = 4;

        if(b2body.getLinearVelocity().x <= vel)
            b2body.applyLinearImpulse(new Vector2(0.08f, 0), b2body.getWorldCenter(), true);

        if(b2body.getPosition().x  > Gdx.graphics.getWidth() / B2DVars.PPM / 2)
            b2body.setTransform(0, b2body.getPosition().y, 0);
    }

    public void jump() {

        float j = 4.8f;

        if(Gdx.graphics.getWidth() >= 1000)
            j = 7.0f;

        Vector2 vel = b2body.getLinearVelocity();
        vel.y = j;
        b2body.setLinearVelocity(vel);
        sound.play();
    }

    public void dispose() {
        sound.dispose();
    }

}

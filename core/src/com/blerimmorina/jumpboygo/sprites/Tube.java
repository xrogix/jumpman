package com.blerimmorina.jumpboygo.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.blerimmorina.jumpboygo.handlers.B2DVars;

import java.util.Random;

/**
 * Created by Igor on 3/17/16.
 */
public class Tube {
    public static final int TUBE_HEIGHT = 33;
    private static final int FLUCTUATION = 90;
    private static final int TUBE_GAP = 80;
    private static final int LOWEST_OPENING = 50;
    private Texture leftTube, rightTube;
    private Vector2 posLeftTube, posRightTube;
    private Random rand;

    public BodyDef bodyDefLeftTube, bodyDefRightTube;
    public FixtureDef fixtureDefLeftTube, fixtureDefRightTube;
    private PolygonShape shapeLeftTube, shapeRightTube;
    public Body bodyLeft, bodyRight;
    private Rectangle boundsLeft, boundsRight;
    private Sprite lsprite, rsprite;


    public Tube(World world, float y) {


        String pipe = "custom_pipe.png";

//        if(Gdx.graphics.getWidth() > 1000) {
//            pipe = "custom_pipe_tablet.png";
//        }

        Float PPM = B2DVars.PPM;

        if(Gdx.graphics.getWidth() > 1000) {
            PPM = PPM/2;
        }

        leftTube = new Texture(pipe);
        rightTube = new Texture(pipe);
        rand = new Random();

        int lowest_opening = LOWEST_OPENING;
        if(Gdx.graphics.getWidth() >= 1000)
            lowest_opening = LOWEST_OPENING+50;

        //Attributes LEFT tube
        Integer position = rand.nextInt(FLUCTUATION) + TUBE_GAP + lowest_opening;
        posLeftTube = new Vector2(position / PPM, y / PPM);

        bodyDefLeftTube = new BodyDef();
        fixtureDefLeftTube = new FixtureDef();
        shapeLeftTube = new PolygonShape();

        bodyDefLeftTube.type = BodyDef.BodyType.StaticBody;
        bodyDefLeftTube.position.set(0, (y + TUBE_HEIGHT) / PPM);
        shapeLeftTube.setAsBox((position - TUBE_GAP) / PPM, leftTube.getHeight() / PPM / 2);
        fixtureDefLeftTube.shape = shapeLeftTube;

        bodyLeft = world.createBody(bodyDefLeftTube);
        bodyLeft.createFixture(fixtureDefLeftTube);
        shapeLeftTube.dispose();

        lsprite = new Sprite(leftTube);
        lsprite.setSize(leftTube.getWidth() / PPM, leftTube.getHeight() / PPM);
        bodyLeft.setUserData(lsprite);

        //Attribues RIGHT tube
        posRightTube = new Vector2((posLeftTube.x - TUBE_GAP - rightTube.getWidth()) / PPM / 2, y / PPM / 2);

        bodyDefRightTube = new BodyDef();
        fixtureDefRightTube = new FixtureDef();
        shapeRightTube = new PolygonShape();

        bodyDefRightTube.type = BodyDef.BodyType.StaticBody;
        bodyDefRightTube.position.set((position + TUBE_GAP + rightTube.getWidth()) / PPM, (y + TUBE_HEIGHT) / PPM);
        shapeRightTube.setAsBox((rightTube.getWidth() + TUBE_GAP) / PPM, rightTube.getHeight() / PPM / 2);
        fixtureDefRightTube.shape = shapeRightTube;

        bodyRight = world.createBody(bodyDefRightTube);
        bodyRight.createFixture(fixtureDefRightTube);
        shapeRightTube.dispose();

        rsprite = new Sprite(rightTube);
        rsprite.setSize(rightTube.getWidth() / PPM, rightTube.getHeight() / PPM);
        bodyRight.setUserData(rsprite);
    }

    public Object getDataLeftTube() {
        return bodyLeft.getUserData();
    }

    public Body getBodyLeftTube() {
        return bodyLeft;
    }

    public Object getBodyRightTube() {
        return bodyRight.getUserData();
    }

    public Texture getLeftTube() {
        return leftTube;
    }

    public Texture getRightTube() {
        return rightTube;
    }

    public Vector2 getBodyLeftPosition() { return bodyLeft.getPosition();}

    public Vector2 getBodyRightPosition() { return bodyRight.getPosition();}

    public Vector2 getPosLeftTube() { return posLeftTube; }

    public Vector2 getPosRightTube() { return posRightTube; }

    public void reposition(float y) {
        bodyLeft.setTransform(new Vector2(0, y), bodyLeft.getAngle());
        bodyRight.setTransform(new Vector2(bodyRight.getPosition().x, y), bodyRight.getAngle());
    }
}

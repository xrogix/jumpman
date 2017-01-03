package com.blerimmorina.jumpboygo.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.blerimmorina.jumpboygo.handlers.B2DVars;
import com.blerimmorina.jumpboygo.handlers.GameStateManager;
import com.blerimmorina.jumpboygo.handlers.WorldContactListener;
import com.blerimmorina.jumpboygo.sprites.Ground;
import com.blerimmorina.jumpboygo.sprites.Megaman;
import com.blerimmorina.jumpboygo.sprites.Tube;

/**
 * Created by Igor on 3/20/16.
 */
public class Play extends GameState {

    private World world;
    private Box2DDebugRenderer b2dr;

    private Megaman megaman;
    private Ground ground;

    private Array<Tube> tubes;
    private static final int TUBE_SPACING = 60;
    private static final int TUBE_COUNT = 50;
    private static final int GROUND = 50;

    private WorldContactListener listener;
    private Texture background, results, playBtn;
    private BitmapFont font, gameOver;
    private Integer positionHeight;

    public Play(GameStateManager gsm) {
        super(gsm);
        background = new Texture("background.png");

        Integer fontSize = 60;
        positionHeight = 90;

        if(Gdx.graphics.getWidth() >= 1000) {
            results = new Texture("ResultsOutputDouble.png");
            playBtn = new Texture("PlayButtonDouble.png");
            fontSize = 120;
            positionHeight = 180;
        } else {
            results = new Texture("ResultsOutput.png");
            playBtn = new Texture("PlayButtonReduced.png");
        }


        world = new World(new Vector2(0f, -10f), true);
        b2dr = new Box2DDebugRenderer();

        //Ground
        ground = new Ground(world);

        //Megaman
        megaman = new Megaman(world);

        //Tuber
        tubes = new Array<Tube>();
        for(int i = 1; i <= TUBE_COUNT; i++) {
            Tube tube = new Tube(world, i * (TUBE_SPACING + Tube.TUBE_HEIGHT) + GROUND);
            tubes.add(tube);
        }

        //Score
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("jumpman.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
        params.size = fontSize;
        params.color = Color.RED;
        font = generator.generateFont(params);

        params.color = Color.BLACK;
        gameOver= generator.generateFont(params);

        cam.setToOrtho(false, Gdx.graphics.getWidth() / B2DVars.PPM / 2, Gdx.graphics.getHeight() / B2DVars.PPM / 2);
        hudCam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        listener = new WorldContactListener();
        world.setContactListener(listener);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            if(listener.getIsGameOver()) {
                dispose();
                gsm.setState(GameStateManager.PLAY);
            } else {
                megaman.jump();
                score.increase();
                score.saveHighscore();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        world.step(dt, 6, 2);

        megaman.update(dt);

        if(megaman.b2body.getPosition().y > cam.viewportHeight / 2)
            cam.position.y = megaman.b2body.getPosition().y;
        else
            cam.position.y = cam.viewportHeight / 2;

        hudCam.update();

        if(!listener.getIsGameOver()) {
            for (Tube tube : tubes) {
                if(cam.position.y - (cam.viewportHeight / 2) > tube.getBodyLeftPosition().y + (Tube.TUBE_HEIGHT + GROUND - 20) / B2DVars.PPM / 2 + 0.85f * TUBE_COUNT - 5) {
                    tube.reposition(tube.getBodyLeftPosition().y + (Tube.TUBE_HEIGHT + GROUND) / B2DVars.PPM / 2 + 0.85f * TUBE_COUNT);
                }
            }
            megaman.run();
        }

        cam.update();
    }

    @Override
    public void render() {
        sb.setProjectionMatrix(cam.combined);

        sb.begin();

        Float PPM = B2DVars.PPM;

        Double distance = 0.8;

        if(Gdx.graphics.getWidth() >= 1000) {
            PPM /= 2;
            distance = 1.6;
        }

        //background
        Sprite bground = new Sprite(background);
        bground.setSize(Gdx.graphics.getWidth() / B2DVars.PPM, background.getHeight() / B2DVars.PPM);
        bground.setPosition(0, 0);
        bground.draw(sb);

        for(Tube tube: tubes) {
            Sprite spriteTubeLeft = (Sprite) tube.getDataLeftTube();
            spriteTubeLeft.setPosition((float) (tube.getPosLeftTube().x - tube.getLeftTube().getWidth()/ PPM - distance), tube.bodyLeft.getPosition().y - tube.getLeftTube().getHeight() / PPM / 2);
            spriteTubeLeft.draw(sb);

            Sprite spriteTubeRight = (Sprite) tube.getBodyRightTube();
            spriteTubeRight.setPosition(tube.getPosLeftTube().x, tube.bodyRight.getPosition().y - tube.getRightTube().getHeight() / PPM / 2);
            spriteTubeRight.draw(sb);
        }

        //Ground
        Sprite sprite = (Sprite) ground.b2body.getUserData();
        sprite.setPosition(ground.b2body.getPosition().x, ground.b2body.getPosition().y);
        sprite.draw(sb);

        sb.draw(
                megaman.getTextureRegion(),
                megaman.b2body.getPosition().x - (megaman.getTextureRegion().getRegionWidth() / PPM / 2) / 2,
                megaman.b2body.getPosition().y - (megaman.getTextureRegion().getRegionHeight() / PPM / 2) / 2,
                megaman.getTextureRegion().getRegionWidth() / PPM / 2,
                megaman.getTextureRegion().getRegionHeight() / PPM / 2);

        sb.end();

        fb.setProjectionMatrix(hudCam.combined);

        fb.begin();

        if(!listener.getIsGameOver()) {
            game.adsManager.closeWindow();
            font.draw(fb, score.getScore(), Gdx.graphics.getWidth() / 2 - score.getScore().length(), Gdx.graphics.getHeight() - 30);
        } else {

            game.adsManager.showAdsGameOver();

            Integer height = results.getHeight();

            fb.draw(results, Gdx.graphics.getWidth() / 2 - results.getWidth() / 2, Gdx.graphics.getHeight() - (height + positionHeight));

            fb.draw(playBtn, Gdx.graphics.getWidth() / 2 - playBtn.getWidth() / 2, Gdx.graphics.getHeight() - (height * 2));

            GlyphLayout glyphLayout = new GlyphLayout();
            String gameover = "GAME OVER";
            glyphLayout.setText(gameOver, gameover);
            float w = glyphLayout.width;

            gameOver.draw(fb, gameover, Gdx.graphics.getWidth() / 2 - w / 2, Gdx.graphics.getHeight() - 30);

            String s = "SCORE: " + score.getScore();
            glyphLayout.setText(gameOver, s);
            float sw = glyphLayout.width;
            gameOver.draw(fb, "SCORE: " + score.getScore(), Gdx.graphics.getWidth() / 2 - sw / 2, Gdx.graphics.getHeight() - height / 2);

            String b = "BEST: " + score.getHighScore();
            glyphLayout.setText(gameOver, b);
            float bw = glyphLayout.width;
            gameOver.draw(fb, "BEST: " + score.getHighScore(), Gdx.graphics.getWidth() / 2 - bw / 2, Gdx.graphics.getHeight() - height);

        }

        fb.end();

//        b2dr.render(world, cam.combined);
    }

    @Override
    public void dispose() {
        background.dispose();
        results.dispose();
        megaman.dispose();
//        font.dispose();
//        generator.dispose();
    }
}

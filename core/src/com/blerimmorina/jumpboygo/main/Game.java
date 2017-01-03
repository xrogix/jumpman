package com.blerimmorina.jumpboygo.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.blerimmorina.jumpboygo.handlers.AdsManager;
import com.blerimmorina.jumpboygo.handlers.GameStateManager;

public class Game extends ApplicationAdapter {

	public AdsManager adsManager;

	private SpriteBatch sb;
	private SpriteBatch fb;
	private OrthographicCamera cam;
	private OrthographicCamera hudCam;

	private GameStateManager gsm;

	public static final float STEP = 1/60f;
	private float accum;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;

	public static final String TITLE = "Jump Boy Go";

	public FPSLogger fps;

	public Game(AdsManager adsManager) {
		this.adsManager = adsManager;
	}

	@Override
	public void create () {

		sb = new SpriteBatch();
		fb = new SpriteBatch();
		gsm = new GameStateManager(this);
		fps = new FPSLogger();
		cam = new OrthographicCamera();
		hudCam = new OrthographicCamera();

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(93 / 255f, 180 / 255f, 207 / 255f, 1);

		accum += Gdx.graphics.getDeltaTime();
		while( accum >= STEP) {
			accum -= STEP;
			gsm.update(STEP);
		}
		gsm.render();

//		fps.log();
	}

	public void dispose() {
		sb.dispose();
	}

	public void resize(int w, int h) {

	}

	public void pause() {};
	public void resume() {};

	public SpriteBatch getBatch() { return sb; }

	public SpriteBatch getFontBatch() { return fb; }

	public OrthographicCamera getCam() {
		return cam;
	}

	public OrthographicCamera getHudCam() {
		return hudCam;
	}
}

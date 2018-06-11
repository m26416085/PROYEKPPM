package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	float renderX;
	float renderY;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		renderX = 100;
		renderY = 100;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderX = renderX + Gdx.input.getAccelerometerY() * 5;
		renderY = renderY -  Gdx.input.getAccelerometerX() * 5;
		if (renderX < 0) renderX = 0;
		if (renderX > Gdx.graphics.getWidth() - 200) renderX = Gdx.graphics.getWidth() - 200;
		if (renderY < 0) renderY = 0;
		if (renderY > Gdx.graphics.getHeight() - 200) renderY = Gdx.graphics.getHeight() - 200;

		batch.begin();
		batch.draw(img, renderX, renderY, 200, 200);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}

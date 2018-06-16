package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img, backgroundimg;
	float renderX;
	float renderY;
	TiledMap map;
	AssetManager manager;
	MapProperties properties;
	int tileWidth, tileHeight,
			mapWidthInTiles, mapHeightInTiles,
			mapWidthInPixels, mapHeightInPixels;

	OrthographicCamera camera;
	OrthogonalTiledMapRenderer renderer;
	StretchViewport viewport;
	Stage stage;

	TiledMapTileLayer collisionLayer;

	float speed;
	@Override
	public void create () {
		batch = new SpriteBatch();
		backgroundimg = new Texture(Gdx.files.internal("mazetutorial.png"));

		img = new Texture("ball1.png");
		renderX = 100;
		renderY = 100;
		speed = 2;

		manager = new AssetManager();
		manager.setLoader(TiledMap.class, new TmxMapLoader());
		manager.load("s7edgemap.tmx", TiledMap.class);
		manager.finishLoading();

		map = manager.get("s7edgemap.tmx", TiledMap.class);
		properties = map.getProperties();
		tileWidth         = properties.get("tilewidth", Integer.class);
		tileHeight        = properties.get("tileheight", Integer.class);
		mapWidthInTiles   = properties.get("width", Integer.class);
		mapHeightInTiles  = properties.get("height", Integer.class);
		mapWidthInPixels  = mapWidthInTiles  * tileWidth;
		mapHeightInPixels = mapHeightInTiles * tileHeight;

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.x = mapWidthInPixels * .5f;
		camera.position.y = mapHeightInPixels * .35f;

		renderer = new OrthogonalTiledMapRenderer(map);

		viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport.apply();

		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));


	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		int check;
		renderX = renderX + Gdx.input.getAccelerometerY() * speed;
		renderY = renderY -  Gdx.input.getAccelerometerX() * speed;

		if (renderX < 0) {renderX= 0;}
		if (renderX > Gdx.graphics.getWidth() - 50) renderX = Gdx.graphics.getWidth() - 50;
		if (renderY < 0) renderY = 0;
		if (renderY > Gdx.graphics.getHeight() - 50) renderY = Gdx.graphics.getHeight() - 50;

		speed = 2;
		//old position
		float oldX = renderX;
		float oldY = renderY;
		boolean collisionX = false, collisionY = false;

		//top left

		/*collisionX = collisionLayer.getCell((int)renderX / tileWidth, (int)renderY / tileHeight).getTile().getProperties().containsKey("blocked");

		//middle left
		if (!collisionX)
			collisionX = collisionLayer.getCell((int) renderX/ tileWidth, (int)(renderY / 2) / tileHeight).getTile().getProperties().containsKey("blocked");

		//bottom left
		if (!collisionX)
			collisionX = collisionLayer.getCell((int) renderX / tileWidth, (int) renderY / tileHeight).getTile().getProperties().containsKey("blocked");

		if (collisionX){
			renderX = 0;
			speed = 0;
		}


		collisionY = collisionLayer.getCell((int) renderX / tileWidth, (int) renderY / tileHeight).getTile().getProperties().containsKey("blocked");

		if (!collisionY)
			collisionY = collisionLayer.getCell((int) (renderX/ 2 )/ tileWidth, (int) renderY / tileHeight).getTile().getProperties().containsKey("blocked");

		if (!collisionY)
			collisionY = collisionLayer.getCell((int) renderX / tileWidth, (int) renderY / tileHeight).getTile().getProperties().containsKey("blocked");

		if (collisionY){
			renderY = 0;
			speed = 0;
		}*/

		camera.position.set(renderX, renderY, 0);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		batch.begin();

		batch.draw(img, renderX, renderY, 50, 50);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		//viewport.update(width, height);
		//stage.getViewport().update(width, height);
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		manager.dispose();
	}
}

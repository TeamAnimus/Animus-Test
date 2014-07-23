package com.greenpumpkin.screens;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.greenpumpkin.game.*;

public class TestMap implements Screen {
	private Image AnimusLogo = new Image(new Texture(Gdx.files.internal("Animus.png")));
	private Image Protag = new Image(new Texture(Gdx.files.internal("ProtagLeft.png")));
	private Stage stage = new Stage();
	private OrthographicCamera camera;
	//private Viewport viewport;
	
	Music caveTheme = Gdx.audio.newMusic(Gdx.files.internal("music/caveTheme.mp3"));
	
	//Currently not being used for anything, but maybe it will be important later.
	public boolean fadeInDone = false;
	
	//START OF LIGHTBOX STUFF
	int numRays = 128; //how many rays are emitted for shadow casting
	float lightDistance = 16f; // distance light goes
	private World world; /** our box2D world, controls physics **/
	RayHandler rayHandler; //the main object of light2d, heavily important
	//END OF LIGHTBOX STUFF
	
	TiledMap tiledMap =  new TmxMapLoader().load("TestMap.tmx");
	OrthogonalTiledMapRenderer renderer;
	
	@Override
	public void show() {
		//camera creation
		camera = new OrthographicCamera(48, 32);
		camera.position.set(25, 16, 0);
		camera.update(true);
		//adds background image
		AnimusLogo.setX(135);
		Protag.setPosition(896, 319);
		Protag.setScale(2);
		stage.addActor(AnimusLogo);
		stage.addActor(Protag);
		
		//viewport = new StretchViewport(1600,900, camera);
		
		caveTheme.play();
		caveTheme.setLooping(true); 
		
		
		//START OF LIGHTBOX STUFF
		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(camera.combined);
		RayHandler.setGammaCorrection(true);
		RayHandler.useDiffuseLight(true);
		rayHandler.setAmbientLight(0.1f, 0.1f, 0.1f, 1f);
		rayHandler.setCulling(true);		
		//rayHandler.setBlur(false);
		rayHandler.setBlurNum(1);
		rayHandler.setShadows(true); 
		rayHandler.setShadows(true);
		
		//tiledmap
		float unitScale = 1/12f;
		renderer = new OrthogonalTiledMapRenderer(tiledMap, unitScale);
		renderer.setMap(tiledMap);
		renderer.setView(camera);
		
		//lights creation
		//water
		new PointLight(rayHandler, numRays, new Color(0f,0f,0.6f,1f), lightDistance*3, -20, 8);
		new PointLight(rayHandler, numRays, new Color(0f,0f,0.6f,1f), lightDistance*2, -15, 8);
		//entrance
		new ConeLight(rayHandler, numRays, new Color(0.1f,0.15f,0.05f,1f), lightDistance*8, 30, 17,140,55);
		new PointLight(rayHandler, numRays, new Color(1f,1f,0.8f,1f), lightDistance, 25.1f, 21f);
		//crystals
		//blue
		new PointLight(rayHandler, numRays, new Color(0f,0f,0.6f,1f), lightDistance/2, -10, 20);
		new PointLight(rayHandler, numRays, new Color(0f,0f,0.6f,1f), lightDistance/1.5f, 5.9f, 23.4f);
		//yellow
		new PointLight(rayHandler, numRays, new Color(0.6f,0.6f,0f,1f), lightDistance/2, -17f, 21.5f);
		new PointLight(rayHandler, numRays, new Color(0.0f,0.6f,0f,1f), lightDistance/3, -5.4f, 23.2f);
		new PointLight(rayHandler, numRays, new Color(0.6f,0.6f,0f,1f), lightDistance/2, 20.2f, 24.8f);
		//red
		new PointLight(rayHandler, numRays, new Color(0.6f,0f,0f,1f), lightDistance/2, 0.9f, 18.1f);
		new PointLight(rayHandler, numRays, new Color(0.6f,0f,0f,1f), lightDistance/2, 15.5f, 21.5f);
		
		//END OF LIGHTBOX STUFF
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		renderer.setView(camera);
		renderer.render();
		rayHandler.updateAndRender();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setCamera(new VirtualResolution(Animus.WIDTH, Animus.HEIGHT));
		//viewport.update(Animus.WIDTH, Animus.HEIGHT);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {		
	}

	@Override
	public void resume() {		
	}

	@Override
	public void dispose() {
		stage.dispose();
		caveTheme.dispose();
		rayHandler.dispose();
		tiledMap.dispose();
	}
}

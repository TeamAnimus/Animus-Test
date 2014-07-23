package com.greenpumpkin.screens;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.greenpumpkin.game.*;

public class TestMap2 implements Screen {
	private Image AnimusLogo = new Image(new Texture(Gdx.files.internal("Animus.png")));
	private Image Protag = new Image(new Texture(Gdx.files.internal("ProtagLeft.png")));
	private Stage stage = new Stage();
	private OrthographicCamera lightCamera;
	private OrthographicCamera mapCamera;
	//private Viewport viewport;
	
	Music caveTheme = Gdx.audio.newMusic(Gdx.files.internal("music/caveTheme.mp3"));
	
	//Currently not being used for anything, but maybe it will be important later.
	public boolean fadeInDone = false;
	
	//START OF LIGHTBOX STUFF
	int numRays = 8; //how many rays are emitted for shadow casting
	float lightDistance = 16f; // distance light goes
	private World world; /** our box2D world, controls physics **/
	RayHandler rayHandler; //the main object of light2d, heavily important
	//END OF LIGHTBOX STUFF
	
	TiledMap tiledMap;
	TiledMap backMap;
	OrthogonalTiledMapRenderer renderer;
	OrthogonalTiledMapRenderer backRenderer;
	
	//player related variables
	static float MAX_VELOCITY = 10f;
	static float JUMP_VELOCITY = 40f;
	static float DAMPING = 0.87f;
	private static final float GRAVITY = -2.5f;
	final Vector2 position = new Vector2();
	final Vector2 velocity = new Vector2();
	long startTime = 0;
	
	@Override
	public void show() {
		//camera creation
		lightCamera = new OrthographicCamera(32, 18);
		lightCamera.position.set(0, 10, 0);
		lightCamera.update(true);
		mapCamera = new OrthographicCamera(32, 18);
		mapCamera.position.set(15, 10, 0);
		mapCamera.update(true);
		
		//adds background image
		
		//AnimusLogo.setX(135);
		//Protag.setPosition(896, 319);
		//Protag.setScale(2);
		//position.set(30, 12);
		
		caveTheme.play();
		caveTheme.setLooping(true); 
		
		
		//START OF LIGHTBOX STUFF
		rayHandler = new RayHandler(world);
		rayHandler.setCombinedMatrix(lightCamera.combined);
		RayHandler.setGammaCorrection(true);
		RayHandler.useDiffuseLight(true);
		rayHandler.setAmbientLight(0.1f, 0.1f, 0.1f, 1f);
		//rayHandler.setCulling(true);
		rayHandler.setBlurNum(1);
		rayHandler.setShadows(true); 
		
		//tiledmap
		tiledMap =  new TmxMapLoader().load("TestMap.tmx");
		backMap = new TmxMapLoader().load("TestMapBack.tmx");
		renderer = new OrthogonalTiledMapRenderer(tiledMap, 1/16f);
		renderer.setMap(tiledMap);
		renderer.setView(mapCamera);
		backRenderer = new OrthogonalTiledMapRenderer(backMap, 1/16f);
		backRenderer.setMap(backMap);
		backRenderer.setView(mapCamera);
		
		//lights creation
		//water
		new PointLight(rayHandler, numRays, new Color(0f,0f,0.6f,1f), lightDistance*3, -20/1.6f, 8/1.6f);
		new PointLight(rayHandler, numRays, new Color(0f,0f,0.6f,1f), lightDistance*2, -15/1.6f, 8/1.6f);
		//entrance
		new ConeLight(rayHandler, numRays, new Color(0.1f,0.15f,0.05f,1f), lightDistance*8, 36/1.6f, 18/1.6f,140,55);
		new PointLight(rayHandler, numRays, new Color(1f,1f,0.8f,1f), lightDistance, 26.1f/1.6f, 19f/1.6f);
		//crystals
		//blue
		new PointLight(rayHandler, numRays, new Color(0f,0f,0.6f,1f), lightDistance/2, -10/1.6f, 20/1.6f);
		new PointLight(rayHandler, numRays, new Color(0f,0f,0.6f,1f), lightDistance/1.5f, 5.9f/1.6f, 23.4f/1.6f);
		//yellow
		new PointLight(rayHandler, numRays, new Color(0.6f,0.6f,0f,1f), lightDistance/2, -17f/1.6f, 21.5f/1.6f);
		new PointLight(rayHandler, numRays, new Color(0.0f,0.6f,0f,1f), lightDistance/3, -5.4f/1.6f, 23.2f/1.6f);
		new PointLight(rayHandler, numRays, new Color(0.6f,0.6f,0f,1f), lightDistance/2, 20.2f/1.6f, 24.8f/1.6f);
		//red
		new PointLight(rayHandler, numRays, new Color(0.6f,0f,0f,1f), lightDistance/2, 0.9f/1.6f, 18.1f/1.6f);
		new PointLight(rayHandler, numRays, new Color(0.6f,0f,0f,1f), lightDistance/2, 15.5f/1.6f, 21.5f/1.6f);
		
		//END OF LIGHTBOX STUFF
		
		position.x=30f;
	}
	
	@Override
	public void render(float delta) {
		startTime= System.currentTimeMillis();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
		moveCamera(delta);
		renderer.setView(mapCamera);
		backRenderer.setView(mapCamera);
		lightCamera.update();
		mapCamera.update();
		backRenderer.render();
		renderer.render();
		rayHandler.render();
		while(true)
			if(((System.currentTimeMillis()-startTime)%17)<=2)
				break;
	}

	private void moveCamera(float delta) {
		if ((Gdx.input.isKeyPressed(Keys.LEFT))){
			velocity.x=-0.06f;
		}
		if ((Gdx.input.isKeyPressed(Keys.RIGHT))){
			velocity.x=0.06f;
		}
		//if(velocity.x>0.2f)
		//	velocity.x=0.2f;
		//if(velocity.x<-0.2f)
		//	velocity.x=-0.2f;
		velocity.x/=1.2f;
		position.x+=velocity.x;
		lightCamera.position.x = position.x-15;
		rayHandler.setCombinedMatrix(lightCamera.combined);
		mapCamera.position.x = position.x;
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().setCamera(new VirtualResolution(Animus.WIDTH, Animus.HEIGHT));
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

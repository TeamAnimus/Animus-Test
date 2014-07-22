package com.greenpumpkin.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Test implements Screen {
	private Texture texture = new Texture(Gdx.files.internal("TestMap.png"));
	private Image Image = new Image(texture);
	private Stage stage = new Stage();
	
	Sound caveTheme = Gdx.audio.newSound(Gdx.files.internal("music/caveTheme.mp3"));
	
	//Currently not being used for anything, but maybe it will be important later.
	public boolean fadeInDone = false;
	
	@Override
	public void show() {
		//adds background image
		stage.addActor(Image);
		caveTheme.play();
		
		// fades in background.
		//pretty much useless because it doesn't fade in the lights.
		Image.addAction(Actions.sequence(Actions.alpha(0)
				,Actions.fadeIn(0.75f),Actions.delay(1.5f),Actions.run(new Runnable() {
			@Override
			public void run() {
				fadeInDone = true;
			}
		})));
		//end of fade in
		
		
		
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
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
		texture.dispose();
		stage.dispose();
		caveTheme.dispose();
	}
}

package greenpumpkin.game;

import greenpumpkin.screens.*;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Animus extends Game {
	public static final String TITLE="Game Project";
	 public static final int WIDTH=960,HEIGHT=640;
	
	@Override
	public void create() {
		setScreen(new Test());
	}
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Animus";
		cfg.width = 960;
		cfg.height = 640;
		new LwjglApplication(new Animus(), cfg);
	}
}
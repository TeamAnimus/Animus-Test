package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.greenpumpkin.game.Animus;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Animus";
		cfg.width = 1280;
		cfg.height = 720;
		cfg.foregroundFPS=30;
		new LwjglApplication(new Animus(), cfg);
	}
}
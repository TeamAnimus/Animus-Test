package com.greenpumpkin.game;

import com.badlogic.gdx.Game;
import com.greenpumpkin.screens.*;

public class Animus extends Game {
	public static final String TITLE="Game Project";
	public static final int WIDTH=1600,HEIGHT=900;
	
	@Override
	public void create() {
		setScreen(new TestMap2());
	}
}
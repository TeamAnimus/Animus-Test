package com.greenpumpkin.game;

import com.badlogic.gdx.Game;
import com.greenpumpkin.screens.Test;

public class Animus extends Game {
	public static final String TITLE="Game Project";
	 public static final int WIDTH=960,HEIGHT=640;
	
	@Override
	public void create() {
		setScreen(new Test());
	}
}

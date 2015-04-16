package com.retroDante.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameRetroDante extends Game {
	
	GameManager gameManager = GameManager.getInstance();
	Screen testScreen;
	
	@Override
	public void create () {
		gameManager.setGame(this);
		
		//testScreen = new TestScreen();
		//this.setScreen(testScreen);
		//testScreen = new MenuScreen();
		//this.setScreen(testScreen);
		//testScreen = new UITestScreen();
		//this.setScreen(testScreen);
		testScreen = new EditorScreen();
		this.setScreen(testScreen);
	}

	@Override
	public void render () {
		super.render();
	}
}

package com.retroDante.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.retroDante.game.Controllable.KeyStatus;

public class TestScreen implements Screen, InputProcessor{

	private SpriteBatch batch;
    private BitmapFont font;
    Player player;
    List<Element2D> m_platformContainer = new ArrayList<Element2D>();
    
    
	
	@Override
	public void show() {
		
		batch = new SpriteBatch();
		font = new BitmapFont();	
		
		TileSetManager tileSetManager = new TileSetManager();
		tileSetManager.load(Gdx.files.getLocalStoragePath()+"/asset/"+"textureInfo/tileSetManagerLoader.txt");
		
		TileSetInfo playerTileSet = tileSetManager.get("test_player");
    	player = new Player(playerTileSet, 0, 1);//new Texture(Gdx.files.internal("badlogic.jpg"))
    	player.setPosition(new Vector2(200, 200));
    	//player.addConstantForce(new Force(new Vector2( 0, 9), Force.TypeOfForce.CONSTANT));
    	
    	//platforms : 
    	TileSetInfo platformTileSet = new TileSetInfo("texture/badlogic.jpg", "textureInfo/aaa.txt");
    	Element2D platform = new Platform(platformTileSet, 0);
    	platform.setPosition(new Vector2(200, 100));
    	
    	m_platformContainer.add(platform);
    	
    	Gdx.input.setInputProcessor(this);
		
	}
	
	public void update(float delta)	{
		
		player.update(delta, m_platformContainer);
		
	}

	@Override
	public void render(float delta) {
		
		//update de la logique :
		update(delta*10.f);
		
		
		//update du rendu : 
		 Gdx.gl.glClearColor(0.2f, 0.2f,0.2f, 1f);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	         
	        batch.begin();
	     font.draw(batch, "Bienvenue dans inGameScreen",50,Gdx.graphics.getHeight()-50);
	     
	     player.draw(batch);
	     for(Element2D e : m_platformContainer)
	     {
	    	 e.draw(batch);
	     }
	     
	     batch.end();
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean keyDown(int keycode) {
		player.listenKey(KeyStatus.DOWN, keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		player.listenKey(KeyStatus.UP, keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}

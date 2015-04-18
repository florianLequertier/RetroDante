package com.retroDante.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class EditorCamera extends OrthographicCamera implements InputProcessor{
	
	public static enum Direction{
		RIGHT,
		LEFT,
		UP,
		DOWN;
	}
	
	private List<Direction> m_directions;
	
	EditorCamera()
	{
		super();
		m_directions = new ArrayList<Direction>();
	}
	
	EditorCamera(float width, float height)
	{
		super(width, height);
		m_directions = new ArrayList<Direction>();
	}
	
	public void updateMovements()
	{
		if(m_directions.contains(Direction.LEFT))
			this.translate(new Vector2(-5,0) );
		if(m_directions.contains(Direction.RIGHT))
			this.translate(new Vector2(5,0) );
		if(m_directions.contains(Direction.UP))
			this.translate(new Vector2(0,5) );
		if(m_directions.contains(Direction.DOWN))
			this.translate(new Vector2(0,-5) );
		
		this.update();
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		if(keycode == Keys.Q || keycode == Keys.A)
			m_directions.add(Direction.LEFT);
		if(keycode == Keys.Z || keycode == Keys.W)
			m_directions.add(Direction.UP);
		if(keycode == Keys.D)
			m_directions.add(Direction.RIGHT);
		if(keycode == Keys.S)
			m_directions.add(Direction.DOWN);
		
		System.out.println("La camera est maintenant en ("+this.position.x+", "+this.position.y+")" );
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		
		if(keycode == Keys.Q || keycode == Keys.A)
			m_directions.remove(Direction.LEFT);
		if(keycode == Keys.Z || keycode == Keys.W)
			m_directions.remove(Direction.UP);
		if(keycode == Keys.D)
			m_directions.remove(Direction.RIGHT);
		if(keycode == Keys.S)
			m_directions.remove(Direction.DOWN);
		
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

package com.retroDante.game.Editor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.retroDante.game.map.Map;

public class EditorCamera extends OrthographicCamera implements InputProcessor{
	
	public static int editorCameraWidth = 600;
	public static int editorCameraHeight = 400;
	
	public static enum Direction{
		RIGHT,
		LEFT,
		UP,
		DOWN;
	}
	
	private List<Direction> m_directions;
	private Vector2 m_currentTranslation;
	private Map m_parralaxTarget = null;
	float m_maxSpeed = 2.f;
	float m_currentSpeed = 1.f;
	
	EditorCamera()
	{
		super(editorCameraWidth, editorCameraHeight);
		m_directions = new ArrayList<Direction>();
	}
	
	EditorCamera(float width, float height)
	{
		super(width, height);
		m_directions = new ArrayList<Direction>();
	}
	
	void setParralaxTarget(Map map)
	{
		m_parralaxTarget = map;
	}
	
	public Vector2 getCurrentTranslation()
	{
		return m_currentTranslation;
	}
	
	
	@Override
	public void translate(float x, float y)
	{
		m_currentTranslation = new Vector2(x * m_currentSpeed, y * m_currentSpeed);
		super.translate(m_currentTranslation);
		if(m_parralaxTarget != null)
		{
			m_parralaxTarget.updateParralax(this);
		}
	}
	
	@Override
	public void translate(Vector2 vec)
	{
		this.translate(vec.x, vec.y);
	}
	
	public void updateMovements()
	{
		if(m_directions.contains(Direction.LEFT))
			this.translate(-5,0);
		if(m_directions.contains(Direction.RIGHT))
			this.translate(5,0);
		if(m_directions.contains(Direction.UP))
			this.translate(0,5);
		if(m_directions.contains(Direction.DOWN))
			this.translate(0,-5);
		
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

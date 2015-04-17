package com.retroDante.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class EditorSceen extends Table{

	private MouseEditor m_mouseEditor; 
	private List<CanvasInterface> m_canvasContainer;
	
	EditorSceen()
	{
		m_canvasContainer = new ArrayList<CanvasInterface>();
		
		this.setFillParent(true);
		this.setPosition(0,0);
		this.align(Align.center);
		this.debug();
		this.setTouchable(Touchable.enabled);
		
		this.addListener( new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				
				if(m_mouseEditor != null)
					dropCanvasOnSceen();
				
				return false;
			}
			
		});
	}
	
	public void setMouse(MouseEditor mouse)
	{
		m_mouseEditor = mouse;
	}
	
	public void dropCanvasOnSceen()
	{
		if(m_mouseEditor != null)
		{
			m_mouseEditor.dropCanvasOn(m_canvasContainer);
		}
		else
		{
			System.out.println("WARNING : EditorSceen : dropCanvasOnSceen : le pointeur vers la mouseEditor n'a pas été initialisé.");
		}
	}
	
	
	@Override
	public void draw(Batch batch, float alpha)
	{
		this.validate();
		
		for(CanvasInterface canvas : m_canvasContainer)
		{
			canvas.draw(batch);
		}
		
		super.draw(batch, alpha);
	}
}

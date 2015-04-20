package com.retroDante.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class HUDGamePause extends Table {
	
	
	HUDGamePause(Skin skin, HUDGame parent)
	{
		
		super();

		//this.setFillParent(true);
		//this.setPosition(300,300);
		//this.align(Align.center);
		//m_tableComponent.put("pauseMenu", pauseMenu);
		//m_stage.addActor(pauseMenu);


		final TextButton button_quit = new TextButton("Quit", skin);
		final TextButton button_resume = new TextButton("Resume", skin);

		this.add(button_quit).space(20).width(186).height(46).row();
		this.add(button_resume).space(20).width(186).height(46).row();


		button_quit.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				button_quit.setChecked(false);
				
				GameManager.getInstance().resetGameSession();
				GameManager.getInstance().changeScreen("menu");
				

			}

		});
		final HUDGame parentPtr = parent;
		button_resume.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				button_resume.setChecked(false);
				
				parentPtr.closePauseMenu();

			}

		});
		this.setName("pauseMenu");

	}
	
	
//	public void close()
//	{
//		if(this.hasParent())
//		{
//			this.remove();
//			GameManager.getInstance().togglePause(); //unpause
//		}
//	}
	
	
}

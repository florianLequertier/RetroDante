package com.retroDante.game.Editor;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.retroDante.game.GameManager;

public class HUDEditorPause extends Table {
	
	
	
	HUDEditorPause(Skin skin, HUDEditor parent)
	{
		
		super();
		
		final TextField textField_save = new TextField("Save", skin);
			textField_save.setText("nom de la map");
		final TextButton button_save = new TextButton("Save", skin);
		final TextButton button_quit = new TextButton("Quit", skin);
		final TextButton button_resume = new TextButton("Resume", skin);
		
		this.add(textField_save).space(20).width(200).height(64).row();
		this.add(button_save).space(20).width(200).height(64).row();
		this.add(button_quit).space(20).width(200).height(64).row();
		this.add(button_resume).space(20).width(200).height(64).row();

		final HUDEditor parentPtr = parent; //(HUDEditor) this.getParent();
		button_save.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				button_quit.setChecked(false);
				
				if(parentPtr == null)
				{
					System.out.println("ERROR : HUDEditorPause : parentPtr est null");
				}
				parentPtr.callSave(textField_save.getText());
				
			}

		});
		button_quit.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				button_quit.setChecked(false);
				
				GameManager.getInstance().resetGameSession();
				GameManager.getInstance().changeScreen("menu");
				

			}

		});
		button_resume.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				button_resume.setChecked(false);
				
				parentPtr.closePauseMenu();

			}

		});
		this.setBackground(skin.newDrawable("white", Color.OLIVE));
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

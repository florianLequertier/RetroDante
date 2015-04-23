package com.retroDante.game.trigger;

import com.badlogic.gdx.graphics.Color;
import com.retroDante.game.GameManager;
import com.retroDante.game.character.Character;

public class NextLevel extends Trigger {
	
	NextLevel()
	{
		super(Color.MAGENTA, 2);
	}
	
	@Override
	public void triggerEventOn(Character target)
	{
		GameManager.getInstance().nextChapter();
	}
}

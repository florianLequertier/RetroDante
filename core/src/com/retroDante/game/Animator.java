package com.retroDante.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animator{
	
	List<Animation> m_animationContainer = new ArrayList<Animation>();
	private Clock m_timer = new Clock();
	int m_currentAnimation;
	float m_currentFrame;
	boolean m_isPlaying;
	boolean m_isLooping;
	
	Animator(Animation animation)
	{
		m_currentFrame = 0;
		m_currentAnimation = 0;
		m_isPlaying = false;
		m_isLooping = true;
		m_animationContainer.add( animation );
	}
	Animator(List<Animation> animationContainer)
	{
		m_currentFrame = 0;
		m_currentAnimation = 0;
		m_isPlaying = false;
		m_isLooping = true;
		m_animationContainer = animationContainer;
	}
	
	void setIsLooping(boolean newState)
	{
		m_isLooping = newState;
	}
	
	boolean getIsLooping()
	{
		return m_isLooping;
	}
	
	TextureRegion getCurrentFrame()
	{
		if(m_isPlaying)
		m_currentFrame = (float) m_timer.getElapsedTime();
		
		return m_animationContainer.get(m_currentAnimation).getKeyFrame(m_currentFrame, m_isLooping);
	}
	
	int getCurrentAnimation()
	{
		return m_currentAnimation;
	}
	
	void changeAnimation(int animationIndex)
	{
		m_currentAnimation = animationIndex;
		if(m_currentAnimation < 0)
		{
			while(m_currentAnimation < 0)
			{
				m_currentAnimation = m_animationContainer.size()-1 - m_currentAnimation;
			}
		}
		else if(m_currentAnimation >= m_animationContainer.size())
		{
			while(m_currentAnimation >= m_animationContainer.size())
			{
				m_currentAnimation = m_currentAnimation - m_animationContainer.size();
			}
		}
	}
	
	void play()
	{
		m_isPlaying = true;
		m_timer.restart();
	}
	void play(boolean isLooping)
	{
		m_isLooping = isLooping;
		m_isPlaying = true;
		m_timer.restart();
	}
	void playAt(float animationSpeed)
	{
		m_isPlaying = true;
		m_timer.restart();
		for(Animation a : m_animationContainer)
		{
			a.setFrameDuration(animationSpeed);
		}
	}
	void playAt(boolean isLooping, float animationSpeed)
	{
		m_isLooping = isLooping;
		m_isPlaying = true;
		m_timer.restart();
		for(Animation a : m_animationContainer)
		{
			a.setFrameDuration(animationSpeed);
		}
	}
	
	void stop()
	{
		m_isPlaying = false;
		m_timer.restart();
	}
	void changeMode(Animation.PlayMode playMode)
	{
		for(Animation a : m_animationContainer)
		{
			a.setPlayMode(playMode);
		}
	}
	void changeSpeed(float animationSpeed)
	{
		for(Animation a : m_animationContainer)
		{
			a.setFrameDuration(animationSpeed);
		}
	}
		
	
}

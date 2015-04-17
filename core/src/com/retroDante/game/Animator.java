package com.retroDante.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Animator{
	
	private List<Animation> m_animationContainer = new ArrayList<Animation>();
	private HashMap<String, Integer> m_animationNames = new HashMap<String, Integer>();
	private Clock m_timer = new Clock();
	private float m_elapsedTime;
	private int m_currentAnimation;
	private float m_currentFrame;
	private boolean m_isPlaying;
	private boolean m_isLooping;
	
	public Animator(Animation animation)
	{
		m_currentFrame = 0;
		m_currentAnimation = 0;
		m_isPlaying = false;
		m_isLooping = true;
		m_animationContainer.add( animation );
		m_elapsedTime = 0;
	}
	public Animator(List<Animation> animationContainer)
	{
		m_currentFrame = 0;
		m_currentAnimation = 0;
		m_isPlaying = false;
		m_isLooping = true;
		m_animationContainer = animationContainer;
		m_elapsedTime = 0;
	}
	
	public Animator(List<Animation> animationContainer, String... names)
	{
		m_currentFrame = 0;
		m_currentAnimation = 0;
		m_isPlaying = false;
		m_isLooping = true;
		m_elapsedTime = 0;
		//m_animationContainer = animationContainer;
		int index = 0;
		for(Animation a : animationContainer)
		{
			if(index >=0 && index < names.length)
			this.addAnimation(a, names[index]);
			else
			this.addAnimation(a);
			index++;
		}
	}
	
	public void removeAllAnimations()
	{
		m_animationNames.clear();
		m_animationContainer.clear();
	}
	
	
	public void addAnimation(Animation newAnimation) 
	{
		m_animationContainer.add(newAnimation);
	}
	
	public void addAnimation(Animation newAnimation, String name)
	{
		m_animationContainer.add(newAnimation);
		int index = m_animationContainer.indexOf(newAnimation);
		m_animationNames.put(name, index);
		if(index == -1)
		{
			System.out.println("ERREUR : \ndans Animator _ addAnimation : animation mal indexée.");
		}
	}
	
	public void setIsLooping(boolean newState)
	{
		m_isLooping = newState;
	}
	
	public boolean getIsLooping()
	{
		return m_isLooping;
	}
	
	/**
	 * 
	 * Retourne la frame actuelle de l'animation. Utilise le timer automatique (ne peut pas être ralenti). 
	 * 
	 * @param delta
	 * @return
	 */
	public TextureRegion getCurrentFrame()
	{
		if(m_isPlaying)
			m_currentFrame = (float) m_timer.getElapsedTime();
		
		return m_animationContainer.get(m_currentAnimation).getKeyFrame(m_currentFrame, m_isLooping);
	}
	
	/**
	 * 
	 * Retourne la frame actuelle de l'animation. Utilise le timer non automatique (peut être ralenti). 
	 * 
	 * @param delta
	 * @return
	 */
	public TextureRegion getCurrentFrame(float delta)
	{
		m_elapsedTime += delta;
		
		if(m_isPlaying)
			m_currentFrame = m_elapsedTime;
		
		return m_animationContainer.get(m_currentAnimation).getKeyFrame(m_currentFrame, m_isLooping);
	}
	
	/**
	 * retourne true si l'animation s'est terminé une premiere fois
	 * 
	 * @return
	 */
	public boolean isAnimationFinished()
	{
			return m_animationContainer.get(m_currentAnimation).isAnimationFinished(m_currentFrame);
	}
	
	public int getCurrentAnimation()
	{
		return m_currentAnimation;
	}
	
	
	public void changeAnimation(String animationName)
	{
		if(m_animationNames.containsKey(animationName))
		{
			m_currentAnimation = m_animationNames.get(animationName);
		}
		else
		{
			System.out.println("ERROR : \n Animator _ changeAnimation : animation non trouvée pour le nom donné ("+animationName+")");
		}
	}
	
	public void changeAnimation(int animationIndex)
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
	
	public void play()
	{
		m_isPlaying = true;
		m_timer.restart();
		m_currentFrame = 0;
	}
	public void play(boolean isLooping)
	{
		m_isLooping = isLooping;
		m_isPlaying = true;
		m_timer.restart();
		m_currentFrame = 0;
	}
	public void playAt(float animationSpeed)
	{
		m_isPlaying = true;
		m_timer.restart();
		m_currentFrame = 0;
		for(Animation a : m_animationContainer)
		{
			a.setFrameDuration(animationSpeed);
		}
	}
	public void playAt(boolean isLooping, float animationSpeed)
	{
		m_isLooping = isLooping;
		m_isPlaying = true;
		m_timer.restart();
		m_currentFrame = 0;
		for(Animation a : m_animationContainer)
		{
			a.setFrameDuration(animationSpeed);
		}
	}
	
	public void stop()
	{
		m_isPlaying = false;
		m_timer.restart();
	}
	public void changeMode(Animation.PlayMode playMode)
	{
		for(Animation a : m_animationContainer)
		{
			a.setPlayMode(playMode);
		}
	}
	public void changeSpeed(float animationSpeed)
	{
		for(Animation a : m_animationContainer)
		{
			a.setFrameDuration(animationSpeed);
		}
	}
		
	
}

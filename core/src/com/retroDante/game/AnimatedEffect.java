package com.retroDante.game;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AnimatedEffect extends VisualEffect{
	
	private Rectangle m_bound;
	private Texture m_visual;
	protected TextureRegion m_texRegion;
	private Animator m_animator;
	private float m_animationSpeed; 
	
	
	AnimatedEffect(String tileSetName, int animationIndex)
	{
		super();
		
		m_bound = new Rectangle();
		m_visual = TileSetManager.getInstance().get(tileSetName).getTexture();
		
		m_animator = new Animator(TileSetManager.getInstance().get(tileSetName).getForAnimation(animationIndex), "effect"); 
		m_animator.changeAnimation(0);
		m_animator.play(m_isLooping);
		m_texRegion = m_animator.getCurrentFrame();
		
		m_texRegion = m_animator.getCurrentFrame();
		
	}
	
	/**
	 * Par defaut, le nom du tileset est cherché à "effects"
	 * 
	 * @param animationIndex
	 */
	AnimatedEffect(int animationIndex)
	{
		super();
		
		m_bound = new Rectangle();
		m_visual = TileSetManager.getInstance().get("effects").getTexture();
		
		m_animator = new Animator(TileSetManager.getInstance().get("effects").getForAnimation(animationIndex), "effect"); 
		m_animator.changeAnimation(0);
		m_animator.play(m_isLooping);
		m_texRegion = m_animator.getCurrentFrame();
		
		m_texRegion = m_animator.getCurrentFrame();
		
	}
	
	/**
	 * 
	 * Par defaut, le nom du tileset est effects, et il prend la premiere animation
	 *
	 */
	AnimatedEffect()
	{
		super();
		
		m_bound = new Rectangle();
		m_visual = TileSetManager.getInstance().get("effects").getTexture();
		
		m_animator = new Animator(TileSetManager.getInstance().get("effects").getForAnimation(0), "effect"); 
		m_animator.changeAnimation(0);
		m_animator.play(m_isLooping);
		m_texRegion = m_animator.getCurrentFrame();
		
		m_texRegion = m_animator.getCurrentFrame();
		
	}
	
	public void setAnimationSpeed(float newSpeed)
	{
		m_animationSpeed = newSpeed;
		m_animator.changeSpeed(m_animationSpeed);
	}
	
	public float getAnimationSpeed()
	{
		return m_animationSpeed;
	}

	@Override
	public void setPosition(Vector2 position) {
		
		m_bound.setPosition(position);
	}

	@Override
	public Vector2 getPosition() {
		return m_bound.getPosition(new Vector2(0,0));
	}

	@Override
	public void setDimension(Vector2 dimension) {
		m_bound.setSize(dimension.x, dimension.y);
	}

	@Override
	public void getDimension() {
		m_bound.getSize(new Vector2(0,0));
		
	}
	
	public void updateAnimation(float deltaTime)
	{
		m_texRegion = m_animator.getCurrentFrame();
	}

	@Override
	public void update(float deltaTime) {
		
		super.update(deltaTime);
		m_texRegion = m_animator.getCurrentFrame();
		if(m_animator.isAnimationFinished())
			m_isActive = false;
		
	}

	@Override
	public void play() {
		m_animator.play(m_isLooping);
	}

	@Override
	public java.util.Iterator<VisualEffect> iterator() {
		return new VisualEffectIterator(this);
	}
	
	@Override
	public void draw(SpriteBatch batch)
	{
		
		if(m_child != null)
			m_child.draw(batch);
		
		if(m_isActive)
		batch.draw( m_texRegion, m_bound.x, m_bound.y, m_bound.width, m_bound.height);
	}
	
	
	
}

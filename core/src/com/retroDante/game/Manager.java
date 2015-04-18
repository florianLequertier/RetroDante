package com.retroDante.game;

public abstract class Manager<T extends Body> {
	
	public abstract void add(T element, int index );
	
	public void add(T m_element)
	{
		add(m_element, 0 );
	}
	
	public abstract boolean remove(T element, int index );
	
	public boolean remove(T element)
	{
		return remove(element, 0 );
	}
	
	public abstract void save(String filePath);
}

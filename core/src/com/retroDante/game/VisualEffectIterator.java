package com.retroDante.game;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class VisualEffectIterator implements Iterator<VisualEffect> {
	
	VisualEffect m_parent = null;
	VisualEffect m_current = null;
	
	VisualEffectIterator(VisualEffect first)
	{
		m_current = first;
	}
	
	@Override
	public boolean hasNext() {
		return m_current.hasChild();
	}

	@Override
	public VisualEffect next() {
		
		if(hasNext())
		{
			m_parent = m_current;
			m_current = m_current.getChild();
			
			return m_parent;
		}
		else{
			throw new NoSuchElementException("Il n'y a plus d'element dans la liste");
		}

	}
	
	@Override
	public void remove()
	{
		if(m_parent != null)
		{
			m_parent.setChild(m_current.getChild()); //On brise la chaine en enlevant le current
		}
		else
		{
            throw new IllegalStateException("You can't delete element before first next() method call");
        }
	}

}

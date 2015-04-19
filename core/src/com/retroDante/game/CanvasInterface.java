package com.retroDante.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.retroDante.game.trigger.Trigger;


/**
 * 
 * Un Canvas est un bouton stockant un objet. Il permet de placer des elements dans l'editor.
 * On peut detecter la souris grace aux propriétés du bouton et le draw dessine l'element stocké dans le canvas. 
 * La methode save sauvegarde l'objet stocké dans le canvas. 
 * 
 * @author florian
 *
 * @param <T>
 */
public interface CanvasInterface {
	
	public void setPosition(Vector2 newPos); //bouge le bouton
	public Vector2 getPosition();
	public void draw(Batch batch, boolean onlyButton); //dessine le composant du canvas, puis le bouton par dessus
	public <T extends Body> void attachOn(Manager<T> manager);
//	public <T extends Body> void attachOn(Manager<T> manager, int index);
	public String getType();
	public void setType(String type);
	public void setLayout(int layout);
	public int getLayout();
	public void setRemainActions(int remainActions);
	public int getRemainActions();
	public void setMaxActions(int remainActions);
	public int getMaxActions();
	public void resizeAction(Vector2 position, boolean decreaseAction);
	public void additionnalAction(boolean decreaseAction);
	public boolean applyDropStrategy(Vector2 worldPosition);
	public void updateDropStrategy(Vector2 worldPosition);
	public Trigger getCollider();
	public <T extends Body> void removeOn(Manager<T> manager);

}

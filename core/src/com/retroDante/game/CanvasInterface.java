package com.retroDante.game;


/**
 * 
 * Un Canvas est un bouton stockant un objet. Il permet de placer des elements dans l'editor.
 * On peut detecter la souris grace aux propri�t�s du bouton et le draw dessine l'element stock� dans le canvas. 
 * La methode save sauvegarde l'objet stock� dans le canvas. 
 * 
 * @author florian
 *
 * @param <T>
 */
public interface CanvasInterface {
	
	void save(); // sauvegarde le composant du canvas
	void move(); //bouge le bouton
	void draw(); //dessine le composant du canvas, puis le bouton par dessus
	
}

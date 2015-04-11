package com.retroDante.game;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.MouseButton;


/**
 * 
 * Classe abstraite repr�sentant un controlleur : c�d un objet cappable de transposer les entr�es clavier/souris en actions
 * les actions sont repr�sent�es par des strings les nommants.
 *  Si une action est en cours d'execution, le booleen associ� � l'action dans actionMap est mis � true
 * 
 * 
 * @author Alexflo
 *
 */
public abstract class Controller {

	Map<Integer, String> mappedKey = new HashMap<Integer, String>(); // associe � chaque key, un evenement
	Map<Integer, String> mappedButton = new HashMap<Integer, String>(); // associe � chaque button, un evenement
	Map<String, Boolean> mappedEvent = new HashMap<String, Boolean>(); //associe � un evenement un booleen pour savoir si l'eveneement est en cours
	
	/**
	 * 
	 * permet de checker si une action a �t� detect� par le controller
	 * 
	 * @param actionName : nom de l'action � checker
	 * @return
	 */
	abstract boolean checkAction(String actionName);
	
	/**
	 * 
	 * permet de checker si une action a �t� detect� par le controller.
	 * Remet enssuite cette action � false
	 * 
	 * @param actionName : nom de l'action � checker
	 * @return
	 */
	abstract boolean checkActionOnce(String actionName);
	
	
//	/**
//	 * 
//	 * ecoute l'evennement de type key down dans le screen
//	 * 
//	 * @param keycode
//	 */
//	abstract void listenKeyDown(int keycode);
//	
//	/**
//	 * 
//	 * ecoute l'evennement de type key up dans le screen
//	 * 
//	 * @param keycode
//	 */
//	abstract void listenKeyUp(int keycode);
//	
//	/**
//	 * 
//	 * ecoute l'evennement de type button up dans le screen
//	 * 
//	 * @param keycode
//	 */
//	abstract void listenButtonUp(MouseButton buttoncode);
//	
//	
//	/**
//	 * 
//	 * ecoute l'evennement de type button down dans le screen
//	 * 
//	 * @param keycode
//	 */
//	abstract void listenButtonDown(MouseButton buttoncode);
	

}

package com.retroDante.game;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.input.MouseButton;


/**
 * 
 * Classe abstraite représentant un controlleur : càd un objet cappable de transposer les entrées clavier/souris en actions
 * les actions sont représentées par des strings les nommants.
 *  Si une action est en cours d'execution, le booleen associé à l'action dans actionMap est mis à true
 * 
 * 
 * @author Alexflo
 *
 */
public abstract class Controller {

	Map<Integer, String> mappedKey = new HashMap<Integer, String>(); // associe à chaque key, un evenement
	Map<Integer, String> mappedButton = new HashMap<Integer, String>(); // associe à chaque button, un evenement
	Map<String, Boolean> mappedEvent = new HashMap<String, Boolean>(); //associe à un evenement un booleen pour savoir si l'eveneement est en cours
	
	/**
	 * 
	 * permet de checker si une action a été detecté par le controller
	 * 
	 * @param actionName : nom de l'action à checker
	 * @return
	 */
	abstract boolean checkAction(String actionName);
	
	/**
	 * 
	 * permet de checker si une action a été detecté par le controller.
	 * Remet enssuite cette action à false
	 * 
	 * @param actionName : nom de l'action à checker
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

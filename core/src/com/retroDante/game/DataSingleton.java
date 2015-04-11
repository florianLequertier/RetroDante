package com.retroDante.game;

/**
 * 
 * DataSingleton design l'interface pour un objet qui est un singleton et qui stock des elements. 
 * Permet de ne pas oublier de surcharger la m�thode clear() pour vider le singleton de ses informations.
 * 
 * @author florian
 *
 * @param <type>
 */
public interface DataSingleton<type> {

	public void clear(); // la m�thode clear permet de vider le singleton de ses informations, vu que celuici ne se desalloue pas automatiquement.
	
}

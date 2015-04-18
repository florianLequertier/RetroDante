package com.retroDante.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.retroDante.game.character.EnemyFactory;
import com.retroDante.game.map.MapFactory;
import com.retroDante.game.trigger.TriggerFactory;

public class EditorPicker extends Table {

	private Skin m_skin;
	private ButtonGroup<TextButton> m_typeGroup;
	private HashMap<String, ButtonGroup<TextButton> > m_elementsGroup;
	private ButtonGroup<TextButton> m_layoutGroup;
	private Table m_layoutPanel;
	private Table m_typePanel;
	private HashMap<String, Table> m_elementsPanel;
	private List<String> m_typeNames; //liste regroupant les noms des types d'elements modifiable dans l'editeur
	private List<String> m_elementNames; //liste contenant le nom de tous les elements, dans l'ordre d'appel
	private List<Integer> m_typeCount; //nombre d'element de chaque type. 
	private int m_numberOfNames; // nombre de type 
	private int m_numberOfElements; // nombre total d'elements 
	private int m_currentTypeIndex; //index du type utilisé
	private int m_layoutIndex; //index du layout utilisé
	private String m_currentTypeName; //nom du type utilisé
	private MouseEditor m_editorMouse; 
	private HashMap<String, Factory<? extends Body> > m_factories; // toutes les factories permettant d'instancier les objets du jeu
	
	EditorPicker()
	{
		//table : 
		super();
		//skin : 
		//setDefaultSkin();
		
		//placeTypeGroup();
		//placeElementGroup();
		
		m_typeNames = new ArrayList<String>();
		m_typePanel = new Table();
			m_typeGroup = new ButtonGroup<TextButton>();
		m_elementsPanel = new HashMap<String, Table>();
			m_elementsGroup = new HashMap<String, ButtonGroup<TextButton> >();
		m_layoutPanel = new Table();
			m_layoutGroup = new ButtonGroup<TextButton>();
		
		m_typeNames = new ArrayList<String>();
		m_typeCount = new ArrayList<Integer>();
		m_elementNames = new ArrayList<String>();
		
		m_factories = new HashMap<String, Factory<? extends Body> >();
		
		initAll();
		
		
		this.setFillParent(false);
		this.setPosition(280,0);
		this.setSize(350,500);
		this.align(Align.center);

		//autorise le débug : 
		this.debug();
		m_typePanel.debug();
		for(Entry<String,Table> t : m_elementsPanel.entrySet())
		t.getValue().debug();
		
		
	}
	
	EditorPicker(float x, float y)
	{
		this();
		this.setPosition(x, y);
	}
	
	/**
	 * Réalise toutes les initialisations dans le bon ordre. 
	 */
	void initAll()
	{
		initTypeNames("enemy", "map", "trigger");
		initTypeCount(8,7,5);
		initElementNames("enemy01", "enemy02", "enemy03", "enemy04", "enemy05", "enemy06", "enemy07", "enemy08", "platform01", "platform02",  "platform03", "platform04",  "platform05", "platform06",  "platform07", "damageTrigger", "blocTrigger", "killTrigger", "bumpTrigger", "teleportTrigger"  );
		initFactories();
		
		setDefaultSkin();
		
		initTypePanel();
			this.add(m_typePanel);
			this.row();
		initLayoutPanel(0, 0);
			this.add(m_layoutPanel);
			this.row();
		initElementsPanel();
			m_currentTypeIndex = 0;
			m_currentTypeName = m_typeNames.get(0);
			this.add(m_elementsPanel.get(m_typeNames.get(0)));
	}
	
	/**
	 * Initialise le tableau contenant les noms de toute les types d'objets instanciables dans l'editeur
	 * 
	 * @param names
	 */
	void initTypeNames(String... names)
	{
		m_typeNames.clear();
		
		for(int i = 0; i < names.length; i++)
		{
			m_typeNames.add(names[i]);
		}
		
		m_numberOfNames = m_typeNames.size();
	}
	
	/**
	 * Initialise le tableau contenant les noms de toute les d'objets instanciables dans l'editeur
	 * 
	 * @param names
	 */
	void initElementNames(String... names)
	{
		m_elementNames.clear();
		
		for(int i = 0; i < names.length; i++)
		{
			m_elementNames.add(names[i]);
		}
		
		
		if(names.length != m_numberOfElements)
		{
			System.out.println("ERROR : EditorPickup : initElementNames : le nombre d'elements passé à la fonction ne correspond pas au nombre d'elements à chercher dans le tileSet");
		}
		
	}
	
	/**
	 * Initialise le tableau contenant le compte des elements par type. 
	 * Attention : Doit être appelé aprés initTypeNames()
	 * 
	 * @param countByType
	 */
	void initTypeCount(int... countByType)
	{
		m_typeCount.clear();
		m_numberOfElements = 0;
		
		for(int i = 0; i < countByType.length; i++)
		{
			m_typeCount.add(countByType[i]);
			m_numberOfElements += countByType[i];
		}
		
		//On verifie qu'on a bien passé un nombre de parametre égale à celui passé dans initTypeName
		if(m_numberOfNames != m_typeCount.size())
		{
			System.out.println("ERROR : EditorPicker : initTypeCount() : initTypeCount appelé avant initTypeNames, ou les nombre d'arguments passé aux deux fonction n'est pas le même");  
		}
		
	}
	
	/**
	 * initialise toutes les factories 
	 */
	void initFactories()
	{
		for(int i = 0; i < m_typeNames.size(); i++)
		{
			if(m_typeNames.get(i).equals("enemy"))
			{
				m_factories.put("enemy", EnemyFactory.getInstance());
			}
			else if(m_typeNames.get(i).equals("map"))
			{
				m_factories.put("map", MapFactory.getInstance());
			}
			else if(m_typeNames.get(i).equals("trigger"))
			{
				m_factories.put("trigger", TriggerFactory.getInstance());
			}
		}
	}
	
	void setMouse(MouseEditor mouse)
	{
		m_editorMouse = mouse;
	}
	
	void changeType(String name)
	{
		
		if(!m_elementsPanel.containsKey(name))
			return;
		
		if(m_elementsPanel.containsKey(m_currentTypeName))
		{
			
			Table tmpTable = m_elementsPanel.get(m_currentTypeName);
			tmpTable.clearActions();
			if(this.removeActor(tmpTable))
			{
				System.out.println("table removed ! ");
			}
		}
		
		m_currentTypeIndex = m_typeNames.indexOf(name);
		m_currentTypeName = name;
		
		this.add(m_elementsPanel.get(name));
	}
	
	void changeLayout(int layoutIndex)
	{
		m_layoutIndex = layoutIndex;
	}


	/**
	 * Initialise le panneau d'interface contenant le choix des types d'objets à créer 
	 */
	void initTypePanel()
	{
		//initialisation du buttonGroup : 
		m_typeGroup.setMaxCheckCount(1);
		
		m_typePanel.defaults().space(5).maxSize(90, 70).minSize(80, 60);
		
		int index = 0;
		for(String name : m_typeNames)
		{
			//création
			final TextButton button = new TextButton(name, m_skin, "type"+name);
			//bind des events 
			final String nameSnap = name;
			button.addListener(new InputListener() {
				
				@Override
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					//System.out.println("Clicked! Is checked: " + button.isChecked());
					//button.setText("Good job!");
					
					changeType(nameSnap);
					
					if(nameSnap == "map")
						initLayoutPanel(-3, 3);
					else
						initLayoutPanel(0, 0);
					
					
					return false;
				}

			});
			//attache aux containers : 
			m_typePanel.add(button);
				m_typeGroup.add(button);
			
			index++;
		}
		
		//this.add(m_typePanel);
	}
	
	/**
	 * Initialise les boutons des layouts
	 * les layout vont de min (inclu) à max (inclu)
	 */
	void initLayoutPanel(int min, int max)
	{
		m_layoutPanel.clear();
		m_layoutGroup.clear();
		
		for(int i= min ; i<=max; i++) // 7 layouts au maximum
		{
			//création
			final TextButton button = new TextButton("layout \n"+i, m_skin); //boutons de type default
			
			final int layoutIndex = i;
			button.addListener(new InputListener() {
				
				@Override
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					//System.out.println("Clicked! Is checked: " + button.isChecked());
					//button.setText("Good job!");
					
					changeLayout(layoutIndex);
					return false;
				}

			});
			
			m_layoutPanel.add(button);
				m_layoutGroup.add(button);
		}
		
		m_layoutGroup.setChecked("layout \n0");
		m_layoutIndex = 0;
		//this.add(m_layoutPanel);
	}
	
	void initElementsPanel()
	{
		//type picker : 
		m_typeGroup = new ButtonGroup<TextButton>();
		m_typeGroup.setMaxCheckCount(1);

		
		for(int typeIndex=0, elementNumber = 0; typeIndex< m_typeNames.size(); typeIndex++)
		{
			String typeName = m_typeNames.get(typeIndex);
			
			Table tmpTable = new Table();
			ButtonGroup<TextButton> tmpButtonGrp = new ButtonGroup<TextButton>();
			
			for(int j=0, k=0; j<6; j++)
			{
				for(int i=0; i<4; i++, k++)
				{
					
					if(k < m_typeCount.get(typeIndex)) // le bouton match avec un element, on initialise le bouton 
					{
						String buttonName = typeName+Integer.toString(k);
						final TextButton button = new TextButton(buttonName, m_skin, buttonName);
						
						tmpTable.add(button).space(5).maxSize(90, 70).minSize(80, 60);
							tmpButtonGrp.add(button);
						
							
							final String finalTypeName = typeName ;
							final String finalElementName = m_elementNames.get(elementNumber) ;
							button.addListener(new /*ChangeListener()*/ InputListener() {
							@Override
							//public void changed (ChangeEvent event, Actor actor) 
							public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
								
								if(m_factories.containsKey(finalTypeName))
								{
									if(finalTypeName == "trigger")
										m_editorMouse.changePlaceable( m_factories.get(finalTypeName).create( finalElementName ), finalTypeName, m_layoutIndex, 3 );
									else
										m_editorMouse.changePlaceable( m_factories.get(finalTypeName).create( finalElementName ), finalTypeName, m_layoutIndex, 1 );
								}
								else
								{
									System.out.println("ERROR : EditorPicker : initElementsPanel : Aucune factory trouvée pour la clef : \""+finalTypeName);
								}
		
								
//								if(finalTypeName.equals("enemy"))
//								{
//									m_editorMouse.changePlaceable(EnemyFactory.getInstance().create( finalElementName ));
//								}
//								else if(finalTypeName.equals("map"))
//								{
//									m_editorMouse.changePlaceable(MapFactory.getInstance().create( finalElementName ));
//								}
//								else if(finalTypeName.equals("trigger"))
//								{
//									m_editorMouse.changePlaceable(TriggerFactory.getInstance().create( finalElementName ));
//								}
								
								return false;
							}

						});
							
						elementNumber++;
					}
					else //bouton "vide" (trop de boutons affichés par rapport au nombre d'objets)
					{

						final TextButton button = new TextButton("", m_skin);
						
						tmpTable.add(button).space(5).maxSize(90, 70).minSize(80, 60);
							tmpButtonGrp.add(button);
						 //pas de listener
					}
	
					
				}
				tmpTable.row();
			}
			
			//ajoute à la map de table et à la map de buttonGroupe la table liée au nom "name"
			m_elementsPanel.put(typeName, tmpTable);
				m_elementsGroup.put(typeName, tmpButtonGrp);
		}
		
//		//ajoute la premiere table de la map à this
//		m_currentTypeIndex = 0;
//		m_currentTypeName = m_typeNames.get(0);
//		this.add(m_elementsPanel.get(m_typeNames.get(0)));
	}
	
	
	
	
	void setDefaultSkin()
	{
		m_skin = new Skin();
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		m_skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		m_skin.add("default", new BitmapFont());
		
		
		//créé tous les styles des boutons :
		

		TileSetInfo buttonsTileSet = TileSetManager.getInstance().get("editorButton");
		TileSetIterator it = (TileSetIterator) buttonsTileSet.iterator();
		
		//boutons de types : 
		for(int index=0; index<m_typeNames.size(); index++)
		{
			if(it.hasNext())
			{
				TextureRegion tex = it.next();
				String typeName = "type"+m_typeNames.get(index);
				m_skin.add( typeName , tex);
				
				TextButtonStyle textButtonStyle = new TextButtonStyle();
				textButtonStyle.up = m_skin.getDrawable(typeName);
				textButtonStyle.down = m_skin.newDrawable(typeName, Color.DARK_GRAY);
				textButtonStyle.checked = m_skin.newDrawable(typeName, Color.BLUE);
				textButtonStyle.over = m_skin.newDrawable(typeName, Color.LIGHT_GRAY);
				textButtonStyle.font = m_skin.getFont("default");
				m_skin.add(typeName, textButtonStyle);
			}
			else
			{
				System.out.println("ERROR : EditorPicker : SetDefaultSkin() : Le nombre de visuels dans le tileSet ne correspond pas au nombre de boutons.");
			}
		}
		
		//boutons des elements : 
		for(int index=0; index<m_typeNames.size(); index++)
		{
			
			for(int i = 0; i< m_typeCount.get(index); i++)
			{
				if(it.hasNext())
				{
					TextureRegion tex = it.next();
					String elementName = m_typeNames.get(index)+Integer.toString(i);
					m_skin.add( elementName , tex);
					
					TextButtonStyle textButtonStyle = new TextButtonStyle();
					textButtonStyle.up = m_skin.getDrawable(elementName);
					textButtonStyle.down = m_skin.newDrawable(elementName, Color.DARK_GRAY);
					textButtonStyle.checked = m_skin.newDrawable(elementName, Color.BLUE);
					textButtonStyle.over = m_skin.newDrawable(elementName, Color.LIGHT_GRAY);
					textButtonStyle.font = m_skin.getFont("default");
					m_skin.add(elementName, textButtonStyle);

				}
				else
				{
					System.out.println("ERROR : EditorPicker : SetDefaultSkin() : Le nombre de visuels dans le tileSet ne correspond pas au nombre de boutons.");
				}
			}
		}


		
		//boutons par defaut : 
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = m_skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = m_skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = m_skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = m_skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = m_skin.getFont("default");
		m_skin.add("default", textButtonStyle);
		
		

	}
	
	
}

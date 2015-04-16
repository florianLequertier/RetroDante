package com.retroDante.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;

public class UITestScreen implements Screen {
	Stage stage;

	@Override
	public void show()
	{
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		FileHandle file = Gdx.files.internal("uiskin.json");
		if(file == null)
			System.out.println("foqsfqjsfqsf");
		
		Skin skin = new Skin(file);
		TextureRegion image2 = new TextureRegion(new Texture(Gdx.files.internal("badlogic.jpg")));
		

		Label nameLabel = new Label("Name:", skin);
		TextField nameText = new TextField("", skin);
		Label addressLabel = new Label("Address:", skin);
		TextField addressText = new TextField("", skin);

		Table table = new Table();
		stage.addActor(table);
		table.setSize(260, 195);
		table.setPosition(190, 142);
		// table.align(Align.right | Align.bottom);

		table.debug();

		TextureRegion upRegion = skin.getRegion("default-slider-knob");
		TextureRegion downRegion = skin.getRegion("default-slider-knob");
		BitmapFont buttonFont = skin.getFont("default-font");

		TextButton button = new TextButton("Button 1", skin);
		button.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("touchDown 1");
				return false;
			}
		});
		table.add(button);
		// table.setTouchable(Touchable.disabled);
		
		/*
		Image image = new Image(image2);
		image.setScaling(Scaling.fill);
		table.add(image).width(image2.getRegionWidth()).height(image2.getRegionHeight());


		Table table2 = new Table();
		stage.addActor(table2);
		table2.setFillParent(true);
		table2.bottom();

		TextButton button2 = new TextButton("Button 2", skin);
		button2.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("2!");
			}

		});
		button2.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("touchDown 2");
				return false;
			}
		});
		table2.add(button2);
		
		*/

		table.debug();
		table.add(new Label("This is regular text.", skin));
		table.row();
		table.add(new Label("This is regular text\nwith a newline.", skin));
		table.row();
		Label label3 = new Label("This is [RED]regular text\n\nwith newlines,\naligned bottom, right.", skin);
		label3.setColor(Color.GREEN);
		label3.setAlignment(Align.bottom | Align.right);
		table.add(label3).minWidth(200 ).minHeight(110 ).fill();
		table.row();
		Label label4 = new Label("This is regular text with NO newlines, wrap enabled and aligned bottom, right.", skin);
		label4.setWrap(true);
		label4.setAlignment(Align.bottom | Align.right);
		table.add(label4).minWidth(200 ).minHeight(110 ).fill();
		table.row();
		Label label5 = new Label("This is regular text with\n\nnewlines, wrap\nenabled and aligned bottom, right.", skin);
		label5.setWrap(true);
		label5.setAlignment(Align.bottom | Align.right);
		table.add(label5).minWidth(200 ).minHeight(110 ).fill();

		table.pack();
	}
	
	@Override
	public void render (float delta)
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}
	
	@Override
	public void resize (int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public void dispose () {
		stage.dispose();
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}

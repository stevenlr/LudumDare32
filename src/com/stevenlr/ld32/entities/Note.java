package com.stevenlr.ld32.entities;

import com.stevenlr.ld32.Sprites;
import com.stevenlr.ld32.components.NoteComponent;
import com.stevenlr.ld32.components.StaticComponent;
import com.stevenlr.ld32.components.StaticTextureRenderComponent;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class Note extends Entity {

	private NoteComponent _note;

	public Note(int id, float x, float y) {
		_note = new NoteComponent(id);
		StaticTextureRenderComponent render = new StaticTextureRenderComponent(Sprites.Inventory.note);

		addComponent(NoteComponent.class, _note);
		addComponent(StaticTextureRenderComponent.class, render);
		addComponent(StaticComponent.class, new StaticComponent(x, y));
	}
}

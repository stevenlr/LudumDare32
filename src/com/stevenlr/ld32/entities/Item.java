package com.stevenlr.ld32.entities;

import com.stevenlr.ld32.components.ItemComponent;
import com.stevenlr.ld32.components.StaticComponent;
import com.stevenlr.ld32.components.StaticTextureRenderComponent;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class Item extends Entity {

	private ItemComponent _item;

	public Item(int item, float x, float y) {
		_item = new ItemComponent(item);
		StaticTextureRenderComponent render = new StaticTextureRenderComponent(Player.getItemIcon(item));

		addComponent(ItemComponent.class, _item);
		addComponent(StaticTextureRenderComponent.class, render);
		addComponent(StaticComponent.class, new StaticComponent(x, y));
	}
}

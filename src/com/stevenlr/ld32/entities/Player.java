package com.stevenlr.ld32.entities;

import com.stevenlr.ld32.Sprites;
import com.stevenlr.ld32.components.CollisionComponent;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.components.PlayerComponent;
import com.stevenlr.ld32.level.Tile;
import com.stevenlr.waffle.entitysystem.entities.Entity;
import com.stevenlr.waffle.graphics.Renderer;

public class Player extends Entity {

	public static final int SX = 12;
	public static final int SY = 30;

	public Player(float x, float y) {
		PhysicalComponent physical = new PhysicalComponent(x, y - SY + Tile.SIZE, 0, 0);
		CollisionComponent collision = new CollisionComponent(SX, SY);
		PlayerComponent player = new PlayerComponent();

		addComponent(PhysicalComponent.class, physical);
		addComponent(CollisionComponent.class, collision);
		addComponent(PlayerComponent.class, player);
	}

	public void draw(Renderer r) {
		PhysicalComponent phys = getComponent(PhysicalComponent.class);

		r.beginBlit(Sprites.player, phys.x, phys.y).blit();
	}
}

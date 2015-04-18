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

	private PhysicalComponent _phys;

	public Player(float x, float y) {
		_phys = new PhysicalComponent(x, y - SY + Tile.SIZE, 0, 0);
		CollisionComponent collision = new CollisionComponent(SX, SY);
		PlayerComponent player = new PlayerComponent();

		addComponent(PhysicalComponent.class, _phys);
		addComponent(CollisionComponent.class, collision);
		addComponent(PlayerComponent.class, player);
	}

	public void draw(Renderer r) {
		r.beginBlit(Sprites.player, _phys.x, _phys.y).blit();
	}

	public float getX() {
		return _phys.x;
	}

	public float getY() {
		return _phys.y;
	}
}

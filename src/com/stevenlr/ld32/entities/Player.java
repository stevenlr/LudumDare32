package com.stevenlr.ld32.entities;

import com.stevenlr.ld32.Sounds;
import com.stevenlr.ld32.Sprites;
import com.stevenlr.ld32.components.CollisionComponent;
import com.stevenlr.ld32.components.KillableComponent;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.components.PlayerComponent;
import com.stevenlr.ld32.level.Tile;
import com.stevenlr.waffle.entitysystem.entities.Entity;
import com.stevenlr.waffle.graphics.IBlittable;
import com.stevenlr.waffle.graphics.Renderer;

public class Player extends Entity {

	public static final int SX = 12;
	public static final int SY = 30;

	public static final int BLUE_DEVICE = 0;
	public static final int ORANGE_DEVICE = 1;
	public static final int KNIFE = 2;
	public static final int CHEMICALS = 3;

	private PhysicalComponent _phys;
	private PlayerComponent _player;

	public Player(float x, float y) {
		this(x, y, null);
	}

	public Player(float x, float y, boolean[] startingInventory) {
		_phys = new PhysicalComponent(x, y - SY + Tile.SIZE, 0, 0);
		CollisionComponent collision = new CollisionComponent(SX, SY);
		_player = new PlayerComponent(startingInventory);

		addComponent(PhysicalComponent.class, _phys);
		addComponent(CollisionComponent.class, collision);
		addComponent(PlayerComponent.class, _player);
		addComponent(KillableComponent.class, new KillableComponent());
	}

	public void draw(Renderer r) {
		if (_player.isDead) {
			return;
		}

		if (_player.lastDirection >= 0) {
			r.beginBlit(_player.sprite, _phys.x, _phys.y).blit();
		} else {
			r.beginBlit(_player.sprite, _phys.x, _phys.y).mirrorX().blit();
		}
	}

	public float getX() {
		return _phys.x;
	}

	public float getY() {
		return _phys.y;
	}

	public static IBlittable getItemIcon(int i) {
		switch (i) {
		case BLUE_DEVICE:
			return Sprites.Inventory.blueDevice;
		case ORANGE_DEVICE:
			return Sprites.Inventory.orangeDevice;
		case KNIFE:
			return Sprites.Inventory.knife;
		case CHEMICALS:
			return Sprites.Inventory.chemicals;
		default:
			return Sprites.Inventory.unused;
		}
	}

	public static String getItemName(int i) {
		switch (i) {
		case BLUE_DEVICE:
			return "the attractor";
		case ORANGE_DEVICE:
			return "the repeller";
		case KNIFE:
			return "a knife";
		case CHEMICALS:
			return "some HK-21";
		default:
			return "";
		}
	}

	public void drawInventory(Renderer r) {
		for (int i = 0; i < 4; ++i) {
			if (i == _player.selected) {
				r.beginBlit(Sprites.Inventory.used, 47 * i, 0).blit();
			} else {
				r.beginBlit(Sprites.Inventory.unused, 47 * i, 0).blit();
			}

			if (_player.inventory[i]) {
				r.beginBlit(getItemIcon(i), 47 * i, 0).blit();
			}
		}
	}

	public boolean isDead() {
		return _player.isDead;
	}

	public void die() {
		_player.isDead = true;
		Sounds.die.play();
	}
}

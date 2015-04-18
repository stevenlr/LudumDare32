package com.stevenlr.ld32.components;

import com.stevenlr.ld32.level.Tile;
import com.stevenlr.waffle.entitysystem.components.Component;

public class CollisionComponent extends Component {

	public float sx = Tile.SIZE;
	public float sy = Tile.SIZE;
	public boolean collidesWithPlayer = true;

	public CollisionComponent() {
	}

	public CollisionComponent(float sx, float sy, boolean collidesWithPlayer) {
		this.sx = sx;
		this.sy = sy;
		this.collidesWithPlayer = collidesWithPlayer;
	}

	public CollisionComponent(float sx, float sy) {
		this.sx = sx;
		this.sy = sy;
	}
}

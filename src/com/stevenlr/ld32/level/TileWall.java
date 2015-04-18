package com.stevenlr.ld32.level;

import com.stevenlr.ld32.Sprites;
import com.stevenlr.waffle.graphics.IBlittable;
import com.stevenlr.waffle.graphics.Renderer;

public class TileWall extends Tile {

	private IBlittable _texture;
	private int _id;
	private boolean _hasCollision;

	public TileWall(int id) {
		super(id, false, null);
	}

	@Override
	public void draw(Renderer r, float x, float y) {
		int id = ((int) (x / Tile.SIZE) * 18731 + (int) (y / Tile.SIZE) * 7617) % 461;
		IBlittable texture = Sprites.tiles.getRegion(0);

		if ((id) % 91 < 4) {
			texture = Sprites.tiles.getRegion(id % 4, 1);
		}

		r.beginBlit(texture, x, y).blit();
	}
}


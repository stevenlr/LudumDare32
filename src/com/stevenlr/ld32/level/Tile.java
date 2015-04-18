package com.stevenlr.ld32.level;

import com.stevenlr.ld32.Sprites;
import com.stevenlr.waffle.graphics.IBlittable;
import com.stevenlr.waffle.graphics.Renderer;

public class Tile {

	public static final int SIZE = 16;

	public static Tile empty = new Tile(0, false, Sprites.tiles.getRegion(0));
	public static Tile wall = new Tile(1, true, Sprites.tiles.getRegion(1));

	private IBlittable _texture;
	private int _id;
	private boolean _hasCollision;

	public Tile(int id, boolean hasCollision, IBlittable texture) {
		_hasCollision = hasCollision;
		_texture = texture;
		_id = id;
	}

	public int getId() {
		return _id;
	}

	public void draw(Renderer r, float x, float y) {
		r.beginBlit(_texture, x, y).blit();
	}

	public boolean hasCollision() {
		return _hasCollision;
	}
}

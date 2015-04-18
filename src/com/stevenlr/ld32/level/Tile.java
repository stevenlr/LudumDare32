package com.stevenlr.ld32.level;

import com.stevenlr.ld32.Sprites;
import com.stevenlr.waffle.graphics.IBlittable;
import com.stevenlr.waffle.graphics.Renderer;

public class Tile {

	public static final int SIZE = 16;

	public static Tile empty = new Tile(0, Sprites.tiles.getRegion(0));
	public static Tile wall = new Tile(1, Sprites.tiles.getRegion(1));

	private IBlittable _texture;
	private int _id;

	public Tile(int id, IBlittable texture) {
		_texture = texture;
		_id = id;
	}

	public int getId() {
		return _id;
	}

	public void draw(Renderer r, float x, float y) {
		r.beginBlit(_texture, x, y).blit();
	}
}

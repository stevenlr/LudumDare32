package com.stevenlr.ld32.level;

import com.stevenlr.ld32.Sprites;
import com.stevenlr.waffle.graphics.IBlittable;
import com.stevenlr.waffle.graphics.Renderer;

public class Tile {

	public static final int SIZE = 16;

	public static Tile empty = new TileWall(0);
	public static Tile wall = new Tile(1, true, Sprites.tiles.getRegion(1));
	public static Tile ground = new Tile(2, true, Sprites.tiles.getRegion(3));
	public static Tile doorTop = new Tile(3, false, Sprites.tiles.getRegion(0, 2));
	public static Tile doorBottom = new Tile(4, false, Sprites.tiles.getRegion(0, 3));

	protected IBlittable _texture;
	protected int _id;
	protected boolean _hasCollision;

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

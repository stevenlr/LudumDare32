package com.stevenlr.ld32.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.components.AnimatedSpriteRenderComponent;
import com.stevenlr.ld32.components.CollisionComponent;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.entities.MetalCrate;
import com.stevenlr.ld32.entities.Player;
import com.stevenlr.ld32.systems.AnimatedSpriteRenderSystem;
import com.stevenlr.ld32.systems.MagneticMovementSystem;
import com.stevenlr.ld32.systems.PhysicalMovementSystem;
import com.stevenlr.ld32.systems.PlayerControlSystem;
import com.stevenlr.ld32.systems.StaticTextureRenderSystem;
import com.stevenlr.waffle.graphics.Renderer;

public class Level {

	private Tile[] _tiles;
	private int _width;
	private int _height;
	private int _playerSpawnX;
	private int _playerSpawnY;
	private Player _player;
	private int _offsetX;
	private int _offsetY;

	private PhysicalMovementSystem _physicalMovementSystem = new PhysicalMovementSystem();
	private PlayerControlSystem _playerControlSystem = new PlayerControlSystem();
	private StaticTextureRenderSystem _staticTextureRenderSystem = new StaticTextureRenderSystem();
	private AnimatedSpriteRenderSystem _animatedSpriteRenderSystem = new AnimatedSpriteRenderSystem();
	private MagneticMovementSystem _magneticMovementSystem = new MagneticMovementSystem();

	public Level(String filename) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(Game.class.getResourceAsStream(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		_width = img.getWidth();
		_height = img.getHeight();
		_tiles = new Tile[_width * _height];

		for (int y = 0; y < _height; ++y) {
			for (int x = 0; x < _width; ++x) {
				int i = y * _width + x;
				Tile tile = Tile.wall;

				switch (img.getRGB(x, y) & 0xffffff) {
				case 0x00ff00:
					_playerSpawnX = x;
					_playerSpawnY = y;
					tile = Tile.empty;
					break;
				case 0xffffff:
					tile = Tile.empty;
					break;
				case 0x000000:
					tile = Tile.wall;
					break;
				case 0xff8000:
					tile = Tile.empty;
					new MetalCrate(x * Tile.SIZE, y * Tile.SIZE);
					break;
				}

				_tiles[i] = tile;
			}
		}

		_player = new Player(_playerSpawnX * Tile.SIZE, _playerSpawnY * Tile.SIZE);
	}

	public void update(float dt) {
		_animatedSpriteRenderSystem.update(dt);
		_magneticMovementSystem.update(dt);
		_playerControlSystem.update(dt);
		_physicalMovementSystem.update(dt);
	}

	public void draw(Renderer r) {
		int x1 = (int) Math.max(_player.getX() - Game.LEVEL_WINDOW_WIDTH / 2 - _player.SX / 2, 0);
		int y1 = (int) Math.max(_player.getY() - Game.LEVEL_WINDOW_HEIGHT / 2 - _player.SY / 2, 0);

		int x2 = x1 + Game.LEVEL_WINDOW_WIDTH;
		int y2 = y1 + Game.LEVEL_WINDOW_HEIGHT;

		if (x2 > _width * Tile.SIZE) {
			x1 = _width * Tile.SIZE - Game.LEVEL_WINDOW_WIDTH;
			x2 = _width * Tile.SIZE;
		}

		if (y2 > _height * Tile.SIZE) {
			y1 = _height * Tile.SIZE - Game.LEVEL_WINDOW_HEIGHT;
			y2 = _height * Tile.SIZE;
		}

		r.save();
		r.translate(-x1, -y1);

		int tx1 = (int) Math.floor(x1 / Tile.SIZE);
		int tx2 = (int) Math.ceil(x2 / Tile.SIZE);
		int ty1 = (int) Math.floor(y1 / Tile.SIZE);
		int ty2 = (int) Math.ceil(y2 / Tile.SIZE);

		for (int y = ty1; y <= ty2; ++y) {
			for (int x = tx1; x <= tx2; ++x) {
				getTile(x, y).draw(r, x * Tile.SIZE, y * Tile.SIZE);
			}
		}

		_staticTextureRenderSystem.draw(r);
		_animatedSpriteRenderSystem.draw(r);
		_player.draw(r);
		r.restore();

		_offsetX = x1;
		_offsetY = y1;
	}

	public void tryMove(PhysicalComponent phys, float dx, float dy, CollisionComponent box) {
		float x = phys.x + dx;
		float y = phys.y + dy;
		boolean collision = false;

		int x1 = (int) Math.floor(x / Tile.SIZE);
		int x2 = (int) Math.ceil((x + box.sx) / Tile.SIZE);
		int y1 = (int) Math.floor(y / Tile.SIZE);
		int y2 = (int) Math.ceil((y + box.sy) / Tile.SIZE);

		for (int ty = y1; ty <= y2; ++ty) {
			for (int tx = x1; tx <= x2; ++tx) {
				Tile tile = getTile(tx, ty);

				if (!tile.hasCollision()) {
					continue;
				}

				if (overlaps(tx * Tile.SIZE, ty * Tile.SIZE, Tile.SIZE, Tile.SIZE, x, y, box.sx, box.sy)) {
					if (dx != 0) {
						if (dx > 0) {
							x = tx *  Tile.SIZE - box.sx;
						} else {
							x = tx * Tile.SIZE + Tile.SIZE;
						}

						collision = true;
					} else {
						if (dy > 0) {
							y = ty *  Tile.SIZE - box.sy;
							phys.onFloor = true;
						} else {
							y = ty * Tile.SIZE + Tile.SIZE;
						}

						collision = true;
					}
				}
			}
		}

		if (collision) {
			if (dx != 0) {
				phys.dx = 0;
			} else {
				phys.dy = 0;
			}

			phys.x = x;
			phys.y = y;
		} else {
			if (dy != 0) {
				phys.onFloor = false;
			}
		}
	}

	private boolean overlaps(float x1, float y1, float sx1, float sy1, float x2, float y2, float sx2, float sy2) {
		return (((x1 < x2 + sx2 && x1 + sx1 > x2) || (x2 < x1 + sx1 && x2 + sx2 > x1))
				&& ((y1 < y2 + sy2 && y1 + sy1 > y2) || (y2 < y1 + sy1 && y2 + sy2 > y1)));
	}

	private Tile getTile(int tx, int ty) {
		if (tx < 0 || tx >= _width || ty < 0 || ty >= _height) {
			return Tile.wall;
		}

		return _tiles[tx + ty * _width];
	}

	public void removeOldDevices() {
		_magneticMovementSystem.removeOldDevices();
	}

	public int getOffsetX() {
		return _offsetX;
	}

	public int getOffsetY() {
		return _offsetY;
	}
}

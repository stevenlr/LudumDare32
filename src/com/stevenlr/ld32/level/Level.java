package com.stevenlr.ld32.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.stevenlr.ld32.Fonts;
import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.Particles;
import com.stevenlr.ld32.components.CollisionComponent;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.entities.Drone;
import com.stevenlr.ld32.entities.Item;
import com.stevenlr.ld32.entities.MetalCrate;
import com.stevenlr.ld32.entities.Note;
import com.stevenlr.ld32.entities.Player;
import com.stevenlr.ld32.screens.GameScreen;
import com.stevenlr.ld32.systems.AnimatedSpriteRenderSystem;
import com.stevenlr.ld32.systems.BulletLogicSystem;
import com.stevenlr.ld32.systems.DroneLogicSystem;
import com.stevenlr.ld32.systems.ItemManagerSystem;
import com.stevenlr.ld32.systems.MagneticMovementSystem;
import com.stevenlr.ld32.systems.NoteManagerSystem;
import com.stevenlr.ld32.systems.PhysicalMovementSystem;
import com.stevenlr.ld32.systems.PlayerControlSystem;
import com.stevenlr.ld32.systems.StaticTextureRenderSystem;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.graphics.Color;
import com.stevenlr.waffle.graphics.Font;
import com.stevenlr.waffle.graphics.Renderer;

public class Level {

	private boolean[] _startingInventory;
	private Tile[] _tiles;
	private int _width;
	private int _height;
	private int _playerSpawnX;
	private int _playerSpawnY;
	private Player _player;
	private int _offsetX;
	private int _offsetY;
	private float _lastValidX;
	private float _lastValidY;
	private float _goalX;
	private float _goalY;
	private boolean _hasStarted = false;
	private boolean _isDone = false;
	private float _animation = 0;
	private int _id;
	private String _deathCause;

	private PhysicalMovementSystem _physicalMovementSystem = new PhysicalMovementSystem();
	private PlayerControlSystem _playerControlSystem = new PlayerControlSystem();
	private StaticTextureRenderSystem _staticTextureRenderSystem = new StaticTextureRenderSystem();
	private AnimatedSpriteRenderSystem _animatedSpriteRenderSystem = new AnimatedSpriteRenderSystem();
	private MagneticMovementSystem _magneticMovementSystem = new MagneticMovementSystem();
	private ItemManagerSystem _itemManagerSystem = new ItemManagerSystem();
	private NoteManagerSystem _noteManagerSystem = new NoteManagerSystem();
	private DroneLogicSystem _droneLogicSystem = new DroneLogicSystem();
	private BulletLogicSystem _bulletLogicSystem = new BulletLogicSystem();

	public String getDeathCause() {
		return _deathCause;
	}

	public Level(int id) {
		_id = id;
		reload();
	}

	public Level(int id, boolean[] inventory) {
		_id = id;
		_startingInventory = inventory;
		reload();
	}

	public void reload() {
		BufferedImage img = null;

		try {
			img = ImageIO.read(Game.class.getResourceAsStream("/levels/level" + _id + ".png"));
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
				int color = img.getRGB(x, y) & 0xffffff;

				switch (color) {
				case 0x00ff00:
					_playerSpawnX = x;
					_playerSpawnY = y;
					tile = Tile.empty;
					break;
				case 0xffffff:
					tile = Tile.empty;

					if (y >= 1 && (img.getRGB(x, y - 1) & 0xffffff) == 0x0000ff) {
						tile = Tile.doorBottom;
					}
					break;
				case 0x000000:
					tile = Tile.wall;

					if (y >= 1 && (img.getRGB(x, y - 1) & 0xffffff) != 0x000000 && (img.getRGB(x, y - 1) & 0xffffff) != 0xff0000) {
						tile = Tile.ground;
					}
					break;
				case 0xff8000:
					tile = Tile.empty;
					new MetalCrate(x * Tile.SIZE, y * Tile.SIZE);
					break;
				case 0x0000ff:
					tile = Tile.doorTop;
					_goalX = x * Tile.SIZE + Tile.SIZE / 2;
					_goalY = (y + 1) * Tile.SIZE;
					break;
				case 0xff0000:
					tile = Tile.acid;
					break;
				case 0xff00ff:
					tile = Tile.empty;
					new Drone(x * Tile.SIZE, y * Tile.SIZE);
					break;
				}

				if ((color & 0xfffff0) == 0x00ff80) {
					int item = color & (~0x00ff80);

					new Item(item, x * Tile.SIZE + Tile.SIZE / 2 - 16, y * Tile.SIZE + Tile.SIZE / 2 - 16);
					tile = Tile.empty;
				}

				if ((color & 0xfffff0) == 0x808080) {
					int id = color & (~0x808080);

					new Note(id, x * Tile.SIZE + Tile.SIZE / 2 - 16, y * Tile.SIZE + Tile.SIZE / 2 - 16);
					tile = Tile.empty;
				}

				_tiles[i] = tile;
			}
		}

		_animation = 0;
		_hasStarted = false;
		_isDone = false;
		respawn();
	}

	public void update(float dt) {
		if (!_hasStarted) {
			_animation += dt;

			if (_animation >= 1.0f) {
				_hasStarted = true;
			}

			return;
		}

		if (_isDone) {
			_animation -= dt;
			return;
		}

		if (_player.isDead()) {
			_animation += dt;
			return;
		}

		_animation = 0;

		_animatedSpriteRenderSystem.update(dt);
		_magneticMovementSystem.update(dt);
		_playerControlSystem.update(dt);
		_physicalMovementSystem.update(dt);
		_itemManagerSystem.update(dt);
		_noteManagerSystem.update(dt);
		_droneLogicSystem.update(dt);
		_bulletLogicSystem.update(dt);

		float dx = _player.getX() - _lastValidX;
		float dy = _player.getY() - _lastValidY;
		float dist = (float) Math.sqrt(dx * dx + dy * dy);

		if (_player.getX() < 0 || _player.getY() < 0 || _player.getX() >= _width * Tile.SIZE || _player.getY() >= _height * Tile.SIZE
				|| dist > Tile.SIZE * 3) {
			_player.die();
			Particles.spawnBloodParticles(_lastValidX, _lastValidY);
			_deathCause = "You got crushed";
		} else {
			_lastValidX = _player.getX();
			_lastValidY = _player.getY();
		}

		if (isInAcid()) {
			Particles.spawnBloodParticles(_lastValidX, _lastValidY);
			_deathCause = "You tried to swim in acid";
			_player.die();
		}

		dx = _player.getX() - _goalX;
		dy = _player.getY() - _goalY;
		dist = (float) Math.sqrt(dx * dx + dy * dy);

		if (dist < 16 && !_player.isDead()) {
			_isDone = true;
			_animation = 1;
		}
	}

	public void setDeathCause(String deathCause) {
		_deathCause = deathCause;
	}

	private void respawn() {
		if (_player != null) {
			Waffle.entitySystem.removeEntity(_player);
		}

		_player = new Player(_playerSpawnX * Tile.SIZE, _playerSpawnY * Tile.SIZE, _startingInventory);
		_lastValidX = _player.getX();
		_lastValidY = _player.getY();
	}

	public void draw(Renderer r) {
		int x1 = (int) Math.max(_player.getX() - GameScreen.LEVEL_WINDOW_WIDTH / 2 - _player.SX / 2, 0);
		int y1 = (int) Math.max(_player.getY() - GameScreen.LEVEL_WINDOW_HEIGHT / 2 - _player.SY / 2, 0);

		int x2 = x1 + GameScreen.LEVEL_WINDOW_WIDTH;
		int y2 = y1 + GameScreen.LEVEL_WINDOW_HEIGHT;

		if (x2 > _width * Tile.SIZE) {
			x1 = _width * Tile.SIZE - GameScreen.LEVEL_WINDOW_WIDTH;
			x2 = _width * Tile.SIZE;
		}

		if (y2 > _height * Tile.SIZE) {
			y1 = _height * Tile.SIZE - GameScreen.LEVEL_WINDOW_HEIGHT;
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
		_droneLogicSystem.draw(r);
		_player.draw(r);

		Particles.bloodParticles.draw(r);
		Particles.launchParticle.draw(r);
		Particles.smokeParticles.draw(r);

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
							x = tx * Tile.SIZE - box.sx;
						} else {
							x = tx * Tile.SIZE + Tile.SIZE;
						}

						collision = true;
					} else {
						if (dy > 0) {
							y = ty * Tile.SIZE - box.sy;
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

	public boolean overlaps(float x1, float y1, float sx1, float sy1, float x2, float y2, float sx2, float sy2) {
		return (((x1 < x2 + sx2 && x1 + sx1 > x2) || (x2 < x1 + sx1 && x2 + sx2 > x1))
				&& ((y1 < y2 + sy2 && y1 + sy1 > y2) || (y2 < y1 + sy1 && y2 + sy2 > y1)));
	}

	public Tile getTile(int tx, int ty) {
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

	public void drawInventory(Renderer r) {
		r.fillRect(0, 0, GameScreen.INVENTORY_WINDOW_WIDTH, GameScreen.INVENTORY_WINDOW_HEIGHT, Color.Black);
		r.save();
		r.translate(9, 9);

		r.drawText("Level " + _id, Color.White, Fonts.fontNormal, 0, 16, Font.HorizontalAlign.LEFT, Font.VerticalAlign.MIDDLE);

		r.translate(200, 0);
		_player.drawInventory(r);
		r.restore();
	}

	public Player getPlayer() {
		return _player;
	}

	public boolean isDone() {
		return _isDone;
	}

	public boolean hasStarted() {
		return _hasStarted;
	}

	public float getAnimation() {
		return _animation;
	}

	public boolean isDead() {
		return _player.isDead();
	}

	boolean isInAcid() {
		int x1 = (int) (_player.getX() / Tile.SIZE);
		int y1 = (int) (_player.getY() / Tile.SIZE);
		int x2 = (int) ((_player.getX() + Player.SX) / Tile.SIZE);
		int y2 = (int) ((_player.getY() + Player.SY * 0.75) / Tile.SIZE);

		for (int y = y1; y <= y2; ++y) {
			for (int x = x1; x <= x2; ++x) {
				if (getTile(x, y).getId() == Tile.acid.getId()) {
					return true;
				}
			}
		}
		return false;
	}
}

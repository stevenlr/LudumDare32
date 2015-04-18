package com.stevenlr.ld32;

import com.stevenlr.ld32.level.Level;
import com.stevenlr.ld32.systems.PhysicalMovementSystem;
import com.stevenlr.ld32.systems.PlayerControlSystem;
import com.stevenlr.waffle.IWaffleGame;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.WaffleGame;
import com.stevenlr.waffle.graphics.Color;
import com.stevenlr.waffle.graphics.Renderer;

@WaffleGame (
		title = "Ludum Dare 32",
		viewportWidth = Game.WIDTH * Game.PIXEL_SIZE,
		viewportHeight = Game.HEIGHT * Game.PIXEL_SIZE,
		pixelAspect = Game.PIXEL_SIZE,
		showFps = true
)
public class Game implements IWaffleGame {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	public static final int PIXEL_SIZE = 2;

	public static Game instance = new Game();

	private Level _level;
	private PhysicalMovementSystem _physicalMovementSystem = new PhysicalMovementSystem();
	private PlayerControlSystem _playerControlSystem = new PlayerControlSystem();

	public static void main(String[] args) {
		Waffle.instance.startGame(instance);
	}

	public Level getLevel() {
		return _level;
	}

	@Override
	public void init() {
		_level = new Level("/level.png");
	}

	@Override
	public void update(float dt) {
		_physicalMovementSystem.update(dt);
		_playerControlSystem.update(dt);
	}

	@Override
	public void draw(Renderer r) {
		r.fillRect(0, 0, WIDTH, HEIGHT, Color.Gray);
		_level.draw(r);
	}
}

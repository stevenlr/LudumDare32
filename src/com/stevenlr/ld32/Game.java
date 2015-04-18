package com.stevenlr.ld32;

import com.stevenlr.ld32.level.Level;
import com.stevenlr.waffle.IWaffleGame;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.WaffleGame;
import com.stevenlr.waffle.graphics.Canvas;
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

	public static final int INVENTORY_WINDOW_WIDTH = WIDTH;
	public static final int INVENTORY_WINDOW_HEIGHT = 50;

	public static final int LEVEL_WINDOW_WIDTH = WIDTH;
	public static final int LEVEL_WINDOW_HEIGHT = HEIGHT - INVENTORY_WINDOW_HEIGHT;

	public static Game instance = new Game();

	private Level _level;

	private Canvas _gameCanvas;
	private Canvas _inventoryCanvas;

	private float _time = 0;

	public static void main(String[] args) {
		Waffle.instance.startGame(instance);
	}

	public Level getLevel() {
		return _level;
	}

	@Override
	public void init() {
		_level = new Level("/levels/level1.png");
		_gameCanvas = new Canvas(LEVEL_WINDOW_WIDTH, LEVEL_WINDOW_HEIGHT);
		_inventoryCanvas = new Canvas(INVENTORY_WINDOW_WIDTH, INVENTORY_WINDOW_HEIGHT);
	}

	@Override
	public void update(float dt) {
		_time += dt;
		_level.update(dt);
	}

	@Override
	public void draw(Renderer r) {
		r.fillRect(0, 0, WIDTH, HEIGHT, Color.Black);

		_level.draw(_gameCanvas.getRenderer());
		_level.drawInventory(_inventoryCanvas.getRenderer());

		r.beginBlit(_gameCanvas, 0, 0).blit();
		r.beginBlit(_inventoryCanvas, 0, LEVEL_WINDOW_HEIGHT).blit();
	}

	public float getTime() {
		return _time;
	}
}

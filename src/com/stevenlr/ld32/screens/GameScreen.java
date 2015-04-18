package com.stevenlr.ld32.screens;

import com.stevenlr.ld32.ControlsConfig;
import com.stevenlr.ld32.Fonts;
import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.components.PlayerComponent;
import com.stevenlr.ld32.level.Level;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.graphics.Canvas;
import com.stevenlr.waffle.graphics.Color;
import com.stevenlr.waffle.graphics.Font;
import com.stevenlr.waffle.graphics.Renderer;

public class GameScreen implements IScreen {

	public static final int NB_LEVELS = 5;
	public static final int INVENTORY_WINDOW_WIDTH = Game.WIDTH;
	public static final int INVENTORY_WINDOW_HEIGHT = 50;
	public static final int LEVEL_WINDOW_WIDTH = Game.WIDTH;
	public static final int LEVEL_WINDOW_HEIGHT = Game.HEIGHT - INVENTORY_WINDOW_HEIGHT;

	private int _currentLevel = 1;
	private Level _level;
	private Canvas _gameCanvas;
	private Canvas _inventoryCanvas;

	public GameScreen() {
		loadLevel();
		_gameCanvas = new Canvas(LEVEL_WINDOW_WIDTH, LEVEL_WINDOW_HEIGHT);
		_inventoryCanvas = new Canvas(INVENTORY_WINDOW_WIDTH, INVENTORY_WINDOW_HEIGHT);
	}

	private void loadLevel() {
		if (_level != null) {
			boolean[] inventory = _level.getPlayer().getComponent(PlayerComponent.class).inventory;

			Waffle.entitySystem.clearAll();
			_level = new Level(_currentLevel, inventory);
		} else {
			Waffle.entitySystem.clearAll();
			_level = new Level(_currentLevel);
		}
	}

	public Level getLevel() {
		return _level;
	}

	@Override
	public void update(float dt) {
		if ((_level.isDead() && Waffle.keyboard.isPressedThisFrame(ControlsConfig.SPACE))
				|| Waffle.keyboard.isPressedThisFrame(ControlsConfig.RESTART)) {
			Waffle.entitySystem.clearAll();
			_level.reload();
		}

		if (_level.isDone() && _level.getAnimation() <= 0) {
			if (_currentLevel == NB_LEVELS) {
				Game.instance.setNextScreen(new EndScreen(true));
			} else {
				_currentLevel = Math.min(_currentLevel + 1, NB_LEVELS);
				loadLevel();
			}
		}

		_level.update(dt);
	}

	@Override
	public void draw(Renderer r) {
		_level.draw(_gameCanvas.getRenderer());
		_level.drawInventory(_inventoryCanvas.getRenderer());

		if (!_level.hasStarted() || _level.isDone()) {
			float animation = _level.getAnimation();

			if (animation > 0) {
				r.setCustomComposite(false, Color.Black, 1 - animation);
			} else {
				r.setCustomComposite(false, Color.Black, 1);
			}

			r.beginBlit(_gameCanvas, 0, 0).blit();
			r.beginBlit(_inventoryCanvas, 0, LEVEL_WINDOW_HEIGHT).blit();
			r.endCustomComposite();
		} else if (_level.isDead()) {
			float animation = _level.getAnimation();

			if (animation < 1) {
				r.setCustomComposite(false, Color.Red, animation);
			} else if (animation < 2) {
				r.setCustomComposite(false, new Color((int) ((1 - (animation - 1)) * 255), 0, 0), 1);
			} else {
				r.setCustomComposite(false, Color.Black, 1);
			}

			r.beginBlit(_gameCanvas, 0, 0).blit();
			r.beginBlit(_inventoryCanvas, 0, LEVEL_WINDOW_HEIGHT).blit();
			r.endCustomComposite();

			r.drawText("YOU DIED", Color.White, Fonts.fontNormal, Game.WIDTH / 2, Game.HEIGHT / 2, Font.HorizontalAlign.MIDDLE, Font.VerticalAlign.MIDDLE);
			r.drawText(_level.getDeathCause(), Color.White, Fonts.fontSmall, Game.WIDTH / 2, Game.HEIGHT / 2 + 35, Font.HorizontalAlign.MIDDLE, Font.VerticalAlign.MIDDLE);
			r.drawText("Press [space] to continue", Color.White, Fonts.fontSmall, Game.WIDTH / 2, Game.HEIGHT / 2 + 60, Font.HorizontalAlign.MIDDLE, Font.VerticalAlign.MIDDLE);
		} else {
			r.beginBlit(_gameCanvas, 0, 0).blit();
			r.beginBlit(_inventoryCanvas, 0, LEVEL_WINDOW_HEIGHT).blit();
		}
	}
}

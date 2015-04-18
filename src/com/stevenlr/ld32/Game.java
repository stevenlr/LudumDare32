package com.stevenlr.ld32;

import com.stevenlr.ld32.screens.GameScreen;
import com.stevenlr.ld32.screens.IScreen;
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

	private float _time = 0;
	private IScreen _currentScreen;
	private IScreen _nextScreen;

	public static void main(String[] args) {
		Waffle.instance.startGame(instance);
	}

	@Override
	public void init() {
		_currentScreen = new GameScreen();
	}

	@Override
	public void update(float dt) {
		if (_nextScreen != null) {
			_currentScreen = _nextScreen;
			_nextScreen = null;
		}

		_time += dt;
		_currentScreen.update(dt);
	}

	@Override
	public void draw(Renderer r) {
		r.fillRect(0, 0, WIDTH, HEIGHT, Color.Black);
		_currentScreen.draw(r);
	}

	public float getTime() {
		return _time;
	}

	public IScreen getCurrentScreen() {
		return _currentScreen;
	}

	public void setNextScreen(IScreen screen) {
		_nextScreen = screen;
	}
}

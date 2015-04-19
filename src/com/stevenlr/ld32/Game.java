package com.stevenlr.ld32;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.stevenlr.ld32.screens.IScreen;
import com.stevenlr.ld32.screens.MainScreen;
import com.stevenlr.waffle.IWaffleGame;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.WaffleGame;
import com.stevenlr.waffle.graphics.Color;
import com.stevenlr.waffle.graphics.Renderer;
import org.newdawn.easyogg.OggClip;

@WaffleGame(
		title = "HK-21 (Ludum Dare 32)",
		viewportWidth = Game.WIDTH * Game.PIXEL_SIZE,
		viewportHeight = Game.HEIGHT * Game.PIXEL_SIZE,
		pixelAspect = Game.PIXEL_SIZE,
		showFps = false
)
public class Game implements IWaffleGame {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	public static final int PIXEL_SIZE = 2;

	public static Game instance = new Game();

	private float _time = 0;
	private IScreen _currentScreen;
	private IScreen _nextScreen;

	private OggClip _music;

	public static void main(String[] args) {
		Image img = null;

		try {
			img = ImageIO.read(Game.class.getResourceAsStream("/icon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		Waffle.instance.setWindowIcon(img);
		Waffle.instance.startGame(instance);
	}

	@Override
	public void init() {
		try {
			_music = new OggClip(Game.class.getResourceAsStream("/sounds/music.ogg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		_music.setGain(0.8f);
		_music.loop();

		_currentScreen = new MainScreen();
	}

	@Override
	public void update(float dt) {
		if (_nextScreen != null) {
			_currentScreen = _nextScreen;
			_nextScreen = null;
		}

		if (Waffle.keyboard.isPressedThisFrame(ControlsConfig.MUSIC)) {
			if (_music.stopped()) {
				_music.loop();
			} else {
				_music.stop();
			}
		}

		Particles.update(dt);
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

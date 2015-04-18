package com.stevenlr.ld32;

import com.stevenlr.waffle.IWaffleGame;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.WaffleGame;
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

	public static void main(String[] args) {
		Waffle.instance.startGame(new Game());
	}

	@Override
	public void init() {

	}

	@Override
	public void update(float v) {

	}

	@Override
	public void draw(Renderer renderer) {

	}
}

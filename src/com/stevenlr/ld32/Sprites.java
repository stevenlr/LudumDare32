package com.stevenlr.ld32;

import com.stevenlr.waffle.graphics.AnimatedSprite;
import com.stevenlr.waffle.graphics.Sprite;
import com.stevenlr.waffle.graphics.SpriteSheet;

public class Sprites {

	public static AnimatedSprite blueBallAnimation = new AnimatedSprite("/ball.png", 6, 6);
	public static AnimatedSprite orangeBallAnimation = new AnimatedSprite("/ball.png", 6, 6);

	static {
		blueBallAnimation.addFrame(0.3f, 0, 0);
		blueBallAnimation.addFrame(0.3f, 1, 0);
		blueBallAnimation.addFrame(0.3f, 2, 0);

		orangeBallAnimation.addFrame(0.3f, 0, 1);
		orangeBallAnimation.addFrame(0.3f, 1, 1);
		orangeBallAnimation.addFrame(0.3f, 2, 1);
	}

	public static Sprite player = new Sprite("/player.png");
	public static SpriteSheet tiles = new SpriteSheet("/tiles.png", 16, 16);
}

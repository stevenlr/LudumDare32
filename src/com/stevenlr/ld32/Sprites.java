package com.stevenlr.ld32;

import com.stevenlr.waffle.graphics.AnimatedSprite;
import com.stevenlr.waffle.graphics.Sprite;
import com.stevenlr.waffle.graphics.SpriteSheet;

public class Sprites {

	public static AnimatedSprite blueBallAnimation = new AnimatedSprite("/ball.png", 6, 6);
	public static AnimatedSprite orangeBallAnimation = new AnimatedSprite("/ball.png", 6, 6);
	public static AnimatedSprite bloodAnimation = new AnimatedSprite("/blood.png", 6, 6);

	static {
		blueBallAnimation.addFrame(0.3f, 0, 0);
		blueBallAnimation.addFrame(0.3f, 1, 0);
		blueBallAnimation.addFrame(0.3f, 2, 0);

		orangeBallAnimation.addFrame(0.3f, 0, 1);
		orangeBallAnimation.addFrame(0.3f, 1, 1);
		orangeBallAnimation.addFrame(0.3f, 2, 1);

		for (int i = 0; i < 6; ++i) {
			bloodAnimation.addFrame(0.12f, i);
		}
	}

	public static Sprite player = new Sprite("/player.png");
	public static SpriteSheet tiles = new SpriteSheet("/tiles.png", 16, 16);

	public static SpriteSheet inventory = new SpriteSheet("/inventory.png", 32, 32);

	public static class Inventory {
		public static SpriteSheet.Region blueDevice = inventory.getRegion(0, 1);
		public static SpriteSheet.Region orangeDevice = inventory.getRegion(1, 1);
		public static SpriteSheet.Region knife = inventory.getRegion(2, 1);
		public static SpriteSheet.Region chemicals = inventory.getRegion(3, 1);

		public static SpriteSheet.Region used = inventory.getRegion(0);
		public static SpriteSheet.Region unused = inventory.getRegion(1);
		public static SpriteSheet.Region absent = inventory.getRegion(2);

		public static SpriteSheet.Region title = inventory.getRegion(0, 6, 8, 2);
	}
}

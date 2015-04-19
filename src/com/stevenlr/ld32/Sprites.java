package com.stevenlr.ld32;

import com.stevenlr.waffle.graphics.AnimatedSprite;
import com.stevenlr.waffle.graphics.Sprite;
import com.stevenlr.waffle.graphics.SpriteSheet;

public class Sprites {

	public static AnimatedSprite blueBallAnimation = new AnimatedSprite("/ball.png", 6, 6);
	public static AnimatedSprite orangeBallAnimation = new AnimatedSprite("/ball.png", 6, 6);
	public static AnimatedSprite bloodAnimation = new AnimatedSprite("/blood.png", 6, 6);
	public static AnimatedSprite smokeAnimation = new AnimatedSprite("/smoke.png", 6, 6);
	public static AnimatedSprite playerAnimation = new AnimatedSprite("/player.png", 12, 30);

	static {
		for (int i = 0; i < 3; ++i) {
			blueBallAnimation.addFrame(0.3f, i, 0);
			orangeBallAnimation.addFrame(0.3f, i, 1);
		}

		for (int i = 0; i < 6; ++i) {
			bloodAnimation.addFrame(0.12f, i);
			smokeAnimation.addFrame(0.25f, i);
		}

		for (int i = 0; i < 4; ++i) {
			playerAnimation.addFrame(0.1f, i);
		}
	}

	public static Sprite launchParticle = new Sprite("/launch_particle.png");
	public static Sprite bullet = new Sprite("/bullet.png");
	public static Sprite scene = new Sprite("/scene.png");
	public static SpriteSheet tiles = new SpriteSheet("/tiles.png", 16, 16);
	public static SpriteSheet drone = new SpriteSheet("/drone.png", 16, 16);
	public static SpriteSheet player = new SpriteSheet("/player.png", 12, 30);
	public static SpriteSheet general = new SpriteSheet("/general.png", 24, 30);

	public static SpriteSheet inventory = new SpriteSheet("/inventory.png", 32, 32);

	public static class Inventory {
		public static SpriteSheet.Region blueDevice = inventory.getRegion(0, 1);
		public static SpriteSheet.Region orangeDevice = inventory.getRegion(1, 1);
		public static SpriteSheet.Region knife = inventory.getRegion(2, 1);
		public static SpriteSheet.Region chemicals = inventory.getRegion(3, 1);
		public static SpriteSheet.Region note = inventory.getRegion(0, 2);

		public static SpriteSheet.Region used = inventory.getRegion(0);
		public static SpriteSheet.Region unused = inventory.getRegion(1);
		public static SpriteSheet.Region absent = inventory.getRegion(2);

		public static SpriteSheet.Region title = inventory.getRegion(0, 7, 4, 1);
	}
}

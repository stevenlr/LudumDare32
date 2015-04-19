package com.stevenlr.ld32.screens;

import com.stevenlr.ld32.ControlsConfig;
import com.stevenlr.ld32.Fonts;
import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.Sprites;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.graphics.Color;
import com.stevenlr.waffle.graphics.Font;
import com.stevenlr.waffle.graphics.Renderer;

public class MainScreen implements IScreen {

	public MainScreen() {
	}

	@Override
	public void update(float dt) {
		if (Waffle.keyboard.isPressedThisFrame(ControlsConfig.SPACE)) {
			Game.instance.setNextScreen(new IntroScreen());
		}
	}

	private void printLine(Renderer r, String text, int i) {
		r.drawText(text, Color.LightGray, Fonts.fontSmall,
				100, 110 + i * 17,
				Font.HorizontalAlign.LEFT, Font.VerticalAlign.BOTTOM);
	}

	@Override
	public void draw(Renderer r) {
		r.fillRect(0, 0, Game.WIDTH, Game.HEIGHT, Color.Black);
		r.beginBlit(Sprites.Inventory.title, Game.WIDTH / 2, 50).center().blit();

		printLine(r, "        A-D   Move", 0);
		printLine(r, "      Space   Jump", 1);
		printLine(r, "          S   Action", 2);
		printLine(r, "          R   Restart", 3);
		printLine(r, "    1-2-3-4   Select slot", 4);
		printLine(r, "Mouse wheel   Select slot", 5);
		printLine(r, " Left click   Launch device", 6);

		r.drawText("Press [space] to start", Color.LightGray, Fonts.fontSmall,
				Game.WIDTH / 2, Game.HEIGHT - 50,
				Font.HorizontalAlign.MIDDLE, Font.VerticalAlign.BOTTOM);

		r.drawText("Game by Steven Le Rouzic (stevenlr.com)", Color.DarkGray, Fonts.fontSmall,
				Game.WIDTH - 8, Game.HEIGHT - 20, Font.HorizontalAlign.RIGHT, Font.VerticalAlign.BOTTOM);
		r.drawText("For Ludum Dare 32 (ludumdare.com)", Color.DarkGray, Fonts.fontSmall,
				Game.WIDTH - 8, Game.HEIGHT - 8, Font.HorizontalAlign.RIGHT, Font.VerticalAlign.BOTTOM);
	}
}

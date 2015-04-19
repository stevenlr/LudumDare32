package com.stevenlr.ld32.screens;

import com.stevenlr.ld32.ControlsConfig;
import com.stevenlr.ld32.Fonts;
import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.Sounds;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.graphics.Color;
import com.stevenlr.waffle.graphics.Font;
import com.stevenlr.waffle.graphics.Renderer;

public class FoundItemScreen implements IScreen {

	private IScreen _parentScreen;
	private String _name;

	public FoundItemScreen(IScreen parentScreen, String name) {
		_parentScreen = parentScreen;
		_name = name;
	}

	@Override
	public void update(float dt) {
		if (Waffle.keyboard.isPressedThisFrame(ControlsConfig.SPACE)) {
			Game.instance.setNextScreen(_parentScreen);
			Sounds.ok.play();
		}
	}

	@Override
	public void draw(Renderer r) {
		_parentScreen.draw(r);
		r.fillRect(0, Game.HEIGHT / 2 - 50, Game.WIDTH, 100, Color.Black);
		r.drawText("You found " + _name, Color.White, Fonts.fontNormal, Game.WIDTH / 2, Game.HEIGHT / 2 - 10, Font.HorizontalAlign.MIDDLE, Font.VerticalAlign.MIDDLE);
		r.drawText("Press [space]", Color.White, Fonts.fontSmall, Game.WIDTH - 20, Game.HEIGHT / 2 + 20, Font.HorizontalAlign.RIGHT, Font.VerticalAlign.MIDDLE);
	}
}

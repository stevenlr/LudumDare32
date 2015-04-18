package com.stevenlr.ld32.screens;

import com.stevenlr.ld32.Fonts;
import com.stevenlr.ld32.Game;
import com.stevenlr.waffle.graphics.Color;
import com.stevenlr.waffle.graphics.Font;
import com.stevenlr.waffle.graphics.Renderer;

public class EndScreen implements IScreen {

	private boolean _won;
	private float _time = 0;

	public EndScreen(boolean won) {
		_won = won;
	}

	@Override
	public void update(float dt) {
		_time += dt;
	}

	@Override
	public void draw(Renderer r) {
		r.fillRect(0, 0, Game.WIDTH, Game.HEIGHT, Color.Black);
		Color color = Color.White;

		if (_time < 2) {
			color = new Color((int) (_time / 2 * 255), (int) (_time / 2 * 255), (int) (_time / 2 * 255));
		}

		r.drawText("YOU WON", color, Fonts.fontNormal, Game.WIDTH / 2, Game.HEIGHT / 2, Font.HorizontalAlign.MIDDLE, Font.VerticalAlign.MIDDLE);
	}
}

package com.stevenlr.ld32.screens;

import java.util.ArrayList;
import java.util.List;

import com.stevenlr.ld32.ControlsConfig;
import com.stevenlr.ld32.Fonts;
import com.stevenlr.ld32.Game;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.graphics.Color;
import com.stevenlr.waffle.graphics.Font;
import com.stevenlr.waffle.graphics.Renderer;

public class EndScreen implements IScreen {

	private List<String> _lines = new ArrayList<String>();
	private float _time = 0;

	public EndScreen(int ending) {
		switch (ending) {
		case 0:
			_lines.add("You were killed by the general.");
			_lines.add("3.4 million innocents died because");
			_lines.add("of the HK-21 gas.");
			break;
		case 1:
			_lines.add("You were killed by the general.");
			_lines.add("The war ended peacefully a few days");
			_lines.add("later thanks to a peace treaty");
			break;
		case 2:
			_lines.add("You killed the general.");
			_lines.add("The war ended peacefully a few days");
			_lines.add("later thanks to a peace treaty");
			_lines.add("You saved millions of people from");
			_lines.add("the HK-21 gas.");
			break;
		}
	}

	@Override
	public void update(float dt) {
		_time += dt;

		if (_time >= 2) {
			if (Waffle.keyboard.isPressedThisFrame(ControlsConfig.SPACE)) {
				Game.instance.setNextScreen(new MainScreen());
			}
		}
	}

	@Override
	public void draw(Renderer r) {
		r.fillRect(0, 0, Game.WIDTH, Game.HEIGHT, Color.Black);

		for (int i = 0; i < _lines.size(); ++i) {
			drawLine(r, _lines.get(i), i);
		}

		if (_time >= 2) {
			r.drawText("Press [space] to exit", Color.Gray, Fonts.fontSmall,
					Game.WIDTH - 20, Game.HEIGHT - 20,
					Font.HorizontalAlign.RIGHT, Font.VerticalAlign.BOTTOM);
		}
	}

	private void drawLine(Renderer r, String s, int i) {
		if (_time < 2) {
			s = s.substring(0, (int) (s.length() * _time / 2));
		}

		r.drawText(s, Color.LightGray, Fonts.fontSmall,
				30, 80 + i * 25,
				Font.HorizontalAlign.LEFT, Font.VerticalAlign.BOTTOM);
	}
}

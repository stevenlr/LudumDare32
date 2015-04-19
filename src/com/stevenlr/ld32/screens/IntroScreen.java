package com.stevenlr.ld32.screens;

import java.util.ArrayList;
import java.util.List;

import com.stevenlr.ld32.ControlsConfig;
import com.stevenlr.ld32.Fonts;
import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.Sprites;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.graphics.Color;
import com.stevenlr.waffle.graphics.Font;
import com.stevenlr.waffle.graphics.Renderer;

public class IntroScreen implements IScreen {

	private float _animation = 0;
	private List<String> _lines;
	private int _currentLine = 0;

	public IntroScreen() {
		_lines = new ArrayList<String>();

		_lines.add("Doctor P...");
		_lines.add("If we want any chance of winning the war");
		_lines.add("we need to get your chemical, the HK-21,");
		_lines.add("into this missile and launch it");
		_lines.add("on the ennemy territory.");
		_lines.add("Their crops will be destroyed");
		_lines.add("and they'll have to capitulate.");
		_lines.add("Now, go fetch it.");
		_lines.add("But be careful, some ennemy drones");
		_lines.add("are still in the area...");
	}

	@Override
	public void update(float dt) {
		if (_animation < 1 && _currentLine == 0) {
			_animation += dt;
			return;
		}

		if (Waffle.keyboard.isPressedThisFrame(ControlsConfig.SPACE)) {
			_currentLine++;
		}

		if (_currentLine >= _lines.size()) {
			_animation = Math.max(_animation - dt, 0);

			if (_animation <= 0) {
				Game.instance.setNextScreen(new GameScreen());
			}
		}
	}

	@Override
	public void draw(Renderer r) {
		r.fillRect(0, 0, Game.WIDTH, Game.HEIGHT, Color.Black);

		if (_animation < 1 || _currentLine >= _lines.size()) {
			r.setCustomComposite(false, Color.Black, 1 - _animation);
		}

		r.beginBlit(Sprites.scene).blit();
		r.beginBlit(Sprites.general.getRegion(0), 180, 163).blit();
		r.beginBlit(Sprites.player.getRegion(1), 220, 163).mirrorX().blit();

		if (_animation >= 1 && _currentLine < _lines.size()) {
			r.fillRect(0, 40, Game.WIDTH, 50, Color.Black);
			r.drawText(_lines.get(_currentLine), Color.White, Fonts.fontSmall,
					20, 50, Font.HorizontalAlign.LEFT, Font.VerticalAlign.TOP);
			r.drawText("Press [space]", Color.Gray, Fonts.fontSmall,
					20, 70, Font.HorizontalAlign.LEFT, Font.VerticalAlign.TOP);
		}

		if (_animation < 1 || _currentLine >= _lines.size()) {
			r.endCustomComposite();
		}
	}
}

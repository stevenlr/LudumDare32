package com.stevenlr.ld32.screens;

import com.stevenlr.ld32.ControlsConfig;
import com.stevenlr.ld32.Fonts;
import com.stevenlr.ld32.Game;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.graphics.Color;
import com.stevenlr.waffle.graphics.Font;
import com.stevenlr.waffle.graphics.Renderer;

public class NoteScreen implements IScreen {

	private int _id;
	private IScreen _parentScreen;
	private float _time = 0;

	public NoteScreen(int id, IScreen parentScreen) {
		_id = id;
		_parentScreen = parentScreen;
	}

	@Override
	public void update(float dt) {
		_time += dt;

		if (_time >= 2) {
			if (Waffle.keyboard.isPressedThisFrame(ControlsConfig.SPACE)) {
				Game.instance.setNextScreen(_parentScreen);
			}
		}
	}

	@Override
	public void draw(Renderer r) {
		r.fillRect(0, 0, Game.WIDTH, Game.HEIGHT, Color.Black);

		if (_id == 0) {
			drawLine(r, "March 16th", 0);
			drawLine(r, "Experiments on the HK-21 gas, supposed", 2);
			drawLine(r, "to destroy crops, has shown extremely", 3);
			drawLine(r, "damaging effects on the human nervous", 4);
			drawLine(r, "system.", 5);
			drawLine(r, "Death ratio is estimated at around 86%.", 6);
			drawLine(r, "                                 Dr. W", 8);
		} else if (_id == 1) {
			drawLine(r, "[CLASSIFIED]", 0);
			drawLine(r, "Fighting has ceased on both fronts.", 2);
			drawLine(r, "The ennemy seems to have undergone", 3);
			drawLine(r, "too many losses to pursue this war.", 4);
			drawLine(r, "The central government has proposed", 5);
			drawLine(r, "a peace treaty. Yet we are still armed", 6);
			drawLine(r, "and could end this thread once and for", 7);
			drawLine(r, "all.", 8);
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
				30, 50 + i * 20,
				Font.HorizontalAlign.LEFT, Font.VerticalAlign.BOTTOM);
	}
}

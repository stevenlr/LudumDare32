package com.stevenlr.ld32.screens;

import com.stevenlr.ld32.ControlsConfig;
import com.stevenlr.ld32.Fonts;
import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.Particles;
import com.stevenlr.ld32.Sprites;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.graphics.Color;
import com.stevenlr.waffle.graphics.Font;
import com.stevenlr.waffle.graphics.Renderer;

public class OutroScreen implements IScreen {

	private float _animation = 0;
	private boolean _hasKnife;
	private boolean _hasChemical;
	private int _phase = 0;
	private int _currentChoice = 0;
	private boolean _isPlayerDead = false;
	private boolean _isGeneralDead = false;

	public OutroScreen(boolean hasKnife, boolean hasChemical) {
		_hasKnife = hasKnife;
		_hasChemical = hasChemical;

		if (!hasChemical) {
			_currentChoice = 1;
		}
	}

	@Override
	public void update(float dt) {
		if (_phase == 0) {
			_animation += dt;

			if (_animation >= 1) {
				_phase = 1;
				_animation = 0;
			}
		} else if (_phase == 1) {
			if (Waffle.keyboard.isPressedThisFrame(ControlsConfig.SPACE)) {
				_phase = 2;
			}
		} else if (_phase == 2) {
			if (Waffle.keyboard.isPressedThisFrame(ControlsConfig.LEFT)) {
				do {
					_currentChoice = (_currentChoice + 2) % 3;
				} while (!isValidChoice(_currentChoice));
			}

			if (Waffle.keyboard.isPressedThisFrame(ControlsConfig.RIGHT)) {
				do {
					_currentChoice = (_currentChoice + 1) % 3;
				} while (!isValidChoice(_currentChoice));
			}

			if (Waffle.keyboard.isPressedThisFrame(ControlsConfig.SPACE)) {
				_phase = 3 + _currentChoice;
			}
		} else if (_phase == 3) {
			Particles.spawnBloodParticles(216, 170);
			_isPlayerDead = true;
			_phase = 6;
		} else if (_phase == 4) {
			Particles.spawnBloodParticles(216, 170);
			_isPlayerDead = true;
			_phase = 6;
		} else if (_phase == 5) {
			Particles.spawnBloodParticles(190, 170);
			_isGeneralDead = true;
			_phase = 6;
		} else if (_phase == 6) {
			_animation += dt;

			if (_animation >= 5) {
				Game.instance.setNextScreen(new EndScreen(_currentChoice));
			}
		}
	}

	private boolean isValidChoice(int choice) {
		if (choice == 0 && !_hasChemical) {
			return false;
		}

		if (choice == 2 && !_hasKnife) {
			return false;
		}

		return true;
	}

	@Override
	public void draw(Renderer r) {
		r.fillRect(0, 0, Game.WIDTH, Game.HEIGHT, Color.Black);

		if (_phase == 0) {
			r.setCustomComposite(false, Color.Black, 1 - _animation);
		} else if (_phase == 6) {
			r.setCustomComposite(false, Color.Black, _animation / 5);
		}

		r.beginBlit(Sprites.scene).blit();

		if (_phase == 1) {
			r.fillRect(0, 40, Game.WIDTH, 50, Color.Black);
			r.drawText("Doctor! Did you get it?", Color.White, Fonts.fontSmall,
					20, 50, Font.HorizontalAlign.LEFT, Font.VerticalAlign.TOP);
			r.drawText("Press [space]", Color.Gray, Fonts.fontSmall,
					20, 70, Font.HorizontalAlign.LEFT, Font.VerticalAlign.TOP);
		} else if (_phase == 2) {
			Color color = Color.DarkGray;
			r.fillRect(0, 40, Game.WIDTH, 100, Color.Black);

			printChoice(r, "Give the chemical", 0);
			printChoice(r, "Say you didn't find it", 1);
			printChoice(r, "Stab him", 2);

			r.drawText("Choose with Q and D, then press [space]", Color.Gray, Fonts.fontSmall,
					20, 120, Font.HorizontalAlign.LEFT, Font.VerticalAlign.TOP);
		}

		if (_isPlayerDead) {
			r.beginBlit(Sprites.general.getRegion(1), 180, 163).blit();
		} else if (!_isGeneralDead) {
			r.beginBlit(Sprites.general.getRegion(0), 180, 163).blit();
		}

		if (!_isPlayerDead) {
			r.beginBlit(Sprites.player.getRegion(1), 210, 163).mirrorX().blit();
		}

		if (_isPlayerDead) {
			if (_currentChoice == 0) {
				r.fillRect(0, 40, Game.WIDTH, 30, Color.Black);
				r.drawText("Thank you, doctor!", Color.White, Fonts.fontSmall,
						20, 50, Font.HorizontalAlign.LEFT, Font.VerticalAlign.TOP);
			} else {
				r.fillRect(0, 40, Game.WIDTH, 30, Color.Black);
				r.drawText("You worthless bastard", Color.White, Fonts.fontSmall,
						20, 50, Font.HorizontalAlign.LEFT, Font.VerticalAlign.TOP);
			}
		}

		Particles.bloodParticles.draw(r);

		if (_phase == 0 || _phase == 6) {
			r.endCustomComposite();
		}
	}

	private void printChoice(Renderer r, String s, int i) {
		if (!isValidChoice(i)) {
			return;
		}

		r.drawText(s, (_currentChoice == i) ? Color.White : Color.DarkGray, Fonts.fontSmall,
				20, 50 + i * 20, Font.HorizontalAlign.LEFT, Font.VerticalAlign.TOP);
	}
}

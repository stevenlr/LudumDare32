package com.stevenlr.ld32.components;

import com.stevenlr.ld32.Sprites;
import com.stevenlr.waffle.entitysystem.components.Component;
import com.stevenlr.waffle.graphics.AnimatedSprite;

public class PlayerComponent extends Component {

	public boolean inventory[] = new boolean[4];
	public int selected = 0;
	public float lastDirection = 0;
	public boolean isDead = false;
	public AnimatedSprite.Instance sprite = (AnimatedSprite.Instance) Sprites.playerAnimation.getBlittable();

	public PlayerComponent() {
		for (int i = 0; i < 4; ++i) {
			inventory[i] = false;
		}
	}

	public PlayerComponent(boolean[] state) {
		if (state != null) {
			for (int i = 0; i < 4; ++i) {
				inventory[i] = state[i];
			}
		} else {
			for (int i = 0; i < 4; ++i) {
				inventory[i] = false;
			}
		}
	}
}

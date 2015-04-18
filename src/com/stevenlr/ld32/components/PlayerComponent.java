package com.stevenlr.ld32.components;

import com.stevenlr.waffle.entitysystem.components.Component;

public class PlayerComponent extends Component {

	public boolean inventory[] = new boolean[4];
	public int selected = 0;

	public PlayerComponent() {
		for (int i = 0; i < 4; ++i) {
			inventory[i] = false;
		}
	}
}

package com.stevenlr.ld32.components;

import com.stevenlr.waffle.entitysystem.components.Component;

public class StaticComponent extends Component {

	public float x;
	public float y;

	public StaticComponent(float x, float y) {
		this.x = x;
		this.y = y;
	}
}

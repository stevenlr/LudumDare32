package com.stevenlr.ld32.components;

import com.stevenlr.waffle.entitysystem.components.Component;

public class PhysicalComponent extends Component {

	public float x = 0;
	public float y = 0;
	public float dx = 0;
	public float dy = 0;
	public float ax = 0;
	public float ay = 0;
	public boolean onFloor = false;

	public PhysicalComponent() {
	}

	public PhysicalComponent(float x, float y, float dx, float dy) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
}

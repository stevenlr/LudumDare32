package com.stevenlr.ld32.components;

import com.stevenlr.waffle.entitysystem.components.Component;

public class MagneticDeviceComponent extends Component {

	public float direction;

	public MagneticDeviceComponent(float direction) {
		this.direction = direction;
	}
}

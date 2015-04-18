package com.stevenlr.ld32.components;

import com.stevenlr.waffle.entitysystem.components.Component;
import com.stevenlr.waffle.graphics.IBlittable;

public class StaticTextureRenderComponent extends Component {

	public IBlittable blittable;

	public StaticTextureRenderComponent(IBlittable blittable) {
		this.blittable = blittable;
	}
}

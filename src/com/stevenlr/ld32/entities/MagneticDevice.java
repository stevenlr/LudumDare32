package com.stevenlr.ld32.entities;

import com.stevenlr.ld32.Sprites;
import com.stevenlr.ld32.components.AnimatedSpriteRenderComponent;
import com.stevenlr.ld32.components.CollisionComponent;
import com.stevenlr.ld32.components.MagneticDeviceComponent;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.waffle.entitysystem.entities.Entity;
import com.stevenlr.waffle.graphics.AnimatedSprite;

public class MagneticDevice extends Entity {

	public static final float SX = 6;
	public static final float SY = 6;

	private PhysicalComponent _phys;

	public MagneticDevice(float x, float y, float dx, float dy, float direction) {
		_phys = new PhysicalComponent(x, y, dx, dy);
		CollisionComponent collision = new CollisionComponent(SX, SY, false);
		AnimatedSpriteRenderComponent render;

		if (direction > 0) {
			render = new AnimatedSpriteRenderComponent((AnimatedSprite.Instance) Sprites.blueBallAnimation.getBlittable());
		} else {
			render = new AnimatedSpriteRenderComponent((AnimatedSprite.Instance) Sprites.orangeBallAnimation.getBlittable());
		}

		addComponent(PhysicalComponent.class, _phys);
		addComponent(CollisionComponent.class, collision);
		addComponent(MagneticDeviceComponent.class, new MagneticDeviceComponent(direction));
		addComponent(AnimatedSpriteRenderComponent.class, render);
	}

	public float getX() {
		return _phys.x + SX / 2;
	}

	public float getY() {
		return _phys.y + SY / 2;
	}
}

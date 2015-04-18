package com.stevenlr.ld32.entities;

import com.stevenlr.ld32.Sprites;
import com.stevenlr.ld32.components.CollisionComponent;
import com.stevenlr.ld32.components.MagneticDeviceComponent;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.components.StaticTextureRenderComponent;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class MagneticDevice extends Entity {

	public static final float SX = 8;
	public static final float SY = 8;

	private PhysicalComponent _phys;

	public MagneticDevice(float x, float y, float dx, float dy) {
		_phys = new PhysicalComponent(x, y, dx, dy);
		CollisionComponent collision = new CollisionComponent(SX, SY);

		addComponent(PhysicalComponent.class, _phys);
		addComponent(CollisionComponent.class, collision);
		addComponent(MagneticDeviceComponent.class, new MagneticDeviceComponent());
		addComponent(StaticTextureRenderComponent.class, new StaticTextureRenderComponent(Sprites.ball));
	}

	public float getX() {
		return _phys.x + SX / 2;
	}

	public float getY() {
		return _phys.y + SY / 2;
	}
}

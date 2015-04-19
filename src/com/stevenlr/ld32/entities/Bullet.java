package com.stevenlr.ld32.entities;

import com.stevenlr.ld32.Sprites;
import com.stevenlr.ld32.components.BulletComponent;
import com.stevenlr.ld32.components.MagneticComponent;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.components.StaticTextureRenderComponent;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class Bullet extends Entity {

	public Bullet(float x, float y, float dx, float dy) {
		addComponent(PhysicalComponent.class, new PhysicalComponent(x, y, dx, dy, false));
		addComponent(BulletComponent.class, new BulletComponent());
		addComponent(StaticTextureRenderComponent.class, new StaticTextureRenderComponent(Sprites.bullet));
		addComponent(MagneticComponent.class, new MagneticComponent());
	}
}

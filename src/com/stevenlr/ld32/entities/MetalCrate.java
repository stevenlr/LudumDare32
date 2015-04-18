package com.stevenlr.ld32.entities;

import com.stevenlr.ld32.Sprites;
import com.stevenlr.ld32.components.CollisionComponent;
import com.stevenlr.ld32.components.MagneticComponent;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.components.StaticTextureRenderComponent;
import com.stevenlr.ld32.level.Tile;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class MetalCrate extends Entity {

	public static final float SX = Tile.SIZE;
	public static final float SY = Tile.SIZE;

	public MetalCrate(float x, float y) {
		PhysicalComponent phys = new PhysicalComponent(x, y, 0, 0);
		CollisionComponent collision = new CollisionComponent(SX, SY);
		StaticTextureRenderComponent render = new StaticTextureRenderComponent(Sprites.tiles.getRegion(2).getBlittable());

		addComponent(PhysicalComponent.class, phys);
		addComponent(CollisionComponent.class, collision);
		addComponent(StaticTextureRenderComponent.class, render);
		addComponent(MagneticComponent.class, new MagneticComponent());
	}
}

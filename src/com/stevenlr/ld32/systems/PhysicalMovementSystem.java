package com.stevenlr.ld32.systems;

import java.util.Iterator;
import java.util.List;

import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.level.Tile;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class PhysicalMovementSystem extends com.stevenlr.waffle.entitysystem.systems.System {

	public static final float GRAVITY = 9.8f * Tile.SIZE * 10;

	public void update(float dt) {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(PhysicalComponent.class);
		Iterator<Entity> it = entities.iterator();

		while (it.hasNext()) {
			Entity e = it.next();
			PhysicalComponent phys = e.getComponent(PhysicalComponent.class);

			phys.dy += GRAVITY * dt;
			phys.x += phys.dx * dt;
			phys.y += phys.dy * dt;
		}
	}
}

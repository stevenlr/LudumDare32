package com.stevenlr.ld32.systems;

import java.util.Iterator;
import java.util.List;

import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.components.CollisionComponent;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.level.Level;
import com.stevenlr.ld32.level.Tile;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class PhysicalMovementSystem extends com.stevenlr.waffle.entitysystem.systems.System {

	public static final float GRAVITY = 9.8f * Tile.SIZE * 10;
	public static final float MAX_SPEED = Tile.SIZE;
	public static final float FLOOR_FRICTION = 0.8f;

	public void update(float dt) {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(PhysicalComponent.class);
		Iterator<Entity> it = entities.iterator();
		Level level = Game.instance.getLevel();

		while (it.hasNext()) {
			Entity e = it.next();
			PhysicalComponent phys = e.getComponent(PhysicalComponent.class);

			phys.ay += GRAVITY * dt;

			if (phys.onFloor) {
				phys.dx *= FLOOR_FRICTION;
			}

			phys.dx += phys.ax * dt;
			phys.dy += phys.ay * dt;

			phys.dx = Math.min(Math.max(phys.dx, -MAX_SPEED), MAX_SPEED);
			phys.dy = Math.min(Math.max(phys.dy, -MAX_SPEED), MAX_SPEED);

			if (e.hasComponent(CollisionComponent.class)) {
				CollisionComponent box = e.getComponent(CollisionComponent.class);

				level.tryMove(phys, phys.dx, 0, box);
				level.tryMove(phys, 0, phys.dy, box);
			} else {
				phys.x += phys.dx * dt;
				phys.y += phys.dy * dt;
			}

			phys.ax = 0;
			phys.ay = 0;
		}
	}
}

package com.stevenlr.ld32.systems;

import java.util.Iterator;
import java.util.List;

import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.components.CollisionComponent;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.components.PlayerComponent;
import com.stevenlr.ld32.level.Level;
import com.stevenlr.ld32.level.Tile;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class PhysicalMovementSystem extends com.stevenlr.waffle.entitysystem.systems.System {

	public static final float GRAVITY = 9.8f * Tile.SIZE * 1000;
	public static final float MAX_SPEED = Tile.SIZE * 30;
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

				level.tryMove(phys, phys.dx * dt, 0, box);
				level.tryMove(phys, 0, phys.dy * dt, box);

				tryMove(e, 0, phys.dy * dt);
				tryMove(e, phys.dx * dt, 0);
			}

			phys.x += phys.dx * dt;
			phys.y += phys.dy * dt;

			phys.ax = 0;
			phys.ay = 0;
		}
	}

	private void tryMove(Entity entity, float dx, float dy) {
		PhysicalComponent phys = entity.getComponent(PhysicalComponent.class);
		CollisionComponent box = entity.getComponent(CollisionComponent.class);

		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(PhysicalComponent.class, CollisionComponent.class);
		Iterator<Entity> it = entities.iterator();

		float x = phys.x + dx;
		float y = phys.y + dy;
		boolean collision = false;

		while (it.hasNext()) {
			Entity e = it.next();

			if (entity == e || e.hasComponent(PlayerComponent.class)) {
				continue;
			}

			PhysicalComponent phys2 = e.getComponent(PhysicalComponent.class);
			CollisionComponent box2 = e.getComponent(CollisionComponent.class);

			if (overlaps(x, y, box.sx, box.sy, phys2.x, phys2.y, box2.sx, box2.sy)) {
				float[] penetration = getShortestPenetration(x, y, box.sx, box.sy, phys2.x, phys2.y, box2.sx, box2.sy);

				x -= penetration[0];
				y -= penetration[1];

				if (penetration[0] != 0 && dx != 0) {
					phys.dx = 0;
					collision = true;
				}

				if (penetration[1] != 0 && dy != 0) {
					if (dy > 0) {
						phys.onFloor = true;
					}

					phys.dy = 0;
					collision = true;
				}
			}
		}

		if (collision) {
			phys.x = x;
			phys.y = y;
		}
	}

	private float[] getShortestPenetration(float x1, float y1, float sx1, float sy1, float x2, float y2, float sx2, float sy2) {
		float[] penetration = new float[2];
		float currentMin = 100000000;

		penetration[0] = 0;
		penetration[1] = 0;

		if (x1 < x2 + sx2 && x1 + sx1 > x2) {
			float p = x1 + sx1 - x2;

			if (Math.abs(p) < currentMin) {
				currentMin = Math.abs(p);
				penetration[0] = p;
				penetration[1] = 0;
			}
		}

		if (x2 < x1 + sx1 && x2 + sx2 > x1) {
			float p = x1 - x2 - sx2;

			if (Math.abs(p) < currentMin) {
				currentMin = Math.abs(p);
				penetration[0] = p;
				penetration[1] = 0;
			}
		}

		if (y1 < y2 + sy2 && y1 + sy1 > y2) {
			float p = y1 + sy1 - y2;

			if (Math.abs(p) < currentMin) {
				currentMin = Math.abs(p);
				penetration[0] = 0;
				penetration[1] = p;
			}
		}

		if (y2 < y1 + sy1 && y2 + sy2 > y1) {
			float p = y1 - y2 - sy2;

			if (Math.abs(p) < currentMin) {
				penetration[0] = 0;
				penetration[1] = p;
			}
		}

		return penetration;
	}

	private boolean overlaps(float x1, float y1, float sx1, float sy1, float x2, float y2, float sx2, float sy2) {
		return (((x1 < x2 + sx2 && x1 + sx1 > x2) || (x2 < x1 + sx1 && x2 + sx2 > x1))
				&& ((y1 < y2 + sy2 && y1 + sy1 > y2) || (y2 < y1 + sy1 && y2 + sy2 > y1)));
	}
}

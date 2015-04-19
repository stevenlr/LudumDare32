package com.stevenlr.ld32.systems;

import java.util.Iterator;
import java.util.List;

import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.Particles;
import com.stevenlr.ld32.Sounds;
import com.stevenlr.ld32.components.BulletComponent;
import com.stevenlr.ld32.components.CollisionComponent;
import com.stevenlr.ld32.components.DroneComponent;
import com.stevenlr.ld32.components.KillableComponent;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.components.PlayerComponent;
import com.stevenlr.ld32.components.StaticComponent;
import com.stevenlr.ld32.entities.Player;
import com.stevenlr.ld32.level.Level;
import com.stevenlr.ld32.level.Tile;
import com.stevenlr.ld32.screens.GameScreen;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class BulletLogicSystem extends com.stevenlr.waffle.entitysystem.systems.System {

	public void update(float dt) {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(BulletComponent.class);
		Iterator<Entity> it = entities.iterator();

		List<Entity> killable = Waffle.entitySystem.getEntitiesWithComponents(KillableComponent.class, CollisionComponent.class);
		List<Entity> collisions = Waffle.entitySystem.getEntitiesWithComponents(CollisionComponent.class);
		Level level = ((GameScreen) Game.instance.getCurrentScreen()).getLevel();

		while (it.hasNext()) {
			Entity e = it.next();
			BulletComponent bullet = e.getComponent(BulletComponent.class);
			PhysicalComponent phys = e.getComponent(PhysicalComponent.class);
			boolean killed = false;

			bullet.age += dt;

			if (bullet.age >= 5) {
				Waffle.entitySystem.removeEntity(e);
				continue;
			}

			int tx = (int) (phys.x / Tile.SIZE);
			int ty = (int) (phys.y / Tile.SIZE);

			if (level.getTile(tx, ty).hasCollision()) {
				Waffle.entitySystem.removeEntity(e);
				continue;
			}

			Iterator<Entity> it2 = killable.iterator();

			while (it2.hasNext() && !killed) {
				Entity e2 = it2.next();

				float x, y;

				if (e2.hasComponent(PhysicalComponent.class)) {
					PhysicalComponent phys2 = e2.getComponent(PhysicalComponent.class);

					x = phys2.x;
					y = phys2.y;
				} else if (e2.hasComponent(StaticComponent.class)) {
					StaticComponent pos = e2.getComponent(StaticComponent.class);

					x = pos.x;
					y = pos.y;
				} else {
					continue;
				}

				CollisionComponent box = e2.getComponent(CollisionComponent.class);

				if (level.overlaps(x, y, box.sx, box.sy, phys.x, phys.y, 3, 3)) {
					it2.remove();

					if (e2.hasComponent(PlayerComponent.class)) {
						Particles.spawnBloodParticles(x + box.sx / 2, y + box.sy / 2);
						((Player) e2).die();
						level.setDeathCause("You got shot");
					} else if (e2.hasComponent(DroneComponent.class)) {
						Particles.spawnSmokeParticles(x + box.sx / 2, y + box.sy / 2);
						Sounds.explode.play();
					}

					Waffle.entitySystem.removeEntity(e);
					Waffle.entitySystem.removeEntity(e2);
					killed = true;
				}
			}

			Iterator<Entity> it3 = collisions.iterator();

			while (it3.hasNext() && !killed) {
				Entity e3 = it3.next();

				float x, y;

				if (e3.hasComponent(PhysicalComponent.class)) {
					PhysicalComponent phys2 = e3.getComponent(PhysicalComponent.class);

					x = phys2.x;
					y = phys2.y;
				} else if (e3.hasComponent(StaticComponent.class)) {
					StaticComponent pos = e3.getComponent(StaticComponent.class);

					x = pos.x;
					y = pos.y;
				} else {
					continue;
				}

				CollisionComponent box = e3.getComponent(CollisionComponent.class);

				if (level.overlaps(x, y, box.sx, box.sy, phys.x, phys.y, 3, 3)) {
					killed = true;
					Waffle.entitySystem.removeEntity(e);
				}
			}
		}
	}
}

package com.stevenlr.ld32.systems;

import java.util.Iterator;
import java.util.List;

import com.stevenlr.ld32.components.BulletComponent;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class BulletLogicSystem extends com.stevenlr.waffle.entitysystem.systems.System {

	public void update(float dt) {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(BulletComponent.class);
		Iterator<Entity> it = entities.iterator();

		while (it.hasNext()) {
			Entity e = it.next();
			BulletComponent bullet = e.getComponent(BulletComponent.class);

			bullet.age += dt;

			if (bullet.age >= 5) {
				Waffle.entitySystem.removeEntity(e);
			}
		}
	}
}

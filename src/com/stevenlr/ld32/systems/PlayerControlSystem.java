package com.stevenlr.ld32.systems;

import java.util.Iterator;
import java.util.List;

import com.stevenlr.ld32.KeyConfig;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.components.PlayerComponent;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class PlayerControlSystem extends com.stevenlr.waffle.entitysystem.systems.System {

	public PlayerControlSystem() {
	}

	public void update (float dt) {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(PhysicalComponent.class, PlayerComponent.class);
		Iterator<Entity> it = entities.iterator();

		while (it.hasNext()) {
			Entity e = it.next();
			PhysicalComponent phys = e.getComponent(PhysicalComponent.class);

			if (Waffle.keyboard.isDown(KeyConfig.JUMP) && phys.onFloor) {
				phys.ay -= 26000;
			}

			if (Waffle.keyboard.isDown(KeyConfig.LEFT)) {
				if (phys.onFloor) {
					phys.ax -= 2000;
				} else {
					phys.ax -= 50;
				}
			}

			if (Waffle.keyboard.isDown(KeyConfig.RIGHT)) {
				if (phys.onFloor) {
					phys.ax += 2000;
				} else {
					phys.ax += 50;
				}
			}
		}
	}
}

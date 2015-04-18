package com.stevenlr.ld32.systems;

import java.util.Iterator;
import java.util.List;

import com.stevenlr.ld32.ControlsConfig;
import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.components.PlayerComponent;
import com.stevenlr.ld32.entities.MagneticDevice;
import com.stevenlr.ld32.entities.Player;
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

			if (Waffle.keyboard.isDown(ControlsConfig.JUMP) && phys.onFloor) {
				phys.ay -= 26000;
			}

			if (Waffle.keyboard.isDown(ControlsConfig.LEFT)) {
				if (phys.onFloor) {
					phys.ax -= 2000;
				} else {
					phys.ax -= 50;
				}
			}

			if (Waffle.keyboard.isDown(ControlsConfig.RIGHT)) {
				if (phys.onFloor) {
					phys.ax += 2000;
				} else {
					phys.ax += 50;
				}
			}

			if (Waffle.mouse.isPressedThisFrame(ControlsConfig.LAUNCH)) {
				float velocity = 1000;
				Game.instance.getLevel().removeOldDevices();
				new MagneticDevice(phys.x + Player.SX, phys.y, velocity, -velocity);
			}
		}
	}
}

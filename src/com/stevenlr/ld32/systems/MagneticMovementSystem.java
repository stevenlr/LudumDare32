package com.stevenlr.ld32.systems;

import java.util.Iterator;
import java.util.List;

import com.stevenlr.ld32.ControlsConfig;
import com.stevenlr.ld32.components.MagneticComponent;
import com.stevenlr.ld32.components.MagneticDeviceComponent;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.entities.MagneticDevice;
import com.stevenlr.ld32.level.Tile;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class MagneticMovementSystem extends com.stevenlr.waffle.entitysystem.systems.System {

	private MagneticDevice getDevice() {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(MagneticDeviceComponent.class);

		if (entities.size() == 0) {
			return null;
		}

		return (MagneticDevice) entities.get(0);
	}

	public void update(float dt) {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(MagneticComponent.class, PhysicalComponent.class);
		Iterator<Entity> it = entities.iterator();

		MagneticDevice device = getDevice();

		if (device == null) {
			return;
		}

		float centerX = device.getX();
		float centerY = device.getY();

		while (it.hasNext()) {
			Entity e = it.next();
			PhysicalComponent phys = e.getComponent(PhysicalComponent.class);

			float dx = phys.x - centerX;
			float dy = phys.y - centerY;
			float dist = (float) Math.sqrt(dx * dx + dy * dy);
			float factor = 0;

			if (Waffle.keyboard.isDown(ControlsConfig.ATTRACT)) {
				factor += -1;
			}

			if (Waffle.keyboard.isDown(ControlsConfig.REPEL)) {
				factor += 1;
			}

			float force = (float) (10000 / Math.pow(dist / Tile.SIZE / 4, 2) * factor);

			float ax = force * dx / dist;
			float ay = force * dy / dist;

			phys.ax += ax;
			phys.ay += ay;
		}
	}

	public void removeOldDevices() {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(MagneticDeviceComponent.class);
		Iterator<Entity> it = entities.iterator();

		while (it.hasNext()) {
			Waffle.entitySystem.removeEntity(it.next());
		}
	}
}

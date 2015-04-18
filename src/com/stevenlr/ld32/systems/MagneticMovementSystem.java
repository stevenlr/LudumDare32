package com.stevenlr.ld32.systems;

import java.util.Iterator;
import java.util.List;

import com.stevenlr.ld32.ControlsConfig;
import com.stevenlr.ld32.Particles;
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

			if (Waffle.keyboard.isDown(ControlsConfig.ACTION)) {
				factor = device.getComponent(MagneticDeviceComponent.class).direction;
			}

			float strength = (float) (1500 / Math.pow(dist / Tile.SIZE / 3, 2) * factor);

			float ax = strength * dx / dist;
			float ay = strength * dy / dist;

			phys.ax += ax;
			phys.ay += ay;
		}
	}

	public void removeOldDevices() {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(MagneticDeviceComponent.class);
		Iterator<Entity> it = entities.iterator();

		while (it.hasNext()) {
			Entity e = it.next();

			float x = e.getComponent(PhysicalComponent.class).x;
			float y = e.getComponent(PhysicalComponent.class).y;

			Particles.spawnSmokeParticles(x, y);
			Waffle.entitySystem.removeEntity(e);
		}
	}
}

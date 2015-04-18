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
			PlayerComponent player = e.getComponent(PlayerComponent.class);

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

			if (Waffle.mouse.isPressedThisFrame(ControlsConfig.LAUNCH)
					&& (player.selected == Player.BLUE_DEVICE || player.selected == Player.ORANGE_DEVICE)) {
				float velocity = 800;
				float clickX = Waffle.mouse.getMouseX() / Game.PIXEL_SIZE + Game.instance.getLevel().getOffsetX();
				float clickY = Waffle.mouse.getMouseY() / Game.PIXEL_SIZE + Game.instance.getLevel().getOffsetY();

				float dx = clickX - phys.x - Player.SX / 2;
				float dy = clickY - phys.y - Player.SY / 2;
				float dist = (float) Math.sqrt(dx * dx + dy * dy);
				float direction = 0;

				if (player.selected == Player.BLUE_DEVICE && player.inventory[Player.BLUE_DEVICE]) {
					direction = -1;
				} else if (player.selected == Player.ORANGE_DEVICE && player.inventory[Player.ORANGE_DEVICE]){
					direction = 1;
				}

				dx /= dist;
				dy /= dist;

				if (direction != 0) {
					Game.instance.getLevel().removeOldDevices();
					new MagneticDevice(phys.x + dx * 10, phys.y + dy * 10, dx * velocity + phys.dx, dy * velocity + phys.dy, direction);
				}
			}

			if (Waffle.mouse.getWheelRotation() != 0) {
				player.selected += Waffle.mouse.getWheelRotation();

				while (player.selected < 0 || player.selected >= 4) {
					player.selected = (player.selected + 4) % 4;
				}
			}

			if (Waffle.keyboard.isPressedThisFrame(ControlsConfig.SELECT_BLUE_DEVICE)) {
				player.selected = Player.BLUE_DEVICE;
			}

			if (Waffle.keyboard.isPressedThisFrame(ControlsConfig.SELECT_ORANGE_DEVICE)) {
				player.selected = Player.ORANGE_DEVICE;
			}

			if (Waffle.keyboard.isPressedThisFrame(ControlsConfig.SELECT_KNIFE)) {
				player.selected = Player.KNIFE;
			}

			if (Waffle.keyboard.isPressedThisFrame(ControlsConfig.SELECT_CHEMICALS)) {
				player.selected = Player.CHEMICALS;
			}
		}
	}
}

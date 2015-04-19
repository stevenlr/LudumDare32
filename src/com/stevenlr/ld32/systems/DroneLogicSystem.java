package com.stevenlr.ld32.systems;

import java.util.Iterator;
import java.util.List;

import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.Sprites;
import com.stevenlr.ld32.components.DroneComponent;
import com.stevenlr.ld32.components.StaticComponent;
import com.stevenlr.ld32.entities.Drone;
import com.stevenlr.ld32.entities.Player;
import com.stevenlr.ld32.screens.GameScreen;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.entitysystem.entities.Entity;
import com.stevenlr.waffle.graphics.Renderer;

public class DroneLogicSystem extends com.stevenlr.waffle.entitysystem.systems.System {

	public void update(float dt) {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(DroneComponent.class);
		Iterator<Entity> it = entities.iterator();

		Player player = ((GameScreen) Game.instance.getCurrentScreen()).getLevel().getPlayer();

		while (it.hasNext()) {
			Entity e = it.next();
			StaticComponent pos = e.getComponent(StaticComponent.class);

			float x = pos.x + Drone.SX / 2;
			float y = pos.y + Drone.SY / 2;

			float px = player.getX() + Player.SX / 2;
			float py = player.getY() + Player.SY / 2;

			float dx = px - x;
			float dy = py - y;

			e.getComponent(DroneComponent.class).rotation = (float) Math.atan2(dy, dx);
		}
	}

	public void draw(Renderer r) {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(DroneComponent.class);
		Iterator<Entity> it = entities.iterator();

		while (it.hasNext()) {
			Entity e = it.next();
			float rotation = e.getComponent(DroneComponent.class).rotation;
			StaticComponent pos = e.getComponent(StaticComponent.class);

			r.save();
			r.translate(pos.x, pos.y);
			r.rotate(Drone.SX / 2, Drone.SY / 2, rotation);
			r.beginBlit(Sprites.drone.getRegion(1)).blit();
			r.restore();
		}
	}
}

package com.stevenlr.ld32.systems;

import java.util.Iterator;
import java.util.List;

import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.components.ItemComponent;
import com.stevenlr.ld32.components.PlayerComponent;
import com.stevenlr.ld32.components.StaticComponent;
import com.stevenlr.ld32.entities.Player;
import com.stevenlr.ld32.level.Tile;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class ItemManagerSystem extends com.stevenlr.waffle.entitysystem.systems.System {

	public void update(float dt) {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(ItemComponent.class);
		Iterator<Entity> it = entities.iterator();
		Player player = Game.instance.getLevel().getPlayer();

		while (it.hasNext()) {
			Entity e = it.next();
			StaticComponent pos = e.getComponent(StaticComponent.class);

			pos.y += Math.cos(Game.instance.getTime() * 3.0f) * 0.075f;

			float dx = player.getX() - pos.x;
			float dy = player.getY() - pos.y;
			float dist = (float) Math.sqrt(dx * dx + dy * dy);

			if (dist < Tile.SIZE) {
				player.getComponent(PlayerComponent.class).inventory[e.getComponent(ItemComponent.class).item] = true;
				Waffle.entitySystem.removeEntity(e);
			}
		}
	}
}

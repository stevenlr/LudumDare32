package com.stevenlr.ld32.systems;

import java.util.Iterator;
import java.util.List;

import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.Sounds;
import com.stevenlr.ld32.components.NoteComponent;
import com.stevenlr.ld32.components.StaticComponent;
import com.stevenlr.ld32.entities.Player;
import com.stevenlr.ld32.level.Tile;
import com.stevenlr.ld32.screens.GameScreen;
import com.stevenlr.ld32.screens.NoteScreen;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class NoteManagerSystem extends com.stevenlr.waffle.entitysystem.systems.System {

	public void update(float dt) {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(NoteComponent.class);
		Iterator<Entity> it = entities.iterator();
		Player player = ((GameScreen) Game.instance.getCurrentScreen()).getLevel().getPlayer();

		while (it.hasNext()) {
			Entity e = it.next();
			StaticComponent pos = e.getComponent(StaticComponent.class);

			pos.y += Math.cos(Game.instance.getTime() * 3.0f) * 0.075f;

			float dx = player.getX() - pos.x;
			float dy = player.getY() - pos.y;
			float dist = (float) Math.sqrt(dx * dx + dy * dy);

			if (dist < Tile.SIZE) {
				int id = e.getComponent(NoteComponent.class).id;

				Waffle.entitySystem.removeEntity(e);
				Game.instance.setNextScreen(new NoteScreen(id, Game.instance.getCurrentScreen()));
				Sounds.grab.play();
			}
		}
	}
}

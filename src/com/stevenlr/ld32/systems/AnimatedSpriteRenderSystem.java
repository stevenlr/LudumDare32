package com.stevenlr.ld32.systems;

import java.util.Iterator;
import java.util.List;

import com.stevenlr.ld32.components.AnimatedSpriteRenderComponent;
import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.entitysystem.entities.Entity;
import com.stevenlr.waffle.graphics.Renderer;

public class AnimatedSpriteRenderSystem extends com.stevenlr.waffle.entitysystem.systems.System {

	public void update(float dt) {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(AnimatedSpriteRenderComponent.class);
		Iterator<Entity> it = entities.iterator();

		while (it.hasNext()) {
			it.next().getComponent(AnimatedSpriteRenderComponent.class).sprite.update(dt);
		}
	}

	public void draw(Renderer r) {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(PhysicalComponent.class, AnimatedSpriteRenderComponent.class);
		Iterator<Entity> it = entities.iterator();

		while (it.hasNext()) {
			Entity e = it.next();
			PhysicalComponent phys = e.getComponent(PhysicalComponent.class);
			AnimatedSpriteRenderComponent render = e.getComponent(AnimatedSpriteRenderComponent.class);

			r.beginBlit(render.sprite, phys.x, phys.y).blit();
		}
	}
}

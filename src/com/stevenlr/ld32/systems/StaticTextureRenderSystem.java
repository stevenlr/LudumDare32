package com.stevenlr.ld32.systems;

import java.util.Iterator;
import java.util.List;

import com.stevenlr.ld32.components.PhysicalComponent;
import com.stevenlr.ld32.components.StaticTextureRenderComponent;
import com.stevenlr.waffle.Waffle;
import com.stevenlr.waffle.entitysystem.entities.Entity;
import com.stevenlr.waffle.graphics.Renderer;

public class StaticTextureRenderSystem extends com.stevenlr.waffle.entitysystem.systems.System {

	public void draw(Renderer r) {
		List<Entity> entities = Waffle.entitySystem.getEntitiesWithComponents(PhysicalComponent.class, StaticTextureRenderComponent.class);
		Iterator<Entity> it = entities.iterator();

		while (it.hasNext()) {
			Entity e = it.next();
			PhysicalComponent phys = e.getComponent(PhysicalComponent.class);
			StaticTextureRenderComponent render = e.getComponent(StaticTextureRenderComponent.class);

			r.beginBlit(render.blittable, phys.x, phys.y).blit();
		}
	}
}

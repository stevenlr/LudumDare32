package com.stevenlr.ld32.entities;

import com.stevenlr.ld32.Sprites;
import com.stevenlr.ld32.components.CollisionComponent;
import com.stevenlr.ld32.components.DroneComponent;
import com.stevenlr.ld32.components.KillableComponent;
import com.stevenlr.ld32.components.StaticComponent;
import com.stevenlr.ld32.components.StaticTextureRenderComponent;
import com.stevenlr.waffle.entitysystem.entities.Entity;

public class Drone extends Entity {

	public static final float SX = 16;
	public static final float SY = 16;

	public Drone(float x, float y) {
		addComponent(StaticComponent.class, new StaticComponent(x, y));
		addComponent(StaticTextureRenderComponent.class, new StaticTextureRenderComponent(Sprites.drone.getRegion(0)));
		addComponent(CollisionComponent.class, new CollisionComponent(SX, SY));
		addComponent(DroneComponent.class, new DroneComponent());
		addComponent(KillableComponent.class, new KillableComponent());
	}
}

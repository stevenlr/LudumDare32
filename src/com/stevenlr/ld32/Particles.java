package com.stevenlr.ld32;

import com.stevenlr.ld32.level.Tile;
import com.stevenlr.waffle.particles.ParticleSystem;
import com.stevenlr.waffle.particles.spawner.LongParticleSpawner;
import com.stevenlr.waffle.particles.spawner.OnceParticleSpawner;
import com.stevenlr.waffle.particles.spawner.ParticleSpawner;
import com.stevenlr.waffle.particles.spawner.PointParticleSpawner;
import com.stevenlr.waffle.particles.spawner.RandomVelocityParticleSpawner;

public class Particles {

	public static ParticleSystem bloodParticles = new ParticleSystem(Sprites.bloodAnimation, 50);
	public static ParticleSystem launchParticle = new ParticleSystem(Sprites.launchParticle, 100);
	public static ParticleSystem smokeParticles = new ParticleSystem(Sprites.smokeAnimation, 100);

	static {
		bloodParticles.setDuration(0.12f * 6 - 0.05f);
		bloodParticles.setLinearAcceleration(0, 10 * Tile.SIZE);

		launchParticle.setDuration(2.0f);
		launchParticle.setLinearAcceleration(0, 50 * Tile.SIZE);

		smokeParticles.setDuration(1.4f);
		smokeParticles.setLinearDamping(0.05f);
	}

	public static void spawnBloodParticles(float x, float y) {
		ParticleSpawner spawner = new RandomVelocityParticleSpawner(30, 100, 0, -1, 0.7f,
				new PointParticleSpawner(x, y,
						new OnceParticleSpawner(32, 0)));

		bloodParticles.addSpawner(spawner);
	}

	public static void spawnLaunchParticles(float x, float y, float dx, float dy) {
		ParticleSpawner spawner = new RandomVelocityParticleSpawner(150, 400, dx, dy, 0.1f,
				new PointParticleSpawner(x, y,
						new OnceParticleSpawner(16, 0)));

		launchParticle.addSpawner(spawner);
	}

	public static void spawnSmokeParticles(float x, float y) {
		ParticleSpawner spawner = new RandomVelocityParticleSpawner(20, 60,
				new PointParticleSpawner(x, y,
						new LongParticleSpawner(8, 0.2f)));

		smokeParticles.addSpawner(spawner);
	}

	public static void update(float dt) {
		bloodParticles.update(dt);
		launchParticle.update(dt);
		smokeParticles.update(dt);
	}
}

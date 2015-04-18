package com.stevenlr.ld32;

import com.stevenlr.ld32.level.Tile;
import com.stevenlr.waffle.particles.ParticleSystem;
import com.stevenlr.waffle.particles.spawner.OnceParticleSpawner;
import com.stevenlr.waffle.particles.spawner.ParticleSpawner;
import com.stevenlr.waffle.particles.spawner.PointParticleSpawner;
import com.stevenlr.waffle.particles.spawner.RandomVelocityParticleSpawner;

public class Particles {

	public static ParticleSystem bloodParticles = new ParticleSystem(Sprites.bloodAnimation, 50);

	static {
		bloodParticles.setDuration(0.12f * 6 - 0.05f);
		bloodParticles.setLinearAcceleration(0, 10 * Tile.SIZE);
	}

	public static void spawnBloodParticles(float x, float y) {
		ParticleSpawner spawner = new RandomVelocityParticleSpawner(30, 100, 0, -1, 0.7f,
				new PointParticleSpawner(x, y,
						new OnceParticleSpawner(32, 0)));

		bloodParticles.addSpawner(spawner);
	}
}

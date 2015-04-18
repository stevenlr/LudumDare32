package com.stevenlr.ld32.screens;

import com.stevenlr.waffle.graphics.Renderer;

public interface IScreen {

	void update(float dt);
	void draw(Renderer r);
}

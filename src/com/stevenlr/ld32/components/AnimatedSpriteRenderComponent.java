package com.stevenlr.ld32.components;

import com.stevenlr.waffle.entitysystem.components.Component;
import com.stevenlr.waffle.graphics.AnimatedSprite;

public class AnimatedSpriteRenderComponent extends Component {

	public AnimatedSprite.Instance sprite;

	public AnimatedSpriteRenderComponent(AnimatedSprite.Instance sprite) {
		this.sprite = sprite;
	}
}

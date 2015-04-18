package com.stevenlr.ld32.components;

import com.stevenlr.waffle.entitysystem.components.Component;

public class ItemComponent extends Component {

	public int item;

	public ItemComponent(int item) {
		this.item = item;
	}
}

package com.stevenlr.ld32.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.stevenlr.ld32.Game;
import com.stevenlr.ld32.Sprites;
import com.stevenlr.ld32.entities.Player;
import com.stevenlr.waffle.graphics.Renderer;

public class Level {

	private Tile[] _tiles;
	private int _width;
	private int _height;
	private int _playerSpawnX;
	private int _playerSpawnY;
	private Player _player;

	public Level(String filename) {
		BufferedImage img = null;

		try {
			img = ImageIO.read(Game.class.getResourceAsStream(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}

		_width = img.getWidth();
		_height = img.getHeight();
		_tiles = new Tile[_width * _height];

		for (int y = 0; y < _height; ++y) {
			for (int x = 0; x < _width; ++x) {
				int i = y * _width + x;
				Tile tile = Tile.wall;

				switch (img.getRGB(x, y) & 0xffffff) {
				case 0x00ff00:
					_playerSpawnX = x;
					_playerSpawnY = y;
					tile = Tile.empty;
					break;
				case 0xffffff:
					tile = Tile.empty;
					break;
				case 0x000000:
					tile = Tile.wall;
					break;
				case 0xff8000:
					tile = Tile.empty;
					break;
				}

				_tiles[i] = tile;
			}
		}

		_player = new Player(_playerSpawnX, _playerSpawnY);
	}

	public void draw(Renderer r) {
		for (int y = 0; y < _height; ++y) {
			for (int x = 0; x < _width; ++x) {
				_tiles[x + y * _width].draw(r, x * Tile.SIZE, y * Tile.SIZE);
			}
		}

		_player.draw(r);
	}
}

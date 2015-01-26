package com.unfinished.asteroids.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.unfinished.asteroids.AsteroidsGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Untitled";
        config.width=320;
        config.height=240;

		new LwjglApplication(new AsteroidsGame(), config);
	}
}

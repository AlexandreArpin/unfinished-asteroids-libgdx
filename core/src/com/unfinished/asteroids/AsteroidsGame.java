package com.unfinished.asteroids;

import com.badlogic.gdx.Game;

public class AsteroidsGame extends Game {

	public static final int TEAM_BLUE = 0;
	public static final int TEAM_RED = 1;
	
	@Override
	public void create () {
        this.setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
        super.render();
	}

    @Override
    public void dispose() {

    }
}

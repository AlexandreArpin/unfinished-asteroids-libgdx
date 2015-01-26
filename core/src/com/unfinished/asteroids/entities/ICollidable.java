package com.unfinished.asteroids.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Alex on 14-10-25.
 */
public interface ICollidable {
    public boolean collision(Actor actor);
}

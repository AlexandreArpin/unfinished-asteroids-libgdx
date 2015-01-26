package com.unfinished.asteroids;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Created by Alex on 2014-10-20.
 */
public class Utils {

    public static float wrap(float value, float min, double max) {
        if (value < min) return (float) (max + (value % max));
        if (value >= max) return (float) (value % max);
        return value;
    }

    public static float distance(Actor a, Actor b) {
        float dx = a.getX(Align.center) - b.getX(Align.center);
        float dy = a.getY(Align.center) - b.getY(Align.center);

        return (float)Math.sqrt(dx * dx + dy * dy);
    }
}

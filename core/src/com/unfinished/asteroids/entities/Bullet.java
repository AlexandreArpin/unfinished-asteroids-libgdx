package com.unfinished.asteroids.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unfinished.asteroids.Utils;

public class Bullet extends Actor implements ICollidable {

    //Movement
    float direction = 0f;
    float speed = 300f;

    float lifespan = 2f;
    int team = 0;

    public Bullet(float x, float y, float direction, int team) {
        this.direction = direction;
        this.team = team;
        this.setPosition(x, y);
        this.setWidth(6);
        this.setHeight(6);
        this.setOrigin(this.getWidth() / 2, this.getWidth() / 2);
    }

    public int getTeam() {
        return team;
    }

    @Override
    public void act(float delta) {
        /* movement */
        this.setX((float) (this.getX() + Math.cos(this.direction) * this.speed * delta));
        this.setY((float) (this.getY() + Math.sin(this.direction) * this.speed * delta));

        /* wrap entity */
        this.setX(Utils.wrap(this.getX(), 0, Gdx.graphics.getWidth()));
        this.setY(Utils.wrap(this.getY(), 0, Gdx.graphics.getHeight()));

        if((this.lifespan -= delta) <= 0){
            this.remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.end();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        //Placeholder bullet shape
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(this.getX(), this.getY(), this.getWidth()/2);
        shapeRenderer.end();

        batch.begin();
    }

    @Override
    public boolean collision(Actor actor) {
        if(actor instanceof Asteroid){
            ((Asteroid)actor).hit(1);
            this.remove();
        }

        return false;
    }
}

package com.unfinished.asteroids.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.unfinished.asteroids.AsteroidAssetManager;
import com.unfinished.asteroids.Utils;

/**
 * Created by Alex on 2014-10-20.
 */
public class Asteroid extends Image implements ICollidable {

    //Movement
    float direction = 0f;
    float speed = 50f;

    int splits = 3;
    float scale = ((float)this.splits + 1.0f) / 4.0f;
    int hp = (int) this.scale * 10;

    public Asteroid(float x, float y, int splits, float direction){
        super(AsteroidAssetManager.getInstance().getAsteroidTexture());
        this.direction = direction;
        this.splits = splits;
        this.scale = ((float)this.splits + 1.0f) / 4.0f;
        this.hp = (int) this.scale * 10;

        this.setPosition(x, y);
        this.setWidth(this.getWidth() * scale);
        this.setHeight(this.getHeight() * scale);
        this.setOrigin(this.getWidth() / 2, this.getWidth() / 2);
        this.setRotation((float)((this.direction - Math.PI /2)/Math.PI) * 180);
    }

    @Override
    public void act(float delta) {
        /* movement */
        this.setX((float) (this.getX() + Math.cos(this.direction) * this.speed * delta));
        this.setY((float) (this.getY() + Math.sin(this.direction) * this.speed * delta));

        /* wrap entity */
        this.setX(Utils.wrap(this.getX(), 0, Gdx.graphics.getWidth()));
        this.setY(Utils.wrap(this.getY(), 0, Gdx.graphics.getHeight()));
    }

    @Override
    public boolean collision(Actor actor) {
        return false;
    }

    public void hit(int damage){
        this.hp -= damage;

        if(this.hp <= 0){

            for(int i =1; i < this.splits; i++){
                float angle =  (float) (Math.random() * 2 * Math.PI);
                float x = this.getX();
                float y = this.getY();

                Asteroid ast = new Asteroid(x, y, this.splits -1 ,angle);
                this.getStage().addActor(ast);
            }

            this.remove();

        }
    }
}

package com.unfinished.asteroids.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.unfinished.asteroids.AsteroidAssetManager;
import com.unfinished.asteroids.Utils;

public class Player extends Image implements ICollidable {

    //Moving - Shooting Flags
    boolean movingLeft = false;
    boolean movingUp = false;
    boolean movingRight = false;
    boolean movingDown = false;
    boolean shooting = false;

    //Movement
    float direction = 0f;
    float speed = 0f;
    float acceleration =  200f;
    float deacceleration = 50f;
    float maxSpeed = 200f;
    float rotationSpeed = (float) Math.PI;

    //Force
    float force = 0f;
    float forceDirection = 0f;
    float forceDamping = 0f;

    //Shooting
    float cooldown = 0f;
    float maxCooldown = 0.3f;

    //Player Propertiesx
    int hp = 10;
    int maxHp = 10;
    int team = 0;

    public Player(float x, float y, int team) {
        super(AsteroidAssetManager.getInstance().getPlayerTexture(team));
        this.team = team;
        this.setPosition(x,y);
        this.setOrigin(this.getWidth() / 2, this.getWidth() / 2);

        addListener(new PlayerInputListener());
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    public int getHp() {
        return Math.max(hp, 0);
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getTeam() {
        return team;
    }

    @Override
    public void act(float delta) {
        /* speed and acceleration */
        if (this.movingUp) {
            this.speed = Math.min(this.maxSpeed, this.speed + this.acceleration * delta);
        }
        else {
            this.speed = Math.max(0, this.speed - this.deacceleration * delta);
        }

        /* movement */
        this.setX((float) (this.getX() + Math.cos(this.direction) * this.speed * delta));
        this.setY((float) (this.getY() + Math.sin(this.direction) * this.speed * delta));

        /* force */
        this.setX((float) (this.getX() + Math.cos(this.forceDirection) * this.force * delta));
        this.setY((float) (this.getY() + Math.sin(this.forceDirection) * this.force * delta));

        this.force = Math.max(0, this.force - this.forceDamping * delta);

        /* rotation */
        if (this.movingLeft){
            this.direction = Utils.wrap(this.direction + this.rotationSpeed * delta, 0, Math.PI * 2);
        }
        if (this.movingRight){
            this.direction = Utils.wrap(this.direction - this.rotationSpeed * delta, 0, Math.PI * 2);
        }

        this.setRotation((float)((this.direction - Math.PI /2)/Math.PI) * 180);

        /* shooting */
        if (this.shooting) {
            if ((this.cooldown -= delta) <= 0) {
                this.shoot();
                this.cooldown = this.maxCooldown;
            }
        }

        /* wrap entity */
        this.setX(Utils.wrap(this.getX(), 0, Gdx.graphics.getWidth()));
        this.setY(Utils.wrap(this.getY(), 0, Gdx.graphics.getHeight()));
    }

    @Override
    public boolean collision(Actor actor) {
        if(actor instanceof Asteroid){
            ((Asteroid)actor).hit(1);
            this.hit(1);

            /* bounce in the opposite direction */
            this.applyForce((float) (this.direction - Math.PI), this.speed * 2);
            this.speed = 0;

            return true;
        }

        return false;
    }

    public void hit(int damage){
        this.hp -= damage;

        if(this.hp <= 0){
            this.remove();
        }
    }

    private void applyForce(float direction, float value){
        this.forceDirection = direction;
        this.force = value;
        this.forceDamping = value;
    }

    private void shoot(){
        Bullet bullet = new Bullet(this.getX(Align.center), this.getY(Align.center), this.direction, this.team);
        this.getStage().addActor(bullet);
    }

    class PlayerInputListener extends InputListener {

        @Override
        public boolean keyDown(InputEvent event, int keycode) {

            switch (keycode)
            {
                case Input.Keys.LEFT:
                    Player.this.movingLeft = true;
                    break;
                case Input.Keys.UP:
                    Player.this.movingUp = true;
                    break;
                case Input.Keys.RIGHT:
                    Player.this.movingRight = true;
                    break;
                case Input.Keys.DOWN:
                    Player.this.movingDown = true;
                    break;
                case Input.Keys.SPACE:
                    Player.this.shooting = true;
                    break;
            }
            return true;
        }

        @Override
        public boolean keyUp(InputEvent event, int keycode) {

            switch (keycode)
            {
                case Input.Keys.LEFT:
                    Player.this.movingLeft = false;
                    break;
                case Input.Keys.UP:
                    Player.this.movingUp = false;
                    break;
                case Input.Keys.RIGHT:
                    Player.this.movingLeft = false;
                    break;
                case Input.Keys.DOWN:
                    Player.this.movingDown = false;
                    break;
                case Input.Keys.SPACE:
                    Player.this.shooting = false;
                    break;
            }
            return true;
        }
    }
}

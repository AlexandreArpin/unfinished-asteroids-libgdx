package com.unfinished.asteroids;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.unfinished.asteroids.entities.Asteroid;
import com.unfinished.asteroids.entities.ICollidable;
import com.unfinished.asteroids.entities.Player;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex on 2014-10-20.
 */
public class GameScreen implements Screen {

    private Stage stage;
    private Touchpad joystick;
    private ImageButton button;

    private List<Player> players = new LinkedList<Player>();

    public GameScreen(AsteroidsGame asteroidsGame){
        //Load Assets
        AsteroidAssetManager.getInstance();

        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        Player player = new Player(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, AsteroidsGame.TEAM_BLUE);

        players.add(player);
        stage.addActor(player);
        stage.setKeyboardFocus(player);

        for(int i =0; i < 4; i++){
            float angle =  (float) (Math.random() * 2 * Math.PI);
            float x = (float) Math.cos(angle) * Gdx.graphics.getWidth();
            float y = (float) Math.sin(angle) * Gdx.graphics.getHeight();

            Asteroid ast = new Asteroid(x, y, 3, angle);
            stage.addActor(ast);
        }

        Hud hud = new Hud(players);
        stage.addActor(hud);

        //Add TouchPad if the player is Android
        if(Gdx.app.getType() == Application.ApplicationType.Android){
            //Create a joystick skin
            Skin touchpadSkin = new Skin();
            touchpadSkin.add("touchBackground", new Texture("touchBackground.png"));
            touchpadSkin.add("touchKnob", new Texture("touchKnob.png"));
            touchpadSkin.add("touchKnob_pressed", new Texture("touchKnob_pressed.png"));

            //Apply the Drawables to the TouchPad Style
            Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();
            touchpadStyle.background = touchpadSkin.getDrawable("touchBackground");
            touchpadStyle.knob = touchpadSkin.getDrawable("touchKnob");

            //Create new TouchPad with the created style
            this.joystick = new Touchpad(10, touchpadStyle);
            this.joystick.setBounds(15, 15, 100, 100);

            ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
            buttonStyle.imageUp = touchpadSkin.getDrawable("touchKnob");
            buttonStyle.imageDown = touchpadSkin.getDrawable("touchKnob_pressed");

            this.button = new ImageButton(buttonStyle);
            this.button.setBounds(Gdx.graphics.getWidth()-115, 15, 100, 100);

            stage.addActor(this.joystick);
            stage.addActor(this.button);
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Player player = players.get(0);

        //Get Touchpad Input
        if(joystick != null && button != null){
            //Rotate Right
            if(joystick.getKnobPercentX() >= 0.15){
                player.setMovingRight(true);
                player.setMovingLeft(false);
            }
            //Rotate Left
            else if(joystick.getKnobPercentX() <= -0.15){
                player.setMovingRight(false);
                player.setMovingLeft(true);
            }
            else{
                player.setMovingRight(false);
                player.setMovingLeft(false);
            }

            //Forward
            if(joystick.getKnobPercentY() >= 0.15){
                player.setMovingUp(true);
                player.setMovingDown(false);
            }
            //Backward?
            else if(joystick.getKnobPercentX() <= -0.15){
                player.setMovingUp(false);
                player.setMovingDown(true);
            }
            else{
                player.setMovingUp(false);
                player.setMovingDown(false);
            }

            //Shooting?
            if(button.isPressed()){
                player.setShooting(true);
            }
            else{
                player.setShooting(false);
            }
        }

        stage.act(delta);

        detectCollisions(stage.getActors());

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    private void detectCollisions(Array<Actor> actors){
        //Collision Detection
        for(int i =0; i < stage.getActors().size; i++){

            Actor a = stage.getActors().get(i);

            if(!(a instanceof ICollidable))
                continue;

            for(int j= i+1; j < stage.getActors().size; j++){
                Actor b = stage.getActors().get(j);

                if(!(b instanceof ICollidable))
                    continue;

                float radiusA = Math.min(a.getWidth(), a.getHeight())/2.0f;
                float radiusB = Math.min(b.getWidth(), b.getHeight())/2.0f;

                if (Utils.distance(a, b) < radiusA + radiusB) {
                    if(!((ICollidable) a).collision(b)){
                        ((ICollidable) b).collision(a);
                    }
                }
            }
        }
    }

    /* this shit is short but buggy - what a crappy day... at crappy company - temporary leaving it for emergency use */
    private void detectCollisions2(List<Actor> actors){
        for(int i =0; i < stage.getActors().size; i++) {
            Actor a = stage.getActors().get(i);

            for(int j= i+1; j < stage.getActors().size; j++){
                Actor b = stage.getActors().get(j);

                float radiusA = Math.min(a.getWidth(), a.getHeight())/2.0f;
                float radiusB = Math.min(b.getWidth(), b.getHeight())/2.0f;

                if (Utils.distance(a, b) < radiusA + radiusB) {
                    ((ICollidable) a).collision(b);
                    ((ICollidable) b).collision(a);
                }
            }
        }
    }
}

package com.unfinished.asteroids;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.unfinished.asteroids.entities.Player;

import java.util.List;

public class Hud extends Actor {

    private static final float BAR_WIDTH = 80;
    private static final float BAR_HEIGHT = 6;

    List<Player> players;

    public Hud(List<Player> players) {
        this.players = players;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float height = this.getStage().getHeight();
        batch.end();
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());

        //Draw Hp.
        for(int i=0; i < players.size(); i++){
            Player player = players.get(i);

            //Bar background
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BLACK);
            shapeRenderer.rect(16, height - (16 *(i+1)) , BAR_WIDTH, BAR_HEIGHT);
            shapeRenderer.end();

            //Bar content
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0f, 0.5f, 1f, 1f);
            shapeRenderer.rect(16, height - (16 *(i+1)), BAR_WIDTH * ((float)player.getHp() / (float)player.getMaxHp()), BAR_HEIGHT);
            shapeRenderer.end();
        }

        batch.begin();
    }

}

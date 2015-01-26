package com.unfinished.asteroids;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Alex on 14-10-23.
 */
public class AsteroidAssetManager {

    //Constants
    private static final String SPRITESHEET = "spritesheet.png";

    //Instance
    private AssetManager assetManager;

    //Singleton
    private static AsteroidAssetManager instance = null;

    private AsteroidAssetManager(){
        this.assetManager = new AssetManager();
        this.assetManager.load(SPRITESHEET, Texture.class);
        this.assetManager.finishLoading();
    }

    public static AsteroidAssetManager getInstance(){
        if(instance == null)
            instance = new AsteroidAssetManager();

        return instance;
    }

    private Texture getSpritesheet(){
        return this.assetManager.get(SPRITESHEET, Texture.class);
    }

    public TextureRegion getPlayerTexture(){
        return getPlayerTexture(0);
    }

    public TextureRegion getPlayerTexture(int team){
        switch(team){
            case 0:
                return new TextureRegion(this.getSpritesheet(), 13,65,20,16);
            case 1:
                return new TextureRegion(this.getSpritesheet(), 36, 65, 20, 16);
            default:
                return new TextureRegion(this.getSpritesheet(), 13,65,20,16);
        }
    }

    public TextureRegion getAsteroidTexture(){
        return new TextureRegion(this.getSpritesheet(), 19,13,32,30);
    }

}

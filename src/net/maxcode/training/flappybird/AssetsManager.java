package net.maxcode.training.flappybird;

import android.content.Context;
import org.andengine.engine.Engine;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by andrei on 4/11/14.
 */
public class AssetsManager {

    private BitmapTextureAtlas mGameAtlas;

    private ITextureRegion mBackgroundTextureRegion;
    private ITextureRegion mGroundTextureRegion;

    private ITiledTextureRegion mPipesTiledTextureRegion;
    private ITiledTextureRegion mFlappyTiledTextureRegion;

    private VertexBufferObjectManager mVertexBufferObjectManager;

    public AssetsManager(Context pContext, Engine pEngine) {
        this.mVertexBufferObjectManager = pEngine.getVertexBufferObjectManager();
        this.mGameAtlas = new BitmapTextureAtlas(pEngine.getTextureManager(), 2048, 2048, TextureOptions.NEAREST_PREMULTIPLYALPHA);

        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        this.mBackgroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameAtlas, pContext, "background.png", 0, 0);
        this.mGroundTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mGameAtlas, pContext, "ground.png", 0, 513);

        this.mPipesTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameAtlas, pContext, "pipes.png", 291, 0, 2, 1);
        this.mFlappyTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameAtlas,pContext,"flappy.png",401,0, 1, 3);
    }

    public ITextureRegion getBackgroundTextureRegion() {
        return mBackgroundTextureRegion;
    }

    public ITextureRegion getGroundTextureRegion() {
        return mGroundTextureRegion;
    }

    public ITiledTextureRegion getPipesTiledTextureRegion() {
        return mPipesTiledTextureRegion;
    }

    public ITiledTextureRegion getFlappyTiledTextureRegion() {
        return mFlappyTiledTextureRegion;
    }

    public VertexBufferObjectManager getVBOManager() {
        return mVertexBufferObjectManager;
    }

    public void load(){
        this.mGameAtlas.load();
    }

    public void unload(){
        this.mGameAtlas.unload();
    }
}

package net.maxcode.training.flappybird.model;

import net.maxcode.training.flappybird.pool.IRecyclable;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by andrei on 4/11/14.
 */
public class Bird extends AnimatedSprite implements IRecyclable{
    public Bird(ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(0, 0, pTiledTextureRegion, pVertexBufferObjectManager);
    }

    @Override
    public void onRecycle() {

    }

    @Override
    public void onObtain() {

    }

    @Override
    public IRecyclable newInstance() {
        return null;
    }

    @Override
    public void poolDispose() {

    }

    @Override
    public void sleep() {
        this.setVisible(false);
        this.setIgnoreUpdate(true);
    }
}

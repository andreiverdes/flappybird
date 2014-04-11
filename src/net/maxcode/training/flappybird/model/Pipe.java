package net.maxcode.training.flappybird.model;

import net.maxcode.training.flappybird.pool.IRecyclable;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by andrei on 4/11/14.
 */
public class Pipe extends TiledSprite implements IRecyclable{

    private Direction mDirection;

    public Pipe(ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(0, 0, pTiledTextureRegion, pVertexBufferObjectManager);
        this.setDirection(Direction.UP);
    }

    public Direction getDirection() {
        return mDirection;
    }

    public void setDirection(Direction mDirection) {
        this.mDirection = mDirection;
        this.setCurrentTileIndex(mDirection.ordinal());
    }

    @Override
    public void onRecycle() {

    }

    @Override
    public void onObtain() {
        this.setDirection(Direction.UP);
    }

    @Override
    public IRecyclable newInstance() {
        return new Pipe(this.getTiledTextureRegion(), this.getVertexBufferObjectManager());
    }

    @Override
    public void poolDispose() {
        this.dispose();
    }

    @Override
    public void sleep() {
        this.setVisible(false);
        this.setIgnoreUpdate(true);
    }
    public enum Direction{
        DOWN,
        UP
    }
}

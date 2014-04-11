package net.maxcode.training.flappybird.model;

import net.maxcode.training.flappybird.AssetsManager;
import net.maxcode.training.flappybird.pool.IRecyclable;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Random;

/**
 * Created by andrei on 4/11/14.
 */
public class Ground extends Sprite implements IRecyclable {
    private static final int PIPES_COUNT = 4;

    private ITiledTextureRegion mPipeITiledTextureRegion;
    private Pipe[] mPipes;
    private Random mRandom;

    private float mCameraWidth;
    private float mCameraHeight;

    private float mDeltaPipesSpace;

    private boolean mAddPipes;

    public Ground(Camera pCamera, AssetsManager pAssetsManager) {
        this(pCamera.getWidth(), pCamera.getHeight(), pAssetsManager.getGroundTextureRegion(), pAssetsManager.getPipesTiledTextureRegion(), pAssetsManager.getVBOManager());
    }

    public Ground(float pCameraWidth, float pCameraHeight, ITextureRegion pGroundTextureRegion, ITiledTextureRegion pPipeTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager){
        super(0, 0, pGroundTextureRegion, pVertexBufferObjectManager);
        this.setWidth(pCameraWidth + 3);
        this.setHeight(pCameraHeight*.25f);
        this.mCameraWidth = pCameraWidth;
        this.mCameraHeight = pCameraHeight;
        this.mDeltaPipesSpace = pCameraHeight*.25f;
        this.mRandom = new Random();
        this.mAddPipes = false;

        this.mPipeITiledTextureRegion = pPipeTiledTextureRegion;
        this.mPipes = new Pipe[PIPES_COUNT];
        this.addPipes(pVertexBufferObjectManager);
    }
    private void addPipes(VertexBufferObjectManager pVertexBufferObjectManager){
        for(int i=0; i<PIPES_COUNT; ++i){
            this.mPipes[i] = new Pipe(mPipeITiledTextureRegion,pVertexBufferObjectManager);
            this.mPipes[i].setZIndex(-1);
            this.mPipes[i].setDirection(Pipe.Direction.values()[i%2]);
            this.mPipes[i].setWidth(130);
            this.mPipes[i].setHeight(mCameraHeight*.75f);
            this.mPipes[i].setVisible(mAddPipes);
            float factor = i<2 ? .25f : .75f;
            this.mPipes[i].setX(this.getWidth()*factor-this.mPipes[i].getWidth()*.5f);

            this.attachChild(mPipes[i]);
            this.sortChildren();
        }
        this.randomizePipes();
    }

    private void randomizePipes(){
        for(int i=0; i<=PIPES_COUNT/2; i+=2){
            float y = - mRandom.nextFloat()*mCameraHeight*.5f;
            this.mPipes[i+1].setY(y);
            this.mPipes[i].setY(y-mPipes[i].getHeight()-mDeltaPipesSpace);
        }
    }

    public boolean isAddPipes() {
        return mAddPipes;
    }

    public void setAddPipes(boolean mAddPipes) {
        this.mAddPipes = mAddPipes;
    }

    public Pipe[] getPipes() {
        return mPipes;
    }

    @Override
    public void onRecycle() {
        this.setVisible(false);
        this.setIgnoreUpdate(true);
    }

    @Override
    public void onObtain() {
        this.setVisible(true);
        this.setIgnoreUpdate(false);
        this.randomizePipes();
        for(int i=0; i<mPipes.length; i++){
            mPipes[i].setVisible(mAddPipes);
        }
    }

    @Override
    public IRecyclable newInstance() {
        return new Ground(mCameraWidth, mCameraHeight, this.getTextureRegion(), mPipeITiledTextureRegion, this.getVertexBufferObjectManager());
    }

    @Override
    public void poolDispose() {

    }

    @Override
    public void sleep() {

    }
}

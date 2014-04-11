package net.maxcode.training.flappybird;

import android.util.Log;
import net.maxcode.training.flappybird.model.Bird;
import net.maxcode.training.flappybird.model.Ground;
import net.maxcode.training.flappybird.model.Pipe;
import net.maxcode.training.flappybird.pool.PoolManager;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.IModifier;

/**
 * Created by andrei on 4/11/14.
 */
public class GameScene extends Scene{

    private Camera mCamera;
    private PoolManager mPoolManager;

    private Bird mFlappyBird;

    private boolean mSceneTouched;
    private boolean mGameStarted;

    private IUpdateHandler mUpdateHandler = new IUpdateHandler() {
        @Override
        public void onUpdate(float pSecondsElapsed) {
            if(mGameStarted){
                mFlappyBird.setY(mFlappyBird.getY() + 7 * (mSceneTouched ? -1 : 1));
            }
            for(Ground ground : mPoolManager.getPool(Ground.class).getAvailableItems()){
                for(Pipe pipe : ground.getPipes()){
                    if((pipe.isVisible() && mFlappyBird.collidesWith(pipe)) || mFlappyBird.collidesWith(ground)){
                        Log.e("FlappyBird","COLLISION!!!");
                        GameScene.this.onGameOver();
                        GameScene.this.unregisterUpdateHandler(this);
                    }
                }
            }
        }

        @Override
        public void reset() {

        }
    };


    public GameScene(Camera pCamera, AssetsManager pAssetsManager) {
        super();
        Sprite backgroundSprite = new Sprite(0,0,pCamera.getWidth(),pCamera.getHeight(),pAssetsManager.getBackgroundTextureRegion(),pAssetsManager.getVBOManager());
        this.setBackground(new SpriteBackground(backgroundSprite));

        this.mSceneTouched = false;
        this.mGameStarted = false;

        this.mCamera = pCamera;
        this.mFlappyBird = new Bird(pAssetsManager.getFlappyTiledTextureRegion(), pAssetsManager.getVBOManager());
        this.mPoolManager = new PoolManager();
        this.mPoolManager.registerNewPool(new Ground(pCamera, pAssetsManager));

        this.addGround(0);
        this.addGround(1);
        this.addBird();
        this.registerUpdateHandler(mUpdateHandler);
        this.setOnSceneTouchListener(new SceneTouchListener());
    }

    private void addGround(int index){
        final Ground ground = mPoolManager.obtainItem(Ground.class);
        ground.setPosition(index * mCamera.getWidth()-index,mCamera.getHeight()-ground.getHeight());
        ground.setAddPipes(mGameStarted);
        float time = 4f;
        float distance = ground.getX()+ground.getWidth();
        float speed = (distance*time)/mCamera.getWidth();
        ground.registerEntityModifier(new MoveXModifier(speed,ground.getX(),-mCamera.getWidth(),new IEntityModifier.IEntityModifierListener() {
            @Override
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {

            }

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                mPoolManager.recycleItem(ground);
                addGround(1);
            }
        }));
        if(!ground.hasParent()){
            this.attachChild(ground);
        }
    }

    private void addBird(){
        this.mFlappyBird.setWidth(100);
        this.mFlappyBird.setHeight(80);
        this.mFlappyBird.animate(200);

        this.mFlappyBird.setPosition(mCamera.getWidth()*.2f, mCamera.getHeight()*.5f);

        this.attachChild(mFlappyBird);
    }

    private void onGameOver(){
        for(Ground ground : mPoolManager.getPool(Ground.class).getAvailableItems()){
            ground.clearEntityModifiers();
        }
        mFlappyBird.stopAnimation();
        mFlappyBird.registerEntityModifier(new MoveYModifier(1,mFlappyBird.getY(),mCamera.getHeight()*.75f-mFlappyBird.getHeight()));
    }

    private class SceneTouchListener implements IOnSceneTouchListener{

        @Override
        public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
            if(pSceneTouchEvent.isActionDown()){
                mSceneTouched = true;
                mGameStarted = true;
            } else if(pSceneTouchEvent.isActionUp()){
                mSceneTouched = false;
            }
            return true;
        }
    }
}

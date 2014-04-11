package net.maxcode.training.flappybird.pool;

/**
 * Created with IntelliJ IDEA.
 * User: Andrei
 * Date: 7/9/12
 * Time: 5:31 PM
 * To change this template use File | Settings | File Templates.
 * All methods must be implemented!
 */
public interface IRecyclable {
    /**
     *  Called when an object is recycled
     */
    public void onRecycle();

    /**
     *  Called when an object is obtained
     */
    public void onObtain();
    /**
     *  Called by the pool in {@link Pool onAllocateItem()}to obtain a new instance of the object
     * @return  New object that implements the interface.
     */
    public IRecyclable newInstance();

    /**
     * Called to dispose the object
     */
    public void poolDispose();

    /**
     * Called to neutralize the mentor entity in the pool,
     * such as clearing modifiers or update handlers or even better setIgnoreUpdate(true)
     */
    public void sleep();
    
}

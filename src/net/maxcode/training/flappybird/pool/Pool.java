package net.maxcode.training.flappybird.pool;


import org.andengine.util.adt.pool.GenericPool;

import java.util.ArrayList;

/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga Inc.
 * 
 * @author Valentin Milea
 * @author Nicolas Gramlich
 * @author Andrei AV.
 * 
 * @since 23:00:21 - 09.07.2012
 */
public class Pool<T extends IRecyclable> extends GenericPool<T> {
    private T mPrototype;
    private ArrayList<T> mItemsAvailable;
	public Pool(T pPrototype) {
		super();
        this.mPrototype = pPrototype;
        this.mPrototype.sleep();
        this.mItemsAvailable = new ArrayList<T>();
	}

	@Override
    @SuppressWarnings("unchecked")
	protected T onAllocatePoolItem() {
		final T recyclable = (T) this.mPrototype.newInstance();
        if(recyclable == null){
            throw new NullPointerException("IRecyclable item.newInstance() returns null! Make sure you have implemented this method correctly!");
        }
//		recyclable.setPoolParent(this);
		return recyclable;
	}

	@Override
	protected void onHandleObtainItem(final T pRecyclable) {
//		pRecyclable.setRecycled(false);
        mItemsAvailable.add(pRecyclable);
		pRecyclable.onObtain();
	}

	@Override
	protected void onHandleRecycleItem(final T pRecyclable) {
		pRecyclable.onRecycle();
//		pRecyclable.setRecycled(true);
	}

	@Override
	public synchronized void recyclePoolItem(final T pRecyclable) {
//		if(pRecyclable.getPoolParent() == null) {
//			throw new IllegalArgumentException("PoolItem not assigned to a pool!");
//		} else if(!pRecyclable.isFromPool(this)) {
//			throw new IllegalArgumentException("PoolItem from another pool!");
//		} else if(pRecyclable.isRecycled()) {
//			throw new IllegalArgumentException("PoolItem already recycled!");
//		}
        this.mItemsAvailable.remove(pRecyclable);
		super.recyclePoolItem(pRecyclable);
	}

    public ArrayList<T> getAvailableItems(){
        return  this.mItemsAvailable;
    }
//	public synchronized boolean ownsPoolItem(final PoolItem pPoolItem) {
//		return pPoolItem.getPoolParent().equals(this);
//	}
    public void recycleAll(){
        while (this.getAvailableItems().size()>0){
            this.recyclePoolItem(this.getAvailableItems().get(0));
        }
    }
    public void removeAll(){
        T item;
        while (this.getAvailableItems().size()>0){
            item = this.getAvailableItems().get(0);
            this.recyclePoolItem(item);
            this.mItemsAvailable.remove(item);
            item.poolDispose();
        }
        this.mItemsAvailable.clear();
    }
    public T getPrototype() {
        return mPrototype;
    }
    public void init(int size){
        for(int i=0; i<size;i++){
            this.obtainPoolItem();
        }
        this.recycleAll();
    }
}

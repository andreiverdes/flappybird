package net.maxcode.training.flappybird.pool;

import android.util.SparseArray;

/**
 * Created with IntelliJ IDEA.
 * User: Andrei
 * Date: 7/6/12
 * Time: 10:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class PoolManager {

    private SparseArray<Pool> mPools;

    public PoolManager(){
        this.mPools = new SparseArray<Pool>();
    }

    public void registerNewPool(final IRecyclable pItem) {
        mPools.put(pItem.getClass().getName().hashCode(), new Pool(pItem));
    }
    public void registerNewPool(final int poolID, final IRecyclable pItem){
        this.mPools.put(poolID, new Pool(pItem));
    }

    public void unregisterPool(final IRecyclable pIRecyclable){
        this.unregisterPool(pIRecyclable.getClass());
    }
    public void unregisterPool(final Class pClass){
        this.unregisterPool(pClass.getName().hashCode());
    }
    public void unregisterPool(final int pPoolID){
        Pool pool = this.mPools.get(pPoolID);
        this.disposePool(pool);
        this.mPools.remove(pPoolID);
    }
    public <T extends IRecyclable> Pool<T> getPool(Class<T> pClass){
        return ((Pool<T>) this.mPools.get(pClass.getName().hashCode()));
    }
    public <T extends IRecyclable> Pool getPool(int poolID){
        return this.mPools.get(poolID);
    }
    @SuppressWarnings("unchecked")
    public <T extends IRecyclable> T obtainItem(Class<T> pClass){
        return (T) this.getPool(pClass).obtainPoolItem();
    }
    public <T extends IRecyclable> T obtainItem(int poolID){
        return (T) this.getPool(poolID).obtainPoolItem();
    }
    public void recycleItem(final IRecyclable pItem){
        if(pItem!=null){
            this.mPools.get(pItem.getClass().getName().hashCode()).recyclePoolItem(pItem);
        }
    }
    public void recycleItem(final int pPoolID, final IRecyclable pItem){
        if(pItem!=null){
            this.mPools.get(pPoolID).recyclePoolItem(pItem);
        }
    }

    public <T extends IRecyclable> void disposePool(Class<T> pClass){
        String pClassName = pClass.getName();
        Pool pool = this.mPools.get(pClassName.hashCode());
        pool.removeAll();
        this.mPools.remove(pClassName.hashCode());
    }
    public void disposePool(int pKey){
        Pool pool = this.mPools.get(pKey);
        pool.removeAll();
        this.mPools.remove(pKey);
    }
    public void disposePool (Pool pPool){
        pPool.removeAll();
        this.mPools.remove(this.mPools.indexOfValue(pPool));
    }
    public void dispose(){
        while (this.mPools.size()>0){
            this.disposePool(this.mPools.valueAt(0));
        }
    }
    public void recycleAll(){
        for(int i=0; i<mPools.size();i++){
            this.mPools.valueAt(i).recycleAll();
        }
    }
    public void removeAll(){
        for(int i=0; i<mPools.size();i++){
            this.mPools.valueAt(i).removeAll();
        }
    }
    public void initPools(int size){
        for(int i=0;i<mPools.size();i++){
            mPools.valueAt(i).init(size);
        }
    }

    public SparseArray<Pool> getPools() {
        return mPools;
    }
}

package ru.isalnikov.utils;

import java.lang.reflect.Array;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.LockSupport;

/**
 * Data holder
 *
 * @author Igor Salnikov <admin@isalnikov.com>
 * @param <T>
 */
public class TakerThread<T> extends Thread {

    private final AtomicReference<T> valueRef = new AtomicReference<>();
    private final T[] results = (T[]) Array.newInstance(Object.class, 1);

    private final Sync sync = new Sync();

    public void updateValue(T newValue) {
        T oldValue = valueRef.getAndSet(newValue);
        //LockSupport.unpark(this);
        if (oldValue == null) {
            sync.release(0);
        }
    }

    public T removeValue() throws InterruptedException {
        return takeValue();
    }

//    private T takeValue() throws InterruptedException {
//        assert currentThread() == this;
//        while (true) {
//            T oldValue = valueRef.get();
//            if (oldValue == null) {
//                LockSupport.park(); //lock free waiting 
//
//                if (interrupted()) {//... Interrupted idiom
//                    throw new InterruptedException();
//                }
//                continue;
//            }
//
//            if (valueRef.compareAndSet(oldValue, null)) {
//                return oldValue;
//            }
//        }
//    }
    private T takeValue() throws InterruptedException {
        int arg = reserveResultSlot();
        sync.acquireInterruptibly(arg);
        if (valueRef.get() != null) {
            sync.release(0);

        }

        return releaseResultSlot(arg);

    }

    private int reserveResultSlot() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private T releaseResultSlot(int arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * wail queue impl for other cases
     */
    private class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            while (true) {

                T oldValue = valueRef.get();
                if (oldValue == null) {
                    return false;
                }

                if (hasQueuedPredecessors()) {
                    return false; // be fair!
                }
                if (!valueRef.compareAndSet(oldValue, null)) {
                    continue; //cas
                }

                results[arg] = oldValue;
                return true;

            }
        }

        @Override
        protected boolean tryRelease(int arg) {
            return true;
        }
    }

}

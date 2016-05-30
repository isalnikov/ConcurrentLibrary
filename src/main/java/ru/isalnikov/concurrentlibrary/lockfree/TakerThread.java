package ru.isalnikov.concurrentlibrary.lockfree;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 *
 * @author Igor Salnikov <admin@isalnikov.com>
 * @param <T>
 */
public class TakerThread<T> extends Thread {

    private final AtomicReference<T> valueRef = new AtomicReference<>();

    public void updateValue(T newValue) {
        valueRef.set(newValue);
        LockSupport.unpark(this);
    }

    public T removeValue() throws InterruptedException {
        return takeValue();
    }

    private T takeValue() throws InterruptedException {
        assert Thread.currentThread() == this;
        while (true) {
            T oldValue = valueRef.get();
            if (oldValue == null) {
                LockSupport.park(); //lock free waiting 

                if (interrupted()) {//... Interrupted idiom
                    throw new InterruptedException();
                }
                continue;
            }

            if (valueRef.compareAndSet(oldValue, null)) {
                return oldValue;
            }
        }
    }

}

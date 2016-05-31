/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.isalnikov.utils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * 
 */
public abstract class RetryTask<T> {

    private final AtomicBoolean stopped = new AtomicBoolean(false);

    private final long retryTimeoutMs = 1000L;

    public T doTask() throws AlreadyStoppedExeptiopn {
        do {
            try {
                return tryToDoTask();
            } catch (RuntimeException e) {
                onError(e);
            }

        } while (!isCancelled());

        throw new AlreadyStoppedExeptiopn("Task interrupted"); //  работа не заверщена у жанного таска 
    }

    private boolean isCancelled() {
        return stopped.get();
    }

    private void onError(RuntimeException e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    protected abstract T tryToDoTask();
}

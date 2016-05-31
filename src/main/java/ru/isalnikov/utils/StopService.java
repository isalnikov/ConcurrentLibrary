/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.isalnikov.utils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * volatile vs AtomicBoolean
 *
 */
public class StopService {

    private final AtomicBoolean stopped = new AtomicBoolean(false);

    public StopService() {

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                while (!stopped.get()) {
                    try {
                        tryTodoTask();
                    } catch (RuntimeException e) {
                        onError(e);
                    }
                }

            }

            private void tryTodoTask() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            private void onError(RuntimeException e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }

    public AtomicBoolean getStopped() {
        return stopped;
    }
    

}

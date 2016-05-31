/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.isalnikov.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 *
 * local broker by Id message
 */
public class MessageBlockingQueue {

    private final BlockingQueue<String> queue = new LinkedBlockingDeque<>(1000);

    /**
     * simple producer
     */
    public void sendMessage(final String msg) {
        try {
            boolean sent = queue.offer(msg, 10, TimeUnit.SECONDS);

        } catch (InterruptedException e) {

        }
    }
    /**
     * cunsumer
     */
    private volatile boolean stopped;

    public void recieveMessage() {
        while (!stopped) {
            try {
                String msg = queue.take();
            } catch (InterruptedException e) {
            }

        }
    }
}

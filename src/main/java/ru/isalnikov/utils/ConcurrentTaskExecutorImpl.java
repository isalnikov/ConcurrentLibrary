
package ru.isalnikov.utils;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * https://www.youtube.com/watch?v=8piqauDj2yo
 * 
 */
public class  ConcurrentTaskExecutorImpl {
   
    //ExecutionException
    private ExecutorService  executorService = Executors.newFixedThreadPool(5);

    public ConcurrentTaskExecutorImpl() {
    }
    
    
    public ConcurrentTaskExecutorImpl(ExecutorService  executorService , int pool) {
        executorService = Executors.newFixedThreadPool(pool);
    }
    
    
    
   
    
    private void processBatches(Date date){
//        ConcurrentTaskExecutorImpl<Boolean> taskExecutor = 
//                new ConcurrentTaskExecutorImpl<>(executorService,5);
    }
    
}

package com.mozzerin.task1.service;

import com.mozzerin.task1.Constants;
import com.mozzerin.task1.service.impl.InputScannerServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

public class ApplicationService {
    private final InputScannerService inputScannerService;
    private final ConcurrentMap<String, Long> threadNameCounterMap;
    private volatile String winner;

    public ApplicationService() {
        inputScannerService = new InputScannerServiceImpl();
        threadNameCounterMap = new ConcurrentHashMap<>();
    }

    public void startProgramExecution() {
        long scannedResult = inputScannerService.scanValidInput();
        System.out.println("Your number is: " + scannedResult + ". Please wait.");
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1, new ThreadFactory() {
            private int counter = 0;

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "mozzerinThread-" + counter++);
            }
        });
        List<Future<Boolean>> resultList = new ArrayList<>();
        for (long i = Constants.MIN_VALUE; i <= scannedResult; i++) {
            long valueToCompare = i;
            resultList.add(fixedThreadPool.submit(() -> {
                boolean comparisonResult = scannedResult == valueToCompare;
                if (comparisonResult) {
                    winner = Thread.currentThread().getName();
                }
                threadNameCounterMap.compute(Thread.currentThread().getName(), (key, value) -> (value == null) ? 0 : value + 1);
                return comparisonResult;
            }));
        }
        try {
            long startTime = System.currentTimeMillis();
            for (Future<Boolean> booleanFuture : resultList) {
                if (booleanFuture.get()) {
                    fixedThreadPool.shutdownNow();
                }
            }
            long endTime = System.currentTimeMillis();
            long l = endTime - startTime;
            System.out.println("Elapsed time: " + l + "ms");
            System.out.println("The winner thread: " + winner);
            System.out.println("The thread_name/iterations_count map: " + threadNameCounterMap);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error. Please try again");
            e.getStackTrace();
        } finally {
            fixedThreadPool.shutdown();
        }
    }
}

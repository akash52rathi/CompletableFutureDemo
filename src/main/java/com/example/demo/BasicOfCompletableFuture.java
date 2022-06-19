package com.example.demo;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class BasicOfCompletableFuture {

    public void Test() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(5);
        CompletableFuture <Void> future =CompletableFuture.runAsync(()->{
            try {

                TimeUnit.SECONDS.sleep(1);
            }
            catch (InterruptedException e)
            {
                throw new IllegalStateException();
            }
            System.out.println("I'll run in seperate thread  " + Thread.currentThread().getName());
        },executor);

        System.out.println("AKash " +Thread.currentThread().getName());
        future.get();
        System.out.println("Akash " +  Thread.currentThread().getName());
    }

    public void test2() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(4);
       CompletableFuture<String>future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
                System.out.println(" Test2 " + Thread.currentThread().getName());
                return "Akash";
            }
        },executor).
               thenApply((n)->{
            return ("Hii  "  + n );
        }).thenApply((n)->{

            return (n
                    + " Greetings");
        });

      String ans = future.get();
        System.out.println(" from test 2 " + ans);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        BasicOfCompletableFuture test = new BasicOfCompletableFuture();
        test.Test();
        test.test2();


    }
}

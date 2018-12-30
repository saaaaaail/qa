package com.sail.qa;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: sail
 * @Date: 2018/12/26 19:08
 * @Version 1.0
 */


public class MainThreadTest {

    class MyThread1 extends Thread{

        private int id;
        public MyThread1(int id){
            this.id = id;
        }

        @Override
        public void run() {
            try {
                for (int i=0;i<10;i++){
                    Thread.sleep(1000);
                    System.out.println(String.format("T1 %d : %d",id,i));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class MyThread2 extends Thread{
        private int id;
        public MyThread2(int id){
            this.id = id;
        }

        @Override
        public void run() {
            try {
                for (int i=0;i<10;i++){
                    Thread.sleep(1000);
                    System.out.println(String.format("T2 %d : %d",id,i));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void testThread(){
        MyThread1 myThread1=new MyThread1(1);
        MyThread1 myThread2=new MyThread1(2);
        myThread1.start();
        myThread2.start();
    }

    private static ThreadLocal<Integer> threadLocalUserId = new ThreadLocal<>();
    private static int userId;
    private static void testThreadLocal(){
        try{
            for (int i=0;i<10;i++){
                final int finalI = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        threadLocalUserId.set(finalI);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("ThreadLocal: "+threadLocalUserId.get());
                    }
                }).start();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void testStaticUserId(){
        try{
            for (int i=0;i<10;i++){
                userId = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("userId: "+userId);
                    }
                }).start();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void testExecutor(){
        //ExecutorService service = Executors.newSingleThreadExecutor();
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    try{
                        Thread.sleep(1000);
                        System.out.println("Executor1: "+ i);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        service.submit(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<10;i++){
                    try{
                        Thread.sleep(1000);
                        System.out.println("Executor2: "+ i);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        service.shutdown();
    }

    private static int counter = 0;
    private static AtomicInteger atomicInteger = new AtomicInteger(0);


    public static void testFuture(){
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                return 1;
            }
        });

        service.shutdown();
        try {
            System.out.println(future.get());
            System.out.println(future.get(100,TimeUnit.MILLISECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] argv){
        MainThreadTest m = new MainThreadTest();
        //m.testThread();
        //testThreadLocal();
        //testStaticUserId();
        //testExecutor();
        testFuture();
    }
}

package com.michael200kg.test.simpleproducer.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static java.util.Objects.nonNull;

/**
 * @author Mikhail_Vershkov
 */

public class FindAllProducersTest {

    //@Test
    public void findAllProducersTest() {
        List returnedClasses = findAllMatchingTypes(IKafkaProducer.class);
        Assertions.assertTrue(nonNull(returnedClasses),"returnedClasses is null");
        Assertions.assertTrue(returnedClasses.size()>0, "returnedClasses is empty");
    }

    private <T> List<Class<? extends T>> findAllMatchingTypes(Class<T> toFind) {
        List<Class<?>> foundClasses = new ArrayList();
        List<Class<? extends T>> returnedClasses = new ArrayList<Class<? extends T>>();
        Class clazz2 = IKafkaProducer.class;
        //this.toFind = toFind;
        //walkClassPath();
        for(Class<?> clazz : foundClasses) {
            returnedClasses.add((Class<? extends T>) clazz);
        }
        return returnedClasses;
    }

    @Test
    public void runThreadsTest() throws InterruptedException {

        class MyRunnable1 implements Runnable {

            private boolean stop = false;

            public void run() {
                System.out.println("Starting new thread1");
                int ii=0;
                while(!stop) {
                    System.out.println("Inside thread1 i="+ii++);
                }
            }
            public void stop() {
                System.out.println("Stopping thread1...");
                stop = true;
            }
        };

        class MyRunnable2 implements Runnable {

            private boolean stop = false;

            public void run() {
                System.out.println("Starting new thread2");
                int ii=0;
                while(!stop) {
                    System.out.println("Inside thread2 i="+ii++);
                }
            }
            public void stop() {
                System.out.println("Stopping thread2...");
                stop = true;
            }
        };


        MyRunnable1 myRunnable1 = new MyRunnable1();
        MyRunnable2 myRunnable2 = new MyRunnable2();
        Thread thread1 = new Thread(myRunnable1);
        Thread thread2 = new Thread(myRunnable2);
        thread1.start();
        thread2.start();
        sleep(100);

        myRunnable1.stop();
        myRunnable2.stop();

    }
}

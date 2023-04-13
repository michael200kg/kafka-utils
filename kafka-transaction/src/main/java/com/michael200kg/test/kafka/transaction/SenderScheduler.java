package com.michael200kg.test.kafka.transaction;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.michael200kg.test.kafka.transaction.sender.TransferRecordsProducer;

/**
 * @author Mikhail_Vershkov
 */

public class SenderScheduler {

    private final TransferRecordsProducer transferRecordsProducer;

    public SenderScheduler(TransferRecordsProducer transferRecordsProducer) {
        this.transferRecordsProducer = transferRecordsProducer;
    }

    public void init() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
           scheduledExecutorService.schedule(new Callable() {
                                                      public Object call() throws Exception {
                                                          transferRecordsProducer.transfer();
                                                          return null;
                                                      }
                                                  },
                        10,
                        TimeUnit.SECONDS);
    }
}

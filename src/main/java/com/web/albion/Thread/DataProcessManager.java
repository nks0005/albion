package com.web.albion.Thread;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
public class DataProcessManager {

    private volatile boolean isRunning;

    private volatile int waittime;

    private Thread process_thread;

    @Autowired
    private MainProcess mainprocess;

    public DataProcessManager() {
        this.isRunning = false;
        this.process_thread = null;
        this.waittime = 1 * 60 * 1000;
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("try cleanup!");
        stopProcess();
        System.out.println("cleanup done!");
    }

    public boolean getIsRunning() {
        return this.isRunning;
    }

    public void startProcess() {
        if (!this.isRunning) {
            // createThreadIfNeeded();
            this.isRunning = true;

            this.process_thread = new Thread(() -> {
                while (this.isRunning) {
                    try {

                        mainprocess.run();
                        Thread.sleep(this.waittime);

                    } catch (InterruptedException e) {
                        System.out.println("stop?");
                        Thread.currentThread().interrupt();
                    }
                }
            });

            this.process_thread.start();
        }
    }

    public void stopProcess() {
        if (this.isRunning) {
            this.isRunning = false;
            if (this.process_thread != null) {
                try {
                    System.out.println("stop watting...");
                    this.process_thread.interrupt();
                    this.process_thread.join();
                    this.process_thread = null;
                    System.out.println("stop done");

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
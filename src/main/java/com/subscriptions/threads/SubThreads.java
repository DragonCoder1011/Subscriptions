package com.subscriptions.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SubThreads {

    public static ExecutorService globalThread = Executors.newCachedThreadPool();
}

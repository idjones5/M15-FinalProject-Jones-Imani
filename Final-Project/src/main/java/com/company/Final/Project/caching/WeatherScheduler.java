package com.company.Final.Project.caching;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;

public class WeatherScheduler {

    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    public void clearMap(Map<String, CachedWeather> map) {
        final Runnable clearM = new Runnable() {
            @Override
            public void run() {
                map.clear();
            }
        };
        final ScheduledFuture<?> clearMapHandle =
                scheduler.scheduleAtFixedRate(clearM, 60, 60, SECONDS);
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                clearMapHandle.cancel(true);
                scheduler.shutdown();
            }
        }, 60 * 60 * 2, SECONDS);
    }
}

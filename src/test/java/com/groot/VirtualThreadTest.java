package com.groot;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class VirtualThreadTest {

    public static void main(String[] args) {
        var start = new Date().getTime();
        System.out.println(start);
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 100).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    list.add(i);
                    return i;
                });
            });
        }
        System.out.println(list.size());
        System.out.println(new Date().getTime() - start);
    }
}

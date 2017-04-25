package ru.otus.hw4;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * mart
 * 25.04.2017
 */
//VM options: -Xmx256m -Xms256m
public class Main {

    private static final long PERIOD = 1000 * 60;

    public static void main(String[] args) throws InterruptedException {
        GCMonitoring monitoring = new GCMonitoring();

        List<Long> list = new ArrayList<>(20_000_000);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(monitoring.getAndClearStat());
            }
        }, PERIOD, PERIOD);

        try {
            for (long i = 0; ; i++) {
                list.add(i);
                if (i % 2 == 0) {
                    list.remove(list.size() - 1);
                }
                if (i % 1000 == 0) {
                    Thread.sleep(20);
                }
            }
        } finally {
            monitoring.uninstall();
            timer.cancel();
        }
    }

}

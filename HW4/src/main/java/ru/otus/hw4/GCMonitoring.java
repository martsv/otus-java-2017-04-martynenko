package ru.otus.hw4;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.ListenerNotFoundException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * mart
 * 25.04.2017
 */
public class GCMonitoring {

    private Map<String, Long> statistics = new ConcurrentHashMap<>();
    private List<Runnable> registrations = new ArrayList<>();

    public GCMonitoring() {
        install();
    }

    public String getAndClearStat() {
        String result = statistics.entrySet().stream()
                .map(e -> (e.getKey() + " - " + e.getValue() + " ms"))
                .collect(Collectors.joining("; "));
        statistics.clear();
        return result.isEmpty() ? "no GC taken" : result;
    }

    public void uninstall() {
        registrations.forEach(Runnable::run);
    }

    private void install() {
        List<GarbageCollectorMXBean> gcbeans = ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gcbean: gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;

            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                    String gcType = info.getGcAction();
                    long duration = info.getGcInfo().getDuration();

                    statistics.merge(gcType, duration, Long::sum);
                }
            };

            emitter.addNotificationListener(listener, null, null);

            registrations.add(() -> {
                try {
                    emitter.removeNotificationListener(listener);
                } catch (ListenerNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

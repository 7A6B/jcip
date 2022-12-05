package com.jcip.chapter4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

class Point {

    public final int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }
}

public class DelegatingVehicleTracker {

    private final ConcurrentMap<String, Point> locations;
    private final Map<String, Point> unmodifiableMap;

    public DelegatingVehicleTracker(Map<String, Point> points) {
        locations = new ConcurrentHashMap<String, Point>(points);
        unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    //返回的是修饰后的locations 即不可修改的map进行包装
    public Map<String, Point> getLocations() {
        return unmodifiableMap;
    }

    public Point getLocation(String id) {
        return locations.get(id);
    }

    //直接替换为新值
    public void setLocation(String id, int x, int y) {
        if (locations.replace(id, new Point(x, y)) == null) {
            throw new IllegalArgumentException("invalid vehicle name: " + id);
        }
    }

    // Alternate version of getLocations (Listing 4.8)
    public Map<String, Point> getLocationsAsStatic() {
        return Collections.unmodifiableMap(new HashMap<String, Point>(locations));
    }

    public static void main(String[] args) {
        final Point point1 = new Point(1, 1);
        final Point point2 = new Point(2, 2);

        final ConcurrentHashMap<String, Point> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("car-1",point1);
        concurrentHashMap.put("car-2",point2);

        final DelegatingVehicleTracker tracker = new DelegatingVehicleTracker(concurrentHashMap);
        new Thread(()->{
            for (Entry<String, Point> entry : tracker.getLocations().entrySet()) {
                System.out.println(entry.getKey()+"==>"+entry.getValue());
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("1秒后再去访问车辆的位置信息");
            for (Entry<String, Point> entry : tracker.getLocations().entrySet()) {
                System.out.println(entry.getKey()+"==>"+entry.getValue());
            }
        }).start();

        try {
            TimeUnit.MILLISECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(()->{
            tracker.setLocation("car-1",5,5);
        }).start();
    }
}


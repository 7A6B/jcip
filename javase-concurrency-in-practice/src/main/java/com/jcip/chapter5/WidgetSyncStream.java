package com.jcip.chapter5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WidgetSyncStream {
    private void iteratorList(List<Widget> list){
        list.forEach(widget -> {
            System.out.println(widget);
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void remove(List<Widget> list,Widget widget){
        list.remove(widget);
    }

    public static void main(String[] args) {
        final WidgetSyncStream widgetSync = new WidgetSyncStream();
        final List<Widget> widgetList = Collections.synchronizedList(new ArrayList<Widget>());
        final Widget widget1 = new Widget();
        final Widget widget2 = new Widget();
        final Widget widget3 = new Widget();
        final Widget widget4 = new Widget();
        widgetList.add(widget1);
        widgetList.add(widget2);
        widgetList.add(widget3);
        widgetList.add(widget4);

        new Thread(()->{
            widgetSync.iteratorList(widgetList);
        }).start();

        new Thread(()->{
            widgetSync.remove(widgetList,widget3);
        }).start();

    }
}

package com.jcip.chapter5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Widget{
    private static int count = 1;
    private final  long id = count++;

    @Override
    public String toString() {
        return "Widget{" + "id=" + id + '}';
    }
}

public class WidgetSync {
    private void iteratorList(List<Widget> list){
        for (Widget widget : list) {
            System.out.println(widget);
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void remove(List<Widget> list,Widget widget){
        list.remove(widget);
    }

    public static void main(String[] args) {
        final WidgetSync widgetSync = new WidgetSync();
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

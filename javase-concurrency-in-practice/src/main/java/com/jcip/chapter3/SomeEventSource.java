package com.jcip.chapter3;

public class SomeEventSource implements EventSource{

    private EventListener eventListener;

    @Override
    public void registerListener(EventListener e) {
        this.eventListener = e;
    }

    public void processEvent(Event event){
        eventListener.onEvent(event);
    }
}

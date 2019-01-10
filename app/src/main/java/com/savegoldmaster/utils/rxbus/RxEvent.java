package com.savegoldmaster.utils.rxbus;

/**
 * Date: 2018/4/28.
 * Author: Zhanglibin
 */

public class RxEvent {
    private String eventType;
    private Object o;

    public RxEvent(String eventType, Object o) {
        this.eventType = eventType;
        this.o = o;

    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }
}

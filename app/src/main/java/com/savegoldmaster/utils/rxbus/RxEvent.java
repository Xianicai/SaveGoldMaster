package com.savegoldmaster.utils.rxbus;

/**
 * Date: 2018/4/28.
 * Author: Zhanglibin
 */

public class RxEvent {
    private String eventType;
    private Object object;

    public RxEvent(String eventType, Object object) {
        this.eventType = eventType;
        this.object = object;

    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}

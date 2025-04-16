package br.com.service;

import br.com.enums.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.com.enums.Event.CLEAR_SPACE;

public class NotifierService {

    private Map<Event, List<EventListener>> listeners = new HashMap<>(){{
        put(CLEAR_SPACE, new ArrayList<>());
    }};

    public void subscriber(final Event eventType, EventListener listener) {

        List<EventListener> selectedListener = listeners.get(eventType);
        selectedListener.add(listener);
    }

    public void notify(final Event eventType) {

        listeners.get(eventType).forEach(l -> l.update(eventType));
    }
}
package br.com.service;

import br.com.enums.Event;

public interface EventListener {

    void update(final Event eventType);
}
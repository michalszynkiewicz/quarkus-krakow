package com.example;

import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QueryForwarder {
    @Channel("search-terms")
    Emitter<JsonObject> searchTermEmitter;

    public void send(JsonObject payload) {
        searchTermEmitter.send(payload);
    }
}

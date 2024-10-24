package com.ias.gateway;

import com.google.gson.Gson;
import com.ias.DynamoReactiveEventAdapter;
import com.ias.event.Event;
import com.ias.event.gateway.EventRepository;
import com.ias.model.EventEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class EventGatewayImpl implements EventRepository {

    private final DynamoReactiveEventAdapter dynamoReactiveEventAdapter;

    private final Gson mapper;



    @Override
    public Mono<Event> getById(String id) {
        return dynamoReactiveEventAdapter.findById(id)
                .map(eventEntity -> mapper.fromJson(mapper.toJson(eventEntity), Event.class));
    }
    @Override
    public Flux<Event> getAll() {
        return dynamoReactiveEventAdapter.findAll()
                .map(eventEntity ->  mapper.fromJson(mapper.toJson(eventEntity), Event.class));
    }
    @Override
    public Flux<Event> getAllByUserId(String id) {
        return Flux.empty();
    }

    @Override
    public Mono<Event> save(Event event) {
        return dynamoReactiveEventAdapter.save(mapper.fromJson(mapper.toJson(event), EventEntity.class))
                .map(eventEntity-> mapper.fromJson(mapper.toJson(eventEntity), Event.class));
    }

    @Override
    public Mono<Event> update(Event event) {
        return dynamoReactiveEventAdapter.save(mapper.fromJson(mapper.toJson(event), EventEntity.class))
                .map(eventEntity-> mapper.fromJson(mapper.toJson(eventEntity), Event.class));
    }

    @Override
    public Mono<Void> delete(String id) {
        return Mono.empty();
    }
}

package com.ias;

import com.google.gson.Gson;
import com.ias.event.Event;
import com.ias.request.EventRequest;
import com.ias.response.StatusEventResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {
    private final GetEventByIdUseCase getEventByIdUseCase;
    private final GetAllEventsUseCase getAllEventsUseCase;
    private final CreateOrUpdateEventUseCase createOrUpdateEventUseCase;
    private final Gson mapper;

    public Mono<ServerResponse> listenGETEvents(ServerRequest serverRequest) {
        return getAllEventsUseCase.get()
                .collectList()
                .flatMap(events -> events.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(events));
    }


    public Mono<ServerResponse> listenGETEventById(ServerRequest serverRequest) {

        return  getEventByIdUseCase.get(serverRequest.pathVariable("id")) //TODO: Agregar trace UUID y logs
                .flatMap(event -> ServerResponse.ok().bodyValue(event))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(new StatusEventResponse("The event not exist")));
    }

    public Mono<ServerResponse> listenPUTCreateOrUpdateEvent(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(EventRequest.class)
                .map(eventRequest -> mapper.fromJson(mapper.toJson(eventRequest), Event.class))
                .flatMap(event ->
                        createOrUpdateEventUseCase.execute(event)
                                .flatMap( eventHandler -> ServerResponse.created(null).bodyValue(eventHandler))
                );
    }

    public Mono<ServerResponse> listenDELETEEvent(ServerRequest serverRequest) {
        String traceUUID = UUID.randomUUID().toString();
        return ServerResponse.ok().bodyValue(traceUUID);
    }

    public Mono<ServerResponse> listenPOSTRegisterUserToEvent(ServerRequest serverRequest) {
        String traceUUID = UUID.randomUUID().toString();
        return ServerResponse.ok().bodyValue(traceUUID);
    }

    public Mono<ServerResponse> listenGETEventByUserId(ServerRequest serverRequest) {
        String traceUUID = UUID.randomUUID().toString();
        return ServerResponse.ok().bodyValue(traceUUID);
    }
}

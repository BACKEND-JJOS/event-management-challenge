package com.ias;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class Handler {

    private final Gson mapper;

    public Mono<ServerResponse> listenGETEvents(ServerRequest serverRequest) {
        String traceUUID = UUID.randomUUID().toString();
        return ServerResponse.ok().bodyValue(traceUUID);
    }

    public Mono<ServerResponse> listenGETEventById(ServerRequest serverRequest) {
        String traceUUID = UUID.randomUUID().toString();
        return ServerResponse.ok().bodyValue(traceUUID);
    }

    public Mono<ServerResponse> listenPUTCreateOrUpdateEvent(ServerRequest serverRequest) {
        String traceUUID = UUID.randomUUID().toString();
        return ServerResponse.ok().bodyValue(traceUUID);
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

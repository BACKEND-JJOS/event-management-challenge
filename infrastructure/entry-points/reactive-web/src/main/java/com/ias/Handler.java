package com.ias;

import com.google.gson.Gson;
import com.ias.auth.JwtUtils;
import com.ias.event.Event;
import com.ias.mapper.MapperEvent;
import com.ias.mapper.MapperUser;
import com.ias.request.EventRequest;
import com.ias.request.UserLoginRequest;
import com.ias.request.UserRequest;
import com.ias.response.AuthResponse;
import com.ias.response.StatusEventResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
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
    private final RegisterUserToEventUseCase registerUserToEventUseCase;
    private final GetEventsByUserIdUseCase getEventsByUserIdUseCase;
    private final DeleteEventByIdUseCase deleteEventByIdUseCase;
    private final UserAuthenticationUseCase userAuthenticationUseCase;
    private  final JwtUtils jwtUtils;
    private final MapperUser mapperUser;
    private final MapperEvent mapperEvent;
    private final Gson mapper;

    private static final String MESSAGE_LOG_TRACE = "CONTROLLER RUN {} WITH TRACE {}";

    public Mono<ServerResponse> listenGETEvents(ServerRequest serverRequest) {
        String traceUUID = UUID.randomUUID().toString();
        log.info(MESSAGE_LOG_TRACE, serverRequest.uri(), traceUUID);
        return getAllEventsUseCase.get(traceUUID)
                .map(mapperEvent::toEventResponse)
                .collectList()
                .flatMap(eventResponses -> eventResponses.isEmpty()
                        ? ServerResponse.noContent().build()
                        : ServerResponse.ok().bodyValue(eventResponses));
    }


    public Mono<ServerResponse> listenGETEventById(ServerRequest serverRequest) {
        String traceUUID = UUID.randomUUID().toString();
        log.info(MESSAGE_LOG_TRACE, serverRequest.uri(), traceUUID);
        return getEventByIdUseCase.get(Integer.valueOf(serverRequest.pathVariable("id")), traceUUID)
                .flatMap(event -> ServerResponse.ok().bodyValue(event))
                .switchIfEmpty(ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(new StatusEventResponse("The event not exist")));
    }

    public Mono<ServerResponse> listenPUTCreateOrUpdateEvent(ServerRequest serverRequest) {
        String traceUUID = UUID.randomUUID().toString();
        log.info(MESSAGE_LOG_TRACE, serverRequest.uri(), traceUUID);
        return serverRequest.bodyToMono(EventRequest.class)
                .map(eventRequest -> mapper.fromJson(mapper.toJson(eventRequest), Event.class))
                .flatMap(event ->
                        createOrUpdateEventUseCase.execute(event, traceUUID)
                                .flatMap(eventHandler -> ServerResponse.created(null).bodyValue(mapperEvent.toEventResponse(eventHandler)))
                );
    }

    public Mono<ServerResponse> listenDELETEEvent(ServerRequest serverRequest) {
        String traceUUID = UUID.randomUUID().toString();
        log.info(MESSAGE_LOG_TRACE, serverRequest.uri(), traceUUID);
        return deleteEventByIdUseCase.execute(Integer.valueOf(serverRequest.pathVariable("id")),traceUUID)
                .then(ServerResponse.ok().bodyValue(new StatusEventResponse("The event was deleted")));
    }

    public Mono<ServerResponse> listenPUTRegisterUserToEvent(ServerRequest serverRequest) {
        String traceUUID = UUID.randomUUID().toString();
        log.info(MESSAGE_LOG_TRACE, serverRequest.uri(), traceUUID);
        return serverRequest.bodyToMono(UserRequest.class)
                .map(mapperUser::toDomain)
                .flatMap(user ->
                        registerUserToEventUseCase.register(user, Integer.valueOf(serverRequest.pathVariable("id")),traceUUID)
                                .then(ServerResponse.ok().bodyValue(new StatusEventResponse("The user was registered to the event")))
                );
    }

    public Mono<ServerResponse> listenGETEventByUserId(ServerRequest serverRequest) {
        String traceUUID = UUID.randomUUID().toString();
        log.info(MESSAGE_LOG_TRACE, serverRequest.uri(), traceUUID);
        return getEventsByUserIdUseCase.execute(Integer.valueOf(serverRequest.pathVariable("userId")),traceUUID)
                .map(event -> mapper.fromJson(mapper.toJson(event), EventRequest.class))
                .collectList()
                .flatMap(eventRequests ->
                        eventRequests.isEmpty()
                                ? ServerResponse.status(HttpStatus.NOT_FOUND).bodyValue(new StatusEventResponse("The user is not registered in the event"))
                                : ServerResponse.ok().bodyValue(eventRequests)
                );

    }

    public Mono<ServerResponse> listenPOSTLogin(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserLoginRequest.class)
                .flatMap(userLoginRequest -> userAuthenticationUseCase.authenticate(userLoginRequest.getUserName(), userLoginRequest.getPassword())
                        .flatMap(auth -> {
                            String token = jwtUtils.createToken(auth.getUsername(), auth.getAuthorities());
                            return ServerResponse.ok().bodyValue(new AuthResponse(auth.getUsername(), token));
                        })
                )
                .onErrorResume(BadCredentialsException.class, e ->
                        ServerResponse.status(HttpStatus.UNAUTHORIZED).bodyValue("Invalid username or password")
                );
    }
}

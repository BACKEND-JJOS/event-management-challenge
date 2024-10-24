package com.ias;

import com.ias.openapi.OpenApiDoc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;


@Configuration
public class RouterRest {

    private final static String EVENTS_ROUTE = "/events";
    private final static String ID_PATH_PARAM = "/{id}";

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route()
                .GET(EVENTS_ROUTE, handler::listenGETEvents, OpenApiDoc::getEvents)
                .GET(EVENTS_ROUTE + "/user/{userId}", handler::listenGETEventByUserId, OpenApiDoc::getEventsByUserId)
                .POST(EVENTS_ROUTE + ID_PATH_PARAM + "/register", handler::listenPOSTRegisterUserToEvent, OpenApiDoc::registerUserToEvent)
                .GET(EVENTS_ROUTE + ID_PATH_PARAM, handler::listenGETEventById, OpenApiDoc::getEventById)
                .PUT(EVENTS_ROUTE + ID_PATH_PARAM, handler::listenPUTCreateOrUpdateEvent, OpenApiDoc::createOrUpdateEvent)
                .DELETE(EVENTS_ROUTE + ID_PATH_PARAM, handler::listenDELETEEvent, OpenApiDoc::deleteEvent)
                .build();
    }
}

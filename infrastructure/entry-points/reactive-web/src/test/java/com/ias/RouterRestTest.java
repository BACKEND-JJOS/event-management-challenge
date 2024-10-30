package com.ias;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.ias.auth.JwtUtils;
import com.ias.auth.SecurityConfig;
import com.ias.event.Event;
import com.ias.mapper.MapperEvent;
import com.ias.mapper.MapperUser;
import com.ias.request.EventRequest;
import com.ias.response.EventResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ContextConfiguration(classes = {RouterRest.class, Handler.class, JwtUtils.class})
@WebFluxTest
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GetEventByIdUseCase getEventByIdUseCase;
    @MockBean
    private GetAllEventsUseCase getAllEventsUseCase;
    @MockBean
    private CreateOrUpdateEventUseCase createOrUpdateEventUseCase;
    @MockBean
    private RegisterUserToEventUseCase registerUserToEventUseCase;
    @MockBean
    private GetEventsByUserIdUseCase getEventsByUserIdUseCase;
    @MockBean
    private DeleteEventByIdUseCase deleteEventByIdUseCase;
    @MockBean
    private UserAuthenticationUseCase userAuthenticationUseCase;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private MapperUser mapperUser;
    @MockBean
    private MapperEvent mapperEvent;
    @MockBean
    private Gson mapper;

    private String token;

    private String traceUUID;

    private static final String EVENTS_ROUTE = "/events";


    @BeforeEach
    void setUp() {
        token = "Bearer test-token";
        given(jwtUtils.createToken(anyString(), anyList()))
                .willReturn("test-token");

        DecodedJWT decodedJWTMock = mock(DecodedJWT.class);
        given(decodedJWTMock.getSubject()).willReturn("user");

        given(jwtUtils.validateToken("test-token"))
                .willReturn(decodedJWTMock);
    }

    @Test
    @WithMockUser(username = "any-user", authorities = {"READ"})
    void shouldGetAllEvents_shouldReturnOk_whenEverythingIsFine() {
        Event eventMock1 = Event.builder()
                .date("2024-10-24T10:00:00Z")
                .id(1)
                .location("any-location")
                .name("any-name")
                .build();

        Event eventMock2 = Event.builder()
                .date("2024-10-24T12:00:00Z")
                .id(2)
                .location("any-location")
                .name("any-name")
                .build();

        EventResponse eventResponseMock1 = EventResponse.builder()
                .date("2024-10-24T10:00:00Z")
                .id(1)
                .location("any-location")
                .name("any-name")
                .build();

        EventResponse eventResponseMock2 = EventResponse.builder()
                .date("2024-10-24T12:00:00Z")
                .id(2)
                .location("any-location")
                .name("any-name")
                .build();

        given(getAllEventsUseCase.get(anyString()))
                .willReturn(Flux.just(eventMock1, eventMock2));

        given(mapperEvent.toEventResponse(eventMock1)).willReturn(eventResponseMock1);
        given(mapperEvent.toEventResponse(eventMock2)).willReturn(eventResponseMock2);

        webTestClient.get()
                .uri(EVENTS_ROUTE)
                .header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(EventResponse.class)
                .hasSize(2)
                .value(eventResponses -> {
                    Assertions.assertThat(eventResponses.get(0).getName()).isEqualTo(eventMock1.getName());
                });
    }

    @Test
    @WithMockUser(username = "user", authorities = {"READ"})
    void shouldGetAllEvents_shouldReturnNoContent_whenNoEventsAreFound() {
        given(getAllEventsUseCase.get(anyString()))
                .willReturn(Flux.empty());

        webTestClient.get()
                .uri(EVENTS_ROUTE)
                .header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void shouldGetAllEvents_shouldReturnUnauthorized_whenTokenIsMissing() {
        webTestClient.get()
                .uri(EVENTS_ROUTE)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser(username = "any-user", authorities = {"READ"})
    void shouldGetEventById_shouldReturnOk_whenEverythingIsFine() {
        Event eventMock1 = Event.builder()
                .date("2024-10-24T10:00:00Z")
                .id(1)
                .location("any-location")
                .name("any-name")
                .build();


        EventResponse eventResponseMock1 = EventResponse.builder()
                .date("2024-10-24T10:00:00Z")
                .id(1)
                .location("any-location")
                .name("any-name")
                .build();


        given(getEventByIdUseCase.get(anyInt(), anyString()))
                .willReturn(Mono.just(eventMock1));

        given(mapperEvent.toEventResponse(eventMock1)).willReturn(eventResponseMock1);

        webTestClient.get()
                .uri(EVENTS_ROUTE + "/1")
                .header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(EventResponse.class)
                .value(eventResponse -> {
                    Assertions.assertThat(eventResponse.getName()).isEqualTo(eventMock1.getName());
                });

    }

    @Test
    @WithMockUser(username = "any-user", authorities = {"READ"})
    void shouldGetEventById_shouldReturnNotFound_whenEventIdIsNotFound() {

        given(getEventByIdUseCase.get(anyInt(), anyString()))
                .willReturn(Mono.empty());
        webTestClient.get()
                .uri(EVENTS_ROUTE + "/1")
                .header(HttpHeaders.AUTHORIZATION, token)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();

    }

}

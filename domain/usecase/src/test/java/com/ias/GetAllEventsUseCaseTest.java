package com.ias;

import com.ias.event.Event;
import com.ias.event.gateway.EventRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Flux;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GetAllEventsUseCaseTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private GetAllEventsUseCase getAllEventsUseCase;

    @Test
    void shouldGetAllEvents_whenEventRepositoryReturnFluxEvents(){
        Event eventMock1 = Event.builder()
                .date("2024-10-24T10:00:00Z")
                .id(1)
                .location("any-location")
                .name("any-name")
                .build();

        Event eventMock2 = Event.builder()
                .date("2024-10-24T10:00:00Z")
                .id(2)
                .location("any-location")
                .name("any-name")
                .build();

        given(eventRepository.getAll(anyString()))
                .willReturn(Flux.just(eventMock1,eventMock2));

        Flux<Event> eventFlux = getAllEventsUseCase.get(anyString());
        List<Event> eventList = eventFlux.collectList().block();

        Assertions.assertThat(eventList).hasSize(2);
        Assertions.assertThat(eventList.get(0).getId()).isNotNull();
        Assertions.assertThat(eventList.get(1).getId()).isNotNull();

        then(eventRepository).should(times(1)).getAll(anyString());
    }
}

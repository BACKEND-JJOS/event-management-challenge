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
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GetEventByAssistantIdUseCaseTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private GetEventsByUserIdUseCase getEventsByUserIdUseCase;

    @Test
    void shouldGetEventById_whenEventRepositoryFindsOne() {
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

        given(eventRepository.getEventsByUserId(anyInt(), anyString()))
                .willReturn(Flux.just(eventMock1,eventMock2));

        Flux<Event> eventMono = getEventsByUserIdUseCase.execute(anyInt(), anyString());
        List<Event> eventList = eventMono.collectList().block();

        Assertions.assertThat(eventList).hasSize(2);
        Assertions.assertThat(eventList.get(0).getId()).isEqualTo(eventMock1.getId());
        then(eventRepository).should(times(1)).getEventsByUserId(anyInt(), anyString());
    }

    @Test
    void shouldReturnEmpty_whenEventRepositoryDoesNotFindAny() {

        given(eventRepository.getEventsByUserId(anyInt(), anyString()))
                .willReturn(Flux.empty());

        Flux<Event> eventMono = getEventsByUserIdUseCase.execute(anyInt(), anyString());
        List<Event> eventList = eventMono.collectList().block();

        Assertions.assertThat(eventList).hasSize(0);

        then(eventRepository).should(times(1)).getEventsByUserId(anyInt(), anyString());
    }
}

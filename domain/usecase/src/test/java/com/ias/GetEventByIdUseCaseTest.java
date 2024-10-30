package com.ias;

import com.ias.event.Event;
import com.ias.event.gateway.EventRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class GetEventByIdUseCaseTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private GetEventByIdUseCase getEventByIdUseCase;

    @Test
    void shouldGetEventById_whenEventRepositoryFindsOne() {
        Event eventMock1 = Event.builder()
                .date("2024-10-24T10:00:00Z")
                .id(1)
                .location("any-location")
                .name("any-name")
                .build();

        given(eventRepository.getById(anyInt(), anyString()))
                .willReturn(Mono.just(eventMock1));

        Mono<Event> eventMono = getEventByIdUseCase.get(anyInt(), anyString());
        Event event = eventMono.block();

        Assertions.assertThat(event).isNotNull();
        Assertions.assertThat(event.getId()).isEqualTo(eventMock1.getId());
        then(eventRepository).should(times(1)).getById(anyInt(), anyString());
    }

    @Test
    void shouldReturnEmpty_whenEventRepositoryDoesNotFindAny() {
        given(eventRepository.getById(anyInt(), anyString()))
                .willReturn(Mono.empty());

        Mono<Event> eventMono = getEventByIdUseCase.get(anyInt(), anyString());
        Event event = eventMono.block();

        Assertions.assertThat(event).isNull();
        then(eventRepository).should(times(1)).getById(anyInt(), anyString());
    }
}

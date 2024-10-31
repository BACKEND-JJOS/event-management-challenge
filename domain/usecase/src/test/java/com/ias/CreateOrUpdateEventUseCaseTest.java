package com.ias;

import com.ias.event.Event;
import com.ias.event.gateway.EventGateway;
import com.ias.event.gateway.EventRepository;
import com.ias.exception.BusinessEventErrorCode;
import com.ias.exception.BusinessException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CreateOrUpdateEventUseCaseTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventGateway eventGateway;

    @InjectMocks
    private CreateOrUpdateEventUseCase createOrUpdateEventUseCase;

    @Test
    void shouldCreateEvent_whenEventHasIdNull_whenFormatValidatorAndBusinessIsOk() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String date = LocalDateTime.now().plusHours(1).format(formatter);
        Event eventReceived = Event.builder()
                .date(date)
                .id(null)
                .location("any-location")
                .name("any-name")
                .build();
        String traceUUID = UUID.randomUUID().toString();
        Event eventCreatedMock = Event.builder()
                .date(date)
                .id(1)
                .location("any-location")
                .name("any-name")
                .build();

        given(eventRepository.save(eventReceived, traceUUID))
                .willReturn(Mono.just(eventCreatedMock));

        given(eventGateway.publishEventCreated(eventCreatedMock, traceUUID))
                .willReturn(Mono.empty());

        Mono<Event> event = createOrUpdateEventUseCase.execute(eventReceived, traceUUID);
        Event eventList = event.block();

        Assertions.assertThat(eventList).isNotNull();
        Assertions.assertThat(eventList.getId()).isEqualTo(eventCreatedMock.getId());

        then(eventRepository).should(times(1)).save(any(Event.class), anyString());
        then(eventGateway).should(times(1)).publishEventCreated(any(Event.class),anyString());
    }

    @Test
    void shouldUpdatedEvent_whenEventHasId_whenFormatValidatorAndBusinessIsOk() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String date = LocalDateTime.now().plusHours(1).format(formatter);
        Event eventReceived = Event.builder()
                .date(date)
                .id(1)
                .location("any-location")
                .name("any-name")
                .build();
        String traceUUID = UUID.randomUUID().toString();
        Event eventUpdatedMock = Event.builder()
                .date(date)
                .id(1)
                .location("any-location")
                .name("any-name")
                .build();

        given(eventRepository.save(eventReceived, traceUUID))
                .willReturn(Mono.empty());

        given(eventRepository.update(eventReceived, traceUUID))
                .willReturn(Mono.just(eventUpdatedMock));

        given(eventGateway.publishEventUpdated(eventUpdatedMock, traceUUID))
                .willReturn(Mono.empty());

        Mono<Event> event = createOrUpdateEventUseCase.execute(eventReceived, traceUUID);
        Event eventList = event.block();

        Assertions.assertThat(eventList).isNotNull();
        Assertions.assertThat(eventList.getId()).isEqualTo(eventUpdatedMock.getId());

        then(eventRepository).should(times(1)).update(any(Event.class), anyString());
        then(eventGateway).should(times(1)).publishEventUpdated(any(Event.class),anyString());
    }

    @Test
    void shouldThrowBusinessException_whenDateFormatIsInvalid() {
        String invalidDate = "2024-24-10";
        Event eventWithInvalidDate = Event.builder()
                .date(invalidDate)
                .location("any-location")
                .name("any-name")
                .build();
        String traceUUID = UUID.randomUUID().toString();

        StepVerifier.create(createOrUpdateEventUseCase.execute(eventWithInvalidDate, traceUUID))
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException &&
                                throwable.getMessage().equals(BusinessEventErrorCode.INVALID_DATE_FORMAT.getMessage())
                )
                .verify();

    }

    @Test
    void shouldThrowBusinessException_whenDateIsInvalid() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        String dateLowerAllowed = LocalDateTime.now().minusHours(1).format(formatter);
        Event eventWithInvalidDate = Event.builder()
                .date(dateLowerAllowed)
                .location("any-location")
                .name("any-name")
                .build();
        String traceUUID = UUID.randomUUID().toString();

        StepVerifier.create(createOrUpdateEventUseCase.execute(eventWithInvalidDate, traceUUID))
                .expectErrorMatches(throwable ->
                        throwable instanceof BusinessException &&
                                throwable.getMessage().equals(BusinessEventErrorCode.EVENT_DATE_IN_PAST.getMessage())
                )
                .verify();

    }


}

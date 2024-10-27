package com.ias.validator;

import com.ias.exception.BusinessEventErrorCode;
import com.ias.exception.BusinessException;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Clase que valida condiciones de negocio relacionadas con eventos.
 */
public class EventBusinessValidator {

    /**
     * Valida que la fecha del evento sea al menos 30 minutos en el futuro.
     *
     * @param date La fecha del evento en formato de cadena.
     * @return Un Mono vacío si la fecha es válida, o un error si la fecha está en el pasado.
     */
    public static Mono<Void> validateDateInFuture(String date) {
        return Mono.just(date)
                .map(LocalDateTime::parse)
                .flatMap(eventDate -> eventDate.isBefore(LocalDateTime.now().plusMinutes(30))
                        ? Mono.error(new BusinessException(BusinessEventErrorCode.EVENT_DATE_IN_PAST))
                        : Mono.empty()
                );
    }
}

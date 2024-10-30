package com.ias.validator;


import com.ias.exception.BusinessEventErrorCode;
import com.ias.exception.BusinessException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Clase que valida la entrada de datos para eventos.
 */
public class EventInputValidator {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Valida el formato de la fecha de entrada.
     *
     * @param date La fecha del evento en formato de cadena.
     * @return Un Mono vacío si el formato es válido, o un error si el formato es inválido.
     */
    public static Mono<Void> validateDateFormat(String date) {
        return Mono.just(date)
                .map(d -> LocalDateTime.parse(d, DATE_FORMAT))
                .then()
                .onErrorMap(DateTimeParseException.class, e ->
                        new BusinessException(BusinessEventErrorCode.INVALID_DATE_FORMAT));
    }
}

package com.ias.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessEventErrorCode {
    EVENT_NOT_FOUND("E001", "Event not found"),
    INVALID_DATE_FORMAT("E002", "Invalid date format. Expected format: yyyy-MM-dd'T'HH:mm:ss"),
    EVENT_DATE_IN_PAST("E003", "The event must start at least 30 minutes from the current time"),
    USER_NOT_FOUND("U001", "User not found"),
    USER_ALREADY_REGISTERED("U002", "User is already registered to the event");

    private final String code;
    private final String message;
}

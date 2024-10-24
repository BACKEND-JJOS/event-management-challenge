package com.ias.openapi;

import com.ias.request.EventRequest;
import com.ias.response.EventResponse;
import com.ias.response.StatusEventResponse;
import lombok.experimental.UtilityClass;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

@UtilityClass
public class OpenApiDoc {

    private static final String MEDIA_TYPE_APPLICATION_JSON = "application/json";
    private static final String TAG_EVENT = "Event";
    private static final String TAG_USER = "User";

    public Builder getEvents(Builder builder) {
        return builder.operationId("getEvents")
                .description("Get the list of all events")
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.OK.toString())
                                .description("List of events")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(EventResponse.class))
                                )
                )
                .tag(TAG_EVENT);
    }

    public Builder getEventById(Builder builder) {
        return builder.operationId("getEventById")
                .description("Get details of a specific event by its ID")
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.OK.toString())
                                .description("Details of the event")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(EventResponse.class))
                                )
                )
                .tag(TAG_EVENT);
    }

    public Builder createOrUpdateEvent(Builder builder) {
        return builder.operationId("createOrUpdateEvent")
                .description("Create a new event or update an existing one")
                .requestBody(
                        requestBodyBuilder()
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(EventRequest.class))
                                )
                )
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.OK.toString())
                                .description("Event created or updated successfully")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(StatusEventResponse.class))
                                )
                )
                .tag(TAG_EVENT);
    }

    public Builder deleteEvent(Builder builder) {
        return builder.operationId("deleteEvent")
                .description("Delete an event by its ID")
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.OK.toString())
                                .description("Event deleted successfully")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(StatusEventResponse.class))
                                )
                )
                .tag(TAG_EVENT);
    }

    public Builder registerUserToEvent(Builder builder) {
        return builder.operationId("registerUserToEvent")
                .description("Register a user for a specific event")
                .requestBody(
                        requestBodyBuilder()
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(EventRequest.class))
                                )
                )
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.OK.toString())
                                .description("User registered successfully for the event")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(StatusEventResponse.class))
                                )
                )
                .tag(TAG_USER);
    }

    public Builder getEventsByUserId(Builder builder) {
        return builder.operationId("getEventsByUserId")
                .description("Retrieve a list of events for which the user is registered")
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.OK.toString())
                                .description("List of events the user is registered for")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(EventResponse.class))
                                )
                )
                .tag(TAG_USER);
    }
}

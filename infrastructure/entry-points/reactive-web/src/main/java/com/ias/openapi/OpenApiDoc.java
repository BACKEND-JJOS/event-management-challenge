package com.ias.openapi;

import com.ias.exception.ErrorResponse;
import com.ias.request.EventRequest;
import com.ias.request.UserLoginRequest;
import com.ias.request.UserRequest;
import com.ias.response.AuthResponse;
import com.ias.response.EventResponse;
import com.ias.response.StatusEventResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.experimental.UtilityClass;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;
import static org.springdoc.core.fn.builders.securityrequirement.Builder.securityRequirementBuilder;

@UtilityClass
@OpenAPIDefinition(info = @Info(title = "API Events", version = "1.0"))
@SecurityScheme(name = "BearerAuth", type = SecuritySchemeType.APIKEY,in = SecuritySchemeIn.HEADER , description = "Enter the token with the `Bearer: ` prefix, e.g. Bearer abcde12345")
public class OpenApiDoc {

    private static final String MEDIA_TYPE_APPLICATION_JSON = "application/json";

    private static final String TYPE_INTEGER = "integer";
    private static final String TYPE_INTEGER_FORMAT = "int32";
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
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.NO_CONTENT.toString())
                                .description("List of events empty")
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
                .security(securityRequirementBuilder().name("Bearer"))
                .parameter(parameterBuilder()
                        .name("id")
                        .description("The Id of the event to retrieve")
                        .in(ParameterIn.PATH)
                        .required(true)
                        .example("9999")
                        .schema(schemaBuilder().type(TYPE_INTEGER).format(TYPE_INTEGER_FORMAT))
                )
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
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.NOT_FOUND.toString())
                                .description("Event not found")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(String.class))
                                )
                )
                .tag(TAG_EVENT);
    }

    public Builder createOrUpdateEvent(Builder builder) {
        return builder.operationId("createOrUpdateEvent")
                .description("Create a new event or update an existing one")
                .requestBody(
                        requestBodyBuilder()
                                .required(true)
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(EventRequest.class))
                                )
                )
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.BAD_REQUEST.toString())
                                .description("""
                                        The event does not conform to the expected date format. 
                                        The event must start at least 30 minutes after the current time.
                                         """)
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(ErrorResponse.class))
                                )

                )
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.CREATED.toString())
                                .description("Event created or updated successfully")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(EventResponse.class))
                                )
                )
                .tag(TAG_EVENT);
    }

    public Builder deleteEvent(Builder builder) {
        return builder.operationId("deleteEvent")
                .description("Delete an event by its ID")
                .parameter(parameterBuilder()
                        .name("id")
                        .description("The id of the event to be deleted")
                        .in(ParameterIn.PATH)
                        .required(true)
                        .example("9999")
                        .schema(schemaBuilder().type(TYPE_INTEGER).format(TYPE_INTEGER_FORMAT))
                )
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
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.NOT_FOUND.toString())
                                .description("Event not found")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(String.class))
                                )
                )
                .tag(TAG_EVENT);
    }

    public Builder registerUserToEvent(Builder builder) {
        return builder.operationId("registerUserToEvent")
                .description("Register a user for a specific event")
                .parameter(parameterBuilder()
                        .name("id")
                        .description("Id of the event that the user wants to register")
                        .in(ParameterIn.PATH)
                        .required(true)
                        .example("9999")
                        .schema(schemaBuilder().type(TYPE_INTEGER).format(TYPE_INTEGER_FORMAT))
                )
                .requestBody(
                        requestBodyBuilder()
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(UserRequest.class))
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
                .parameter(parameterBuilder()
                        .name("userId")
                        .description("Id of the user who requires their events")
                        .in(ParameterIn.PATH)
                        .required(true)
                        .example("9999")
                        .schema(schemaBuilder().type(TYPE_INTEGER).format(TYPE_INTEGER_FORMAT))
                )
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

    public Builder postLogin(Builder builder) {
        return builder.operationId("login")
                .description("Authenticate user and generate a JWT token.")
                .requestBody(
                        requestBodyBuilder()
                                .required(true)
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(UserLoginRequest.class))
                                )
                )
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.OK.toString())
                                .description("Successful authentication with JWT token")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(AuthResponse.class))
                                )
                )
                .response(
                        responseBuilder()
                                .responseCode(HttpStatus.UNAUTHORIZED.toString())
                                .description("Invalid username or password")
                                .content(
                                        contentBuilder()
                                                .mediaType(MEDIA_TYPE_APPLICATION_JSON)
                                                .schema(schemaBuilder().implementation(String.class))
                                )
                )
                .tag("Authentication");
    }
}

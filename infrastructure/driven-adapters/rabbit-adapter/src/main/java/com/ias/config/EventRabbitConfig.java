package com.ias.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventRabbitConfig {
    public static final String EVENT_EXCHANGE = "event.exchange";

    public static final String EVENT_CREATED_QUEUE = "event.created.notifications.queue";
    public static final String EVENT_UPDATED_QUEUE = "event.updated.notifications.queue";
    public static final String EVENT_DELETED_QUEUE = "event.deleted.notifications.queue";
    public static final String EVENT_USER_REGISTER_QUEUE = "user.event.register.notifications.queue";

    public static final String EVENT_CREATED_ROUTING_KEY = "event.created";
    public static final String EVENT_UPDATED_ROUTING_KEY = "event.updated";
    public static final String EVENT_DELETED_ROUTING_KEY = "event.deleted";
    public static final String EVENT_USER_REGISTER_ROUTING_KEY = "user.event.register";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EVENT_EXCHANGE, true, false);
    }

    @Bean
    public Queue createdQueue() {
        return new Queue(EVENT_CREATED_QUEUE, true);
    }

    @Bean
    public Queue updatedQueue() {
        return new Queue(EVENT_UPDATED_QUEUE, true);
    }

    @Bean
    public Queue deletedQueue() {
        return new Queue(EVENT_DELETED_QUEUE, true);
    }

    @Bean
    public Queue userRegisterQueue() {
        return new Queue(EVENT_USER_REGISTER_QUEUE, true);
    }

    @Bean
    public Binding bindingCreatedQueue() {
        return BindingBuilder.bind(createdQueue()).to(exchange()).with(EVENT_CREATED_ROUTING_KEY);
    }

    @Bean
    public Binding bindingUpdatedQueue() {
        return BindingBuilder.bind(updatedQueue()).to(exchange()).with(EVENT_UPDATED_ROUTING_KEY);
    }

    @Bean
    public Binding bindingDeletedQueue() {
        return BindingBuilder.bind(deletedQueue()).to(exchange()).with(EVENT_DELETED_ROUTING_KEY);
    }

    @Bean
    public Binding bindingUserRegisterQueue() {
        return BindingBuilder.bind(userRegisterQueue()).to(exchange()).with(EVENT_USER_REGISTER_ROUTING_KEY);
    }
}

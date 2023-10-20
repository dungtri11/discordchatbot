package com.discordbot.mathew.service;


import com.discordbot.mathew.entity.ChatGPT;
import com.discordbot.mathew.listener.EventListener;
import com.discordbot.mathew.listener.MessageListener;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageUpdateService extends MessageListener implements EventListener<MessageUpdateEvent> {
    @Override
    public Class<MessageUpdateEvent> getEventType() {
        return MessageUpdateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageUpdateEvent event) {
        return Mono.just(event)
                .filter(MessageUpdateEvent::isContentChanged)
                .flatMap(MessageUpdateEvent::getMessage)
                .flatMap(super::processMessage);
    }
}

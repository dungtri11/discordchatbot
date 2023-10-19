package com.discordbot.mathew.listener;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class MessageListener {
    private String author = "UNKNOWN";

    public Mono<Void> processMessage(final Message message) {
        return Mono.just(message)
                .filter(msg -> {
                    final Boolean notFromBot = msg.getAuthor()
                            .map(user -> !user.isBot())
                            .orElse(false);
                    if (notFromBot) {
                        msg.getAuthor().ifPresent(user -> author = user.getUsername());
                    }
                    return notFromBot;
                })
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(String.format("Hi %s", author)))
                .then();
    }

    private Mono<Void> callBot(final Message message) {
        return Mono.just(message)
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(String.format("Hi %s", author)))
                .then();
    }
}

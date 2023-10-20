package com.discordbot.mathew.listener;

import com.discordbot.mathew.entity.ChatGPT;
import discord4j.core.object.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
@Slf4j
public abstract class MessageListener {
    private String author = "UNKNOWN";
    private String message;
    @Autowired
    private ChatGPT gpt;

    public Mono<Void> processMessage(final Message message) {

        return Mono.just(message)
                .filter(msg -> {
                    final Boolean notFromBot = msg.getAuthor()
                            .map(user -> !user.isBot())
                            .orElse(false);
                    final Boolean isCallBot = msg.getContent().toLowerCase().startsWith("hey mathew,");
                    if (notFromBot) {
                        msg.getAuthor().ifPresent(user -> author = user.getUsername());
                    }
                    return notFromBot && isCallBot;
                })
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(gpt.responseTo(message
                        .getContent().replaceAll("hey mathew,","").trim())))
                .then();
    }


}

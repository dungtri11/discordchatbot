package com.discordbot.mathew.listener;

import com.discordbot.mathew.repository.ConversationRepository;
import com.discordbot.mathew.service.ChatGPTService;
import discord4j.core.object.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Slf4j
public abstract class MessageListener {
    private String message;
    @Autowired
    private ChatGPTService gpt;
    @Autowired
    private ConversationRepository repository;

    public Mono<Void> processMessage(final Message message) {
        String content = message.getContent().toLowerCase();
        if (repository.getMessages().size() > 500) {
            return Mono.just(message)
                    .filter(msg -> {
                        final Boolean notFromBot = msg.getAuthor()
                                .map(user -> !user.isBot())
                                .orElse(false);
                        final Boolean isCallBot = content.startsWith("hey mathew");
                        if (notFromBot) {
                            repository.setMessages(new ArrayList<>());
                            if (isCallBot) {
                                String author = msg.getAuthor().orElse(null).getUsername();
                                log.info("author : {}", author);
                                if (!repository.getAuthor().equals(author)) {
                                    repository.addToRepo(new com.discordbot.mathew.gpt.request.Message("system",
                                            "You are talking to a user named " + author));
                                    repository.setAuthor(author);
                                }
                            }
                        }
                        return notFromBot;
                    })
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage("Sorry, the conversation is too long. I will make new one and we can continue right away"))
                    .then();
        }
        return Mono.just(message)
                .filter(msg -> {
                    final Boolean notFromBot = msg.getAuthor()
                            .map(user -> !user.isBot())
                            .orElse(false);
                    final Boolean isCallBot = content.startsWith("hey mathew");
                    if (notFromBot) {
                        log.info("Repo size : {}", repository.getMessages().size());
                        if (isCallBot) {
                            String author = msg.getAuthor().orElse(null).getUsername();
                            log.info("author : {}", author);
                            if (!repository.getAuthor().equals(author)) {
                                repository.addToRepo(new com.discordbot.mathew.gpt.request.Message("system",
                                        "You are talking to a user named " + author));
                                repository.setAuthor(author);
                            }
                        }
                    }
                    return notFromBot && isCallBot;
                })
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(gpt.responseConversation(content
                        .replaceAll("(hey mathew,)|(hey mathew)","").trim())))
                .then();
    }


}

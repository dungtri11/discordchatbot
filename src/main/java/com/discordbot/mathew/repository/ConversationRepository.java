package com.discordbot.mathew.repository;

import com.discordbot.mathew.gpt.request.Message;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
@Setter
public class ConversationRepository {
    private List<Message> messages = new ArrayList<>();
    private String author = "Unknown";
    public List<Message> addToRepo(Message message) {
        this.messages.add(message);
        return messages;
    }

}

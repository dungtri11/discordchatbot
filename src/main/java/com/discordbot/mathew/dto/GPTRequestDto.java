package com.discordbot.mathew.dto;

import com.discordbot.mathew.gpt.request.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GPTRequestDto {
    private String model;
    private List<Message> messages;
}

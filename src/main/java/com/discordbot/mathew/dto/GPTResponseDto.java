package com.discordbot.mathew.dto;

import com.discordbot.mathew.gpt.response.Choice;
import com.discordbot.mathew.gpt.response.Usage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GPTResponseDto {
    private String id;
    private String object;
    private String created;
    private String model;
    private List<Choice> choices;
    private Usage usage;
}

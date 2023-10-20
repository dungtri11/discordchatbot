package com.discordbot.mathew.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Choice {
    private Long index;
    private Message message;
    @JsonProperty("finish_reason")
    private String finishReason;
}

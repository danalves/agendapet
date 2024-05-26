package com.danalves.agendapet.dto;

import com.danalves.agendapet.model.Animal;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AwsSqsMessageReceived(
        String TopicArn,
        String Message) {
}

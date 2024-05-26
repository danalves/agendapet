package com.danalves.agendapet.event;

import com.danalves.agendapet.model.Animal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnimalCreatedEvent {
    private Animal animal;
}

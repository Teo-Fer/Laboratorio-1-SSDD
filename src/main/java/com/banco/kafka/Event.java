package com.banco.kafka;

import com.banco.model.Movement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Event<Movement> {
    @Builder.Default
    public final EventType type = null;
    @Builder.Default
    public final String serverId = null;
    @Builder.Default
    public final Movement movement = null;
}

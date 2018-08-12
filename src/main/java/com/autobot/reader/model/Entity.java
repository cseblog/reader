package com.autobot.reader.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by trunghuynh on 8/11/18.
 */

public class Entity {
    @Getter @Setter private EntityType type;
    @Getter @Setter String text;
    @Getter @Setter String link;

    public Entity(EntityType type, String text, String link) {
        this.type = type;
        this.text = text;
        this.link = link;
    }

}

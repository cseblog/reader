package com.autobot.reader.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Created by trunghuynh on 8/11/18.
 */
public class Post {
    @Getter @Setter private String title;

    @Getter @Setter private String summary;

    @Getter @Setter private ArrayList<Entity> body;

    @Getter @Setter private String source;
}

package com.basecamp.ice_and_fire.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CharacterParser {
    @JsonProperty(value = "allegiances")
    private List<String> urls;
}

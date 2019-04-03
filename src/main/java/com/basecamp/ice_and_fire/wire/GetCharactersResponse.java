package com.basecamp.ice_and_fire.wire;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class GetCharactersResponse {
    private Map<String, UUID> nameToId = new HashMap<>();
}

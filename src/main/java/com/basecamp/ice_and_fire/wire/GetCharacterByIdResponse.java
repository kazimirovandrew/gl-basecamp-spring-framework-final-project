package com.basecamp.ice_and_fire.wire;

import com.basecamp.ice_and_fire.domain.House;
import lombok.Data;

import java.util.Iterator;
import java.util.List;

@Data
public class GetCharacterByIdResponse {
    private String message;

    public void setMessage(String name, List<House> houses) {

        StringBuilder builder = new StringBuilder();

        builder.append(name);

        if (houses.isEmpty()) {
            builder.append(" is a homeless");

        } else {
            Iterator<House> iterator = houses.iterator();

            while (iterator.hasNext()) {
                House house = iterator.next();

                builder.append(" from " + house.getName() + ",");

                if (house.getWords().isEmpty()) {
                    builder.append(" where there is no any slogan");

                } else {
                    builder.append(" where everyone says '" + house.getWords() + "'");
                }

                if (iterator.hasNext()) {
                    builder.append(" and");
                }
            }
        }

        message = builder.toString();
    }
}

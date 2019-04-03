package com.basecamp.ice_and_fire.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue()
    private UUID id;

    @Column(name = "url")
    private String url;

    @Column(name = "name")
    private String name;

    @Column(name = "words")
    private String words;

    @ManyToMany(mappedBy = "houses", cascade = CascadeType.ALL)
    private List<Character> characters = new ArrayList<>();
}

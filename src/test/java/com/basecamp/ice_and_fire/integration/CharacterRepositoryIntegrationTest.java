package com.basecamp.ice_and_fire.integration;

import com.basecamp.ice_and_fire.domain.Character;
import com.basecamp.ice_and_fire.repository.CharacterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CharacterRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CharacterRepository characterRepository;

    @Test
    public void givenName_whenFindByName_thenReturnCharacter() {
        final String NAME = "Jon Snow";

        //given
        Character character = new Character();
        character.setName(NAME);
        entityManager.persist(character);
        entityManager.flush();

        //when
        Character found = characterRepository
                .findByName(NAME)
                .get();

        //then
        assertEquals(character.getName(), found.getName());
    }
}

package com.basecamp.ice_and_fire.integration;

import com.basecamp.ice_and_fire.domain.House;
import com.basecamp.ice_and_fire.repository.HouseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class HouseRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private HouseRepository houseRepository;

    @Test
    public void givenUrl_whenFindByUrl_thenReturnHouse() {
        final String URL = "https://www.anapioficeandfire.com/api/houses/362";

        //given
        House house = new House();
        house.setUrl(URL);
        entityManager.persist(house);
        entityManager.flush();

        //when
        House found = houseRepository
                .findByUrl(URL)
                .get();

        //then
        assertEquals(house, found);
    }
}

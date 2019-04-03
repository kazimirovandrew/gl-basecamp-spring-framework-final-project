package com.basecamp.ice_and_fire.repository;

import com.basecamp.ice_and_fire.domain.House;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface HouseRepository extends CrudRepository<House, UUID> {
    Optional<House> findByUrl(String url);
}

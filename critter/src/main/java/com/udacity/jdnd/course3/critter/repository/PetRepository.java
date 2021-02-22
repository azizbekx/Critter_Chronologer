package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> getPetsByOwner_Id(Long ownerId);

    @Query("select p.owner from Pet p where p.id = :petId")
    Customer getOwnerForPet(Long petId);

}

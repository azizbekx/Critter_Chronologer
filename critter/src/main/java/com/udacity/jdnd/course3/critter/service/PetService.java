package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.notFound.ObjectNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PetService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PetRepository petRepository;

    public Pet save(Pet pet) {
        pet = petRepository.save(pet);
        return pet;
    }
    public Pet findById(long id) {
        return petRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Pet not found in ID : " + id));
    }

    /*
     * GET one Pet By Pet Id
     */
    public Pet getPetById(long petId) {
        return petRepository.getOne(petId);
    }
    /*
     * GET All Pets
     */
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
    public List<Pet> findAllById(List<Long>petId){
        return petRepository.findAllById(petId);
    }
    /*
     * GET Pets By Owner ID
     */
    public List<Pet> getPetsByOwnerId(long customerId) {
        return petRepository.findPetsByOwnerId(customerId);
    }
}

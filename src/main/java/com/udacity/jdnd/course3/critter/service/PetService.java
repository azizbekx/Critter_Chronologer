package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
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

    public Pet save(Pet pet, Long ownerId) {
        Customer customer = customerRepository.getOne(ownerId);
        pet.setCustomer(customer);
        pet = petRepository.save(pet);
        customer.addPet(pet);
        customerRepository.save(customer);
        return pet;
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
    /*
     * GET Pets By Owner ID
     */
    public List<Pet> getPetsByOwnerId(long customerId) {
        return petRepository.findPetsByOwnerId(customerId);
    }
}
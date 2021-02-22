package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {
    @Autowired
    PetRepository petRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerService customerService;

    public Pet savePet(Pet pet){
        Pet savedPet = petRepository.save(pet);

        Customer customer = savedPet.getOwner();
        List<Pet> customerPets = customer.getPets();

        if(customerPets == null){
            customerPets = new ArrayList<>();
        }

        customerPets.add(savedPet);
        customer.setPets(customerPets);
        customerRepository.save(customer);

        return savedPet;
//        return petRepository.save(pet);
    }
    public Pet getPet(Long petId){
        return petRepository.findById(petId)
                .orElseThrow(ResourceNotFoundException::new);
    }

    public List<Pet> getPets(){
        return petRepository.findAll();
    }

    public List<Pet> getPetsByOwner(Long ownerId){
        return petRepository.getPetsByOwner_Id(ownerId);
    }

    public List<Pet> getPetsBySchedule(Long scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(ResourceNotFoundException::new);

        return schedule.getPets();
    }


}

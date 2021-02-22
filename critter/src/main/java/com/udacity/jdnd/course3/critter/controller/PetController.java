package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.pet.PetDTO;
import com.udacity.jdnd.course3.critter.entities.pet.PetType;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private PetService petService;
    private CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }


    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet= convertPetDTOToPet(petDTO);

        return convertPetToPetDTO(petService.savePet(pet));
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertPetToPetDTO(petService.getPet(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){

        return petService.getPets()
                .stream()
                .map(this::convertPetToPetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        return petService.getPetsByOwner(ownerId)
                .stream()
                .map(this::convertPetToPetDTO)
                .collect(Collectors.toList());

    }


    private PetDTO convertPetToPetDTO(Pet pet){
        PetDTO petDTO = new PetDTO();

        BeanUtils.copyProperties(pet,petDTO);
        if (pet.getOwner()!=null){
            petDTO.setOwnerId(pet.getOwner().getId());
        }

        petDTO.setType(pet.getPetType());
        return petDTO;
    }
    private Pet convertPetDTOToPet(PetDTO petDTO){
        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setPetType(PetType.valueOf(petDTO.getType().name()));
        pet.setNotes(petDTO.getNotes());
        pet.setOwner(customerService.getCustomerById(petDTO.getOwnerId()));
        pet.setBirthDate(petDTO.getBirthDate());
        return pet;
    }


}

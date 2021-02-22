package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getOwnerByPet(long petId){
        return petRepository.getOwnerForPet(petId);
    }

    public Customer getCustomerById(long customerId){
       return customerRepository.findById(customerId)
               .orElseThrow(ResourceNotFoundException::new);
    }
}

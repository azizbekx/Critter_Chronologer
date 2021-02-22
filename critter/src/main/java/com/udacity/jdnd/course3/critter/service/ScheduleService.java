package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public Schedule createSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId){

        Pet pet = petRepository.findById(petId)
                .orElseThrow(ResourceNotFoundException::new);

        return scheduleRepository.getSchedulesByPetsContains(pet);
    }

    public List<Schedule> getScheduleForEmployee(long employeeId){
        Employee employee= employeeRepository.findById(employeeId)
                .orElseThrow(ResourceNotFoundException::new);

        return scheduleRepository.getSchedulesByEmployeesContains(employee);
    }

    public List<Schedule> getScheduleForCustomer(long petId){
        return scheduleRepository.findByPets_Id(petId);
    }

}

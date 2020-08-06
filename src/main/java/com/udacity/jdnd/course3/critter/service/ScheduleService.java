package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private ScheduleRepository scheduleRepository;
    private EmployeeRepository employeeRepository;
    private CustomerRepository customerRepository;
    private PetRepository petRepository;

    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleForPet(long petId) {
        return scheduleRepository.getDetailsByPet(petRepository.getOne(petId));

    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        return scheduleRepository.getDetailsByEmployee(employeeRepository.getOne(employeeId));
    }

    public List<Schedule> getScheduleForCustomer(long customerId) {
        return scheduleRepository.getDetailsByCustomer(customerRepository.getOne(customerId));

    }

}

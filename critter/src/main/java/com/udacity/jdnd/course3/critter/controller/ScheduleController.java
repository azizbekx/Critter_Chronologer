package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.entities.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    private PetService petService;
    private CustomerService customerService;
    private EmployeeService employeeService;
    private ScheduleService scheduleService;

    public ScheduleController(PetService petService, CustomerService customerService,
                              EmployeeService employeeService, ScheduleService scheduleService) {
        this.petService = petService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.scheduleService = scheduleService;
    }


    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        return convertScheduleToScheduleDTO(
                scheduleService.createSchedule(
                        convertScheduleDTOToSchedule(scheduleDTO)
                )
        );
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        return scheduleService.getAllSchedules()
                .stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        return scheduleService.getScheduleForPet(petId)
                .stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {

        return scheduleService.getScheduleForEmployee(employeeId)
                .stream()
                .map(this::convertScheduleToScheduleDTO)
                .collect(Collectors.toList());

    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> scheduleDTOS= new ArrayList<>();
        Customer customer = customerService.getCustomerById(customerId);
        List<Pet> pets = customer.getPets();

        ArrayList<Schedule> schedules = new ArrayList<>();

        for(Pet pet: pets){
            schedules.addAll(scheduleService.getScheduleForCustomer(pet.getId()));
        }
        return schedules.stream().map(this::convertScheduleToScheduleDTO).collect(Collectors.toList());
    }

    private ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        scheduleDTO.setActivities(schedule.getEmployeeSkills());

          scheduleDTO.setPetIds(petService.getPetsBySchedule(
                    schedule.getId())
                    .stream()
                    .map(this::getPetId)
                    .collect(Collectors.toList()));

            scheduleDTO.setEmployeeIds(
                    employeeService.getEmployeesBySchedule(
                            schedule.getId())
                            .stream().map(this::getEmployeeId)
                    .collect(Collectors.toList()));

        return scheduleDTO;
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO){
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO,schedule);

        schedule.setEmployeeSkills(scheduleDTO.getActivities());

            List<Employee> employees = new LinkedList<>();

            for (Long employeeId : scheduleDTO.getEmployeeIds()) {
                employees.add(employeeService.getEmployeeById(employeeId));
            }
            schedule.setEmployees(employees);

            List<Pet> pets = new LinkedList<>();

            for (Long petId : scheduleDTO.getPetIds()) {
                pets.add(petService.getPet(petId));
            }
            schedule.setPets(pets);

        return schedule;

    }

    private Long getPetId(Pet pet) {
        return pet.getId();
    }
    private Long getEmployeeId(Employee employee) {
        return employee.getId();
    }
}

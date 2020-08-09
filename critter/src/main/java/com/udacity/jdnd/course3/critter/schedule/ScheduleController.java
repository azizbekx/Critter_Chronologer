package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private PetService petService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        List<Employee> employee = userService.findAllById(scheduleDTO.getEmployeeIds());
        List<Pet> pet = petService.findAllById(scheduleDTO.getPetIds());
        Schedule schedule = new Schedule();

        BeanUtils.copyProperties(scheduleDTO, schedule);
        schedule.setEmployee(employee);
        schedule.setPet(pet);

        scheduleService.createSchedule(schedule);

        employee.forEach(thisEmployee -> {
            if (thisEmployee.getSchedules() == null)
                thisEmployee.setSchedules(new ArrayList<>());

            thisEmployee.getSchedules().add(schedule);
        });

        pet.forEach(thisPet -> {
            if (thisPet.getSchedules() == null)
                thisPet.setSchedules(new ArrayList<>());

            thisPet.getSchedules().add(schedule);
        });

        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return schedules.stream().map(this::setScheduleDTO).collect(Collectors.toList());

    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<Schedule> schedules = petService.findById(petId).getSchedules();

        return setHashScheduleDTO(schedules);
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        Employee employee = userService.getEmployeeById(employeeId);
        if (employee.getSchedules() == null) return null;
        List<Schedule> schedules = employee.getSchedules();

        return schedules.stream().map(this::setScheduleDTO).collect(Collectors.toList());

    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<Pet> pets = userService.findById(customerId).getPets();
        HashMap<Long, Schedule> scheduleHashMap = new HashMap<>();

        pets.stream().forEach(pet -> {
            pet.getSchedules().stream().forEach(thisSchedule -> {
                scheduleHashMap.put(thisSchedule.getId(), thisSchedule);
            });
        });
        return setHashScheduleDTO(new ArrayList<>(scheduleHashMap.values()));
    }

    private ScheduleDTO setScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule,scheduleDTO);
        List<Long> employeeId = schedule.getEmployee().stream().map(Employee::getId).collect(Collectors.toList());
        List<Long> petId = schedule.getPet().stream().map(Pet::getId).collect(Collectors.toList());
        scheduleDTO.setEmployeeIds(employeeId);
        scheduleDTO.setPetIds(petId);


        return scheduleDTO;
    }

    private List<ScheduleDTO> setHashScheduleDTO(List<Schedule> schedules) {
        return schedules.stream().map(thisSchedule -> {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            BeanUtils.copyProperties(thisSchedule, scheduleDTO);

            scheduleDTO.setEmployeeIds(thisSchedule.getEmployee().stream().map(Employee::getId).collect(Collectors.toList()));
            scheduleDTO.setPetIds(thisSchedule.getPet().stream().map(Pet::getId).collect(Collectors.toList()));

            return scheduleDTO;

        }).collect(Collectors.toList());
    }
}

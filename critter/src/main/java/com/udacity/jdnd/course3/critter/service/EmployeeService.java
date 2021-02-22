package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.entities.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;


import java.time.DayOfWeek;
import java.util.*;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ScheduleRepository scheduleRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(long employeeId) {
        return employeeRepository.getOne(employeeId);
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        Employee employee1;
        if (employee.isPresent()) {
            employee1 = employee.get();
            employee1.setDaysAvailable(daysAvailable);
            employeeRepository.save(employee1);
        }


    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, DayOfWeek dayAvailable) {
        List<Employee> employees= employeeRepository.findAllBySkillsInAndDaysAvailableContains(skills, dayAvailable);

        List<Employee> employeeList = new LinkedList<>();

        employees.forEach(thisEmployee -> {
            if(thisEmployee.getSkills().containsAll(skills)){ employeeList.add(thisEmployee); }
        });
        return employeeList;
    }

    public List<Employee> getEmployeesBySchedule(Long scheduleId){
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(ResourceNotFoundException::new);
        return schedule.getEmployees();
    }

}

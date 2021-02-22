package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.entities.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entities.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> findAllBySkillsInAndDaysAvailableContains(Set<EmployeeSkill> skills, DayOfWeek daysAvailable);

    @Query("select e.schedules from Employee e where e.id  = :employeeId")
    List<Schedule> getSchedulesForEmployee(Long employeeId);

}


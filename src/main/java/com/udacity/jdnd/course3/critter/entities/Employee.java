package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
@Table
public class Employee {
    @Id
    @GeneratedValue
    private Long id;

    @Nationalized
    private String name;

    @ElementCollection
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<DayOfWeek> daysAvailable;

    @ElementCollection
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<EmployeeSkill> skills;

    public Employee() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> dayOfWeeks) {
        this.daysAvailable = dayOfWeeks;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }
}

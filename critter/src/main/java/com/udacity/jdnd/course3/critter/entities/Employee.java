package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Entity
public class Employee {
    //    @Id
//    @GeneratedValue
//    private Long id;
//
//    @Nationalized
//    private String name;
//
//    @ElementCollection
//    @JoinTable(name = "employee_days", joinColumns = @JoinColumn(name = "employee_id"))
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private Set<DayOfWeek> daysAvailable;
//
//    @ElementCollection
//    @JoinTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
//    @Column(name = "skills", nullable = false)
//    @Enumerated(EnumType.STRING)
//    @Cascade(org.hibernate.annotations.CascadeType.ALL)
//    private Set<EmployeeSkill> skills;
//
//    @ManyToMany(mappedBy = "employee")
//    private List<Schedule> schedules;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(length = 64)
    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @JoinTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "skill", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @JoinTable(name = "employee_days", joinColumns = @JoinColumn(name = "employee_id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<DayOfWeek> daysAvailable;

    @ManyToMany(mappedBy = "employee")
    private List<Schedule> schedules;

    public Employee() {
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
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

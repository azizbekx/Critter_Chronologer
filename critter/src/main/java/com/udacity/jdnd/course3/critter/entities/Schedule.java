package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.entities.user.EmployeeSkill;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schudele_employee",
            joinColumns = {@JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "employee_id")}
    )
    private List<Employee> employees;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "schudele_customer",
            joinColumns = {@JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "customer_id")}
    )
    private List<Customer> customers;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "schedule_pet",
            joinColumns = {@JoinColumn(name = "schedule_id")},
            inverseJoinColumns = {@JoinColumn(name = "pet_id")}
    )
    private List<Pet> pets;



    @ElementCollection(targetClass = EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> employeeSkills;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getEmployeeSkills() {
        return employeeSkills;
    }

    public void setEmployeeSkills(Set<EmployeeSkill> employeeSkills) {
        this.employeeSkills = employeeSkills;
    }
}

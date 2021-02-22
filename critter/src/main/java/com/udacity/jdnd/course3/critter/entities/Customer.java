package com.udacity.jdnd.course3.critter.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(length = 50)
    private String name;
    private String phoneNumber;
    private String notes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner",
            cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Pet> pets;

    @ManyToMany(mappedBy = "customers")
    private List<Schedule> schedules;

    public Customer() {
    }

    public Customer(Long id, String name, String phoneNumber, String notes, List<Pet> pets) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.pets = pets;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}

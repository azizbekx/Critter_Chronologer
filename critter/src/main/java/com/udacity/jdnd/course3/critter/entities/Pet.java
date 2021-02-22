package com.udacity.jdnd.course3.critter.entities;

import com.udacity.jdnd.course3.critter.entities.pet.PetType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    @Column(length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    private PetType petType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Customer owner;

    private LocalDate birthDate;
    private String notes;

    @ManyToMany(mappedBy = "pets")
    private List<Schedule> schedules;



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

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}

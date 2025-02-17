package com.exercise.client_service.domain;

import com.exercise.client_service.domain.enums.PersonGender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Long personId;

    @Column(nullable = false)
    String name;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    PersonGender gender;

    Long age;

    @Column(length = 50, nullable = false, unique = true)
    @Size(max = 50)
    String identification;

    @Column(length = 200)
    @Size(max = 200)
    String address;

    @Column(length = 20)
    @Size(max = 20)
    String phone;

    public Person() {
    }

    public Person(String name, PersonGender gender, Long age, String identification, String address, String phone) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.identification = identification;
        this.address = address;
        this.phone = phone;
    }
}

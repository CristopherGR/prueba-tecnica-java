package com.exercise.client_service.domain;

import com.exercise.client_service.domain.enums.PersonGender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonGender getGender() {
        return gender;
    }

    public void setGender(PersonGender gender) {
        this.gender = gender;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personId, person.personId) && Objects.equals(age, person.age) && Objects.equals(name, person.name) && Objects.equals(gender, person.gender) && Objects.equals(identification, person.identification) && Objects.equals(address, person.address) && Objects.equals(phone, person.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, name, gender, age, identification, address, phone);
    }

    @Override
    public String toString() {
        return "Person{" +
                "person_id=" + personId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", identification='" + identification + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

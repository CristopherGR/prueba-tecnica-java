package com.exercise.client_service.domain;

import com.exercise.client_service.domain.enums.PersonGender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
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
}

package com.exercise.client_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "client")
@Getter
@Setter
@ToString(exclude = "password")
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Client extends Person {

    @Column(unique = true, nullable = false, length = 19)
    @Size(max = 19)
    @EqualsAndHashCode.Include
    String clientId;

    @Column(length = 100, nullable = false)
    @Size(max = 100)
    String password;

    Boolean status;
}


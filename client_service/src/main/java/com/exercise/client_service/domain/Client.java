package com.exercise.client_service.domain;

import com.exercise.client_service.domain.enums.PersonGender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "client")
@Getter
@Setter
@ToString(exclude = "password")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Client extends Person {

    @Column(unique = true, nullable = false, length = 19)
    @Size(max = 19)
    @EqualsAndHashCode.Include
    String clientId;

    @Column(length = 100, nullable = false)
    @Size(max = 100)
    String password;

    boolean status;

    public Client() {
        super();
    }

    public Client(String clientId, String password, boolean status, String name, PersonGender gender, Long age, String identification, String address, String phone) {
        super(name, gender, age, identification, address, phone);
        this.clientId = clientId;
        this.password = password;
        this.status = status;
    }
}

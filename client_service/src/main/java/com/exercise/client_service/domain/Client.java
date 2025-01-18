package com.exercise.client_service.domain;

import com.exercise.client_service.domain.enums.PersonGender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "client")
public class Client extends Person {

    @Column(unique = true, nullable = false, length = 19)
    @Size(max = 19)
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        Client client = (Client) obj;
        return Objects.equals(clientId, client.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), clientId);
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId='" + clientId + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", person=" + super.toString() +
                '}';
    }
}

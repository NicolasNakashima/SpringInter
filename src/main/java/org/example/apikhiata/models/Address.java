package org.example.apikhiata.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashSet;
import java.util.Set;

@Entity
@Schema(description = "Representa o endereço do usuário")
@Table(name = "adress")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do endereço")
    private int id;

    @NotNull(message = "O nome do destinatário não pode ser null")
    @Size(min = 1, max = 60, message = "O nome do destinatário não pode ter mais que 60 caracteres")
    @Schema(description = "Nome do destinatário")
    private String recipient;

    @NotNull(message = "A rua não pode ser null")
    @Size(min = 1, max = 60, message = "A rua não pode ter mais que 60 caracteres")
    @Schema(description = "Nome da rua")
    private String street;

    @NotNull(message = "O número não pode ser null")
    @Schema(description = "Número da casa ou apartamento")
    private int number;

    @Size(max = 50, message = "O complemento não pode ter mais que 50 caracteres")
    @Schema(description = "Complemento do endereço")
    private String complement;

    @Size(max = 50, message = "O rótulo não pode ter mais que 50 caracteres")
    @Schema(description = "Rótulo do endereço", example = "Casa, Trabalho")
    private String label;

    @JsonIgnore
    @ManyToMany(mappedBy = "addresses")
    private Set<User> users = new HashSet<>();

    // Construtor vazio
    public Address() {
    }

    // Construtor com parâmetros
    public Address(int id,String recipient, String street, int number, String complement, String label) {
        this.id = id;
        this.recipient = recipient;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.label = label;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}

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
    @Schema(description = "ID do endereço", example = "1")
    private int id;

    @NotNull(message = "O estado não pode ser null")
    @Size(min = 1, max = 100, message = "O estado não pode ter mais que 60 caracteres")
    @Schema(description = "Nome do estado", example = "Santa Catarina")
    private String state;

    @NotNull(message = "O país não pode ser null")
    @Size(min = 1, max = 100, message = "O país não pode ter mais que 60 caracteres")
    @Schema(description = "Nome do país", example = "Brasil")
    private String country;

    @NotNull(message = "A rua não pode ser null")
    @Size(min = 1, max = 60, message = "A rua não pode ter mais que 60 caracteres")
    @Schema(description = "Nome da rua", example = "Av. Paulista")
    private String street;

    @NotNull(message = "O número não pode ser null")
    @Schema(description = "Número da casa ou apartamento", example = "123")
    private int number;

    @Size(max = 50, message = "O complemento não pode ter mais que 50 caracteres")
    @Schema(description = "Complemento do endereço", example = "Perto do Oba Hortifruit")
    private String complement;

    @Size(max = 50, message = "O rótulo não pode ter mais que 50 caracteres")
    @Schema(description = "Rótulo do endereço", example = "Casa, Trabalho")
    private String label;

    @Size(max = 8, message = "O CEP não pode ter mais que 8 caracteres")
    @Schema(description = "CEP do endereço", example = "12345678")
    private String cep;

    @Schema(description = "Se o endereço está inativo", example = "true")
    boolean deactivate;

    @JsonIgnore
    @ManyToMany(mappedBy = "addresses")
    private Set<User> users = new HashSet<>();

    // Construtor vazio
    public Address() {
    }

    // Construtor com parâmetros

    public Address(int id, String state, String country, String street, int number, String complement, String label, String cep, boolean deactivate) {
        this.id = id;
        this.state = state;
        this.country = country;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.label = label;
        this.cep = cep;
        this.deactivate = deactivate;
    }


    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public boolean isDeactivate() {
        return deactivate;
    }

    public void setDeactivate(boolean deactivate) {
        this.deactivate = deactivate;
    }
}

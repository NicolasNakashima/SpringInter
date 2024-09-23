package org.example.apikhiata.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Schema(description = "Representa o gênero do usuário")
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID do gênero")
    private int id;

    @NotNull(message = "O gênero não pode ser null")
    @Size(min = 1, max = 60, message = "O gênero não pode ter mais que 60 caracteres")
    @Schema(description = "Descrição do gênero", example = "Masculino")
    private String gender;

    // Construtor vazio
    public Gender() {
    }

    // Construtor com parâmetros
    public Gender(int id, String gender) {
        this.id = id;
        this.gender = gender;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

package org.example.apikhiata.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = true)
    @NotNull(message = "O nome não pode ser nulo")
    @Size(min = 1, max = 60, message = "O nome deve ter entre 1 e 60 caracteres")
    @Schema(description = "Nome do usuário", example = "Joaquim", required = true)
    private String name;

    @Column(name = "cpf", unique = true)
    @NotNull(message = "O CPF não pode ser nulo")
    @CPF(message = "CPF inválido")
    @Schema(description = "CPF do usuário", example = "111.222.333-44", required = true)
    private String cpf;

    @Column(name = "gender_id", nullable = false)
    @Schema(description = "Id do genero", example = "1", required = true)
    private int genderId;

    @NotNull(message = "A idade não pode ser nula")
    @Schema(description = "Idade do usuário", example = "30", required = true)
    private int age;

    @Column(name = "is_dressmaker")
    @Schema(description = "Se é um vendedor", example = "true", required = true)
    private boolean isDressmaker;

    @NotNull(message = "É necessário definir o status do usuário")
    @Column(name = "premium_status")
    @Schema(description = "Status do premium do usuário", example = "1", required = true)
    private int premiumStatus;

    @NotNull(message = "O telefone não pode ser nulo")
    @Schema(description = "Telefone do usuário", example = "11 99999-9999", required = true)
    private String phones;

    @Size(max = 100, message = "A URL da imagem não pode exceder 100 caracteres")
    @Column(name = "image_url")
    @Schema(description = "URL da imagem", example = "https://example.com/image.jpg")
    private String imageURL;

    @NotNull(message = "A senha não pode ser nula")
    @Size(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres")
    @Schema(description = "Senha do usuário", example = "abc12345678", required = true)
    private String password;

    @Column(name = "email", unique = true)
    @NotNull(message = "O e-mail não pode ser nulo")
    @Email(message = "E-mail inválido")
    @Size(max = 100, message = "O e-mail não pode exceder 100 caracteres")
    @Schema(description = "E-mail do usuário", example = "nicolas@example.com", required = true)
    private String email;

    @Size(max = 100, message = "A URL da foto de perfil não pode exceder 100 caracteres")
    @Column(name = "profile_picture_url")
    @Schema(description = "URL da imagem de perfil", example = "https://example.com/image.jpg")
    private String profilePictureUrl;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_adress",
            joinColumns = @JoinColumn(name = "pfk_user_id"),
            inverseJoinColumns = @JoinColumn(name = "pfk_adress_id")
    )
    private Set<Address> addresses = new HashSet<>();

    @Schema(description = "Avaliação do usuário", example = "4.5")
    private double avaliation;

    @Column(name = "is_admin")
    @Schema(description = "Se é um administrador", example = "true")
    private Boolean isAdmin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<UserPreference> userPreferences;

    // Construtor vazio
    public User() {
    }

    // Construtor completo
    public User(int id, String name, String cpf, int genderId, int age, boolean isDressmaker, int premiumStatus,
                String phones, String imageURL, String password, String email, String profilePictureUrl, double avaliation, Boolean isAdmin, Set<UserPreference> userPreferences) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.genderId = genderId;
        this.age = age;
        this.isDressmaker = isDressmaker;
        this.premiumStatus = premiumStatus;
        this.phones = phones;
        this.imageURL = imageURL;
        this.password = password;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
        this.avaliation = avaliation;
        this.isAdmin = isAdmin;
        this.userPreferences = userPreferences;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean getIsDressmaker() {
        return isDressmaker;
    }

    public void setIsDressmaker(boolean dressmaker) {
        this.isDressmaker = dressmaker;
    }

    public int getPremiumStatus() {
        return premiumStatus;
    }

    public void setPremiumStatus(int premium) {
        premiumStatus = premium;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public double getAvaliation() {
        return avaliation;
    }

    public void setAvaliation(double avaliation) {
        this.avaliation = avaliation;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        this.isAdmin = admin;
    }

    public Set<UserPreference> getUserPreferences() {
        return userPreferences;
    }

    public void setUserPreferences(Set<UserPreference> userPreferences) {
        this.userPreferences = userPreferences;
    }
}
